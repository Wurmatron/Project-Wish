package wish.wurmatron.common.intergration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.registry.Registry;

@JEIPlugin
public class WishPlugin implements IModPlugin {

  @Override
  public void register(IModRegistry registry) {
    for (Ore ore : WishAPI.oreRegistry.getOres()) {
      for (int index = 0; index < 9; index++) {
        registry
            .addDescription(new ItemStack(Registry.blockOre.get(ore), 1, index),
                I18n.translateToLocal(
                    "jei.unlocalizedName.name").replaceAll("%NAME%", ore.getUnlocalizedName()),
                I18n.translateToLocal("jei.rockType.name").replaceAll("%TYPES%", getRockType(ore)));
      }
    }
  }

  private static String getRockType(Ore ore) {
    StringBuilder builder = new StringBuilder();
    boolean added = false;
    for (RockType type : ore.getRockTypes()) {
      builder.append(type.toString());
      added = true;
    }
    if (!added) {
      for (StoneType type : ore.getStoneTypes()) {
        builder.append(type.toString());
      }
    }
    return builder.toString();
  }
}
