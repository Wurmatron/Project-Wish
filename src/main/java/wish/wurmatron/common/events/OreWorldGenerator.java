package wish.wurmatron.common.events;

import com.ferreusveritas.dynamictrees.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.WishBlock;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.utils.LogHandler;
import wish.wurmatron.common.world.RandomizeRockTypeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OreWorldGenerator implements IWorldGenerator {

	private static List <OreGen> oreGeneration = new ArrayList <> ();
	private static int maxWeight = 0;

	public static void add (OreGen gen) {
		oreGeneration.add (gen);
		if (gen.oreType.getRarity () > maxWeight)
			maxWeight = gen.oreType.getRarity ();
	}

	@Override
	public void generate (Random random,int chunkX,int chunkZ,World world,IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) {
		int minWeight = random.nextInt (maxWeight);
		boolean run = false;
		for (int count = 0; count < Settings.oreRarity; count++) {
			OreGen check = oreGeneration.get (random.nextInt (oreGeneration.size ()));
			int y = check.minY != check.maxY ? check.minY + random.nextInt (check.maxY - check.minY) : check.minY;
			StoneType.RockType type = getGenType (world,new BlockPos (chunkX * 16,y,(chunkZ * 16)));
			if (canGen (world,check,minWeight,type) && random.nextInt (5) < check.oreType.getRarity ())
				check.helper.generate (world,random,new BlockPos (chunkX * 16 + 8,y,chunkZ * 16 + 8));
			if (!run) {
				generateGround (random,chunkX,chunkZ,world);
				run = true;
			}
		}
	}

	private boolean canGen (World world,OreGen ore,int currentWeight,StoneType.RockType genType) {
		if (ore.oreType.getRarity () < currentWeight)
			return false;
		if (ore.blacklistDim != null)
			for (int dim : ore.blacklistDim)
				if (world.provider.getDimension () == dim)
					return false;
		boolean valid = false;
		for (StoneType.RockType type : ore.oreType.getOreType ())
			if (type.equals (genType))
				valid = true;
		return ore.helper.check (ore.helper.oreBlock) && valid;
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

	public class OreGen {

		private int[] blacklistDim;
		private int minY;
		private int maxY;
		private OreType oreType;
		private WorldGenOreHelper helper;

		public OreGen (int[] blacklistDim,int minY,int maxY,OreType oreType,int maxVeinSize,IBlockState state) {
			this.blacklistDim = blacklistDim;
			this.minY = minY;
			this.maxY = maxY;
			this.oreType = oreType;
			helper = new WorldGenOreHelper (oreType,state,maxVeinSize);
		}
	}
}
