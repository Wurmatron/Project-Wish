package wish.wurmatron.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.ProjectWish;

public class ItemMeta extends Item {

  public static final String[] metaItems = new String[]{"sharpRock"};

  public ItemMeta() {
    setUnlocalizedName("itemMeta");
    setCreativeTab(ProjectWish.Items);
    hasSubtypes = true;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == ProjectWish.Items) {
      for (int index = 0; index < metaItems.length; index++) {
        items.add(new ItemStack(this, 1, index));
      }
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    return I18n.translateToLocal(
        "item." + metaItems[stack.getItemDamage() < metaItems.length ? stack.getItemDamage() : 0]
            + ".name");
  }
}
