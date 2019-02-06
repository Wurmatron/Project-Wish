package wish.wurmatron;

import static wish.wurmatron.api.WishAPI.gemRegistry;
import static wish.wurmatron.api.WishAPI.oreRegistry;
import static wish.wurmatron.common.registry.Registry.blockOre;
import static wish.wurmatron.common.registry.Registry.gemItems;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.api.WishItems;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.api.rock.gem.Gem.GRADE;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.CommonProxy;
import wish.wurmatron.common.blocks.ProjectWishBlocks;
import wish.wurmatron.common.blocks.stone.BrickWish;
import wish.wurmatron.common.blocks.stone.CobbleWish;
import wish.wurmatron.common.blocks.stone.StoneWish;
import wish.wurmatron.common.blocks.terra.GravelWish;
import wish.wurmatron.common.blocks.terra.SandWish;
import wish.wurmatron.common.blocks.utils.BlockRockType;
import wish.wurmatron.common.entity.EntityThrowingRock;
import wish.wurmatron.common.events.GemEvents;
import wish.wurmatron.common.events.OreEvents;
import wish.wurmatron.common.events.RandomizeRockTypeStandardEvent;
import wish.wurmatron.common.events.WishOreGenerator;
import wish.wurmatron.common.events.WishWorldGenerator;
import wish.wurmatron.common.farming.CropEvent;
import wish.wurmatron.common.items.ProjectWishItems;
import wish.wurmatron.common.network.GuiHandler;
import wish.wurmatron.common.network.NetworkHandler;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.registry.WishGemRegistry;
import wish.wurmatron.common.registry.WishOreRegistry;

@Mod(modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

  @Mod.Instance(Global.MODID)
  public static ProjectWish instance;

  @SidedProxy(serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
  public static CommonProxy proxy;

  public static Logger logger;
  public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

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

    @Override
    public boolean hasSearchBar() {
      return true;
    }
  };

  public static final CreativeTabs tabMisc = new CreativeTabs("wishMisc") {
    @Override
    public ItemStack getTabIconItem() {
      return new ItemStack(WishItems.gems.get(0), 1, 0);
    }
  };

  public static RandomizeRockTypeStandardEvent randomizeRockType;

  @Mod.EventHandler
  public void onPreInit(FMLPreInitializationEvent e) {
    logger = e.getModLog();
    oreRegistry = new WishOreRegistry();
    gemRegistry = new WishGemRegistry();
    ((WishOreRegistry) oreRegistry).loadAllOres();
    ((WishGemRegistry) gemRegistry).loadAllGems();
    ProjectWishBlocks.registerBlocks();
    ProjectWishItems.registerItems();
    MinecraftForge.EVENT_BUS.register(new Registry());
    MinecraftForge.EVENT_BUS.register(new OreEvents());
    MinecraftForge.EVENT_BUS.register(new GemEvents());
    MinecraftForge.EVENT_BUS.register(new CropEvent());
    if (!Loader.isModLoaded("cubicchunks")) {
      MinecraftForge.EVENT_BUS.register(randomizeRockType = new RandomizeRockTypeStandardEvent());
      GameRegistry.registerWorldGenerator(new WishWorldGenerator(), 0);
      GameRegistry.registerWorldGenerator(new WishOreGenerator(), 0);
    } else {
      // TODO CubicChunk Stone Randomizer
    }
    EntityRegistry.registerModEntity(new ResourceLocation(Global.MODID, "throwingRock"),
        EntityThrowingRock.class, "throwingRock", 0, instance, 64, 10, true);
    proxy.preInit();
  }

  @Mod.EventHandler
  public void onInit(FMLInitializationEvent e) {
    NetworkHandler.registerPackets();
    NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    proxy.init();
    if (ConfigHandler.addOreDictionaryEntries) {
      for (Block block : Registry.blocks) {
        if (block instanceof BlockRockType) {
          for (int index = 0; index < 9; index++) {
            if (block instanceof StoneWish) {
              OreDictionary.registerOre("stone", new ItemStack(block, 1, index));
            } else if (block instanceof CobbleWish) {
              OreDictionary.registerOre("cobblestone", new ItemStack(block, 1, index));
            } else if (block instanceof BrickWish) {
              OreDictionary.registerOre("bricksStone", new ItemStack(block, 1, index));
            } else if(block instanceof SandWish) {
              OreDictionary.registerOre("sand", new ItemStack(block, 1, index));
              OreDictionary.registerOre("blockSand", new ItemStack(block, 1, index));
            } else if(block instanceof GravelWish) {
              OreDictionary.registerOre("gravel", new ItemStack(block, 1, index));
              OreDictionary.registerOre("blockGravel", new ItemStack(block, 1, index));
            }
          }
        }
      }
    }
  }

  @Mod.EventHandler
  public void onPostInit(FMLPostInitializationEvent e) {
    proxy.postInit();
    for (Ore o : oreRegistry.getOres()) {
      WishOreGenerator.addOreGen(o, blockOre.get(o).getDefaultState(),
          o.getGenerationStyle().getMaxiumOre(), o.getMinMaxHeight()[0], o.getMinMaxHeight()[1],
          o.getGenerationStyle().getChance());
      OreDictionary
          .registerOre(o.getOreEntry(),
              blockOre.get(WishAPI.oreRegistry.getOreFromName(o.getUnlocalizedName())));
    }
    for (Gem gem : gemRegistry.getGems()) {
      for (int index = 0; index < GRADE.values().length; index++) {
        OreDictionary.registerOre("gem" + (index + 1), new ItemStack(gemItems.get(gem), 1, index));
      }
    }
  }
}
