package wish.wurmatron.common.blocks.stone;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wish.wurmatron.common.blocks.utils.BlockRockType;
import wish.wurmatron.common.blocks.utils.BlockSlideGravity;
import wish.wurmatron.common.registry.Registry;

public class CobbleWish extends BlockSlideGravity {

  private int amount;

  public CobbleWish(Material material, int amount) {
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

  public int damageDropped(IBlockState state) {
    return state.getValue(BlockRockType.TYPE);
  }
}
