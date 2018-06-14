package wish.wurmatron.common.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wish.wurmatron.common.blocks.stone.BlockRockType;

public class BlockStick extends BlockRockType {

  public static final AxisAlignedBB AABB = new AxisAlignedBB(.1f, 0, .1f, .9f, .2f, .9f);
  private int amount;

  public BlockStick(Material material, int amount) {
    super(material);
    setHardness(0f);
    setSoundType(SoundType.STONE);
    this.amount = amount;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return AABB;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.XZ;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess,
      BlockPos pos, EnumFacing side) {
    return false;
  }

  @Override
  public boolean isBlockNormalCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean isFullBlock(IBlockState state) {
    return false;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Items.STICK;
  }

  @Override
  public int damageDropped(IBlockState state) {
    return 0;
  }

  @Override
  public int quantityDropped(IBlockState state, int fortune, Random random) {
    return 1 + random.nextInt(3);
  }

  @Override
  public CreativeTabs getCreativeTabToDisplayOn() {
    return null;
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
      BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    this.checkAndDropBlock(worldIn, pos, state);
  }

  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    this.checkAndDropBlock(worldIn, pos, state);
  }

  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.AIR) {
      worldIn.spawnEntity(
          new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.STICK)));
      worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }
  }

}
