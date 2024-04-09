package dev.tizwarp.ftbu;

import dev.tizwarp.ftbu.block.BlockIdeaTable;
import dev.tizwarp.ftbu.block.BlockResearchTable;
import dev.tizwarp.ftbu.criterion.TriggerInspect;
import dev.tizwarp.ftbu.criterion.TriggerRecipeLocked;
import dev.tizwarp.ftbu.criterion.TriggerTechnology;
import dev.tizwarp.ftbu.item.ItemMagnifyingGlass;
import dev.tizwarp.ftbu.item.ItemParchmentEmpty;
import dev.tizwarp.ftbu.item.ItemParchmentIdea;
import dev.tizwarp.ftbu.item.ItemParchmentResearch;
import dev.tizwarp.ftbu.item.ItemResearchBook;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public final class Content {

	public static final String n_ideaTable = "idea_table";
	public static final String n_researchTable = "research_table";

	public static final String n_parchmentEmpty = "parchment_empty";
	public static final String n_parchmentIdea = "parchment_idea";
	public static final String n_parchmentResearch = "parchment_research";
	public static final String n_researchBook = "research_book";
	public static final String n_magnifyingGlass = "magnifying_glass";

	public static final Block b_ideaTable = new BlockIdeaTable(n_ideaTable);
	public static final Block b_researchTable = new BlockResearchTable(n_researchTable);

	public static final Item i_parchmentEmpty = new ItemParchmentEmpty(n_parchmentEmpty);
	public static final Item i_parchmentIdea = new ItemParchmentIdea(n_parchmentIdea);
	public static final Item i_parchmentResearch = new ItemParchmentResearch(n_parchmentResearch);
	public static final Item i_researchBook = new ItemResearchBook(n_researchBook);
	public static final Item i_magnifyingGlass = new ItemMagnifyingGlass(n_magnifyingGlass);

	public static final TriggerTechnology c_technologyUnlocked = new TriggerTechnology("technology_unlocked");
	public static final TriggerTechnology c_technologyResearched = new TriggerTechnology("technology_researched");
	public static final TriggerRecipeLocked c_itemLocked = new TriggerRecipeLocked("recipe_locked");
	public static final TriggerInspect c_inspect = new TriggerInspect("block_inspected");

	static final ItemBlock i_ideaTable = new ItemBlock(b_ideaTable);
	static final ItemBlock i_researchTable = new ItemBlock(b_researchTable);

}
