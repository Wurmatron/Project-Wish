package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import wish.wurmatron.api.event.BlockFallEvent;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.entity.EntityGravityBlock;

import java.util.*;

public class BlockGravity extends BlockFalling {

	private static final double slideChange = 0.9;

	public BlockGravity (Material material) {
		super (material);
	}

	@Override
	public int tickRate (World world) {
		return Settings.gravityUpdate;
	}

	@Override
	public void updateTick (World world,BlockPos pos,IBlockState state,Random rand) {
		if (!world.isRemote) {
			checkFallable (world,pos,state);
		}
	}

	private void checkFallable (World world,BlockPos pos,IBlockState state) {
		if (!world.isRemote && pos.getY () > 0)
			if (!fallInstantly && world.isAreaLoaded (pos.add (-32,-32,-32),pos.add (32,32,32))) {
				if (canFallThrough (world.getBlockState (pos.down ())) && pos.getY () > 0) {
					if (MinecraftForge.EVENT_BUS.post (new BlockFallEvent.Pre (world,pos,state)))
						fall (world,pos,state);
				} else {
					BlockPos slidePos = canSlide (world,pos);
					if (slidePos != null && MinecraftForge.EVENT_BUS.post (new BlockFallEvent.Pre (world,pos,state))) {
						world.setBlockToAir (pos);
						world.setBlockState (slidePos,getDefaultState ());
					}
				}
			} else {
				world.setBlockToAir (pos);
				BlockPos blockpos;
				for (blockpos = pos.down (); (canFallThrough (world.getBlockState (blockpos))) && (blockpos.getY () > 0); blockpos = blockpos.down ())
					if (blockpos.getY () > 0)
						world.setBlockState (blockpos.up (),getBlockState ().getBaseState ());
			}
	}

	private void fall (World world,BlockPos pos,IBlockState state) {
		EntityGravityBlock entity = new EntityGravityBlock (world,pos.getX () + .5D,pos.getY (),pos.getZ () + .5D,state);
		world.spawnEntity (entity);
		MinecraftForge.EVENT_BUS.post (new BlockFallEvent.Post (world,pos,state,entity));
	}

	public static boolean canFallThrough (IBlockState state) {
		Block block = state.getBlock ();
		Material material = state.getMaterial ();
		return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA || material == Material.LEAVES;
	}

	public static BlockPos canSlide (World world,BlockPos pos) {
		if (pos.getY () == 0 || world.rand.nextFloat () < 1 - slideChange || !world.isAirBlock (pos.up ()))
			return null;
		List <BlockPos> possibleLoc = new ArrayList <> ();
		if (!world.isSideSolid (pos.north (),EnumFacing.NORTH) && slideHeight (world,pos.north ()) >= 2)
			possibleLoc.add (pos.north ());
		if (!world.isSideSolid (pos.south (),EnumFacing.SOUTH) && slideHeight (world,pos.south ()) >= 2)
			possibleLoc.add (pos.south ());
		if (!world.isSideSolid (pos.east (),EnumFacing.EAST) && slideHeight (world,pos.east ()) >= 2)
			possibleLoc.add (pos.east ());
		if (!world.isSideSolid (pos.west (),EnumFacing.WEST) && slideHeight (world,pos.west ()) >= 2)
			possibleLoc.add (pos.west ());
		if (possibleLoc.size () - 1 > 0) {
			Collections.shuffle (possibleLoc);
			return possibleLoc.get (world.rand.nextInt (possibleLoc.size () - 1));
		}
		return null;
	}

	private static int slideHeight (World world,BlockPos pos) {
		BlockPos testPos;
		for (int i = 1; i < 255; i++) {
			testPos = pos.down (i);
			if (!canFallThrough (world.getBlockState (testPos)))
				return i - 1;
		}
		return 0;
	}
}