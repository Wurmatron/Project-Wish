package wish.wurmatron.common.config;


import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.Local;
import wish.wurmatron.common.utils.LogHandler;

@Mod.EventBusSubscriber(modid = Global.MODID)
@Config(modid = Global.MODID, name = "ProjectWish/Global")
public class ConfigHandler {

  // General
  @Config.LangKey(Local.CONFIG_DEBUG)
  public static boolean debug = false;
  @Config.LangKey(Local.CONFIG_OREDICT)
  public static boolean oreDictionary = true;
  @Config.LangKey(Local.CONFIG_FUN)
  public static boolean fun = true;
  @Config.LangKey(Local.CONFIG_ORE_ITEMS)
  public static boolean oreItems = false;

  // WorldGen
  @Config.LangKey(Local.CONFIG_REPLACE_WORLDGEN)
  public static boolean replaceWorldGen = true;
  @Config.LangKey(Local.CONFIG_GEM_RARITY)
  public static int gemRarity = 1000;
  @Config.LangKey(Local.CONFIG_ORE_RARITY)
  public static int oreRarity = 5;
  @Config.LangKey(Local.CONFIG_ROCKS_PER_CHUNK)
  public static int rocksPerChunk = 10;
  @Config.LangKey(Local.CONFIG_STICKS_PER_CHUNK)
  public static int sticksPerChunk = 20;
  @Config.LangKey(Local.CONFIG_OVERRIDE_ORE)
  public static boolean overrideOre = true;
  @Config.LangKey(Local.CONFIG_SMALL_VEIN)
  public static int smallClusterMaxSize = 75;
  @Config.LangKey(Local.CONFIG_LARGE_VEIN)
  public static int largeClusterMaxSize = 150;
  @Config.LangKey(Local.CONFIG_HUGE_VEIN)
  public static int hugeClusterMaxSize = 300;

  // Farming
  @Config.LangKey(Local.CONFIG_FARMING_SPEED)
  public static float cropGrowth = .5f;

  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(Global.MODID)) {
      ConfigManager.load(Global.MODID, Config.Type.INSTANCE);
      LogHandler.info("Config Saved!");
    }
  }
}
