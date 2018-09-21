package wish.wurmatron.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.items.blocks.ItemBlockOreType;
import wish.wurmatron.common.items.blocks.ItemBlockRockType;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Global.MODID)
public class Registry {

	public static List<Item> items = new ArrayList <> ();
	public static List <Block> blocks = new ArrayList <> ();
	public static HashMap <Block, Item> blockItems = new HashMap <> ();
	public static HashMap <Ore, Block> blockOre = new HashMap <> ();
	public static HashMap <Ore, Item> itemOre = new HashMap <> ();
	public static HashMap <Gem, Item> gemItems = new HashMap <> ();

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

	public static Block registerBlock (Block block,String registryName,Ore type) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (registryName);
		ItemBlockOreType itemBlock = new ItemBlockOreType (block,type);
		registerItem (itemBlock,registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
		blockOre.put (type,block);
		return block;
	}

	public static Item registerItem (Item item,String registryName,Ore type) {
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
		blocks.add (block);
		blockItems.put (block,itemBlock);
		return block;
	}

	public static Item registerGem (Item item,String registryName,Gem gem) {
		registerItem (item,registryName);
		gemItems.putIfAbsent (gem,item);
		return item;
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