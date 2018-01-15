package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wish.wurmatron.common.entity.EntityGravityBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockGravity extends Block {

	private static int counter = 0;

	public BlockGravity (Material m) {
		super (m);
	}

	@Override
	public void onBlockAdded (World worldIn,BlockPos pos,IBlockState state) {
		worldIn.scheduleUpdate (pos,this,tickRate (worldIn));
	}

	@Override
	public void neighborChanged (IBlockState state,World worldIn,BlockPos pos,Block blockIn,BlockPos fromPos) {
		worldIn.scheduleUpdate (pos,this,tickRate (worldIn));
	}

	@Override
	public void updateTick (World worldIn,BlockPos pos,IBlockState state,Random rand) {
		if (!worldIn.isRemote) {
			//			checkFallable (worldIn,pos,state);
		}
	}

	protected void checkFallable (World worldIn,BlockPos pos,IBlockState state) {
		BlockPos slidePos = slideScan (worldIn,pos);
		if ((canFallInto (worldIn,pos.down ())) && (pos.getY () >= 0)) {
			fall (worldIn,pos,state);
		} else if (slidePos != null && (slidePos.getY () >= 0)) {
			worldIn.setBlockToAir (pos);
			fall (worldIn,slidePos,state);
		}
	}

	protected void fall (World worldIn,BlockPos pos,IBlockState state) {
		int i = 32;

		if ((!BlockFalling.fallInstantly) && (worldIn.isAreaLoaded (pos.add (-i,-i,-i),pos.add (i,i,i)))) {
			if (!worldIn.isRemote) {
				EntityGravityBlock entityfallingblock = new EntityGravityBlock (worldIn,pos.getX () + 0.5D,pos.getY (),pos.getZ () + 0.5D,state);
				worldIn.spawnEntity (entityfallingblock);
			}
		} else {
			worldIn.setBlockToAir (pos);

			BlockPos blockpos;
			for (blockpos = pos.down (); (canFallInto (worldIn,blockpos)) && (blockpos.getY () > 0); blockpos = blockpos.down ()) {
			}

			if (blockpos.getY () > 0) {
				worldIn.setBlockState (blockpos.up (),getDefaultState ());
			}
		}
	}


	@Override
	public int tickRate (World worldIn) {
		return 5;
	}

	public boolean canFallInto (World worldIn,BlockPos pos) {
		if (worldIn.isAirBlock (pos))
			return true;
		Block block = worldIn.getBlockState (pos).getBlock ();
		Material material = block.getMaterial (worldIn.getBlockState (pos));
		return (block == Blocks.FIRE) || (material == Material.AIR) || (material == Material.WATER) || (material == Material.LAVA) || block.isReplaceable (worldIn,pos);
	}

	public void onEndFalling (World worldIn,BlockPos pos) {
	}

	/**
	 @return Minimum cliffheight required for this block to slide down to lower elevation. -1 disables sliding
	 */
	public int getSlideHeight () {
		return -1;
	}

	/**
	 @return Chance that a block will slide [0.0 - 1.0]
	 */
	public float getSlideChance () {
		return 0f;
	}

	protected BlockPos slideScan (World world,BlockPos pos) {
		if (pos.getY () == 0)
			return null;
		if (world.rand.nextFloat () < 1 - getSlideChance ())
			return null;

		if (!world.isAirBlock (pos.up ()) || !world.getBlockState (pos.up ()).getBlock ().isReplaceable (world,pos.up ()))
			return null;

		if (getSlideHeight () == -1)
			return null;
		else {
			List <BlockPos> pot = new ArrayList <> ();
			int slideheightE = getSlideHeight ();
			int slideheightW = getSlideHeight ();
			int slideheightN = getSlideHeight ();
			int slideheightS = getSlideHeight ();

			boolean underWater = isWater (world.getBlockState (pos.up ()));

			if (isWater (world.getBlockState (pos.east ())))
				slideheightE = slideheightE * (underWater ? 3 : 2);
			if (isWater (world.getBlockState (pos.west ())))
				slideheightW = slideheightW * (underWater ? 3 : 2);
			if (isWater (world.getBlockState (pos.north ())))
				slideheightN = slideheightN * (underWater ? 3 : 2);
			if (isWater (world.getBlockState (pos.south ())))
				slideheightS = slideheightS * (underWater ? 3 : 2);


			if (!world.isSideSolid (pos.east (),EnumFacing.WEST) && depthScan (world,pos.east ()) >= slideheightE)
				pot.add (pos.east ());
			if (!world.isSideSolid (pos.west (),EnumFacing.EAST) && depthScan (world,pos.west ()) >= slideheightW)
				pot.add (pos.west ());
			if (!world.isSideSolid (pos.north (),EnumFacing.SOUTH) && depthScan (world,pos.north ()) >= slideheightN)
				pot.add (pos.north ());
			if (!world.isSideSolid (pos.south (),EnumFacing.NORTH) && depthScan (world,pos.south ()) >= slideheightS)
				pot.add (pos.south ());

			if (pot.size () > 0) {
				Collections.shuffle (pot);
				return pot.get (0);
			}
		}

		return null;
	}

	private int depthScan (World world,BlockPos pos) {
		BlockPos scanPos;
		for (int i = 1; i < 255; i++) {
			scanPos = pos.down (i);
			if (!canFallInto (world,scanPos))
				return i - 1;
		}
		return 0;
	}

	private boolean isWater (IBlockState state) {
		return state.getBlock () == Blocks.WATER || state.getBlock () == Blocks.FLOWING_WATER;
	}

	protected float getFallChance () {
		return 1f;
	}

	protected boolean hasGravity () {
		return true;
	}
}