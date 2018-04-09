package wish.wurmatron.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.common.blocks.stone.BlockRockType;

public class BlockStick extends BlockRockType {

	public static final AxisAlignedBB AABB = new AxisAlignedBB (.2f,0,.2f,.8f,.2f,.8f);

	public BlockStick (Material material) {
		super (material);
		setHardness (0f);
		setSoundType (SoundType.STONE);
	}

	@Override
	public AxisAlignedBB getBoundingBox (IBlockState state,IBlockAccess source,BlockPos pos) {
		return AABB;
	}

	@Override
	@SideOnly (Side.CLIENT)
	public EnumOffsetType getOffsetType () {
		return EnumOffsetType.XZ;
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

	@Override
	public void breakBlock (World world,BlockPos pos,IBlockState state) {
		world.spawnEntity (new EntityItem (world,pos.getX () + .5,pos.getY () + .5,pos.getZ () + .5,new ItemStack (Items.STICK,1 + world.rand.nextInt (3))));
	}
}
