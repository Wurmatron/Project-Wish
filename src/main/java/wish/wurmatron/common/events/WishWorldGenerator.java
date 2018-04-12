package wish.wurmatron.common.events;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.world.RandomizeRockTypeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WishWorldGenerator implements IWorldGenerator {

	private static List <OreGen> oreGeneration = new ArrayList <> ();
	private static int maxWeight = 0;

	public static void addOreGen (IBlockState state,int maxVeinSize,int minY,int maxY,int weight) {
		if (weight > maxWeight)
			maxWeight = weight;
		OreGen gen = new OreGen (state,maxVeinSize,minY,maxY,weight);
		oreGeneration.add (gen);
	}

	@Override
	public void generate (Random random,int chunkX,int chunkZ,World world,IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) {
		generateGround (random,chunkX,chunkZ,world);
		if (oreGeneration.size () > 0)
			for(int i = 0; i < 1 + random.nextInt (3) ; i++)
			oreGeneration.get (random.nextInt (oreGeneration.size ())).generate (world,random,(chunkX * 16),(chunkZ * 16));
	}

	private StoneType.RockType getGenType (World world,BlockPos pos) {
		int[] biomeMeta = RandomizeRockTypeEvent.rockLayerMeta.get (Biome.getIdForBiome (world.getBiome (pos)));
		if (biomeMeta != null && biomeMeta.length > 0) {
			int shift = ((pos.getY () / 40) + biomeMeta[7]) % RandomizeRockTypeEvent.STONE_SHIFT.length;
			return getStoneTypeFromMeta (RandomizeRockTypeEvent.STONE_SHIFT[shift]);
		}
		return StoneType.RockType.Igneous;
	}

	private StoneType.RockType getStoneTypeFromMeta (IBlockState state) {
		if (WishBlocks.stoneIgneous == state.getBlock ())
			return StoneType.RockType.Igneous;
		else if (WishBlocks.stoneMetamorphic == state.getBlock ())
			return StoneType.RockType.Metamorphic;
		else
			return StoneType.RockType.Sedimentary;
	}

	private void generateGround (Random rand,int chunkX,int chunkZ,World world) {
		for (int i = 0; i < rand.nextInt (Settings.rocksPerChunk); i++) {
			int x = chunkX * 16 + rand.nextInt (16) + 8;
			int z = chunkZ * 16 + rand.nextInt (16) + 8;
			BlockPos pos = world.getTopSolidOrLiquidBlock (new BlockPos (x,0,z)).down ();
			Block block = world.getBlockState (pos).getBlock ();
			if (isRockSpawnable (block))
				if (world.isAirBlock (pos.up ()))
					world.setBlockState (pos.up (),getRock (WorldGenOreHelper.getRockType (world,pos)).getStateFromMeta (WorldGenOreHelper.getMeta (world,pos)),3);
		}
		for (int i = 0; i < rand.nextInt (Settings.rocksPerChunk * 2); i++) {
			int x = chunkX * 16 + rand.nextInt (16) + 8;
			int z = chunkZ * 16 + rand.nextInt (16) + 8;
			BlockPos pos = world.getTopSolidOrLiquidBlock (new BlockPos (x,0,z)).down ();
			Block block = world.getBlockState (pos).getBlock ();
			if (isRockSpawnable (block))
				if (world.isAirBlock (pos.up ()))
					world.setBlockState (pos.up (),WishBlocks.stick.getDefaultState (),3);
		}
	}

	private Block getRock (Block block) {
		if (block == WishBlocks.stoneMetamorphic)
			return WishBlocks.rockMetamorphic;
		else if (block == WishBlocks.stoneSedimentary)
			return WishBlocks.rockSedimentary;
		return WishBlocks.rockIgneous;
	}

	private boolean isRockSpawnable (Block block) {
		return block == Blocks.DIRT || block == Blocks.GRASS || block instanceof BlockRockType;
	}

	public static class OreGen {

		WorldGenMinable pluton;
		IBlockState state;
		int minY;
		int maxY;
		int weight;

		public OreGen (IBlockState state,int maxVeinSize,int minY,int maxY,int weight) {
			this.pluton = new WorldGenMinable (state,maxVeinSize,new WorldGenOreHelper.StonePredicate ());
			this.state = state;
			this.minY = Math.min (minY,maxY);
			this.maxY = Math.max (minY,maxY);
			this.weight = weight;
		}

		public void generate (World world,Random rand,int x,int z) {
			if (rand.nextInt (100) < weight) {
				int y = minY != maxY ? minY + rand.nextInt (maxY - minY) : minY;
				pluton.generate (world,rand,new BlockPos (x + (rand.nextBoolean () ? 8 : -8),y,z + (rand.nextBoolean () ? 8 : -8)));
			}
		}
	}
}
