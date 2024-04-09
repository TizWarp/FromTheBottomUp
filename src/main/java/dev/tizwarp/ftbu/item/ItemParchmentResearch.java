package dev.tizwarp.ftbu.item;

import dev.tizwarp.ftbu.Content;
import dev.tizwarp.ftbu.packet.PacketDispatcher;
import dev.tizwarp.ftbu.packet.client.TechnologyMessage;
import dev.tizwarp.ftbu.technology.Technology;
import dev.tizwarp.ftbu.util.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemParchmentResearch extends Item {

	public ItemParchmentResearch(String name) {
		setTranslationKey(name);
		setMaxStackSize(1);
		setContainerItem(Content.i_parchmentEmpty);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		return new ActionResult<>(EnumActionResult.SUCCESS, research(hand == EnumHand.MAIN_HAND ? player.getHeldItemMainhand() : player.getHeldItemOffhand(), player, true));
	}

	public ItemStack research(ItemStack item, EntityPlayer player, boolean already) {
		if (!player.world.isRemote) {
			Technology t = StackUtils.INSTANCE.getTechnology(item);
			if (t != null) {
				if (t.isResearched(player)) {
					if (already)
						player.sendMessage(new TextComponentTranslation("technology.complete.already", t.getDisplayText()));
				} else {
					if (t.canResearchIgnoreCustomUnlock(player)) {
						t.setResearched(player, true);

						PacketDispatcher.sendTo(new TechnologyMessage(player, true, t), (EntityPlayerMP) player);
						return new ItemStack(Content.i_parchmentEmpty);
					} else
						player.sendMessage(new TextComponentTranslation("technology.complete.understand"));
				}
			}
		}
		return item;
	}

}
