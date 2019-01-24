package wish.wurmatron.common.events;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import wish.wurmatron.ConfigHandler;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.common.blocks.BlockRock;
import wish.wurmatron.common.blocks.BlockStick;
import wish.wurmatron.common.blocks.utils.BlockRockType;

public class WishWorldGenerator implements IWorldGenerator {

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
      IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    generateGround(random, chunkX, chunkZ, world);
  }

  private void generateGround(Random rand, int chunkX, int chunkZ, World world) {
    for (int i = 0; i < rand.nextInt(ConfigHandler.rocksPerChunk); i++) {
      int x = chunkX * 16 + rand.nextInt(16) + 8;
      int z = chunkZ * 16 + rand.nextInt(16) + 8;
      BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).down();
      Block block = world.getBlockState(pos).getBlock();
      if (isRockSpawnable(block)) {
        if (world.isAirBlock(pos.up())) {
          IBlockState state = ProjectWish.randomizeRockType.getStoneForPos(world, pos.up());
          if (state.getBlock() != Blocks.AIR) {
            world.setBlockState(pos.up(),
                getRock(state.getBlock()).getStateFromMeta(state.getValue(
                    BlockRockType.TYPE)), 3);
          }
        }
      }
    }
    for (int i = 0; i < rand.nextInt(ConfigHandler.sticksPerChunk); i++) {
      int x = chunkX * 16 + rand.nextInt(16) + 8;
      int z = chunkZ * 16 + rand.nextInt(16) + 8;
      BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).down();
      Block block = world.getBlockState(pos).getBlock();
      if (isRockSpawnable(block)) {
        if (world.isAirBlock(pos.up())) {
          world.setBlockState(pos.up(), WishBlocks.stick.getDefaultState(), 3);
        }
      }
    }
  }

  private Block getRock(Block block) {
    if (block == WishBlocks.stoneMetamorphic) {
      return WishBlocks.rockMetamorphic;
    } else if (block == WishBlocks.stoneSedimentary) {
      return WishBlocks.rockSedimentary;
    }
    return WishBlocks.rockIgneous;
  }

  private boolean isRockSpawnable(Block block) {
    return block == Blocks.DIRT || block == Blocks.GRASS
        || block instanceof BlockRockType && !(block instanceof BlockRock)
        && !(block instanceof BlockStick);
  }
}
