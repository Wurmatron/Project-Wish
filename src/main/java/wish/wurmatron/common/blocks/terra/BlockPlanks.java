package wish.wurmatron.common.blocks.terra;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.TreeType;

public class BlockPlanks extends Block {

	private static final PropertyEnum <TreeType> VARIANT = PropertyEnum.create ("variant",TreeType.class);

	public BlockPlanks () {
		super (Material.WOOD);
		setDefaultState (blockState.getBaseState ().withProperty (VARIANT,TreeType.ASH));
		setCreativeTab (ProjectWish.BLOCKS);
	}

	public int damageDropped (IBlockState state) {
		return (state.getValue (VARIANT)).getMeta ();
	}

	public void getSubBlocks (CreativeTabs itemIn,NonNullList <ItemStack> items) {
		for (TreeType type : TreeType.values ())
			items.add (new ItemStack (this,1,type.getMeta ()));
	}

	public IBlockState getStateFromMeta (int meta) {
		return getDefaultState ().withProperty (VARIANT,TreeType.from (meta));
	}

	public int getMetaFromState (IBlockState state) {
		return (state.getValue (VARIANT)).getMeta ();
	}

	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,VARIANT);
	}
}
