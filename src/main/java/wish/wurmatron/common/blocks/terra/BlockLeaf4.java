package wish.wurmatron.common.blocks.terra;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.TreeType;

public class BlockLeaf4 extends BlockLeaves {

	public static final PropertyEnum <TreeType> VARIANT = PropertyEnum.create ("variant",TreeType.class,apply -> apply.getMeta () < 16 && apply.getMeta () >= 12);


	public BlockLeaf4 () {
		this.setDefaultState (this.blockState.getBaseState ().withProperty (VARIANT,TreeType.SEQUOIA).withProperty (CHECK_DECAY,true).withProperty (DECAYABLE,true));
		setCreativeTab (ProjectWish.BLOCKS);
	}

	@Override
	protected void dropApple (World worldIn,BlockPos pos,IBlockState state,int chance) {
	}

	@Override
	protected int getSaplingDropChance (IBlockState state) {
		// TODO Needs Saplings First
		return 0;
	}

	@Override
	public BlockPlanks.EnumType getWoodType (int meta) {
		return null;
	}

	@Override
	public void getSubBlocks (CreativeTabs itemIn,NonNullList <ItemStack> items) {
		items.add (new ItemStack (this,1,12));
		items.add (new ItemStack (this,1,13));
		items.add (new ItemStack (this,1,14));
		items.add (new ItemStack (this,1,15));
	}

	@Override
	protected ItemStack getSilkTouchDrop (IBlockState state) {
		return new ItemStack (Item.getItemFromBlock (this),1,state.getValue (VARIANT).getMeta ());
	}


	@Override
	public IBlockState getStateFromMeta (int meta) {
		return this.getDefaultState ().withProperty (VARIANT,getType (meta)).withProperty (DECAYABLE,(meta & 4) == 0).withProperty (CHECK_DECAY,(meta & 8) > 0);
	}

	private TreeType getType (int meta) {
		return TreeType.from ((meta & 3) + 12);
	}

	@Override
	public int getMetaFromState (IBlockState state) {
		int i = 0;
		i = i | (state.getValue (VARIANT)).getMeta ();
		if (!state.getValue (DECAYABLE))
			i |= 4;
		if (state.getValue (CHECK_DECAY))
			i |= 8;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,VARIANT,CHECK_DECAY,DECAYABLE);
	}

	@Override
	public int damageDropped (IBlockState state) {
		return (state.getValue (VARIANT)).getMeta ();
	}

	@Override
	public NonNullList <ItemStack> onSheared (ItemStack item,net.minecraft.world.IBlockAccess world,BlockPos pos,int fortune) {
		return NonNullList.withSize (1,new ItemStack (this,1,world.getBlockState (pos).getValue (VARIANT).getMeta ()));
	}
}
