package wish.wurmatron.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import wish.wurmatron.ProjectWish;

public class ItemOre extends Item {

	public ItemOre (String name) {
		setUnlocalizedName ("itemOre" + name);
		setCreativeTab (ProjectWish.Items);
		setMaxStackSize (32);
		setHasSubtypes (true);
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> items) {
		if (tab == ProjectWish.Items)
			for (int meta = 0; meta < 3; meta++)
				items.add (new ItemStack (this,1,meta));
	}
}
