package wish.wurmatron.common.items;

import java.util.ArrayList;
import net.minecraft.item.Item;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.WishItems;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.items.ore.ItemOre;
import wish.wurmatron.common.registry.Registry;

public class ProjectWishItems extends WishItems {


  public static void registerItems() {
    createAndRegisterOresDrops();
  }

  private static void createAndRegisterOresDrops () {
    WishItems.oreDrops = new ArrayList<>();
    for (Ore ore : WishAPI.oreRegistry.getOres ()) {
      Item  item = new ItemOre(ore.getUnlocalizedName());
      Registry.registerItem(item, "itemore" + ore.getUnlocalizedName(),ore);
      WishItems.oreDrops.add(item);
    }
  }

}
