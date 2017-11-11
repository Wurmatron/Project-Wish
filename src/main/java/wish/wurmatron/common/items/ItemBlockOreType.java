package wish.wurmatron.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.common.blocks.BlockOre;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockOreType extends ItemBlock {

	private OreType type;

	public ItemBlockOreType (Block block,OreType type) {
		super (block);
		setMaxDamage (0);
		setHasSubtypes (true);
		setCreativeTab (ProjectWish.BLOCKS);
		this.type = type;
	}

	@Override
	public int getMetadata (int damage) {
		return damage;
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		return I18n.translateToLocal (stack.getUnlocalizedName () + "_" + BlockOre.getOreNames (type)[stack.getItemDamage ()] + ".name");
	}
}
