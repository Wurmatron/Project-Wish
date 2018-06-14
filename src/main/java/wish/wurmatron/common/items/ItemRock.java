package wish.wurmatron.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.entity.EntityThrowingRock;

public class ItemRock extends Item {

  public ItemRock() {
    setCreativeTab(ProjectWish.Items);
    setUnlocalizedName("throwingRock");
    setMaxStackSize(64);
    setMaxDamage(1);
    setMaxDamage(-1);
    hasSubtypes = true;
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
        player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
      }
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == ProjectWish.Items) {
      for (int index = 0; index < StoneType.values().length; index++) {
        items.add(new ItemStack(WishItems.itemRock, 1, index));
      }
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    return I18n.translateToLocal(StoneType.values()[stack.getItemDamage()].getName()) + " " + I18n
        .translateToLocal("item.rock.name");
  }
}
