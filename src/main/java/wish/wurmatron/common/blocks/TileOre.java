package wish.wurmatron.common.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.blocks.utils.BlockRockType;
import wish.wurmatron.common.tile.TileEntityOre;


public class TileOre extends BlockRockType implements ITileEntityProvider {

  public Ore ore;

  public TileOre(Ore ore) {
    super(Material.IRON);
    this.ore = ore;
    setCreativeTab(ProjectWish.tabOre);
    setHardness(1.5f);
    setResistance(30f);
    setHarvestLevel("pickaxe", 1);
    setSoundType(SoundType.STONE);
  }

  @Override
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.CUTOUT_MIPPED;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World world, int meta) {
    return new TileEntityOre(ore, 2);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return null;
  }

  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.scheduleUpdate(pos, this, tickRate(world));
  }

  @Override
  public int tickRate(World worldIn) {
    return 5;
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
    if (ore.isRadioactive() && !world.isRemote) {
      if (world.isAnyPlayerWithinRangeAt(pos.getX(), pos.getY(), pos.getZ(), 8)) {
        EntityPlayer player = world.getNearestAttackablePlayer(pos, 8, 8);
        if (player != null) {
          player.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 2));
        }
      }
      world.scheduleUpdate(pos, state.getBlock(), tickRate(world));
    }
  }
}
