package wish.wurmatron.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.api.world.StoneType;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockRockType extends ItemBlock {

	private StoneType.RockType type;

	public ItemBlockRockType (Block block,StoneType.RockType type) {
		super (block);
		setMaxDamage (0);
		setHasSubtypes (true);
		this.type = type;
	}

	@Override
	public int getMetadata (int damage) {
		return damage;
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
		StoneType stoneType = StoneType.getRockFromMeta (type,stack.getItemDamage ());
		if (stoneType != null)
			tip.add (TextFormatting.GRAY + I18n.translateToLocal ("tooltip.stoneSubType." + stoneType.getSubType ().name ().substring (0,1) + stoneType.getSubType ().name ().toLowerCase ().substring (1) + ".name"));
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		StoneType stoneType = StoneType.getRockFromMeta (type,stack.getItemDamage ());
		if (stoneType != null)
			return I18n.translateToLocal ("item.stone" + stoneType.getName () + ".name");
		return I18n.translateToLocal ("item.stoneError.name");
	}
}
