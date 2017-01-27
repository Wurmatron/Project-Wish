package wish.wurmatron.common.world;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderOverworld;

public class WishChunkProvider extends ChunkProviderOverworld {
		public WishChunkProvider(final World worldIn, final long seed, final boolean mapFeaturesEnabledIn, final String p_i46668_5_) {
				super(worldIn, seed, mapFeaturesEnabledIn, p_i46668_5_);
		}

		@Override
		public void replaceBiomeBlocks(final int x, final int z, final ChunkPrimer primer, final Biome[] biomesIn) {}
}
