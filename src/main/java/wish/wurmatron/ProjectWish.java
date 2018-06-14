package wish.wurmatron;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.OreType.GenType;
import wish.wurmatron.common.blocks.WishModBlocks;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.entity.EntityThrowingRock;
import wish.wurmatron.common.events.WishWorldGenerator;
import wish.wurmatron.common.events.WorldEvents;
import wish.wurmatron.common.farming.CropEvent;
import wish.wurmatron.common.items.WishModItems;
import wish.wurmatron.common.proxy.CommonProxy;
import wish.wurmatron.common.utils.Registry;
import wish.wurmatron.common.world.DimTransferEvent;
import wish.wurmatron.common.world.RandomizeRockTypeEvent;

import java.io.File;
import java.lang.reflect.Field;

@Mod(modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

  public static final CreativeTabs BLOCKS = new CreativeTabs("blocks") {
    @Override
    public ItemStack getTabIconItem() {
      return new ItemStack(WishBlocks.stoneIgneous, 1, 3);
    }

    @Override
    public boolean hasSearchBar() {
      return true;
    }
  };
  public static final CreativeTabs Items = new CreativeTabs("items") {
    @Override
    public ItemStack getTabIconItem() {
      return new ItemStack(WishItems.gemBloodstone, 1, 1);
    }

    @Override
    public boolean hasSearchBar() {
      return true;
    }
  };
  @Mod.Instance(Global.MODID)
  public static ProjectWish instance;
  @SidedProxy(serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
  public static CommonProxy proxy;

  public static RandomizeRockTypeEvent randRockType;

  @Mod.EventHandler
  public void onPreInit(FMLPreInitializationEvent e) {
    MinecraftForge.EVENT_BUS.register(new Registry());
    WishModBlocks.registerBlocks();
    WishModItems.registerItems();
    proxy.preInit();
    EntityRegistry.registerModEntity(new ResourceLocation(Global.MODID, "throwingRock"),
        EntityThrowingRock.class, "throwingRock", 0, instance, 64, 10, true);
  }

  @Mod.EventHandler
  public void onInit(FMLInitializationEvent e) {
    proxy.init();
    MinecraftForge.EVENT_BUS.register(new CropEvent());
    MinecraftForge.EVENT_BUS.register(new WorldEvents());
    MinecraftForge.EVENT_BUS.register(new DimTransferEvent());
    randRockType = new RandomizeRockTypeEvent();
    if (ConfigHandler.replaceWorldGen) {
      MinecraftForge.EVENT_BUS.register(randRockType);
    }
    MinecraftForge.ORE_GEN_BUS.register(new WorldEvents());
    for (Block block : Registry.blocks) {
      if (block.getUnlocalizedName().contains("stone")) {
        OreDictionary.registerOre("stone", block);
      } else if (block.getUnlocalizedName().contains("cobble")) {
        OreDictionary.registerOre("cobblestone", block);
      } else if (block.getUnlocalizedName().contains("sand")) {
        OreDictionary.registerOre("sand", block);
      } else if (block.getUnlocalizedName().contains("gravel")) {
        OreDictionary.registerOre("gravel", block);
      }
    }
    for (GemType gem : GemType.values()) {
      for (int index = 0; index < 6; index++) {
        OreDictionary
            .registerOre("gem" + index, new ItemStack(Registry.gemItems.get(gem), 1, index));
      }
    }
    GameRegistry.registerWorldGenerator(new WishWorldGenerator(), 0);
  }

  @Mod.EventHandler
  public void onPostInit(FMLPostInitializationEvent e) {
    generateDefaultOreGeneration();
  }

  @Mod.EventHandler
  public void onServerLoading(FMLServerStartingEvent e) {
    DimTransferEvent
        .loadData(new File(Loader.instance().getConfigDir() + File.separator + Global.NAME));
  }

  private void generateDefaultOreGeneration() {
    for (OreType ore : OreType.values()) {
      try {
        Field field = WishBlocks.class.getDeclaredField("ore" + ore.getName());
        Block block = (Block) field.get(WishBlocks.oreAnthracite);
        int veinSize = 0;
        if (ore.getGenerationType() == GenType.HUGE_CLUSTER) {
          veinSize = ConfigHandler.hugeClusterMaxSize / ore.getRarity();
        } else if (ore.getGenerationType() == OreType.GenType.LARGE_CLUSTER) {
          veinSize = ConfigHandler.largeClusterMaxSize / ore.getRarity();
        } else if (ore.getGenerationType() == OreType.GenType.SMALL_CLUSTER) {
          veinSize = ConfigHandler.smallClusterMaxSize / ore.getRarity();
        } else if (ore.getGenerationType() == OreType.GenType.SINGLE) {
          veinSize = 1;
        }
        WishWorldGenerator
            .addOreGen(ore, block.getDefaultState(), veinSize, 0, 150, ore.getRarity() * 15);
      } catch (NoSuchFieldException | IllegalAccessException f) {
        f.printStackTrace();
      }
    }
  }
}
