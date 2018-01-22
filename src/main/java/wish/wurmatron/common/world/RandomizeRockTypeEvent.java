package wish.wurmatron.common.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.blocks.WishBlock;
import wish.wurmatron.common.blocks.stone.BlockRockType;

import java.util.HashMap;
import java.util.Random;

@Mod.EventBusSubscriber
public class RandomizeRockTypeEvent {

	private static HashMap <Integer, int[]> rockLayerMeta = new HashMap <> ();
	private static final Random rand = new Random ();
	private static final int LAYER_HEIGHT = 40;
	private static final Block[] STONE_SHIFT = new Block[] {WishBlocks.stoneSedimentary,WishBlocks.stoneIgneous,WishBlocks.stoneMetamorphic};
	private static final Block[] SAND_SHIFT = new Block[] {WishBlocks.sandSedimentary,WishBlocks.sandIgneous,WishBlocks.sandMetamorphic};
	private static final Block[] GRAVEL_SHIFT = new Block[] {WishBlocks.gravelSedimentary,WishBlocks.gravelIgneous,WishBlocks.gravelMetamorphic};

	@SubscribeEvent
	public void onPopulateChunkEvent (PopulateChunkEvent.Pre e) {
		Chunk chunk = e.getWorld ().getChunkFromChunkCoords (e.getChunkX (),e.getChunkZ ());
		int[] meta = getMeta (chunk);
		for (int x = 0; x < 16; ++x)
			for (int z = 0; z < 16; ++z)
				for (int y = 0; y < (chunk.getHeightValue (x,z) + 10); y++)
					if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockStone)
						chunk.setBlockState (new BlockPos (x,y,z),STONE_SHIFT[((y / LAYER_HEIGHT) + meta[7]) % STONE_SHIFT.length].getDefaultState ().withProperty (BlockRockType.TYPE,meta[(y / LAYER_HEIGHT)]));
					else if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockSand)
						chunk.setBlockState (new BlockPos (x,y,z),SAND_SHIFT[((y / LAYER_HEIGHT) + meta[7]) % SAND_SHIFT.length].getDefaultState ().withProperty (BlockRockType.TYPE,meta[(y / LAYER_HEIGHT)]));
					else if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockGravel)
						chunk.setBlockState (new BlockPos (x,y,z),GRAVEL_SHIFT[((y / LAYER_HEIGHT) + meta[7]) % GRAVEL_SHIFT.length].getDefaultState ().withProperty (BlockRockType.TYPE,meta[(y / LAYER_HEIGHT)]));
		chunk.markDirty ();
	}

	private int[] getMeta (Chunk chunk) {
		if (chunk != null) {
			rand.setSeed (chunk.getWorld ().getSeed ());
			Biome biome = chunk.getBiome (chunk.getPos ().getBlock (0,0,0),chunk.getWorld ().getBiomeProvider ());
			int name = Biome.getIdForBiome (biome);
			if (rockLayerMeta.containsKey (name))
				return rockLayerMeta.get (name);
			else
				rockLayerMeta.put (name,new int[] {chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (2)});
		}
		return new int[] {0,0,0,0,0,0,0,0};
	}
}
