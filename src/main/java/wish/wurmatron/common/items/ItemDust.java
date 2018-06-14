package wish.wurmatron.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;

public class ItemDust extends Item {

  public ItemDust() {
    setCreativeTab(ProjectWish.Items);
    setUnlocalizedName("dust");
    hasSubtypes = true;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == ProjectWish.Items) {
      for (int index = 0; index < OreType.values().length; index++) {
        items.add(new ItemStack(this, 1, index));
      }
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    return I18n.translateToLocal(
        "item.dust" + OreType.values()[stack.getItemDamage() < OreType.values().length ? stack
            .getItemDamage() : 0].getName() + ".name");
  }
}
