package wish.wurmatron.common.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityGravityBlock extends EntityFallingBlock {

	public EntityGravityBlock (World world,double x,double y,double z,IBlockState state) {
		super (world,x,y,z,state);
	}

	@Override
	public void onUpdate () {
		super.onUpdate ();
		if (ticksExisted > 1000)
			dropItem (Item.getItemFromBlock (getBlock ().getBlock ()),1);
	}
}
