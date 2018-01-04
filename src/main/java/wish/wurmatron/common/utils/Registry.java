package wish.wurmatron.common.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.api.world.TreeType;
import wish.wurmatron.common.items.ItemBlockOreType;
import wish.wurmatron.common.items.ItemBlockRockType;
import wish.wurmatron.common.items.ItemBlockTreeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registry {

	public static List <Item> items = new ArrayList <> ();
	public static List <Block> blocks = new ArrayList <> ();
	public static HashMap <Block, Item> blockItems = new HashMap <> ();
	public static HashMap <OreType, Block> blockOre = new HashMap <> ();
	public static HashMap <OreType, Item> itemOre = new HashMap <> ();
	public static HashMap <TreeType, Block[]> treeBlock = new HashMap <> ();

	public static void registerItem (Item item,String registryName) {
		item.setRegistryName (registryName);
		item.setUnlocalizedName (registryName);
		items.add (item);
	}

	public static Block registerBlock (Block block,String registryName,StoneType.RockType type) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (registryName);
		ItemBlockRockType itemBlock = new ItemBlockRockType (block,type);
		registerItem (itemBlock,registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
		return block;
	}

	public static Block registerBlock (Block block,String registryName,OreType type) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (registryName);
		ItemBlockOreType itemBlock = new ItemBlockOreType (block,type);
		registerItem (itemBlock,registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
		blockOre.put (type,block);
		return block;
	}

	public static Block registerBlock (Block block,String registryName,TreeType[] type) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (registryName);
		ItemBlockTreeType itemBlock = new ItemBlockTreeType (block,type);
		registerItem (itemBlock,registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
		for (TreeType a : type)
			if (treeBlock.get (a) != null) {
				if (block.getUnlocalizedName ().contains ("log"))
					treeBlock.get (a)[0] = block;
				else if (block.getUnlocalizedName ().contains ("planks"))
					treeBlock.get (a)[1] = block;
				else if (block.getUnlocalizedName ().contains ("leaf"))
					treeBlock.get (a)[2] = block;
				else if (block.getUnlocalizedName ().contains ("sapling"))
					treeBlock.get (a)[3] = block;
			} else {
				Block[] treeBlocks = new Block[4];
				if (block.getUnlocalizedName ().contains ("log"))
					treeBlocks[0] = block;
				else if (block.getUnlocalizedName ().contains ("planks"))
					treeBlocks[1] = block;
				else if (block.getUnlocalizedName ().contains ("leaf"))
					treeBlocks[2] = block;
				else if (block.getUnlocalizedName ().contains ("sapling"))
					treeBlocks[3] = block;
				treeBlock.put (a,treeBlocks);
			}
		return block;
	}

	public static Item registerItem (Item item,String registryName,OreType type) {
		item.setRegistryName (registryName);
		item.setUnlocalizedName (registryName);
		items.add (item);
		itemOre.put (type,item);
		return item;
	}

	public static Block registerBlock (Block block,String registryName) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (registryName);
		ItemBlock itemBlock = new ItemBlock (block);
		itemBlock.setRegistryName (registryName);
		itemBlock.setCreativeTab (ProjectWish.BLOCKS);
		blocks.add (block);
		blockItems.put (block,itemBlock);
		return block;
	}

	@SubscribeEvent
	public void registerBlocks (RegistryEvent.Register <Block> e) {
		e.getRegistry ().registerAll (blocks.toArray (new Block[0]));
	}

	@SubscribeEvent
	public void registerItems (RegistryEvent.Register <Item> e) {
		e.getRegistry ().registerAll (items.toArray (new Item[0]));
	}
}
