package wish.wurmatron.common.blocks.terra;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import wish.wurmatron.api.world.TreeType;

public class BlockWood extends BlockLog {

	public static final PropertyEnum <TreeType> VARIANT = PropertyEnum.create ("variant",TreeType.class,apply -> apply.getMeta () < 4);

	public BlockWood () {
		setDefaultState (blockState.getBaseState ().withProperty (VARIANT,TreeType.ASH).withProperty (LOG_AXIS,BlockLog.EnumAxis.Y));
	}

	@Override
	public void getSubBlocks (CreativeTabs itemIn,NonNullList <ItemStack> items) {
		items.add (new ItemStack (this,1,0));
		items.add (new ItemStack (this,1,1));
		items.add (new ItemStack (this,1,2));
		items.add (new ItemStack (this,1,3));
	}

	@Override
	public IBlockState getStateFromMeta (int meta) {
		IBlockState state = this.getDefaultState ().withProperty (VARIANT,TreeType.from ((meta & 3) % 4));
		switch (meta & 12) {
			case 0:
				state = state.withProperty (LOG_AXIS,BlockLog.EnumAxis.Y);
				break;
			case 4:
				state = state.withProperty (LOG_AXIS,BlockLog.EnumAxis.X);
				break;
			case 8:
				state = state.withProperty (LOG_AXIS,BlockLog.EnumAxis.Z);
				break;
			default:
				state = state.withProperty (LOG_AXIS,BlockLog.EnumAxis.NONE);
		}

		return state;
	}

	@Override
	public int getMetaFromState (IBlockState state) {
		int i = 0;
		i = i | state.getValue (VARIANT).getMeta ();
		switch (state.getValue (LOG_AXIS)) {
			case X:
				i |= 4;
				break;
			case Z:
				i |= 8;
				break;
			case NONE:
				i |= 12;
		}
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,VARIANT,LOG_AXIS);
	}

	@Override
	protected ItemStack getSilkTouchDrop (IBlockState state) {
		return new ItemStack (Item.getItemFromBlock (this),1,(state.getValue (VARIANT)).getMeta ());
	}

	@Override
	public int damageDropped (IBlockState state) {
		return (state.getValue (VARIANT)).getMeta ();
	}
}
