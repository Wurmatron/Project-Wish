package wish.wurmatron.common.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.blocks.utils.BlockRockType;
import wish.wurmatron.common.tile.TileEntityOre;


public class TileOre extends BlockRockType implements ITileEntityProvider {

  public Ore type;

  public TileOre(Ore type) {
    super(Material.IRON);
    this.type = type;
    setCreativeTab(CreativeTabs.COMBAT);
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
    return new TileEntityOre(type, 2);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return null;
  }
}
