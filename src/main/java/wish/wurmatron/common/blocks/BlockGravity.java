package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import wish.wurmatron.api.event.BlockFallEvent;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.entity.EntityGravityBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockGravity extends BlockFalling {

	public BlockGravity (Material material) {
		super (material);
	}

	@Override
	public int tickRate (World world) {
		return Settings.gravityUpdate;
	}

	@Override
	public void updateTick (World world,BlockPos pos,IBlockState state,Random rand) {
		if (!world.isRemote && hasGravity ())
			checkFallable (world,pos,state);
	}

	protected boolean hasGravity () {
		return true;
	}

	private void checkFallable (World world,BlockPos pos,IBlockState state) {
		if (!world.isRemote && pos.getY () > 0)
			if (!fallInstantly && world.isAreaLoaded (pos.add (-32,-32,-32),pos.add (32,32,32))) {
				BlockFallEvent.Pre event = new BlockFallEvent.Pre (world,pos,state);
				MinecraftForge.EVENT_BUS.post (event);
				if (event.isCanceled ())
					return;
				if (canMove (world,pos) && canFallThrough (world.getBlockState (pos.down ())) && pos.getY () > 0) {
					fall (world,pos,state);
				} else {
					BlockPos slidePos = canSlide (world,pos);
					if (canMove (world,pos) && slidePos != null) {
						world.setBlockToAir (pos);
						world.setBlockState (slidePos,state);
					}
				}
			} else {
				world.setBlockToAir (pos);
				BlockPos blockpos;
				for (blockpos = pos.down (); (canFallThrough (world.getBlockState (blockpos))) && (blockpos.getY () > 0); blockpos = blockpos.down ())
					if (blockpos.getY () > 0)
						world.setBlockState (blockpos.up (),state);
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

	private static BlockPos canSlide (World world,BlockPos pos) {
		if (pos.getY () == 0 || !world.isAirBlock (pos.up ()))
			return null;
		List <BlockPos> possibleLoc = new ArrayList <> ();
		for (int i = 2; i < 6; i++)
			if (!world.isSideSolid (pos.offset (EnumFacing.getFront (i)),EnumFacing.getFront (i)) && slideHeight (world,pos.offset (EnumFacing.getFront (i))) >= 2)
				possibleLoc.add (pos.offset (EnumFacing.getFront (i)));
		if (possibleLoc.size () - 1 > 0) {
			Collections.shuffle (possibleLoc);
			return possibleLoc.get (world.rand.nextInt (possibleLoc.size () - 1));
		} else if (possibleLoc.size () == 1)
			return possibleLoc.get (0);
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

	private boolean canMove (World world,BlockPos pos) {
		return world.getBlockState (pos.up ()).getBlock () != Blocks.AIR && world.rand.nextFloat () < 1 - getFallChance () && !isSupported (world,pos);
	}

	protected float getFallChance () {
		return 0.9f;
	}

	private boolean isSupported (World world,BlockPos pos) {
		return false;
	}
}