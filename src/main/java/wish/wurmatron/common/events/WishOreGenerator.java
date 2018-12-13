package wish.wurmatron.common.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.utils.OreGeneration;

public class WishOreGenerator implements IWorldGenerator {

  private static List<OreGeneration> oreGeneration = new ArrayList<>();
  private static int maxWeight = 0;

  public static void addOreGen(Ore type, IBlockState state, int maxVeinSize, int minY, int maxY,
      int weight) {
    if (weight > maxWeight) {
      maxWeight = weight;
    }
    OreGeneration gen = new OreGeneration(type, state, maxVeinSize);
    oreGeneration.add(gen);
  }

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
      IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (oreGeneration.size() > 0) {
      for (int i = 0; i < 1 + random.nextInt(2); i++) {
        OreGeneration gen = oreGeneration.get(random.nextInt(oreGeneration.size()));
        if (random.nextInt(gen.ore.getGenerationStyle().getChance()) == 0) {
          gen.generate(world, random, new BlockPos((chunkX * 16), random.nextInt(world.getTopSolidOrLiquidBlock(new BlockPos(chunkX * 16,0,chunkZ * 16)).getY()), (chunkZ * 16)));
        }
      }
    }
  }

  private Block getRock(Block block) {
    if (block == WishBlocks.stoneMetamorphic) {
      return WishBlocks.stoneMetamorphic;
    } else if (block == WishBlocks.stoneSedimentary) {
      return WishBlocks.stoneSedimentary;
    }
    return WishBlocks.stoneIgneous;
  }
}
