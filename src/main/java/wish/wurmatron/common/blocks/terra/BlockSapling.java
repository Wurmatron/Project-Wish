package wish.wurmatron.common.blocks.terra;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.TreeType;

import java.util.Random;

public class BlockSapling extends BlockBush implements IGrowable {

	public static final PropertyEnum <TreeType> TYPE = PropertyEnum.create ("type",TreeType.class);
	public static final PropertyInteger STAGE = PropertyInteger.create ("stage",0,1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB (0.09999999403953552D,0.0D,0.09999999403953552D,0.8999999761581421D,0.800000011920929D,0.8999999761581421D);

	public BlockSapling () {
		this.setDefaultState (this.blockState.getBaseState ().withProperty (TYPE,TreeType.ASH).withProperty (STAGE,0));
		this.setCreativeTab (ProjectWish.BLOCKS);
	}

	public AxisAlignedBB getBoundingBox (IBlockState state,IBlockAccess source,BlockPos pos) {
		return SAPLING_AABB;
	}

	public void updateTick (World worldIn,BlockPos pos,IBlockState state,Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick (worldIn,pos,state,rand);

			if (worldIn.getLightFromNeighbors (pos.up ()) >= 9 && rand.nextInt (7) == 0) {
				this.grow (worldIn,pos,state,rand);
			}
		}
	}

	public void grow (World worldIn,BlockPos pos,IBlockState state,Random rand) {
		if (state.getValue (STAGE) == 0) {
			worldIn.setBlockState (pos,state.cycleProperty (STAGE),4);
		} else {
			this.generateTree (worldIn,pos,state,rand);
		}
	}

	// TODO Correct for the type of tree...
	public void generateTree (World worldIn,BlockPos pos,IBlockState state,Random rand) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree (worldIn,rand,pos))
			return;
		WorldGenerator worldgenerator = (rand.nextInt (10) == 0 ? new WorldGenBigTree (true) : new WorldGenTrees (true));
		int i = 0;
		int j = 0;
		boolean flag = false;

		switch (state.getValue (TYPE)) {
			case ASH:
					label68:

				for (i = 0; i >= -1; --i) {
					for (j = 0; j >= -1; --j) {
						if (this.isTwoByTwoOfType (worldIn,pos,i,j,TreeType.ASH)) {
							worldgenerator = new WorldGenMegaPineTree (false,rand.nextBoolean ());
							flag = true;
							break label68;
						}
					}
				}

				if (!flag) {
					i = 0;
					j = 0;
					worldgenerator = new WorldGenTaiga2 (true);
				}

				break;
			case BIRCH:
				worldgenerator = new WorldGenBirchTree (true,false);
				break;
			case CEDAR:
				IBlockState iblockstate = WishBlocks.log1.getDefaultState ().withProperty (BlockLeaf.VARIANT,TreeType.CEDAR);
				IBlockState iblockstate1 = WishBlocks.log1.getDefaultState ().withProperty (BlockLeaf.VARIANT,TreeType.CEDAR).withProperty (BlockLeaves.CHECK_DECAY,Boolean.FALSE);
					label82:

				for (i = 0; i >= -1; --i) {
					for (j = 0; j >= -1; --j) {
						if (this.isTwoByTwoOfType (worldIn,pos,i,j,TreeType.CEDAR)) {
							worldgenerator = new WorldGenMegaJungle (true,10,20,iblockstate,iblockstate1);
							flag = true;
							break label82;
						}
					}
				}

				if (!flag) {
					i = 0;
					j = 0;
					worldgenerator = new WorldGenTrees (true,4 + rand.nextInt (7),iblockstate,iblockstate1,false);
				}

				break;
			case ASPEN:
				worldgenerator = new WorldGenSavannaTree (true);
				break;
			case ELM:
					label96:

				for (i = 0; i >= -1; --i) {
					for (j = 0; j >= -1; --j) {
						if (this.isTwoByTwoOfType (worldIn,pos,i,j,TreeType.ELM)) {
							worldgenerator = new WorldGenCanopyTree (true);
							flag = true;
							break label96;
						}
					}
				}

				if (!flag) {
					return;
				}

			case OAK:
		}

		IBlockState iblockstate2 = Blocks.AIR.getDefaultState ();

		if (flag) {
			worldIn.setBlockState (pos.add (i,0,j),iblockstate2,4);
			worldIn.setBlockState (pos.add (i + 1,0,j),iblockstate2,4);
			worldIn.setBlockState (pos.add (i,0,j + 1),iblockstate2,4);
			worldIn.setBlockState (pos.add (i + 1,0,j + 1),iblockstate2,4);
		} else {
			worldIn.setBlockState (pos,iblockstate2,4);
		}

		if (!worldgenerator.generate (worldIn,rand,pos.add (i,0,j))) {
			if (flag) {
				worldIn.setBlockState (pos.add (i,0,j),state,4);
				worldIn.setBlockState (pos.add (i + 1,0,j),state,4);
				worldIn.setBlockState (pos.add (i,0,j + 1),state,4);
				worldIn.setBlockState (pos.add (i + 1,0,j + 1),state,4);
			} else {
				worldIn.setBlockState (pos,state,4);
			}
		}
	}

	private boolean isTwoByTwoOfType (World worldIn,BlockPos pos,int p_181624_3_,int p_181624_4_,TreeType type) {
		return this.isTypeAt (worldIn,pos.add (p_181624_3_,0,p_181624_4_),type) && this.isTypeAt (worldIn,pos.add (p_181624_3_ + 1,0,p_181624_4_),type) && this.isTypeAt (worldIn,pos.add (p_181624_3_,0,p_181624_4_ + 1),type) && this.isTypeAt (worldIn,pos.add (p_181624_3_ + 1,0,p_181624_4_ + 1),type);
	}

	public boolean isTypeAt (World worldIn,BlockPos pos,TreeType type) {
		IBlockState iblockstate = worldIn.getBlockState (pos);
		return iblockstate.getBlock () == this && iblockstate.getValue (TYPE) == type;
	}

	public int damageDropped (IBlockState state) {
		return (state.getValue (TYPE)).getMeta ();
	}

	@Override
	public void getSubBlocks (CreativeTabs itemIn,NonNullList <ItemStack> items) {
		for (TreeType blockplanks$enumtype : TreeType.values ()) {
			items.add (new ItemStack (this,1,blockplanks$enumtype.getMeta ()));
		}
	}

	public boolean canGrow (World worldIn,BlockPos pos,IBlockState state,boolean isClient) {
		return true;
	}

	public boolean canUseBonemeal (World worldIn,Random rand,BlockPos pos,IBlockState state) {
		return (double) worldIn.rand.nextFloat () < 0.45D;
	}

	public void grow (World worldIn,Random rand,BlockPos pos,IBlockState state) {
		this.grow (worldIn,pos,state,rand);
	}

	public IBlockState getStateFromMeta (int meta) {
		return this.getDefaultState ().withProperty (TYPE,TreeType.from (meta & 7)).withProperty (STAGE,(meta & 8) >> 3);
	}

	public int getMetaFromState (IBlockState state) {
		int i = 0;
		i = i | state.getValue (TYPE).getMeta ();
		i = i | state.getValue (STAGE) << 3;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,TYPE,STAGE);
	}
}
