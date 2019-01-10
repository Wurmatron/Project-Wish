package wish.wurmatron;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.fml.common.Mod;
import wish.wurmatron.api.Global;

@Mod.EventBusSubscriber (modid = Global.MODID)
@Config (modid = Global.MODID)
public class ConfigHandler {

  @Comment("Height of a rock / stone layer")
  public static int yLayerHeight = 40;

  @Comment("Remove other mods ore?")
  public static boolean overrideOre = true;

  @Comment("How rare are gems")
  public static int gemRarity = 1000;

}
