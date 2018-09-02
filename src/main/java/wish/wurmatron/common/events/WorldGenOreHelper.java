package wish.wurmatron.common.events;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.tile.TileOre;

public class WorldGenOreHelper extends WorldGenerator {

  public final IBlockState oreBlock;
  private final int numberOfBlocks;
  private final Predicate<IBlockState> predicate;
  private final OreType ore;

  public WorldGenOreHelper(OreType ore, IBlockState state, int blockCount) {
    this(ore, state, blockCount, new WorldGenOreHelper.StonePredicate());
  }

  private WorldGenOreHelper(OreType ore, IBlockState state, int blockCount,
      Predicate<IBlockState> predicate) {
    this.ore = ore;
    this.oreBlock = state;
    this.numberOfBlocks = blockCount;
    this.predicate = predicate;
  }

  @Override
  public boolean generate(World world, Random rand, BlockPos pos) {
    int oreTier = 2 + rand.nextInt(2) - rand.nextInt(2);
    int radius = 5 + rand.nextInt(4);
    int rarity = 5 / ore.getRarity();
    BlockPos core = pos.add(8, 0, 8);
    int totalGenerated = 0;
    if (rarity >= 4) {
      rarity = 2;
      radius = 2 + rand.nextInt(2);
            if (radius > 8) {
                radius = 8;
            }
    }
    do {
            for (int x = 0; x < radius; x++) {
                for (int z = 0; z < radius; z++) {
                    for (int y = pos.getY(); y < pos.getY() + (radius * 2); y++) {
                        BlockPos[] oreLoc = new BlockPos[]{new BlockPos(core.getX() + x, y, core.getZ() + z),
                                new BlockPos(core.getX() + x, y, core.getZ() - z),
                                new BlockPos(core.getX() - x, y, core.getZ() + z),
                                new BlockPos(core.getX() - x, y, core.getZ() - z)};
                        for (BlockPos tempPos : oreLoc) {
                            totalGenerated++;
                            double disFromCore = calcDistanceFromCore(core, tempPos, radius);
                            double chance;
                            if (disFromCore < .3) {
                                chance = .8;
                            } else {
                                chance = ((rarity - disFromCore) / (disFromCore >= .3 ? 4 : 8 + rand.nextInt(4)));
                            }
                            if (rand.nextDouble() < chance) {
                                IBlockState state = world.getBlockState(tempPos);
                                if (state.getBlock().isReplaceableOreGen(state, world, tempPos, predicate)) {
                                    setBlock(world, tempPos,
                                            oreTier + ((calcDistanceFromCore(core, tempPos, radius) <= .2) ? 1 : 0));
                                }
                            }
                        }
                    }
                }
            }
    }
    while (totalGenerated <= numberOfBlocks);
    return true;
  }

  private double calcDistanceFromCore(BlockPos center, BlockPos pos, int radius) {
    double a = center.getX() - pos.getX();
    double b = center.getY() - pos.getY();
    double c = center.getZ() - pos.getZ();
    double distance = Math.sqrt(a * a + b * b + c * c);
    return distance / radius;
  }

  private void setBlock(World world, BlockPos pos, int oreTier) {
    world.setBlockState(pos, correctRockType(oreBlock, world, pos), 2);
    world.setTileEntity(pos, new TileOre(ore, oreTier));
  }

  public IBlockState correctRockType(IBlockState state, World world, BlockPos pos) {
    return state.withProperty(BlockRockType.TYPE, ProjectWish.randRockType.getRockMeta(world, pos));
  }

  static class StonePredicate implements Predicate<IBlockState> {

    public boolean apply(IBlockState state) {
            if (state != null && state.getBlock() != Blocks.AIR) {
                return state.getBlock().getUnlocalizedName().contains("ore") || state.getBlock()
                        .getUnlocalizedName().contains("stone");
            }
      return false;
    }

    @Override
    public boolean test(IBlockState state) {
            if (state != null && state.getBlock() != Blocks.AIR) {
                return state.getBlock().getUnlocalizedName().contains("ore") || state.getBlock()
                        .getUnlocalizedName().contains("stone");
            }
      return false;
    }
  }
}
