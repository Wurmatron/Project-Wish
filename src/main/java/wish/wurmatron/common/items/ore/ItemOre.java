package wish.wurmatron.common.items.ore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemOre extends Item {

  public final int SIZES = 5;

  public ItemOre(String name) {
    setUnlocalizedName("itemore" + name);
    setCreativeTab(CreativeTabs.COMBAT);
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
