package wish.wurmatron.common.blocks.terra;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.utils.Registry;

public class BlockSand extends BlockRockType {

	private int amount;

	public BlockSand (Material material,int amount) {
		super (material);
		this.amount = amount;
	}

	@Override
	public void getSubBlocks (CreativeTabs tab,NonNullList <ItemStack> list) {
		for (int m = 0; m < amount; m++)
			list.add (new ItemStack (this,1,m));
	}

	@Override
	public ItemStack getPickBlock (IBlockState state,RayTraceResult target,World world,BlockPos pos,EntityPlayer player) {
		return new ItemStack (Registry.blockItems.get (this),1,getMetaFromState (world.getBlockState (pos)));
	}
}
