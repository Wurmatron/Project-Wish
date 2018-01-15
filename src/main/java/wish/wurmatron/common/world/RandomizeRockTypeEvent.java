package wish.wurmatron.common.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.common.blocks.stone.BlockRockType;

import java.util.HashMap;
import java.util.Random;

@Mod.EventBusSubscriber
public class RandomizeRockTypeEvent {

	private static HashMap <Integer, int[]> rockLayerMeta = new HashMap <> ();
	private static final Random rand = new Random ();

	@SubscribeEvent
	public void onPopulateChunkEvent (PopulateChunkEvent.Pre e) {
		Chunk chunk = e.getWorld ().getChunkFromChunkCoords (e.getChunkX (),e.getChunkZ ());
		int[] meta = getMeta (chunk);
		for (int x = 0; x < 16; ++x)
			for (int z = 0; z < 16; ++z)
				for (int y = 0; y < 200; ++y)
					if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockRockType)
						if (y >= 90)
							chunk.setBlockState (new BlockPos (x,y,z),chunk.getBlockState (x,y,z).getBlock ().getDefaultState ().withProperty (BlockRockType.TYPE,meta[0]));
						else if (y >= 60)
							chunk.setBlockState (new BlockPos (x,y,z),chunk.getBlockState (x,y,z).getBlock ().getDefaultState ().withProperty (BlockRockType.TYPE,meta[1]));
						else if (y >= 30)
							chunk.setBlockState (new BlockPos (x,y,z),chunk.getBlockState (x,y,z).getBlock ().getDefaultState ().withProperty (BlockRockType.TYPE,meta[2]));
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
				rockLayerMeta.put (name,new int[] {chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9)});
		}
		return new int[] {0,0,0};
	}
}
