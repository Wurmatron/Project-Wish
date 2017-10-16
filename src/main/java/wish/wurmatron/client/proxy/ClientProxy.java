package wish.wurmatron.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.proxy.CommonProxy;
import wish.wurmatron.common.utils.Registry;

/**
 Client-Side Proxy
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit () {
		MinecraftForge.EVENT_BUS.register (new ClientProxy ());
	}

	@Override
	public void init () {
	}

	@SubscribeEvent
	public void modelBakeEvent (ModelRegistryEvent e) {
		for (StoneType type : StoneType.values ())
			if (type.getType () == StoneType.RockType.Sedimentary) {
				createModel (WishBlocks.stoneSedimentary,type.getId (),"stone_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.cobbleSedimentary,type.getId (),"cobble_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.smoothSedimentary,type.getId (),"smooth_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.brickSedimentary,type.getId (),"brick_" + type.getName ().toLowerCase ());
			} else if (type.getType () == StoneType.RockType.Metamorphic) {
				createModel (WishBlocks.stoneMetamorphic,type.getId (),"stone_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.cobbleMetamorphic,type.getId (),"cobble_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.smoothMetamorphic,type.getId (),"smooth_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.brickMetamorphic,type.getId (),"brick_" + type.getName ().toLowerCase ());
			} else if (type.getType () == StoneType.RockType.Igneous) {
				createModel (WishBlocks.stoneIgneous,type.getId (),"stone_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.cobbleIgneous,type.getId (),"cobble_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.smoothIgneous,type.getId (),"smooth_" + type.getName ().toLowerCase ());
				createModel (WishBlocks.brickIgneous,type.getId (),"brick_" + type.getName ().toLowerCase ());
			}
	}

	private static void createModel (Block block,int meta,String name) {
		ModelLoader.setCustomModelResourceLocation (Registry.blockItems.get (block),meta,new ModelResourceLocation (Global.MODID + ":" + name,"inventory"));
	}

	private static void createModel (Item item,String name) {
		ModelLoader.setCustomModelResourceLocation (item,0,new ModelResourceLocation (Global.MODID + ":" + name,"inventory"));
	}
}
