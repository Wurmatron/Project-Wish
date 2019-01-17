package wish.wurmatron.common.items.crafting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.ProjectWish;

public class ItemDust extends Item {

  private final String[] metaItems;

  public ItemDust(String[] items) {
    setCreativeTab(ProjectWish.tabMisc);
    setHasSubtypes(true);
    this.metaItems = items;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == ProjectWish.tabMisc) {
      for (int index = 0; index < metaItems.length; index++) {
        items.add(new ItemStack(this, 1, index));
      }
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    if (stack.getItemDamage() < metaItems.length) {
      return I18n.translateToLocal(metaItems[stack.getItemDamage()]) + " " + I18n
          .translateToLocal("item.dust.name");
    }
    return "item.null.name";
  }
}
