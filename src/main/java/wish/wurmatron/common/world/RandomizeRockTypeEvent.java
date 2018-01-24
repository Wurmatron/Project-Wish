package wish.wurmatron.common.world;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.blocks.BlockGravity;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.utils.LogHandler;

import java.util.HashMap;
import java.util.Random;

@Mod.EventBusSubscriber
public class RandomizeRockTypeEvent {

	private static final Random rand = new Random ();
	private static final IBlockState[] STONE_SHIFT = new IBlockState[] {WishBlocks.stoneSedimentary.getDefaultState (),WishBlocks.stoneIgneous.getDefaultState (),WishBlocks.stoneMetamorphic.getDefaultState ()};
	private static final IBlockState[] SAND_SHIFT = new IBlockState[] {WishBlocks.sandSedimentary.getDefaultState (),WishBlocks.sandIgneous.getDefaultState (),WishBlocks.sandMetamorphic.getDefaultState ()};
	private static final IBlockState[] GRAVEL_SHIFT = new IBlockState[] {WishBlocks.gravelSedimentary.getDefaultState (),WishBlocks.gravelIgneous.getDefaultState (),WishBlocks.gravelMetamorphic.getDefaultState ()};
	private static HashMap <Integer, int[]> rockLayerMeta = new HashMap <> ();

	@SubscribeEvent
	public void onPopulateChunkEvent (PopulateChunkEvent.Pre e) {
		Chunk chunk = e.getWorld ().getChunkFromChunkCoords (e.getChunkX (),e.getChunkZ ());
		int[] meta = getMeta (chunk);
		int layer = 0;
		for (int x = 0; x < 16; ++x)
			for (int z = 0; z < 16; ++z) {
				for (int y = 0; y < (chunk.getHeightValue (x,z)); y++) {
					int shift = (layer + meta[7]) % STONE_SHIFT.length;
					if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockStone)
						chunk.setBlockState (new BlockPos (x,y,z),STONE_SHIFT[shift % STONE_SHIFT.length].withProperty (BlockRockType.TYPE,meta[layer]));
					else if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockSand)
						chunk.setBlockState (new BlockPos (x,y,z),SAND_SHIFT[shift % SAND_SHIFT.length].withProperty (BlockRockType.TYPE,meta[layer]));
					else if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockGravel)
						chunk.setBlockState (new BlockPos (x,y,z),GRAVEL_SHIFT[shift % GRAVEL_SHIFT.length].withProperty (BlockRockType.TYPE,meta[layer]));
					layer = y / 40;
				}
			}
		chunk.markDirty ();
	}

	private int[] getMeta (Chunk chunk) {
		if (chunk != null) {
			rand.setSeed (chunk.getWorld ().getSeed ());
			int name = (int) chunk.getBiomeArray ()[0];
			if (rockLayerMeta.containsKey (name))
				return rockLayerMeta.get (name);
			else
				rockLayerMeta.put (name,new int[] {chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (2)});
		}
		return new int[] {0,0,0,0,0,0,0,0};
	}
}
