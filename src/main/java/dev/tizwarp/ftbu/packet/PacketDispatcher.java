package dev.tizwarp.ftbu.packet;

import dev.tizwarp.ftbu.packet.client.HintMessage;
import dev.tizwarp.ftbu.packet.client.HintMessage.HintMessageHandler;
import dev.tizwarp.ftbu.packet.client.TechnologyInfoMessage;
import dev.tizwarp.ftbu.packet.client.TechnologyInfoMessage.TechnologyInfoMessageHandler;
import dev.tizwarp.ftbu.packet.client.TechnologyMessage;
import dev.tizwarp.ftbu.packet.client.TechnologyMessage.TechnologyMessageHandler;
import dev.tizwarp.ftbu.packet.server.CopyTechMessage;
import dev.tizwarp.ftbu.packet.server.CopyTechMessage.CopyTechMessageHandler;
import dev.tizwarp.ftbu.packet.server.RequestMessage;
import dev.tizwarp.ftbu.packet.server.RequestMessage.RequestMessageHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class PacketDispatcher {

	private static byte packetId = 0;

	public static SimpleNetworkWrapper dispatcher;

	public static void registerPackets() {
		dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel("FTBU");

		PacketDispatcher.registerMessage(RequestMessageHandler.class, RequestMessage.class, Side.SERVER);
		PacketDispatcher.registerMessage(CopyTechMessageHandler.class, CopyTechMessage.class, Side.SERVER);

		PacketDispatcher.registerMessage(TechnologyMessageHandler.class, TechnologyMessage.class, Side.CLIENT);
		PacketDispatcher.registerMessage(TechnologyInfoMessageHandler.class, TechnologyInfoMessage.class, Side.CLIENT);
		PacketDispatcher.registerMessage(HintMessageHandler.class, HintMessage.class, Side.CLIENT);
	}

	@SuppressWarnings({"unchecked"})
	private static void registerMessage(Class handlerClass, Class messageClass, Side side) {
		PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {
		PacketDispatcher.dispatcher.sendTo(message, player);
	}

	public static void sendToAll(IMessage message) {
		PacketDispatcher.dispatcher.sendToAll(message);
	}

	public static void sendToServer(IMessage message) {
		PacketDispatcher.dispatcher.sendToServer(message);
	}

}
