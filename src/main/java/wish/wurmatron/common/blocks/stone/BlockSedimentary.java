package wish.wurmatron.common.blocks.stone;

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

public class BlockSedimentary extends BlockRockType {

		public BlockSedimentary(Material material) {
				super(material);
				setUnlocalizedName("stone_Sedimentary");
				setRegistryName("stone_Sedimentary");
		}

		@Override
		public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
				for (int sed = 0; sed < 8; sed++)
						list.add(new ItemStack(item, 1, sed));
		}

		@Override
		public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
				return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
		}
}
