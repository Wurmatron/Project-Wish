package wish.wurmatron.common.blocks.stone;

import java.util.Random;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.common.blocks.utils.BlockRockType;
import wish.wurmatron.common.registry.Registry;

public class StoneWish extends BlockRockType {

  private int amount;

  public StoneWish(Material material, int amount) {
    super(material);
    this.amount = amount;
    setHardness(3f);
    setResistance(10f);
    setHarvestLevel("pickaxe", 1);
    setSoundType(SoundType.STONE);
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

  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    if (state.getBlock().equals(WishBlocks.stoneIgneous)) {
      return Item.getItemFromBlock(WishBlocks.cobbleIgneous);
    } else if (state.getBlock().equals(WishBlocks.stoneMetamorphic)) {
      return Item.getItemFromBlock(WishBlocks.cobbleMetamorphic);
    } else {
      return Item.getItemFromBlock(WishBlocks.cobbleSedimentary);
    }
  }

  public int damageDropped(IBlockState state) {
    return state.getValue(BlockRockType.TYPE);
  }
}
