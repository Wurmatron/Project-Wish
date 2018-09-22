package wish.wurmatron;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.CommonProxy;
import wish.wurmatron.common.blocks.ProjectWishBlocks;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.registry.WishOreRegistry;

import static wish.wurmatron.api.WishAPI.oreRegistry;

@Mod(modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

  @Mod.Instance(Global.MODID)
  public static ProjectWish instance;

  @SidedProxy(serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
  public static CommonProxy proxy;

  public static Logger logger;

  @Mod.EventHandler
  public void onPreInit(FMLPreInitializationEvent e) {
    logger = e.getModLog ();
    oreRegistry = new WishOreRegistry ();
    ((WishOreRegistry) oreRegistry).loadAllOres ();
    ProjectWishBlocks.registerBlocks ();
    MinecraftForge.EVENT_BUS.register (new Registry ());
    proxy.preInit();
  }

  @Mod.EventHandler
  public void onInit(FMLInitializationEvent e) {
    proxy.init();
  }

  @Mod.EventHandler
  public void onPostInit(FMLPostInitializationEvent e) {
      proxy.postInit ();
  }
}
