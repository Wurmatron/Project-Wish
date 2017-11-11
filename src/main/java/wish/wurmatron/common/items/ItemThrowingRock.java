package wish.wurmatron.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.common.entity.EntityThrowingRock;

public class ItemThrowingRock extends Item {

	public ItemThrowingRock () {
		setCreativeTab (ProjectWish.Items);
		setUnlocalizedName ("throwingRock");
		setMaxStackSize (1);
		setMaxDamage (1);
	}

	@Override
	public ActionResult <ItemStack> onItemRightClick (World world,EntityPlayer player,EnumHand hand) {
		if (!player.capabilities.isCreativeMode)
			player.getHeldItem (hand).setCount (player.getHeldItem (hand).getCount () - 1);
		if (!world.isRemote) {
			EntityThrowingRock rock = new EntityThrowingRock (world,player);
			rock.addVelocity (player.getLookVec ().x * 2,player.getLookVec ().y * 2,player.getLookVec ().z * 2);
			world.spawnEntity (rock);
			if (!player.isCreative ())
				setDamage (player.getHeldItem (hand),1);
		}
		return new ActionResult <> (EnumActionResult.SUCCESS,player.getHeldItem (hand));
	}
}
