package wish.wurmatron.common.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.items.ItemBlockRockType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registry {

	public static List <Item> items = new ArrayList <> ();
	public static List <Block> blocks = new ArrayList <> ();
	public static HashMap <Block, Item> blockItems = new HashMap <> ();

	public static void registerItem (Item item,String registryName) {
		item.setRegistryName (registryName);
		item.setUnlocalizedName (item.getRegistryName ().toString ());
		items.add (item);
	}

	public static Block registerBlock (Block block,String registryName,StoneType.RockType type) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (block.getRegistryName ().toString ());
		Item itemBlock = new ItemBlockRockType (block,type).setUnlocalizedName (block.getUnlocalizedName ());
		itemBlock.setRegistryName (registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
		return block;
	}

	public static void registerBlock (Block block,String registryName) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (block.getRegistryName ().toString ());
		ItemBlock itemBlock = new ItemBlock (block);
		itemBlock.setRegistryName (registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
	}

	@SubscribeEvent
	public void registerBlocks (RegistryEvent.Register <Block> e) {
		e.getRegistry ().registerAll (blocks.toArray (new Block[0]));
	}

	@SubscribeEvent
	public void registerItems (RegistryEvent.Register <Item> e) {
		e.getRegistry ().registerAll (items.toArray (new Item[0]));
		e.getRegistry ().registerAll (blockItems.values ().toArray (new Item[0]));
	}
}
