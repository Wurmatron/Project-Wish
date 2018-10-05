package wish.wurmatron;

import static wish.wurmatron.api.WishAPI.oreRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.api.WishItems;
import wish.wurmatron.common.CommonProxy;
import wish.wurmatron.common.blocks.ProjectWishBlocks;
import wish.wurmatron.common.events.OreEvents;
import wish.wurmatron.common.events.StoneEvents;
import wish.wurmatron.common.items.ProjectWishItems;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.registry.WishOreRegistry;

@Mod(modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

  @Mod.Instance(Global.MODID)
  public static ProjectWish instance;

  @SidedProxy(serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
  public static CommonProxy proxy;

  public static Logger logger;

  public static final CreativeTabs tabBlocks = new CreativeTabs("wishBlocks") {
    @Override
    public ItemStack getTabIconItem() {
      return new ItemStack(WishBlocks.stoneIgneous, 1, 3);
    }
  };

  public static final CreativeTabs tabOre = new CreativeTabs("wishOre") {
    @Override
    public ItemStack getTabIconItem() {
      return new ItemStack(WishItems.oreDrops.get(0), 1, 0);
    }
  };

  @Mod.EventHandler
  public void onPreInit(FMLPreInitializationEvent e) {
    logger = e.getModLog();
    oreRegistry = new WishOreRegistry();
    ((WishOreRegistry) oreRegistry).loadAllOres();
    ProjectWishBlocks.registerBlocks();
    ProjectWishItems.registerItems();
    MinecraftForge.EVENT_BUS.register(new Registry());
    MinecraftForge.EVENT_BUS.register(new OreEvents());
    MinecraftForge.EVENT_BUS.register(new StoneEvents());
    proxy.preInit();
  }

  @Mod.EventHandler
  public void onInit(FMLInitializationEvent e) {
    proxy.init();
  }

  @Mod.EventHandler
  public void onPostInit(FMLPostInitializationEvent e) {
    proxy.postInit();
  }
}
