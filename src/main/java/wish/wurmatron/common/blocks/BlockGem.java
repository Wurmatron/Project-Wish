package wish.wurmatron.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import wish.wurmatron.common.utils.LogHandler;

public class BlockGem extends WishBlock {

	public static final PropertyInteger TYPE = PropertyInteger.create ("type",0,15);

	public BlockGem (Material material) {
		super (material);
		setDefaultState (this.blockState.getBaseState ().withProperty (TYPE,0));
	}

	@Override
	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,TYPE);
	}

	@Override
	public IBlockState getStateFromMeta (int meta) {
		return getDefaultState ().withProperty (TYPE,meta);
	}

	@Override
	public int getMetaFromState (IBlockState state) {
		return state.getValue (TYPE);
	}

	@Override
	public int damageDropped (IBlockState state) {
		return getMetaFromState (state);
	}

	@Override
	public void getSubBlocks (CreativeTabs tab,NonNullList <ItemStack> list) {
		LogHandler.info ("GETBLOCK: " + this.blockHardness);
		for (int m = 0; m < 16; m++)
			list.add (new ItemStack (this,1,m));
	}
}
