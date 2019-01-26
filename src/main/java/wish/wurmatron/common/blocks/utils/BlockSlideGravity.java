package wish.wurmatron.common.blocks.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSlideGravity extends BlockRockType {

  // TODO Find a way of setting this correctly
  public static boolean isWorldGen = true;

  public BlockSlideGravity(Material material) {
    super(material);
  }

  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.scheduleUpdate(pos, this, tickRate(world));
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn,
      BlockPos fromPos) {
    if (!isWorldGen) {
      world.scheduleUpdate(pos, this, tickRate(world));
    }
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
    if (!world.isRemote) {
      checkFallable(world, pos);
    }
  }

  protected void checkFallable(World world, BlockPos pos) {
    if (world.isAirBlock(pos.down())) {
      if (!isWorldGen) {
        EntityFallingBlock entityfallingblock = new EntityFallingBlock(world,
            (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D,
            world.getBlockState(pos));
        world.spawnEntity(entityfallingblock);
      } else {
        instantFall(pos, world, pos);
      }
    } else {
      BlockPos slide = findSlideSideways(world, pos.down());
      if (slide != null) {
        if (!isWorldGen) {
          IBlockState state = world.getBlockState(pos);
          world.setBlockToAir(pos);
          world.setBlockState(slide, state, 3);
        } else {
          instantFall(slide, world, pos);
        }
      }
    }
  }

  private void instantFall(BlockPos startingPos, World world, BlockPos blockPos) {
    BlockPos fallPos;
    IBlockState state = world.getBlockState(blockPos);
    world.setBlockToAir(blockPos);
    for (fallPos = startingPos.down();
        (world.isAirBlock(fallPos) || canFallThrough(world.getBlockState(fallPos)));
        fallPos = fallPos.down()) {
    }
    world.setBlockState(fallPos.up(), state);
  }

  private BlockPos findSlideSideways(World world, BlockPos pos) {
    List<BlockPos> possibleLocations = new ArrayList<>();
    if (world.getBlockState(pos.north()) == Blocks.AIR.getDefaultState()) {
      possibleLocations.add(pos.north());
    }
    if (world.getBlockState(pos.south()) == Blocks.AIR.getDefaultState()) {
      possibleLocations.add(pos.south());
    }
    if (world.getBlockState(pos.east()) == Blocks.AIR.getDefaultState()) {
      possibleLocations.add(pos.east());
    }
    if (world.getBlockState(pos.west()) == Blocks.AIR.getDefaultState()) {
      possibleLocations.add(pos.west());
    }
    if (possibleLocations.size() > 0) {
      Collections.shuffle(possibleLocations);
      return possibleLocations.get(0);
    }
    return null;
  }

  private boolean canFallThrough(IBlockState state) {
    return state.getMaterial() == Material.AIR
        || state.getMaterial() == Material.LEAVES;
  }

  @Override
  public int tickRate(World worldIn) {
    return 5;
  }
}
