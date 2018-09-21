package wish.wurmatron.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import wish.wurmatron.api.rock.ore.Ore;

public class ItemBlockOreType extends ItemBlock {

	private Ore type;

	public ItemBlockOreType (Block block,Ore type) {
		super (block);
		setMaxDamage (0);
		setHasSubtypes (true);
		setCreativeTab (CreativeTabs.COMBAT);
		this.type = type;
	}

	@Override
	public int getMetadata (int damage) {
		return damage;
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		return I18n.translateToLocal ("tile.ore" + type.getUnlocalizedName () + ".name");
	}
}
