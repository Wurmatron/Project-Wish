package wish.wurmatron.common.events;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.WishBlocks;

public class StoneEvents {

  @SubscribeEvent(priority = EventPriority.HIGH)
  public void onPopulateChunkEvent(PopulateChunkEvent.Post e) {

    Chunk chunk = e.getWorld().getChunkFromChunkCoords(e.getChunkX(), e.getChunkZ());
    BlockPos pos = new BlockPos(0, 0, 0);
    for (int x = 0; x < 16; x++) {
      for (int z = 0; z < 16; z++) {
        for (int y = 0; y < 255; y++) {
          if (chunk.getBlockState(x, y, z) != Blocks.AIR.getDefaultState()
              && chunk.getBlockState(x, y, z).getBlock() instanceof BlockStone) {
            chunk.setBlockState(pos.add(x, y, z), getState(e.getWorld(), y));
          }
        }
      }
    }
    e.getWorld().getChunkFromChunkCoords(e.getChunkX(), e.getChunkZ()).setModified(true);
  }

  private static long getRockType(World world, long y) {
    if (y == 0) {
      y = 1;
    }
    return world.getSeed() % y;
  }

  private static long normalize(World world, int y) {
    int value = (int) getRockType(world, y);
    if (value > 2) {
      return 2;
    } else if (value < 0) {
      return 0;
    }
    return value;
  }

  private static IBlockState getState(World world, int y) {
    int value = (int) normalize(world, y);
    if (value == 0) {
      return WishBlocks.stoneIgneous.getDefaultState();
    } else if (value == 1) {
      return WishBlocks.stoneMetamorphic.getDefaultState();
    } else {
      return WishBlocks.stoneSedimentary.getDefaultState();
    }

  }
}
