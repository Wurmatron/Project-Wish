package wish.wurmatron.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.common.events.GemEvents;
import java.util.*;

public class ItemGem extends Item {

  public final Gem type;

  public ItemGem(Gem gem) {
    this.type = gem;
    setUnlocalizedName("gem" + gem.getUnlocalizedName());
    setCreativeTab(ProjectWish.tabMisc);
    setHasSubtypes(true);
    GemEvents.gemItems.add(this);
  }

  @Override
  public void addInformation(ItemStack stack, World worldIn, List<String> tip, ITooltipFlag flag) {
    tip.add(
        TextFormatting.GRAY + I18n.translateToLocal("tooltip.grade.name") + ": " + getGrade(stack)
            .getName());
  }

  public static Gem.GRADE getGrade(ItemStack stack) {
    if (stack != ItemStack.EMPTY && stack.getItemDamage() <= Gem.GRADE.values().length) {
      return Gem.GRADE.values()[stack.getItemDamage()];
    }
    return Gem.GRADE.D;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
    if (tab == ProjectWish.tabMisc) {
      for (int meta = 0; meta < Gem.GRADE.values().length; meta++) {
        list.add(new ItemStack(this, 1, meta));
      }
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    if (getGrade(stack) == Gem.GRADE.S) {
      return TextFormatting.GOLD + super.getItemStackDisplayName(stack);
    } else if (getGrade(stack) == Gem.GRADE.AA) {
      return TextFormatting.YELLOW + super.getItemStackDisplayName(stack);
    }
    return super.getItemStackDisplayName(stack);
  }

  public static ItemStack getRandomGemGrade(Item gem) {
    int max = 0;
    for (Gem.GRADE grade : Gem.GRADE.values()) {
      max += grade.getChance();
    }
    int id = (int) (Math.random() * max);
    for (int index = 0; index < Gem.GRADE.values().length; index++) {
      if (id > Gem.GRADE.values()[index].getChance()) {
        return new ItemStack(gem, 1, index);
      }
    }
    return new ItemStack(gem, 1, 0);
  }
}
