package wish.wurmatron.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import wish.wurmatron.ProjectWish;

public class ItemOre extends Item {

  public final int SIZES = 5;

  public ItemOre(String name) {
    setUnlocalizedName("itemore" + name);
    setCreativeTab(ProjectWish.tabOre);
    setMaxStackSize(32);
    setHasSubtypes(true);
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == getCreativeTab()) {
      for (int meta = 0; meta < SIZES; meta++) {
        items.add(new ItemStack(this, 1, meta));
      }
    }
  }

}
