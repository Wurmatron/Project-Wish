package wish.wurmatron.common.world;


import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import wish.wurmatron.api.blocks.WishBlocks;

import javax.annotation.Nullable;
import java.util.List;

/**
	* Basic Chunk Provider for Default (Wish) World Type
	*/
public class WishChunkProvider implements IChunkGenerator {

		private World worldObj;

		public WishChunkProvider(World world) {
				this.worldObj = world;
		}

		@Override
		public Chunk provideChunk(int x2, int z2) {
				ChunkPrimer primer = new ChunkPrimer();
				// Generates the layer of stone
				for (int x = 0; x < 16; x++)
						for (int y = 1; y < 120; y++)
								for (int z = 0; z < 16; z++) {
										if (y <= 1) primer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
										else if (y <= 30) primer.setBlockState(x, y, z, WishBlocks.stoneMetamorphic.getDefaultState());
										else if (y > 30 && y <= 60) primer.setBlockState(x, y, z, WishBlocks.stoneSedimentary.getDefaultState());
										else if (y > 60 && y <= 90) primer.setBlockState(x, y, z, WishBlocks.stoneIgneous.getDefaultState());
										else if (y > 90 && y <= 120) primer.setBlockState(x, y, z, WishBlocks.stoneIgneous.getStateFromMeta(1));
								}
				Chunk chunk = new Chunk(worldObj, primer, x2, z2);
				chunk.generateSkylightMap();
				return chunk;
		}

		@Override
		public void populate(int x, int z) {}

		@Override
		public boolean generateStructures(final Chunk chunkIn, final int x, final int z) {
				return false;
		}

		@Override
		public List<Biome.SpawnListEntry> getPossibleCreatures(final EnumCreatureType creatureType, final BlockPos pos) {
				return null;
		}

		@Nullable
		@Override
		public BlockPos getStrongholdGen(final World worldIn, final String structureName, final BlockPos position, final boolean p_180513_4_) {
				return null;
		}

		@Override
		public void recreateStructures(final Chunk chunkIn, final int x, final int z) {}
}
