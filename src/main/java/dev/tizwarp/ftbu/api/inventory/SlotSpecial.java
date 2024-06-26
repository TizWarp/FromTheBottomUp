package dev.tizwarp.ftbu.api.inventory;

import java.util.function.Predicate;

import dev.tizwarp.ftbu.api.FTBUAPI;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSpecial extends Slot {

	private final Predicate<ItemStack> special;
	private final int limit;

	public SlotSpecial(IInventory inventory, int index, int x, int y, int limit, Predicate<ItemStack> special) {
		super(inventory, index, x, y);
		this.special = special;
		this.limit = limit;
	}

	public SlotSpecial(IInventory inventory, int index, int x, int y, int limit, Iterable<ItemStack> special) {
		this(inventory, index, x, y, limit, item -> {
			for (ItemStack s : special)
				if (FTBUAPI.stackUtils.isStackOf(s, item))
					return true;
			return false;
		});
	}

	public SlotSpecial(IInventory inventory, int index, int x, int y, int limit, ItemStack special) {
		this(inventory, index, x, y, limit, item -> FTBUAPI.stackUtils.isStackOf(special, item));
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return special == null || special.test(stack);
	}

	@Override
	public int getSlotStackLimit() {
		return limit;
	}

}
