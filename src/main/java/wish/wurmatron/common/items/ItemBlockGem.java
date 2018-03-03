package wish.wurmatron.common.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.common.blocks.BlockGem;
import wish.wurmatron.common.utils.LogHandler;

public class ItemBlockGem extends ItemBlock {

	public ItemBlockGem (Block block) {
		super (block);
		setMaxDamage (0);
		setHasSubtypes (true);
	}

	@Override
	public int getMetadata (int damage) {
		return damage;
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		return I18n.translateToLocal ("item.block" + getBlock ().getUnlocalizedName ().substring (5) + ".name");
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> items) {
		LogHandler.info ("GETBLOCK: " + getBlock ());
		if (tab == ProjectWish.BLOCKS)
			if (getBlock () instanceof BlockGem) {
				for (int meta = 0; meta < 16; meta++)
					items.add (new ItemStack (this,1,meta));
			} else {
				for (int meta = 0; meta < 9; meta++)
					items.add (new ItemStack (this,1,meta));
			}
	}
}
