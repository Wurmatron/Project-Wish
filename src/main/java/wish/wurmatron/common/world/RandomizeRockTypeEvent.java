package wish.wurmatron.common.world;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.utils.LogHandler;

import java.util.HashMap;
import java.util.Random;

@Mod.EventBusSubscriber
public class RandomizeRockTypeEvent {

	private static Random rand;
	private static HashMap <String, int[]> rockLayers = new HashMap <> ();
	private static HashMap<String, Integer> layerHeightCache = new HashMap <> ();

	public static int[] getRockData (Biome biome) {
		if (rockLayers.containsKey (biome.getBiomeName ()))
			return rockLayers.get (biome.getBiomeName ());
		int size = (256 / (findLayerHeight (biome))) + 1;
		int[] newRockData = new int[size];
		for (int index = 0; index < size - 2; index++)
			newRockData[index] = rand.nextInt (9);
		newRockData[size - 1] = getRockType (biome);
		rockLayers.put (biome.getBiomeName (),newRockData);
		return newRockData;
	}

	// TODO Find This not just random
	private static int getRockType (Biome biome) {
		return rand.nextInt (2);
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
		if (chunk.getBlockState (pos).getBlock () instanceof BlockStone || chunk.getBlockState (pos).getBlock () == Blocks.BEDROCK)
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
			}
			case 1: {
				chunk.setBlockState (pos,WishBlocks.stoneMetamorphic.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
			case 2: {
				chunk.setBlockState (pos,WishBlocks.stoneSedimentary.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
		}
	}

	private static void setGravel (Chunk chunk,World world,BlockPos pos,int type) {
		int meta = getRockData (chunk.getBiome (pos,world.getBiomeProvider ()))[calculateShift (chunk.getBiome (pos,world.getBiomeProvider ()),pos)];
		switch (type) {
			case 0: {
				chunk.setBlockState (pos,WishBlocks.gravelIgneous.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
			case 1: {
				chunk.setBlockState (pos,WishBlocks.gravelMetamorphic.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
			case 2: {
				chunk.setBlockState (pos,WishBlocks.gravelSedimentary.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
		}
	}

	private static void setSand (Chunk chunk,World world,BlockPos pos,int type) {
		int meta = getRockData (chunk.getBiome (pos,world.getBiomeProvider ()))[calculateShift (chunk.getBiome (pos,world.getBiomeProvider ()),pos)];
		switch (type) {
			case 0: {
				chunk.setBlockState (pos,WishBlocks.sandIgneous.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
			case 1: {
				chunk.setBlockState (pos,WishBlocks.sandMetamorphic.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
			case 2: {
				chunk.setBlockState (pos,WishBlocks.sandSedimentary.getDefaultState ().withProperty (BlockRockType.TYPE,meta));
			}
		}
	}

	private static int calculateShift (Biome biome,BlockPos pos) {
		return pos.getY () / findLayerHeight (biome);
	}

	private static int findLayerHeight (Biome biome) {
		if(layerHeightCache.containsKey (biome.getBiomeName ()))
			return layerHeightCache.get (biome.getBiomeName ());
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
		if(height >= 24)
			height = 24;
		int layerHeight = 256 / (height / 4);
		if(layerHeight > 40)
			layerHeight = 40;
		int outputHeight = layerHeight > 0 ? layerHeight : 1;
		layerHeightCache.put (biome.getBiomeName (), outputHeight);
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

	//	public static final IBlockState[] STONE_SHIFT = new IBlockState[] {WishBlocks.stoneSedimentary.getDefaultState (),WishBlocks.stoneIgneous.getDefaultState (),WishBlocks.stoneMetamorphic.getDefaultState ()};
	//	private static final Random rand = new Random ();
	//	private static final IBlockState[] SAND_SHIFT = new IBlockState[] {WishBlocks.sandSedimentary.getDefaultState (),WishBlocks.sandIgneous.getDefaultState (),WishBlocks.sandMetamorphic.getDefaultState ()};
	//	private static final IBlockState[] GRAVEL_SHIFT = new IBlockState[] {WishBlocks.gravelSedimentary.getDefaultState (),WishBlocks.gravelIgneous.getDefaultState (),WishBlocks.gravelMetamorphic.getDefaultState ()};
	//	public static HashMap <Integer, int[]> rockLayerMeta = new HashMap <> ();
	//
	//	public static int layerHeight = 40;
	//
	//	@SubscribeEvent
	//	public void onPopulateChunkEvent (PopulateChunkEvent.Pre e) {
	//		Chunk chunk = e.getWorld ().getChunkFromChunkCoords (e.getChunkX (),e.getChunkZ ());
	//		int layer = 0;
	//		for (int x = 0; x < 16; ++x)
	//			for (int z = 0; z < 16; ++z)
	//				for (int y = 0; y < (chunk.getHeightValue (x,z)) + 5; y++) {
	//					int[] meta = getMeta (chunk,x,z);
	//					int shift = (layer + meta[15]) % STONE_SHIFT.length;
	//					if (layer >= meta.length)
	//						layer = meta.length - 1;
	//					if (layer < 0)
	//						layer = 0;
	//					int shiftMeta = meta[layer];
	//					if (shift > 3 || shift < 0)
	//						shift = 0;
	//					if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockStone || chunk.getBlockState (x,y,z).getBlock () == Blocks.BEDROCK)
	//						chunk.setBlockState (new BlockPos (x,y,z),STONE_SHIFT[shift].withProperty (BlockRockType.TYPE,shiftMeta));
	//					else if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockSand)
	//						chunk.setBlockState (new BlockPos (x,y,z),SAND_SHIFT[shift].withProperty (BlockRockType.TYPE,shiftMeta));
	//					else if (chunk.getBlockState (x,y,z).getBlock () instanceof BlockGravel)
	//						chunk.setBlockState (new BlockPos (x,y,z),GRAVEL_SHIFT[shift].withProperty (BlockRockType.TYPE,shiftMeta));
	//					layer = (int) (y / ((layerHeight * (getHeight (e.getWorld (),x,z))))/10);
	//				}
	//		chunk.markDirty ();
	//	}
	//
	//	private float getHeight (World world,int x,int z) {
	//		return world.getBiome (new BlockPos (x,0,z)).getBaseHeight ();
	//	}
	//
	//	public int[] getMeta (Chunk chunk,int x,int z) {
	//		if (chunk != null) {
	//			rand.setSeed (chunk.getWorld ().getSeed ());
	//			int index = x * z;
	//			index = index < 0 ? 0 : index;
	//			index = index > 255 ? 255 : index;
	//			int name = (int) chunk.getBiomeArray ()[index];
	//			if (rockLayerMeta.containsKey (name))
	//				return rockLayerMeta.get (name);
	//			else
	//				rockLayerMeta.put (name,new int[] {chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (9),chunk.getWorld ().rand.nextInt (2)});
	//		}
	//		return new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//	}
	//
	//	public IBlockState getState (World world,BlockPos pos) {
	//		return STONE_SHIFT[(((pos.getY () / layerHeight) + getMeta (world.getChunkFromBlockCoords (pos),0,0)[7]) % STONE_SHIFT.length) % STONE_SHIFT.length];
	//	}
}
