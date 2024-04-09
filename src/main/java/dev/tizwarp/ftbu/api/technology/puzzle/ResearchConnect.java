package dev.tizwarp.ftbu.api.technology.puzzle;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.tizwarp.ftbu.api.FTBUAPI;
import dev.tizwarp.ftbu.api.technology.ITechnology;
import dev.tizwarp.ftbu.api.technology.recipe.IPuzzle;
import dev.tizwarp.ftbu.api.technology.recipe.IResearchRecipe;
import dev.tizwarp.ftbu.api.util.BlockSerializable;
import dev.tizwarp.ftbu.api.util.JsonContextPublic;
import dev.tizwarp.ftbu.api.util.predicate.ItemPredicate;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ResearchConnect implements IResearchRecipe {

	public final ItemPredicate left;
	public final ItemPredicate right;
	private ITechnology technology;

	public ResearchConnect(ItemPredicate left, ItemPredicate right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean inspect(BlockSerializable block, List<BlockSerializable> inspected) {
		return false;
	}

	@Override
	public IPuzzle createInstance() {
		return new PuzzleConnect(this);
	}

	@Override
	public ITechnology getTechnology() {
		return technology;
	}

	@Override
	public void setTechnology(ITechnology tech) {
		this.technology = tech;
	}

	public static class Factory implements IResearchRecipe.Factory<ResearchConnect> {

		private static ItemPredicate getStack(JsonElement json, String name, JsonContextPublic context) {
			JsonObject object;
			if (json.isJsonPrimitive()) {
				object = new JsonObject();
				object.addProperty("item", JsonUtils.getString(json, name));
				object.addProperty("data", 0);
			} else
				object = JsonUtils.getJsonObject(json, name);
			return FTBUAPI.stackUtils.getItemPredicate(object, context);
		}

		@Override
		public ResearchConnect deserialize(JsonObject object, JsonContextPublic context, ResourceLocation technology) {
			JsonElement left = object.get("left");
			if (left == null)
				throw new JsonSyntaxException("Missing left, expected to find a string or a JsonObject");
			JsonElement right = object.get("right");
			if (right == null)
				throw new JsonSyntaxException("Missing right, expected to find a string or a JsonObject");
			return new ResearchConnect(getStack(left, "left", context), getStack(right, "right", context));
		}

	}

}
