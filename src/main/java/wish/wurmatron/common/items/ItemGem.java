package wish.wurmatron.common.items;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.common.events.WorldEvents;

public class ItemGem extends Item {

  public final GemType type;

  public ItemGem(GemType gem) {
    this.type = gem;
    setUnlocalizedName("gem" + gem.getName());
    setCreativeTab(ProjectWish.Items);
    setHasSubtypes(true);
    WorldEvents.gemItems.add(this);
  }

  @Override
  public void addInformation(ItemStack stack, World worldIn, List<String> tip, ITooltipFlag flag) {
    tip.add(
        TextFormatting.GRAY + I18n.translateToLocal("tooltip.grade.name") + ": " + getGrade(stack)
            .getName());
  }

  public static GemType.GRADE getGrade(ItemStack stack) {
        if (stack != ItemStack.EMPTY && stack.getItemDamage() <= GemType.GRADE.values().length) {
            return GemType.GRADE.values()[stack.getItemDamage()];
        }
    return GemType.GRADE.D;
  }

  public static GemType.GRADE getGrade(int meta) {
        if (meta < GemType.GRADE.values().length) {
            return GemType.GRADE.values()[meta];
        }
    return GemType.GRADE.D;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (tab == ProjectWish.Items) {
            for (int meta = 0; meta < GemType.GRADE.values().length; meta++) {
                list.add(new ItemStack(this, 1, meta));
            }
        }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
        if (getGrade(stack) == GemType.GRADE.S) {
            return TextFormatting.GOLD + super.getItemStackDisplayName(stack);
        } else if (getGrade(stack) == GemType.GRADE.AA) {
            return TextFormatting.YELLOW + super.getItemStackDisplayName(stack);
        }
    return super.getItemStackDisplayName(stack);
  }

  public static ItemStack getRandomGemGrade(Item gem) {
    int max = 0;
        for (GemType.GRADE grade : GemType.GRADE.values()) {
            max += grade.getChance();
        }
    int id = (int) (Math.random() * max);
        for (int index = 0; index < GemType.GRADE.values().length; index++) {
            if (id > GemType.GRADE.values()[index].getChance()) {
                return new ItemStack(gem, 1, index);
            }
        }
    return new ItemStack(gem, 1, 0);
  }
}
