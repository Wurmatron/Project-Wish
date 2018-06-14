package wish.wurmatron.common.world;

import java.util.HashMap;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.config.ConfigHandler;

public class RandomizeRockTypeEvent {

  // Static Data
  private static final IBlockState[] SHIFT_TABLE = new IBlockState[]{
      WishBlocks.stoneSedimentary.getDefaultState(), WishBlocks.stoneIgneous.getDefaultState(),
      WishBlocks.stoneMetamorphic.getDefaultState()};
  private static final IBlockState[] SHIFT_TABLE_SAND = new IBlockState[]{
      WishBlocks.sandSedimentary.getDefaultState(), WishBlocks.sandIgneous.getDefaultState(),
      WishBlocks.sandMetamorphic.getDefaultState()};
  private static final IBlockState[] SHIFT_TABLE_GRAVEL = new IBlockState[]{
      WishBlocks.gravelSedimentary.getDefaultState(), WishBlocks.gravelIgneous.getDefaultState(),
      WishBlocks.gravelMetamorphic.getDefaultState()};
  private static final int AMOUNT_OF_ROCKS = 9;
  private static Random rand;
  // Rock Type Storage
  private static HashMap<String, int[]> rockLayers = new HashMap<>();
  private static HashMap<String, Integer> biomeHeight = new HashMap<>();

  @SubscribeEvent
  public void onPopulateChunkEvent(PopulateChunkEvent.Post e) {
    if (rand == null) {
      rand = new Random(e.getWorld().getSeed());
    }
    Chunk chunk = e.getWorld().getChunkFromChunkCoords(e.getChunkX(), e.getChunkZ());
    BlockPos pos = new BlockPos(0, 0, 0);
    for (int x = 0; x < 16; x++) {
      for (int z = 0; z < 16; z++) {
        for (int y = 0; y < chunk.getHeightValue(x, z); y++) {
          if (chunk.getBlockState(x, y, z) != Blocks.AIR.getDefaultState()) {
            setBlock(chunk, pos.add(x, y, z));
          }
        }
      }
    }
  }

  private void setBlock(Chunk chunk, BlockPos pos) {
    Biome biome = chunk.getBiome(pos, chunk.getWorld().getBiomeProvider());
    int biomeHeight = getBiomeHeight(biome);
    int blockType = getBlockType(pos, biome, biomeHeight);
    int rockMeta = getRockMeta(biome, pos, biomeHeight);
    if (chunk.getBlockState(pos).getBlock() == Blocks.STONE
        || chunk.getBlockState(pos).getBlock() == Blocks.BEDROCK
        || ConfigHandler.overrideOre && chunk.getBlockState(pos).getBlock().getUnlocalizedName()
        .contains("ore")) {
      chunk.setBlockState(pos,
          getStoneBlockShift(biome, blockType).withProperty(BlockRockType.TYPE, rockMeta));
    } else if (chunk.getBlockState(pos).getBlock() == Blocks.SAND) {
      chunk.setBlockState(pos,
          getSandBlockShift(biome, blockType).withProperty(BlockRockType.TYPE, rockMeta));
    } else if (chunk.getBlockState(pos).getBlock() == Blocks.GRAVEL) {
      chunk.setBlockState(pos,
          getGravelBlockShift(biome, blockType).withProperty(BlockRockType.TYPE, rockMeta));
    }
  }

  private IBlockState getStoneBlockShift(Biome biome, int type) {
    int biomeHeight = getBiomeHeight(biome);
    int shift = (256 / biomeHeight) + type;
    if (shift > 2) {
      shift = shift % 3;
    }
    return SHIFT_TABLE[shift];
  }

  private IBlockState getSandBlockShift(Biome biome, int type) {
    int biomeHeight = getBiomeHeight(biome);
    int shift = (256 / biomeHeight) + type;
    if (shift > 2) {
      shift = shift % 3;
    }
    return SHIFT_TABLE_SAND[shift];
  }

  private IBlockState getGravelBlockShift(Biome biome, int type) {
    int biomeHeight = getBiomeHeight(biome);
    int shift = (256 / biomeHeight) + type;
    if (shift > 2) {
      shift = shift % 3;
    }
    return SHIFT_TABLE_GRAVEL[shift];
  }

  private int getRockMeta(Biome biome, BlockPos pos, int biomeHeight) {
    if (rockLayers.containsKey(biome.getRegistryName().toString())) {
      return rockLayers.get(biome.getRegistryName().toString())[pos.getY() / biomeHeight];
    }
    genRockLayers(biomeHeight, biome);
    return getRockMeta(biome, pos, biomeHeight);
  }

  public int getRockMeta(World world, BlockPos pos) {
    Biome biome = world.getBiome(pos);
    return getRockMeta(biome, pos, getBiomeHeight(biome));
  }

  private int getBlockType(BlockPos pos, Biome biome, int biomeHeight) {
    if (rockLayers.containsKey(biome.getRegistryName().toString())) {
      return pos.getY() / biomeHeight;
    }
    genRockLayers(biomeHeight, biome);
    return getBlockType(pos, biome, biomeHeight);
  }

  private int getBiomeHeight(Biome biome) {
    if (biomeHeight.containsKey(biome.getRegistryName().toString())) {
      return biomeHeight.get(biome.getRegistryName().toString());
    }
    return genShift(biome);
  }

  private void genRockLayers(int biomeHeight, Biome biome) {
    int[] newRockLayers = new int[(256 / biomeHeight) + 1];
    for (int index = 0; index < newRockLayers.length - 1; index++) {
      newRockLayers[index] = rand.nextInt(AMOUNT_OF_ROCKS);
    }
    newRockLayers[newRockLayers.length - 1] = rand.nextInt(2);
    rockLayers.put(biome.getRegistryName().toString(), newRockLayers);
  }

  private int genShift(Biome biome) {
    float adjBiomeHeight = Math.abs(biome.getBaseHeight() * 8); // 0 - 16, .25
    float adjHeightVariation = biome.getHeightVariation() / 2;   // 0 - 1, .025
    if (adjBiomeHeight == 0) {
      adjBiomeHeight = 8;
    }
    int height;
    if (Math.abs(adjBiomeHeight) > 5 && adjHeightVariation > .1) {
      height = Math.abs((int) ((256 / adjBiomeHeight) + (adjHeightVariation * 10)));
    } else if (Math.abs(adjBiomeHeight) < .5 && adjBiomeHeight <= .2) {
      height = (int) Math.abs((8 * adjBiomeHeight) / (adjBiomeHeight * 2));
    } else {
      height = Math.abs((int) ((256 / (adjBiomeHeight)) - ((1 - adjHeightVariation) * 1000))) / 30;
    }
    if (height >= 24) {
      height = 24;
    }
    int layerHeight = 256 / (height / 4);
    if (layerHeight > 30) {
      layerHeight = 30;
    }
    int outputHeight = layerHeight > 0 ? layerHeight : 1;
    biomeHeight.put(biome.getRegistryName().toString(), outputHeight);
    return outputHeight;
  }

  public IBlockState getStoneForPos(World world, BlockPos pos) {
    Biome biome = world.getBiome(pos);
    int biomeHeight = getBiomeHeight(biome);
    return getStoneBlockShift(biome, getBlockType(pos, biome, biomeHeight))
        .withProperty(BlockRockType.TYPE, getRockMeta(biome, pos, biomeHeight));
  }
}
