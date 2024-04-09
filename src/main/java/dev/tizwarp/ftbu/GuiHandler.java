package dev.tizwarp.ftbu;

import dev.tizwarp.ftbu.inventory.ContainerIdeaTable;
import dev.tizwarp.ftbu.inventory.ContainerResearchTable;
import dev.tizwarp.ftbu.technology.TechnologyManager.GUI;
import dev.tizwarp.ftbu.tileentity.TileEntityIdeaTable;
import dev.tizwarp.ftbu.tileentity.TileEntityResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		if (tileEntity != null) {
			if (id == GUI.IDEATABLE.ordinal()) {
				return new ContainerIdeaTable((TileEntityIdeaTable) tileEntity, player.inventory);
			} else if (id == GUI.RESEARCHTABLE.ordinal()) {
				return new ContainerResearchTable((TileEntityResearchTable) tileEntity, player.inventory);
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
