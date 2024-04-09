package dev.tizwarp.ftbu.criterion;

import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import dev.tizwarp.ftbu.FTBU;
import dev.tizwarp.ftbu.api.util.predicate.BlockPredicate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class TriggerInspect extends TriggerFTBU<TriggerInspect.Instance> {

	public TriggerInspect(String id) {
		super(new ResourceLocation(FTBU.MODID, id));
	}

	@Override
	public Instance deserializeInstance(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
		return new Instance(BlockPredicate.deserialize(jsonObject), JsonUtils.getBoolean(jsonObject, "success", true));
	}

	public void trigger(EntityPlayerMP player, BlockPos pos, IBlockState state, boolean success) {
		Set<Listener<Instance>> listeners = this.listeners.get(player.getAdvancements());
		if (listeners != null) {
			WorldServer world = player.getServerWorld();
			for (Listener<Instance> listener : listeners)
				if (listener.getCriterionInstance().test(world, pos, state, success))
					listener.grantCriterion(player.getAdvancements());
		}
	}

	public class Instance extends TriggerFTBU.Instance {

		private final BlockPredicate block;
		private final boolean success;

		public Instance(BlockPredicate block, boolean success) {
			this.block = block;
			this.success = success;
		}

		public boolean test(WorldServer world, BlockPos pos, IBlockState state, boolean success) {
			return block.test(world, pos, state) && this.success == success;
		}

	}

}
