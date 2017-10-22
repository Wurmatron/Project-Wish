package wish.wurmatron.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.common.events.WorldEvents;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGem extends Item {

	public final GemType type;

	public ItemGem (GemType gem) {
		this.type = gem;
		setCreativeTab (ProjectWish.Items);
		setUnlocalizedName ("gem" + gem.getName ());
		setHasSubtypes (true);
		WorldEvents.gemItems.add (new ItemStack (this,1,0));
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World worldIn,List <String> tip,ITooltipFlag flag) {
		tip.add (TextFormatting.GRAY + I18n.translateToLocal ("tooltip.grade.name") + ": " + getGrade (stack).getName ());
	}

	public static GemType.GRADE getGrade (ItemStack stack) {
		if (stack != ItemStack.EMPTY && stack.getItemDamage () <= GemType.GRADE.values ().length)
			return GemType.GRADE.values ()[stack.getItemDamage ()];
		return GemType.GRADE.D;
	}

	public static GemType.GRADE getGrade (int meta) {
		if (meta < GemType.GRADE.values ().length)
			return GemType.GRADE.values ()[meta];
		return GemType.GRADE.D;
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> list) {
		for (int meta = 0; meta < GemType.GRADE.values ().length; meta++)
			list.add (new ItemStack (this,1,meta));
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		if (getGrade (stack) == GemType.GRADE.S)
			return TextFormatting.GOLD + super.getItemStackDisplayName (stack);
		else if (getGrade (stack) == GemType.GRADE.AA)
			return TextFormatting.YELLOW + super.getItemStackDisplayName (stack);
		return super.getItemStackDisplayName (stack);
	}
}
