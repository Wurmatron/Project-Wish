package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 Gives gravity to a block
 */
public class BlockGravity extends BlockFalling {

	private static final Random rand = new Random ();

	public BlockGravity (Material m) {
		super (m);
	}

	@Override
	public void updateTick (World world,BlockPos pos,IBlockState state,Random rand) {
		if (!world.isRemote) {
			super.updateTick (world,pos,state,rand);
			checkSides (world,pos,state);
		}
	}

	private void checkSides (World world,BlockPos pos,IBlockState state) {
		if (!world.isRemote) {
			BlockPos newLocation = checkSide (world,pos,false);
			if (newLocation != null) {
				world.setBlockState (newLocation,world.getBlockState (pos));
				world.setBlockToAir (pos);
			}
		}
	}

	private static BlockPos checkSide (World world,BlockPos pos,boolean downCheck) {
		ArrayList <BlockPos> possibleLocations = new ArrayList <> ();
		if (!(rand.nextInt (4) % 4 == 1)) {
			if (isValidMove (world,pos.east ()))
				if (downCheck || isValidMove (world,pos.east ().down ()))
					possibleLocations.add (pos.east ().down ());
			if (isValidMove (world,pos.west ()))
				if (downCheck || isValidMove (world,pos.west ().down ()))
					possibleLocations.add (pos.west ().down ());
			if (isValidMove (world,pos.south ()))
				if (downCheck || isValidMove (world,pos.south ().down ()))
					possibleLocations.add (pos.south ().down ());
			if (isValidMove (world,pos.north ()))
				if (downCheck || isValidMove (world,pos.north ().down ()))
					possibleLocations.add (pos.north ().down ());
			if (possibleLocations.size () > 0) {
				Collections.shuffle (possibleLocations);
				BlockPos location = possibleLocations.get (0);
				if (!world.isAirBlock (location.up ()))
					world.setBlockToAir (location.up ());
				if (!world.isAirBlock (location))
					world.setBlockToAir (location);
				return location;
			}
		}
		if (isValidMove (world,pos.down ())) {
			if (!world.isAirBlock (pos.down ()))
				world.setBlockToAir (pos.down ());
			return pos.down ();
		}
		return null;
	}

	private static boolean isValidMove (World world,BlockPos pos) {
		return world.isAirBlock (pos) || canFallThrough (world.getBlockState (pos));
	}

	public int tickRate (World worldIn) {
		return 2;
	}

	public static boolean canFallThrough (IBlockState state) {
		Block block = state.getBlock ();
		Material material = state.getMaterial ();
		return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA || material == Material.PLANTS || block == Blocks.TALLGRASS;
	}
}