package dev.tizwarp.ftbu.util;

import dev.tizwarp.ftbu.packet.PacketDispatcher;
import dev.tizwarp.ftbu.packet.client.TechnologyMessage;
import dev.tizwarp.ftbu.technology.Technology;
import dev.tizwarp.ftbu.technology.TechnologyManager;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;

public final class ListenerTechnology<T extends ICriterionInstance> extends ICriterionTrigger.Listener<T> {

	private final Technology technology;
	private final String name;

	public ListenerTechnology(T instance, Technology technology, String name) {
		super(instance, null, null);
		this.technology = technology;
		this.name = name;
	}

/*	@Override
	public void grantCriterion(PlayerAdvancements playerAdvancements) {
		EntityPlayerMP player = playerAdvancements.;

		if (TechnologyManager.INSTANCE.contains(technology))
			technology.grantCriterion(player, name);
		PacketDispatcher.sendTo(new TechnologyMessage(player, true), player);
	}*/

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof ListenerTechnology && (this.getCriterionInstance().equals(((ListenerTechnology) obj).getCriterionInstance()) && this.technology == ((ListenerTechnology) obj).technology);
	}

	@Override
	public int hashCode() {
		int i = getCriterionInstance().hashCode();
		i = 31 * i + technology.hashCode();
		i = 31 * i + name.hashCode();
		return i;
	}

}
