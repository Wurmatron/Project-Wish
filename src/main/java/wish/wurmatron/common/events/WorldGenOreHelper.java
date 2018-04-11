package wish.wurmatron.common.events;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.tile.TileOre;
import wish.wurmatron.common.utils.LogHandler;
import wish.wurmatron.common.world.RandomizeRockTypeEvent;

import java.util.Random;

public class WorldGenOreHelper extends WorldGenerator {

	public final IBlockState oreBlock;
	private final int numberOfBlocks;
	private final Predicate <IBlockState> predicate;
	private final OreType ore;

	public WorldGenOreHelper (OreType ore,IBlockState state,int blockCount) {
		this (ore,state,blockCount,new WorldGenOreHelper.StonePredicate ());
	}

	private WorldGenOreHelper (OreType ore,IBlockState state,int blockCount,Predicate <IBlockState> predicate) {
		this.ore = ore;
		this.oreBlock = state;
		this.numberOfBlocks = blockCount;
		this.predicate = predicate;
	}

	@Override
	public boolean generate (World world,Random rand,BlockPos position) {
		int oreTier = rand.nextInt (4) ;
		float f = rand.nextFloat () * (float) Math.PI;
		double d0 = (double) ((float) (position.getX ()) + MathHelper.sin (f) * (float) this.numberOfBlocks / 8.0F);
		double d1 = (double) ((float) (position.getX ()) - MathHelper.sin (f) * (float) this.numberOfBlocks / 8.0F);
		double d2 = (double) ((float) (position.getZ ()) + MathHelper.cos (f) * (float) this.numberOfBlocks / 8.0F);
		double d3 = (double) ((float) (position.getZ ()) - MathHelper.cos (f) * (float) this.numberOfBlocks / 8.0F);
		double d4 = (double) (position.getY () + rand.nextInt (3) - 2);
		double d5 = (double) (position.getY () + rand.nextInt (3) - 2);
		for (int i = 0; i < this.numberOfBlocks; ++i) {
			float f1 = (float) i / (float) this.numberOfBlocks;
			double d6 = d0 + (d1 - d0) * (double) f1;
			double d7 = d4 + (d5 - d4) * (double) f1;
			double d8 = d2 + (d3 - d2) * (double) f1;
			double d9 = rand.nextDouble () * (double) this.numberOfBlocks / 16.0D;
			double d10 = (double) (MathHelper.sin ((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
			double d11 = (double) (MathHelper.sin ((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
			int j = MathHelper.floor (d6 - d10 / 2.0D);
			int k = MathHelper.floor (d7 - d11 / 2.0D);
			int l = MathHelper.floor (d8 - d10 / 2.0D);
			int i1 = MathHelper.floor (d6 + d10 / 2.0D);
			int j1 = MathHelper.floor (d7 + d11 / 2.0D);
			int k1 = MathHelper.floor (d8 + d10 / 2.0D);
			for (int l1 = j; l1 <= i1; ++l1) {
				double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);
				if (d12 * d12 < 1.0D) {
					for (int i2 = k; i2 <= j1; ++i2) {
						double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);
						if (d12 * d12 + d13 * d13 < 1.0D) {
							for (int j2 = l; j2 <= k1; ++j2) {
								double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);
								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
									BlockPos blockpos = new BlockPos (l1,i2,j2);
									IBlockState state = world.getBlockState (blockpos);
									if (state.getBlock ().isReplaceableOreGen (state,world,blockpos,this.predicate)) {
										if (blockpos.getY () < 24 && rand.nextInt (100) == 0)
											world.setTileEntity (position,new TileOre (ore,oreTier + 1));
										else
											world.setTileEntity (position,new TileOre (ore,oreTier));
										world.setBlockState (blockpos,correctRockType (oreBlock,world,position),2);
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	public boolean check (IBlockState state) {
		return predicate.apply (state);
	}

	public IBlockState correctRockType (IBlockState state,World world,BlockPos pos) {
		int type = getNext (world,pos).getBlock () instanceof BlockRockType ? getNext (world,pos).getValue (BlockRockType.TYPE) : 0;
		state.withProperty (BlockRockType.TYPE,type);
		return state;
	}

	private IBlockState getNext (World world,BlockPos pos) {
		IBlockState east = world.getBlockState (pos.east ());
		IBlockState west = world.getBlockState (pos.west ());
		IBlockState north = world.getBlockState (pos.north ());
		IBlockState south = world.getBlockState (pos.south ());
		if (east.getBlock () instanceof BlockRockType)
			return east;
		else if (west.getBlock () instanceof BlockRockType)
			return west;
		else if (north.getBlock () instanceof BlockRockType)
			return north;
		else if (south.getBlock () instanceof BlockRockType)
			return south;
		return world.getBlockState (pos);
	}

	public static int getMeta (World world,BlockPos pos) {
		int[] metaLoc = ProjectWish.randRockType.getMeta (world.getChunkFromBlockCoords (pos));
		return metaLoc[pos.getY () / RandomizeRockTypeEvent.layerHeight];
	}

	public static Block getRockType(World world, BlockPos pos) {
		return ProjectWish.randRockType.getState (world,pos).getBlock ();
	}

	static class StonePredicate implements Predicate <IBlockState> {

		public boolean apply (IBlockState state) {
			return state.getBlock ().getUnlocalizedName ().contains ("ore") || state.getBlock ().getUnlocalizedName ().contains ("stone");
		}

		@Override
		public boolean test (IBlockState iBlockState) {
			return true;
		}
	}
}