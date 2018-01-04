package wish.wurmatron.common.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.TreeType;

public class ItemBlockTreeType extends ItemBlock {

	private TreeType[] type;

	public ItemBlockTreeType (Block block,TreeType[] type) {
		super (block);
		setMaxDamage (0);
		this.type = type;
		setHasSubtypes (true);
		setCreativeTab (ProjectWish.BLOCKS);
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		return I18n.translateToLocal ("tile.log" + type[stack.getItemDamage ()].getName () + ".name");
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> items) {
		if (tab == ProjectWish.BLOCKS)
			for (int m = 0; m < type.length; m++)
				items.add (new ItemStack (this,1,m));
	}
}
