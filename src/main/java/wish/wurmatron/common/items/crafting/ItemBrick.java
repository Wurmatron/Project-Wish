package wish.wurmatron.common.items.crafting;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;

public class ItemBrick extends Item {

  private final String[] metaItems;

  public ItemBrick(String[] items) {
    setCreativeTab(ProjectWish.tabMisc);
    setHasSubtypes(true);
    this.metaItems = items;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == ProjectWish.tabMisc) {
      for (int index = 0; index < metaItems.length; index++) {
        items.add(new ItemStack(this, 1, index));
      }
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    if (stack.getItemDamage() < metaItems.length) {
      if (stack.getItem().getUnlocalizedName().contains("Igneous")) {
        return I18n.translateToLocal("stone." + StoneType
            .getRockFromMeta(RockType.Igneous, stack.getItemDamage()).getName().toLowerCase()
            + ".name") + " " + I18n.translateToLocal("tile.brick.name");
      } else if (stack.getItem().getUnlocalizedName().contains("Metamorphic")) {
        return I18n.translateToLocal("stone." + StoneType
            .getRockFromMeta(RockType.Metamorphic, stack.getItemDamage()).getName().toLowerCase()
            + ".name") + " " + I18n.translateToLocal("tile.brick.name");
      } else if (stack.getItem().getUnlocalizedName().contains("Sedimentary")) {
        return I18n.translateToLocal("stone." + StoneType
            .getRockFromMeta(RockType.Sedimentary, stack.getItemDamage()).getName().toLowerCase()
            + ".name") + " " + I18n.translateToLocal("tile.brick.name");
      }
    }
    return "item.null.name";
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tip,
      ITooltipFlag flag) {
    if (stack.getItem().getUnlocalizedName().contains("Igneous")) {
      tip.add(TextFormatting.GRAY + I18n.translateToLocal("stone." + StoneType
          .getRockFromMeta(RockType.Igneous, stack.getItemDamage()).getName().toLowerCase()
          + ".name"));
    } else if (stack.getItem().getUnlocalizedName().contains("Metamorphic")) {
      tip.add(TextFormatting.GRAY + I18n.translateToLocal("stone." + StoneType
          .getRockFromMeta(RockType.Metamorphic, stack.getItemDamage()).getName().toLowerCase()
          + ".name"));
    } else if (stack.getItem().getUnlocalizedName().contains("Sedimentary")) {
      tip.add(TextFormatting.GRAY + I18n.translateToLocal("stone." + StoneType
          .getRockFromMeta(RockType.Sedimentary, stack.getItemDamage()).getName().toLowerCase()
          + ".name"));
    }
  }
}
