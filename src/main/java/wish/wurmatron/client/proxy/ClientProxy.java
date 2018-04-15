package wish.wurmatron.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.BlockOre;
import wish.wurmatron.common.entity.EntityThrowingRock;
import wish.wurmatron.common.items.*;
import wish.wurmatron.common.proxy.CommonProxy;
import wish.wurmatron.common.utils.Registry;

/**
 Client-Side Proxy
 */
public class ClientProxy extends CommonProxy {

	private static void createModel (Block block,int meta,String name) {
		ModelLoader.setCustomModelResourceLocation (Registry.blockItems.get (block),meta,new ModelResourceLocation (Global.MODID + ":" + name,"inventory"));
	}

	private static void createModel (Item item,String name) {
		createModel (item,0,name);
	}

	private static void createModel (Item item,int meta,String name) {
		ModelLoader.setCustomModelResourceLocation (item,meta,new ModelResourceLocation (Global.MODID + ":" + name,"inventory"));
	}


	@Override
	public void preInit () {
		MinecraftForge.EVENT_BUS.register (new ClientProxy ());
	}

	@Override
	public void init () {
		RenderingRegistry.registerEntityRenderingHandler (EntityThrowingRock.class,new RenderSnowball <EntityThrowingRock> (Minecraft.getMinecraft ().getRenderManager (),WishItems.itemRock,Minecraft.getMinecraft ().getRenderItem ()));
	}

	@SubscribeEvent
	public void modelBakeEvent (ModelRegistryEvent e) {
		for (StoneType type : StoneType.values ()) {
			if (type.getType () == StoneType.RockType.Metamorphic) {
				createModel (WishBlocks.stoneMetamorphic,type.getId (),"stone_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.cobbleMetamorphic,type.getId (),"cobble_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.smoothMetamorphic,type.getId (),"smooth_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.brickMetamorphic,type.getId (),"brick_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.chiselMetamorphic,type.getId (),"chisel_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.sandMetamorphic,type.getId (),"sand_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.gravelMetamorphic,type.getId (),"gravel_" + type.getName ().toLowerCase ());
			}
			if (type.getType () == StoneType.RockType.Sedimentary) {
				createModel (WishBlocks.stoneSedimentary,type.getId (),"stone_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.cobbleSedimentary,type.getId (),"cobble_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.smoothSedimentary,type.getId (),"smooth_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.brickSedimentary,type.getId (),"brick_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.chiselSedimentary,type.getId (),"chisel_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.sandSedimentary,type.getId (),"sand_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.gravelSedimentary,type.getId (),"gravel_" + type.getName ().toLowerCase ());
			}
			if (type.getType () == StoneType.RockType.Igneous) {
				createModel (WishBlocks.stoneIgneous,type.getId (),"stone_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.cobbleIgneous,type.getId (),"cobble_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.smoothIgneous,type.getId (),"smooth_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.brickIgneous,type.getId (),"brick_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.chiselIgneous,type.getId (),"chisel_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.sandIgneous,type.getId (),"sand_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.gravelIgneous,type.getId (),"gravel_" + type.getName ().toLowerCase ());
			}
		}
		for (int index = 0; index < 9; index++) {
			createModel (WishBlocks.rockIgneous,index,"rockIgneous_" + StoneType.getRockFromMeta (StoneType.RockType.Igneous,index));
			createModel (WishBlocks.rockMetamorphic,index,"rockMetamorphic_" + StoneType.getRockFromMeta (StoneType.RockType.Metamorphic,index));
			createModel (WishBlocks.rockSedimentary,index,"rockSedimentary_" + StoneType.getRockFromMeta (StoneType.RockType.Sedimentary,index));
			createModel (WishBlocks.stick,index,"stick");
		}
		for (OreType ore : OreType.values ())
			for (int index = 0; index < BlockOre.getOreNames (ore).length; index++)
				createModel (Registry.blockOre.get (ore),index,Registry.blockOre.get (ore).getUnlocalizedName ().substring (5) + "_" + BlockOre.getOreNames (ore)[index]);
		for (Item item : Registry.items)
			if (item instanceof ItemGem) {
				for (int meta = 0; meta < GemType.GRADE.values ().length; meta++)
					createModel (item,meta,item.getUnlocalizedName ().substring (5) + "_" + ItemGem.getGrade (meta));
			} else if (item instanceof ItemRock) {
				for (int meta = 0; meta < StoneType.values ().length; meta++)
					createModel (item,meta,"rock" + StoneType.values ()[meta]);
			} else if (item.getUnlocalizedName ().contains ("itemOre")) {
				for (int meta = 0; meta < 5; meta++)
					createModel (item,meta,item.getUnlocalizedName ().substring (5) + "_" + meta);
			} else
				createModel (item,item.getUnlocalizedName ().substring (5));
		for (int index = 0; index < ItemMeta.metaItems.length; index++)
			createModel (WishItems.itemMeta,index,ItemMeta.metaItems[index]);
		for (int index = 0; index < OreType.values ().length; index++)
			createModel (WishItems.dustOre,index,"dust" + OreType.values ()[index].getName ());
		for (Item item : ItemSludge.validSludge)
			for (int index = 0; index < ItemSludge.WEIGHTS.length; index++)
				createModel (item,index,item.getUnlocalizedName ().substring (5) + "_" + index);
		for (Item item : ItemShard.valid)
			for (int index = 0; index < ItemShard.WEIGHTS.length; index++)
				createModel (item,index,item.getUnlocalizedName ().substring (5) + "_" + index);
		for (int index = 0; index < ItemCrystal.metaItems.length; index++)
			createModel (WishItems.crystalOre,index,"crystal" + ItemCrystal.metaItems[index]);
	}
}
