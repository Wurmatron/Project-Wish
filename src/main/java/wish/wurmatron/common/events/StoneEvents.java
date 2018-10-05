package wish.wurmatron.common.events;

import net.minecraft.block.BlockStone;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.WishBlocks;

public class StoneEvents {

  // TODO Temp
  @SubscribeEvent
  public void onPopulateChunkEvent(PopulateChunkEvent.Pre e) {
    Chunk chunk = e.getWorld().getChunkFromChunkCoords(e.getChunkX(), e.getChunkZ());
    BlockPos pos = new BlockPos(0, 0, 0);
    for (int x = 0; x < 16; x++) {
      for (int z = 0; z < 16; z++) {
        for (int y = 0; y < chunk.getHeightValue(x, z); y++) {
          if (chunk.getBlockState(x, y, z).getBlock() instanceof BlockStone) {
            chunk.setBlockState(pos.add(x, y, z), WishBlocks.stoneIgneous.getDefaultState());
          }
        }
      }
    }
    chunk.setModified(true);
  }
}
