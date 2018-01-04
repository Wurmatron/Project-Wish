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
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.TreeType;

import java.util.Random;

public class BlockLeaf extends BlockLeaves {

	public static final PropertyEnum <TreeType> VARIANT = PropertyEnum.create ("variant",TreeType.class,apply -> apply.getMeta () < 4);


	public BlockLeaf () {
		this.setDefaultState (this.blockState.getBaseState ().withProperty (VARIANT,TreeType.ASH).withProperty (CHECK_DECAY,true).withProperty (DECAYABLE,true));
		setCreativeTab (ProjectWish.BLOCKS);
	}

	@Override
	protected void dropApple (World worldIn,BlockPos pos,IBlockState state,int chance) {
	}

	@Override
	protected int getSaplingDropChance (IBlockState state) {
		return super.getSaplingDropChance (state);
	}

	@Override
	public BlockPlanks.EnumType getWoodType (int meta) {
		return null;
	}

	@Override
	public void getSubBlocks (CreativeTabs itemIn,NonNullList <ItemStack> items) {
		items.add (new ItemStack (this,1,0));
		items.add (new ItemStack (this,1,1));
		items.add (new ItemStack (this,1,2));
		items.add (new ItemStack (this,1,3));
	}

	@Override
	protected ItemStack getSilkTouchDrop (IBlockState state) {
		return new ItemStack (Item.getItemFromBlock (this),1,state.getValue (VARIANT).getMeta ());
	}


	@Override
	public IBlockState getStateFromMeta (int meta) {
		return this.getDefaultState ().withProperty (VARIANT,getType (meta)).withProperty (DECAYABLE,Boolean.valueOf ((meta & 4) == 0)).withProperty (CHECK_DECAY,Boolean.valueOf ((meta & 8) > 0));
	}

	private TreeType getType (int meta) {
		return TreeType.from ((meta & 3) % 4);
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

	@Override
	public Item getItemDropped (IBlockState state,Random rand,int fortune) {
		return Item.getItemFromBlock (WishBlocks.sapling);
	}
}
