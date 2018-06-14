package wish.wurmatron.common.blocks.terra;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.utils.Registry;

public class BlockSand extends BlockRockType {

  private int amount;

  public BlockSand(Material material, int amount) {
    super(material);
    this.amount = amount;
    setHardness(1f);
    setResistance(3f);
    setHarvestLevel("shovel", 0);
    setSoundType(SoundType.SAND);
  }

  public static boolean canFallThrough(IBlockState state) {
    Block block = state.getBlock();
    Material material = state.getMaterial();
    return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER
        || material == Material.LAVA || material == Material.LEAVES;
  }

  @Override
  public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int m = 0; m < amount; m++) {
			list.add(new ItemStack(this, 1, m));
		}
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
      EntityPlayer player) {
    return new ItemStack(Registry.blockItems.get(this), 1,
        getMetaFromState(world.getBlockState(pos)));
  }

  public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos,
      EnumFacing direction, net.minecraftforge.common.IPlantable plantable) {
    IBlockState plant = plantable.getPlant(world, pos.offset(direction));
    net.minecraftforge.common.EnumPlantType plantType = plantable
        .getPlantType(world, pos.offset(direction));
		if (plant.getBlock() == net.minecraft.init.Blocks.CACTUS
				|| plant.getBlock() == net.minecraft.init.Blocks.REEDS) {
			return true;
		}
    switch (plantType) {
      case Desert:
        return true;
      case Nether:
        return this == net.minecraft.init.Blocks.SOUL_SAND;
      case Crop:
        return this == net.minecraft.init.Blocks.FARMLAND;
      case Cave:
        return state.isSideSolid(world, pos, EnumFacing.UP);
      case Plains:
        return false;
      case Water:
        return state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0;
      case Beach:
        return (world.getBlockState(pos.east()).getMaterial() == Material.WATER
            || world.getBlockState(pos.west()).getMaterial() == Material.WATER
            || world.getBlockState(pos.north()).getMaterial() == Material.WATER
            || world.getBlockState(pos.south()).getMaterial() == Material.WATER);
    }
    return super.canSustainPlant(state, world, pos, direction, plantable);
  }

  @Override
  public int damageDropped(IBlockState state) {
    return getMetaFromState(state);
  }

  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.scheduleUpdate(pos, this, tickRate(world));
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
      BlockPos fromPos) {
    worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
  }

  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			this.checkFallable(worldIn, pos);
		}
  }

  private void checkFallable(World worldIn, BlockPos pos) {
    if ((worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())))
        && pos.getY() >= 0) {
      if (!BlockFalling.fallInstantly && worldIn
          .isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
        if (!worldIn.isRemote) {
          EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn,
              (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D,
              worldIn.getBlockState(pos));
          worldIn.spawnEntity(entityfallingblock);
        }
      } else {
        IBlockState state = worldIn.getBlockState(pos);
        worldIn.setBlockToAir(pos);
        BlockPos blockpos;

        for (blockpos = pos.down();
            (worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos)))
                && blockpos.getY() > 0; blockpos = blockpos.down()) {
          ;
        }

        if (blockpos.getY() > 0) {
          worldIn.setBlockState(blockpos.up(),
              state); //Forge: Fix loss of state information during world gen.
        }
      }
    }
  }

  public int tickRate(World worldIn) {
    return 2;
  }

  @SideOnly(Side.CLIENT)
  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    if (rand.nextInt(16) == 0) {
      BlockPos blockpos = pos.down();

      if (canFallThrough(worldIn.getBlockState(blockpos))) {
        double d0 = (double) ((float) pos.getX() + rand.nextFloat());
        double d1 = (double) pos.getY() - 0.05D;
        double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
        worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D,
            Block.getStateId(stateIn));
      }
    }
  }

  @SideOnly(Side.CLIENT)
  public int getDustColor(IBlockState state) {
    return -16777216;
  }
}
