package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.common.blocks.stone.BlockRockType;

import java.util.Random;

public class BlockRock extends BlockRockType {

	public static final AxisAlignedBB AABB = new AxisAlignedBB (.2f,0,.2f,.8f,.2f,.8f);
	private int amount;

	public BlockRock (Material material,int amount) {
		super (material);
		setHardness (0f);
		setSoundType (SoundType.STONE);
		this.amount = amount;
	}

	@Override
	public AxisAlignedBB getBoundingBox (IBlockState state,IBlockAccess source,BlockPos pos) {
		return AABB;
	}

	@Override
	@SideOnly (Side.CLIENT)
	public Block.EnumOffsetType getOffsetType () {
		return Block.EnumOffsetType.XZ;
	}

	@SideOnly (Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered (IBlockState blockState,IBlockAccess blockAccess,BlockPos pos,EnumFacing side) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube (IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock (IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube (IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube (IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable (IBlockAccess worldIn,BlockPos pos) {
		return true;
	}

	public Item getItemDropped (IBlockState state,Random rand,int fortune) {
		return WishItems.itemRock;
	}

	@Override
	public int damageDropped (IBlockState state) {
		if (state.getBlock () == WishBlocks.rockSedimentary)
			return state.getValue (BlockRockType.TYPE);
		else if (state.getBlock () == WishBlocks.rockIgneous)
			return 18 + state.getValue (BlockRockType.TYPE);
		else
			return 9 + state.getValue (BlockRockType.TYPE);
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn () {
		return null;
	}
}
