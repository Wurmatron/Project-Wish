package wish.wurmatron.common.world;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.blocks.BlockOre;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.utils.LogHandler;

import java.util.HashMap;
import java.util.Random;

@Mod.EventBusSubscriber
public class RandomizeRockTypeEvent {

	private static Random rand;
	private static HashMap <String, int[]> rockLayers = new HashMap <> ();
	private static HashMap <String, Integer> layerHeightCache = new HashMap <> ();

	public static int[] getRockData (Biome biome) {
		if (rockLayers.containsKey (biome.getRegistryName ().toString ()))
			return rockLayers.get (biome.getRegistryName ().toString ());
		int size = (256 / (findLayerHeight (biome))) + 1;
		int[] newRockData = new int[size];
		for (int index = 0; index < size - 2; index++)
			newRockData[index] = rand.nextInt (9);
		newRockData[size - 1] = getRockType (biome);
		rockLayers.put (biome.getRegistryName ().toString (),newRockData);
		return newRockData;
	}

	// TODO Find This not just random
	private static int getRockType (Biome biome) {
		int num = rand.nextInt (1);
		boolean t = rand.nextBoolean ();
		if (t)
			return 1 + num;
		else
			return 1 - num;
	}

	public static int getRockMeta (World world,BlockPos pos) {
		Biome biome = world.getBiome (pos);
		return getRockData (biome)[calculateShift (biome,pos)];
	}

	public static IBlockState getStoneForPos (World world,BlockPos pos) {
		int meta = getRockMeta (world,pos);
		int[] rockData = getRockData (world.getBiome (pos));
		int rockType = rockData[rockData.length - 1];
		switch (rockType) {
			case 0: {
				return WishBlocks.stoneIgneous.getDefaultState ().withProperty (BlockRockType.TYPE,meta);
			}
			case 1: {
				return WishBlocks.stoneMetamorphic.getDefaultState ().withProperty (BlockRockType.TYPE,meta);
			}
			case 2: {
				return WishBlocks.stoneSedimentary.getDefaultState ().withProperty (BlockRockType.TYPE,meta);
			}
		}
		LogHandler.debug ("Unable to find rocktype for " + pos);
		return Blocks.AIR.getDefaultState ();
	}

	private static void setBlock (Chunk chunk,World world,BlockPos pos,int type) {
		if (chunk.getBlockState (pos).getBlock () instanceof BlockStone || chunk.getBlockState (pos).getBlock () == Blocks.BEDROCK || Settings.overrideOre && chunk.getBlockState (pos).getBlock ().getUnlocalizedName ().contains ("ore") && !(chunk.getBlockState (pos).getBlock () instanceof BlockOre))
			setStone (chunk,world,pos,type);
		else if (chunk.getBlockState (pos).getBlock () instanceof BlockGravel)
			setGravel (chunk,world,pos,type);
		else if (chunk.getBlockState (pos).getBlock () instanceof BlockSand)
			setSand (chunk,world,pos,type);
	}

	private static void setStone (Chunk chunk,World world,BlockPos pos,int type) {
		int meta = getRockData (chunk.getBiome (pos,world.getBiomeProvider ()))[calculateShift (chunk.getBiome (pos,world.getBiomeProvider ()),pos)];
		switch (type) {
			case 0: {
				chunk.setBlockState (pos,WishBlocks.stoneIgneous.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
			case 1: {
				chunk.setBlockState (pos,WishBlocks.stoneMetamorphic.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
			case 2: {
				chunk.setBlockState (pos,WishBlocks.stoneSedimentary.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
		}
	}

	private static void setGravel (Chunk chunk,World world,BlockPos pos,int type) {
		int meta = getRockData (chunk.getBiome (pos,world.getBiomeProvider ()))[calculateShift (chunk.getBiome (pos,world.getBiomeProvider ()),pos)];
		switch (type) {
			case 0: {
				chunk.setBlockState (pos,WishBlocks.gravelIgneous.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
			case 1: {
				chunk.setBlockState (pos,WishBlocks.gravelMetamorphic.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
			case 2: {
				chunk.setBlockState (pos,WishBlocks.gravelSedimentary.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
		}
	}

	private static void setSand (Chunk chunk,World world,BlockPos pos,int type) {
		int meta = getRockData (chunk.getBiome (pos,world.getBiomeProvider ()))[calculateShift (chunk.getBiome (pos,world.getBiomeProvider ()),pos)];
		switch (type) {
			case 0: {
				chunk.setBlockState (pos,WishBlocks.sandIgneous.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
			case 1: {
				chunk.setBlockState (pos,WishBlocks.sandMetamorphic.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
			case 2: {
				chunk.setBlockState (pos,WishBlocks.sandSedimentary.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
				break;
			}
		}
	}

	private static int calculateShift (Biome biome,BlockPos pos) {
		return pos.getY () / findLayerHeight (biome);
	}

	private static int findLayerHeight (Biome biome) {
		if (layerHeightCache.containsKey (biome.getRegistryName ().toString ()))
			return layerHeightCache.get (biome.getRegistryName ().toString ());
		float adjBiomeHeight = Math.abs (biome.getBaseHeight () * 8); // 0 - 16, .25
		float adjHeightVariation = biome.getHeightVariation () / 2;   // 0 - 1, .025
		if (adjBiomeHeight == 0)
			adjBiomeHeight = 8;
		int height;
		if (Math.abs (adjBiomeHeight) > 5 && adjHeightVariation > .1)
			height = Math.abs ((int) ((256 / adjBiomeHeight) + (adjHeightVariation * 10)));
		else if (Math.abs (adjBiomeHeight) < .5 && adjBiomeHeight <= .2)
			height = (int) Math.abs ((8 * adjBiomeHeight) / (adjBiomeHeight * 2));
		else
			height = Math.abs ((int) ((256 / (adjBiomeHeight)) - ((1 - adjHeightVariation) * 1000))) / 30;
		if (height >= 24)
			height = 24;
		int layerHeight = 256 / (height / 4);
		if (layerHeight > 30)
			layerHeight = 30;
		int outputHeight = layerHeight > 0 ? layerHeight : 1;
		layerHeightCache.put (biome.getRegistryName ().toString (),outputHeight);
		return outputHeight;
	}

	@SubscribeEvent
	public void onPopulateChunkEvent (PopulateChunkEvent.Post e) {
		if (rand == null)
			rand = e.getRand ();
		Chunk chunk = e.getWorld ().getChunkFromChunkCoords (e.getChunkX (),e.getChunkZ ());
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				for (int y = 0; y < chunk.getHeightValue (x,z); y++) {
					BlockPos currentPos = new BlockPos (x,y,z);
					int[] rockLayerData = getRockData (e.getWorld ().getBiome (currentPos));
					setBlock (chunk,e.getWorld (),currentPos,rockLayerData[rockLayerData.length - 1]);
				}
		chunk.markDirty ();
	}
}
