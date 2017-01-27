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

public class BlockMetamorphic extends BlockRockType {

		public BlockMetamorphic(Material material) {
				super(material);
				setUnlocalizedName("stone_Metamorphic");
				setRegistryName("stone_Metamorphic");
		}

		@Override
		public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
				for (int meta = 0; meta < 8; meta++)
						list.add(new ItemStack(item, 1, meta));
		}

		@Override
		public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
				return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
		}
}
