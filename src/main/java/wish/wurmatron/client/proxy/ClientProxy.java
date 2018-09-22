package wish.wurmatron.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.CommonProxy;
import wish.wurmatron.common.blocks.TileOre;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.utils.WishOre;

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

    @SubscribeEvent
    public void modelBakeEvent (ModelRegistryEvent e) {
        for (Block block : Registry.blocks) {
            if (block instanceof TileOre) {
                String[] metaData = ((WishOre) (((TileOre) block).type)).getNames ();
                for (int meta = 0; meta < metaData.length; meta++)
                    createModel (block,meta,block.getUnlocalizedName ().substring (5)+"_" +  metaData[meta]);
            }
        }
    }
}
