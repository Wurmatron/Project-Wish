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

public class BlockWood4 extends BlockLog {


	public static final PropertyEnum <TreeType> VARIANT = PropertyEnum.create ("variant",TreeType.class,apply -> apply.getMeta () < 16 && apply.getMeta () >= 12);

	public BlockWood4 () {
		setDefaultState (blockState.getBaseState ().withProperty (VARIANT,TreeType.SEQUOIA).withProperty (LOG_AXIS,EnumAxis.Y));
	}

	@Override
	public void getSubBlocks (CreativeTabs itemIn,NonNullList <ItemStack> items) {
		items.add (new ItemStack (this,1,12));
		items.add (new ItemStack (this,1,13));
		items.add (new ItemStack (this,1,14));
		//		items.add (new ItemStack (this,1,15));
	}

	public IBlockState getStateFromMeta (int meta) {
		IBlockState iblockstate = getDefaultState ().withProperty (VARIANT,TreeType.from ((meta & 3) + 12));

		switch (meta & 12) {
			case 0:
				iblockstate = iblockstate.withProperty (LOG_AXIS,BlockLog.EnumAxis.Y);
				break;
			case 4:
				iblockstate = iblockstate.withProperty (LOG_AXIS,BlockLog.EnumAxis.X);
				break;
			case 8:
				iblockstate = iblockstate.withProperty (LOG_AXIS,BlockLog.EnumAxis.Z);
				break;
			default:
				iblockstate = iblockstate.withProperty (LOG_AXIS,BlockLog.EnumAxis.NONE);
		}

		return iblockstate;
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
