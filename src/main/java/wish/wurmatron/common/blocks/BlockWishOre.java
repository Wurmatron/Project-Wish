package wish.wurmatron.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.tile.TileOre;

public class BlockWishOre extends BlockRockType implements ITileEntityProvider {

  private OreType type;

  public BlockWishOre(Material material, OreType type) {
    super(material);
    this.type = type;
    setCreativeTab(ProjectWish.BLOCKS);
    setHardness(1.5f);
    setResistance(30f);
    setHarvestLevel("pickaxe", 1);
    setSoundType(SoundType.STONE);
  }

  public static String[] getOreNames(OreType type) {
    List<String> names = new ArrayList<>();
		if (type.getOreType().length > 0) {
			for (StoneType.RockType oreType : type.getOreType()) {
				for (StoneType rock : StoneType.values()) {
					if (oreType.equals(rock.getType())) {
						if (!names.contains(rock.getName())) {
							names.add(rock.getName());
						}
					}
				}
			}
		}
    return names.toArray(new String[0]);
  }

  @Override
  public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int m = 0; m < getOreNames(type).length; m++) {
			list.add(new ItemStack(this, 1, m));
		}
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return null;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World world, int meta) {
    return new TileOre(type, 2);
  }

  @Override
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.CUTOUT_MIPPED;
  }

  @Override
  public CreativeTabs getCreativeTabToDisplayOn() {
    return null;
  }
}