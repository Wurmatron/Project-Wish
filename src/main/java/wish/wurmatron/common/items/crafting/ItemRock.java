package wish.wurmatron.common.items.crafting;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.common.entity.EntityThrowingRock;

public class ItemRock extends Item {

  public final String[] metaItems;

  public ItemRock(String[] items) {
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
      return I18n.translateToLocal("stone." + StoneType
          .getRockFromMeta(stack.getItemDamage()).getName().toLowerCase()
          + ".name") + " " + I18n.translateToLocal("item.rock.name");
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

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    if (!player.capabilities.isCreativeMode) {
      player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
    }
    if (!world.isRemote) {
      EntityThrowingRock rock = new EntityThrowingRock(world, player);
      rock.addVelocity(player.getLookVec().x * 2, player.getLookVec().y * 2,
          player.getLookVec().z * 2);
      world.spawnEntity(rock);
      if (!player.isCreative()) {
        player.addExhaustion(.5f);
        player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
      }
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
  }
}
