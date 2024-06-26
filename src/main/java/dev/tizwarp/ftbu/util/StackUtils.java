package dev.tizwarp.ftbu.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.tizwarp.ftbu.Content;
import dev.tizwarp.ftbu.api.FTBUAPI;
import dev.tizwarp.ftbu.api.technology.ITechnology;
import dev.tizwarp.ftbu.api.util.BlockSerializable;
import dev.tizwarp.ftbu.api.util.IStackUtils;
import dev.tizwarp.ftbu.api.util.JsonContextPublic;
import dev.tizwarp.ftbu.api.util.predicate.ItemCompound;
import dev.tizwarp.ftbu.api.util.predicate.ItemIngredient;
import dev.tizwarp.ftbu.api.util.predicate.ItemPredicate;
import dev.tizwarp.ftbu.item.ItemMagnifyingGlass;
import dev.tizwarp.ftbu.technology.Technology;
import dev.tizwarp.ftbu.technology.TechnologyManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.advancements.critereon.ItemPredicates;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.oredict.OreDictionary;

public class StackUtils implements IStackUtils {

	public static final StackUtils INSTANCE = new StackUtils();

	private static final Map<ResourceLocation, ItemPredicate.Factory> REGISTRY = new HashMap<>();

	static {
		FTBUAPI.stackUtils = INSTANCE;
	}

	public NBTTagCompound getItemData(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt;
	}

	@Override
	public boolean isStackOf(ItemStack ingredient, ItemStack stack) {
		return ingredient.getItem() == stack.getItem()
				&& (ingredient.getItemDamage() == OreDictionary.WILDCARD_VALUE
						|| ingredient.getItemDamage() == stack.getItemDamage())
				&& (!ingredient.hasTagCompound() || ItemStack.areItemStackTagsEqual(ingredient, stack));
	}

	@Override
	public boolean isEqual(ItemStack s1, ItemStack s2) {
		return s1.getItem() == s2.getItem() && s1.getItemDamage() == s2.getItemDamage()
				&& ItemStack.areItemStackTagsEqual(s1, s2);
	}

	@Override
	public ItemPredicate getItemPredicate(JsonElement element, JsonContextPublic context) {
		List<ItemPredicate> predicates = new LinkedList<>();

		if (element.isJsonPrimitive()) {
			String item = element.getAsString();
			if (item.startsWith("#")) {
				ItemPredicate constant = context.getConstant(item.substring(1));
				if (constant == null)
					throw new JsonSyntaxException("Predicate referenced invalid constant: " + item);
				return constant;
			}
			JsonObject object = new JsonObject();
			object.add("item", element);
			object.addProperty("data", OreDictionary.WILDCARD_VALUE);
			return new ItemIngredient(CraftingHelper.getIngredient(object, context));
		}
		if (element.isJsonArray())
			for (JsonElement json : element.getAsJsonArray())
				predicates.add(getItemPredicate(json, context));
		else if (element.isJsonObject()) {
			JsonObject object = element.getAsJsonObject();
			if (!object.has("type") && object.has("item")) {
				String item = JsonUtils.getString(object, "item");
				if (item.startsWith("#")) {
					ItemPredicate constant = context.getConstant(item.substring(1));
					if (constant == null)
						throw new JsonSyntaxException("Predicate referenced invalid constant: " + item);
					return constant;
				}
			}
			if (object.has("type")) {
				ResourceLocation type = new ResourceLocation(context.appendModId(JsonUtils.getString(object, "type")));
				if (REGISTRY.containsKey(type))
					return REGISTRY.get(type).apply(object);
			}
			return new ItemIngredient(CraftingHelper.getIngredient(object, context));
		} else
			throw new JsonSyntaxException("Expected predicate to be an object or an array of objects");

		return new ItemCompound(predicates);
	}

	@Override
	public void registerItemPredicate(ResourceLocation location, ItemPredicate.Factory factory) {
		REGISTRY.put(location, factory);
		CraftingHelper.register(location, factory);
		ItemPredicates.register(location, factory.andThen());
	}

	@Override
	public ItemStack getParchment(ITechnology tech, Parchment type) {
		ItemStack stack = new ItemStack(type == Parchment.IDEA ? Content.i_parchmentIdea : Content.i_parchmentResearch);
		getItemData(stack).setString("FTGU", tech.getRegistryName().toString());
		return stack;
	}

	@Nullable
	@Override
	public Technology getTechnology(ItemStack parchment) {
		return TechnologyManager.INSTANCE.getTechnology(new ResourceLocation(getItemData(parchment).getString("FTGU")));
	}

	@Override
	public List<BlockSerializable> getInspected(ItemStack inspector) {
		return ItemMagnifyingGlass.getInspected(inspector);
	}

}
