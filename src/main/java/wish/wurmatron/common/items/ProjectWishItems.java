package wish.wurmatron.common.items;

import java.util.ArrayList;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.WishItems;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.items.armor.ItemGogglesMining;
import wish.wurmatron.common.registry.Registry;

public class ProjectWishItems extends WishItems {

  public static void registerItems() {
    createAndRegisterOresDrops();
    createAndRegisterGems();
    helmetMining = new ItemGogglesMining(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.HEAD);
    Registry.registerItem(helmetMining, helmetMining.getUnlocalizedName().substring(5));
  }

  private static void createAndRegisterOresDrops() {
    WishItems.oreDrops = new ArrayList<>();
    for (Ore ore : WishAPI.oreRegistry.getOres()) {
      Item item = new ItemOre(ore.getUnlocalizedName());
      Registry.registerItem(item, "itemore" + ore.getUnlocalizedName(), ore);
      WishItems.oreDrops.add(item);
    }
  }

  private static void createAndRegisterGems() {
    WishItems.gems = new ArrayList<>();
    for (Gem gem : WishAPI.gemRegistry.getGems()) {
      Item item = new ItemGem(gem);
      Registry.registerGem(item, "gem" + gem.getUnlocalizedName(), gem);
      WishItems.gems.add(item);
    }
  }

}
