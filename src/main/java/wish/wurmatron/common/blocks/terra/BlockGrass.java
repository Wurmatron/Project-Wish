package wish.wurmatron.common.blocks.terra;

import net.minecraft.block.Block;
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
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.utils.Registry;

import java.util.Random;

public class BlockGrass extends BlockRockType {

	private int amount;
	private static final int updateTime = Settings.gravityUpdate * 2;

	public BlockGrass (Material material,int amount) {
		super (material);
		this.amount = amount;
		setTickRandomly (true);
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

	@Override
	protected float getFallChance () {
		return 0.5f;
	}

	private Block getBlock (Block block) {
		if (block.getUnlocalizedName ().contains ("igneous"))
			return WishBlocks.dirtIgneous;
		else if (block.getUnlocalizedName ().contains ("sedimentary"))
			return WishBlocks.dirtSedimentary;
		else if (block.getUnlocalizedName ().contains ("metamorphic"))
			return WishBlocks.dirtMetamorphic;
		return block;
	}

	public Item getItemDropped (IBlockState state,Random rand,int fortune) {
		return WishBlocks.dirtMetamorphic.getItemDropped (getBlock (state.getBlock ()).getDefaultState ().withProperty (BlockRockType.TYPE,state.getValue (BlockRockType.TYPE)),rand,fortune);
	}

	@Override
	public void updateTick (World world,BlockPos pos,IBlockState state,Random rand) {
		if (!world.isRemote) {
			if (world.getLightFromNeighbors (pos.up ()) < 4 && world.getBlockState (pos.up ()).getLightOpacity (world,pos.up ()) > 2) {
				world.setBlockState (pos,getBlock (state.getBlock ()).getDefaultState ().withProperty (BlockRockType.TYPE,state.getValue (BlockRockType.TYPE)));
			} else {
				if (world.getLightFromNeighbors (pos.up ()) >= 9) {
					for (int i = 0; i < 4; ++i) {
						BlockPos blockpos = pos.add (rand.nextInt (3) - 1,rand.nextInt (5) - 3,rand.nextInt (3) - 1);

						if (blockpos.getY () >= 0 && blockpos.getY () < 256 && !world.isBlockLoaded (blockpos)) {
							return;
						}

						IBlockState iblockstate = world.getBlockState (blockpos.up ());
						IBlockState iblockstate1 = world.getBlockState (blockpos);

						if (iblockstate1.getBlock () instanceof BlockDirt && world.getLightFromNeighbors (blockpos.up ()) >= 4 && iblockstate.getLightOpacity (world,pos.up ()) <= 2) {
							world.setBlockState (blockpos,state.withProperty (BlockRockType.TYPE,iblockstate1.getValue (BlockRockType.TYPE)));
						}
					}
				}
			}
		}
	}

	@Override
	public int tickRate (World world) {
		return updateTime;
	}
}
