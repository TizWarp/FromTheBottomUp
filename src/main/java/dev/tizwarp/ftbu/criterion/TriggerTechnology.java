package dev.tizwarp.ftbu.criterion;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.tizwarp.ftbu.FTBU;
import dev.tizwarp.ftbu.technology.Technology;
import dev.tizwarp.ftbu.technology.TechnologyManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class TriggerTechnology extends TriggerFTBU<TriggerTechnology.Instance> {

	public TriggerTechnology(String id) {
		super(new ResourceLocation(FTBU.MODID, id));
	}

	@Override
	public Instance deserializeInstance(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
		Technology technology = null;
		if (jsonObject.has("technology")) {
			String name = JsonUtils.getString(jsonObject, "technology");
			technology = TechnologyManager.INSTANCE.getTechnology(new ResourceLocation(name));
			if (technology == null)
				throw new JsonSyntaxException("Unknown technology '" + name + "'");
		}
		return new Instance(technology);
	}

	public void trigger(EntityPlayerMP player, Technology technology) {
		Set<Listener<Instance>> listeners = this.listeners.get(player.getAdvancements());
		if (listeners != null)
			for (Listener<Instance> listener : listeners)
				if (listener.getCriterionInstance().test(technology))
					listener.grantCriterion(player.getAdvancements());
	}

	public class Instance extends TriggerFTBU.Instance {

		@Nullable
		private final Technology technology;

		public Instance(@Nullable Technology technology) {
			this.technology = technology;
		}

		public boolean test(Technology technology) {
			return this.technology == null || this.technology.equals(technology);
		}

	}

}
