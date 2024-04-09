package dev.tizwarp.ftbu.item;

import dev.tizwarp.ftbu.Content;
import net.minecraft.item.Item;

public class ItemParchmentIdea extends Item {

	public ItemParchmentIdea(String name) {
		setTranslationKey(name);
		setMaxStackSize(1);
		setContainerItem(Content.i_parchmentEmpty);
	}

}
