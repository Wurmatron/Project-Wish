package wish.wurmatron.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.ProjectWish;

public class ItemCrystal extends Item {

	private final String[] metaItems;

	public ItemCrystal (String[] items) {
		setCreativeTab (CreativeTabs.MATERIALS);
		setHasSubtypes (true);
		this.metaItems = items;
		setUnlocalizedName ("crystal");
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList<ItemStack> items) {
		if (tab == ProjectWish.Items)
			for (int index = 0; index < metaItems.length; index++)
				items.add (new ItemStack (this,1,index));
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		if (stack.getItemDamage () < metaItems.length)
			return I18n.translateToLocal ("item." + metaItems[stack.getItemDamage ()] + ".name");
		return "item.null.name";
	}
}
