package dev.tizwarp.ftbu.criterion;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.tizwarp.ftbu.FTBU;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class TriggerRecipeLocked extends TriggerFTBU<TriggerRecipeLocked.Instance> {

	public TriggerRecipeLocked(String id) {
		super(new ResourceLocation(FTBU.MODID, id));
	}

	@Override
	public Instance deserializeInstance(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
		IRecipe recipe = null;
		if (jsonObject.has("recipe")) {
			ResourceLocation location = new ResourceLocation(JsonUtils.getString(jsonObject, "recipe"));
			recipe = CraftingManager.getRecipe(location);
			if (recipe == null)
				throw new JsonSyntaxException("Unknown recipe '" + location + "'");
		}
		return new Instance(recipe, ItemPredicate.deserialize(jsonObject.get("item")));
	}

	public void trigger(EntityPlayerMP player, @Nullable IRecipe recipe, ItemStack stack) {
		Set<Listener<Instance>> listeners = this.listeners.get(player.getAdvancements());
		if (listeners != null)
			for (Listener<Instance> listener : listeners)
				if (listener.getCriterionInstance().test(recipe, stack))
					listener.grantCriterion(player.getAdvancements());
	}

	public class Instance extends TriggerFTBU.Instance {

		@Nullable
		private final IRecipe recipe;
		private final ItemPredicate stack;

		public Instance(@Nullable IRecipe recipe, ItemPredicate stack) {
			this.recipe = recipe;
			this.stack = stack;
		}

		public boolean test(@Nullable IRecipe recipe, ItemStack stack) {
			return (this.recipe == null || this.recipe.equals(recipe)) && this.stack.test(stack);
		}

	}

}
