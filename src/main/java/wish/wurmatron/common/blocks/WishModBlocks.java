package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.api.world.TreeType;
import wish.wurmatron.common.blocks.stone.*;
import wish.wurmatron.common.blocks.terra.*;
import wish.wurmatron.common.tile.TileOre;
import wish.wurmatron.common.utils.Registry;

import java.util.Arrays;

/**
 Initalizes all the mods blocks for use in WishBlocks

 @see WishBlocks */
public class WishModBlocks {

	public static void registerBlocks () {
		// Rocks
		WishBlocks.stoneSedimentary = register (new BlockStone (Material.ROCK,9).setUnlocalizedName ("stoneSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.stoneMetamorphic = register (new BlockStone (Material.ROCK,9).setUnlocalizedName ("stoneMetamorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.stoneIgneous = register (new BlockStone (Material.ROCK,9).setUnlocalizedName ("stoneIgneous"),StoneType.RockType.Igneous);
		WishBlocks.cobbleSedimentary = register (new BlockCobble (Material.ROCK,9).setUnlocalizedName ("cobbleSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.cobbleMetamorphic = register (new BlockCobble (Material.ROCK,9).setUnlocalizedName ("cobbleMetamorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.cobbleIgneous = register (new BlockCobble (Material.ROCK,9).setUnlocalizedName ("cobbleIgneous"),StoneType.RockType.Igneous);
		WishBlocks.smoothSedimentary = register (new BlockSmooth (Material.ROCK,9).setUnlocalizedName ("smoothSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.smoothMetamorphic = register (new BlockSmooth (Material.ROCK,9).setUnlocalizedName ("smoothMetamorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.smoothIgneous = register (new BlockSmooth (Material.ROCK,9).setUnlocalizedName ("smoothIgneous"),StoneType.RockType.Igneous);
		WishBlocks.brickSedimentary = register (new BlockBrick (Material.ROCK,9).setUnlocalizedName ("brickSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.brickMetamorphic = register (new BlockBrick (Material.ROCK,9).setUnlocalizedName ("brickMetamorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.brickIgneous = register (new BlockBrick (Material.ROCK,9).setUnlocalizedName ("brickIgneous"),StoneType.RockType.Igneous);
		WishBlocks.chiselSedimentary = register (new BlockChisel (Material.ROCK,9).setUnlocalizedName ("chiselSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.chiselMetamorphic = register (new BlockChisel (Material.ROCK,9).setUnlocalizedName ("chiselMetmorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.chiselIgneous = register (new BlockChisel (Material.ROCK,9).setUnlocalizedName ("chiselIgneous"),StoneType.RockType.Igneous);
		// Terra
		WishBlocks.dirtIgneous = register (new BlockDirt (Material.GROUND,9).setUnlocalizedName ("dirtIgneous"),StoneType.RockType.Igneous);
		WishBlocks.dirtSedimentary = register (new BlockDirt (Material.GROUND,9).setUnlocalizedName ("dirtSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.dirtMetamorphic = register (new BlockDirt (Material.GROUND,9).setUnlocalizedName ("dirtMetmorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.grassIgneous = register (new BlockGrass (Material.GROUND,9).setUnlocalizedName ("grassIgneous"),StoneType.RockType.Igneous);
		WishBlocks.grassSedimentary = register (new BlockGrass (Material.GROUND,9).setUnlocalizedName ("grassSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.grassMetamorphic = register (new BlockGrass (Material.GROUND,9).setUnlocalizedName ("grassMetmorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.sandIgneous = register (new BlockSand (Material.GROUND,9).setUnlocalizedName ("sandIgneous"),StoneType.RockType.Igneous);
		WishBlocks.sandSedimentary = register (new BlockSand (Material.GROUND,9).setUnlocalizedName ("sandSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.sandMetamorphic = register (new BlockSand (Material.GROUND,9).setUnlocalizedName ("sandMetmorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.gravelIgneous = register (new BlockGravel (Material.GROUND,9).setUnlocalizedName ("gravelIgneous"),StoneType.RockType.Igneous);
		WishBlocks.gravelSedimentary = register (new BlockGravel (Material.GROUND,9).setUnlocalizedName ("gravelSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.gravelMetamorphic = register (new BlockGravel (Material.GROUND,9).setUnlocalizedName ("gravelMetmorphic"),StoneType.RockType.Metamorphic);
		// Wood / Logs
		WishBlocks.log1 = register (new BlockWood ().setUnlocalizedName ("log1"),new TreeType[] {TreeType.ASH,TreeType.ASPEN,TreeType.BIRCH,TreeType.CEDAR});
		WishBlocks.log2 = register (new BlockWood2 ().setUnlocalizedName ("log2"),new TreeType[] {TreeType.ELM,TreeType.MAPLE,TreeType.OAK,TreeType.PINE});
		WishBlocks.log3 = register (new BlockWood3 ().setUnlocalizedName ("log3"),new TreeType[] {TreeType.SPRUCE,TreeType.SYCAMORE,TreeType.FIR,TreeType.ARCACIA});
		WishBlocks.log4 = register (new BlockWood4 ().setUnlocalizedName ("log4"),new TreeType[] {TreeType.SEQUOIA,TreeType.REDWOOD,TreeType.DOGWOOD,TreeType.CEDAR});
		WishBlocks.planks = register (new BlockPlanks ().setUnlocalizedName ("planks"),Arrays.copyOfRange (TreeType.values (),0,15));
		WishBlocks.leaves1 = register (new BlockLeaf ().setUnlocalizedName ("leaf1"),new TreeType[] {TreeType.ASH,TreeType.ASPEN,TreeType.BIRCH,TreeType.CEDAR});
		WishBlocks.leaves2 = register (new BlockLeaf2 ().setUnlocalizedName ("leaf2"),new TreeType[] {TreeType.ELM,TreeType.MAPLE,TreeType.OAK,TreeType.PINE});
		WishBlocks.leaves3 = register (new BlockLeaf3 ().setUnlocalizedName ("leaf3"),new TreeType[] {TreeType.SPRUCE,TreeType.SYCAMORE,TreeType.FIR,TreeType.ARCACIA});
		WishBlocks.leaves4 = register (new BlockLeaf4 ().setUnlocalizedName ("leaf4"),new TreeType[] {TreeType.SEQUOIA,TreeType.REDWOOD,TreeType.DOGWOOD,TreeType.CEDAR});
		WishBlocks.sapling = register (new BlockSapling ().setUnlocalizedName ("sapling"),Arrays.copyOfRange (TreeType.values (),0,15));

		// Ores
		WishBlocks.orePetalite = register (new BlockOre (Material.ROCK,OreType.PETALITE).setUnlocalizedName ("orePetalite"),OreType.PETALITE);
		// Tiles
		GameRegistry.registerTileEntity (TileOre.class,"tileOre");
	}

	private static Block register (Block block,StoneType.RockType type) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),type);
		return block;
	}

	private static Block register (Block block) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5));
		return block;
	}

	private static Block register (Block block,TreeType[] type) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),type);
		return block;
	}

	private static Block register (Block block,OreType type) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),type);
		return block;
	}
}
