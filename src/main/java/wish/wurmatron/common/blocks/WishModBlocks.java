package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.*;
import wish.wurmatron.common.blocks.terra.BlockDirt;
import wish.wurmatron.common.blocks.terra.BlockGrass;
import wish.wurmatron.common.tile.TileOre;
import wish.wurmatron.common.utils.Registry;

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
		WishBlocks.dirtIgneous = register (new BlockDirt (Material.GROUND,9).setUnlocalizedName ("dirtIgneous"), StoneType.RockType.Igneous);
		WishBlocks.dirtSedimentary = register (new BlockDirt (Material.GROUND,9).setUnlocalizedName ("dirtSedimentary"), StoneType.RockType.Sedimentary);
		WishBlocks.dirtMetamorphic = register (new BlockDirt (Material.GROUND,9).setUnlocalizedName ("dirtMetmorphic"), StoneType.RockType.Metamorphic);
		WishBlocks.grassIgneous = register (new BlockGrass (Material.GROUND,9).setUnlocalizedName ("grassIgneous"), StoneType.RockType.Igneous);
		WishBlocks.grassSedimentary = register (new BlockGrass (Material.GROUND,9).setUnlocalizedName ("grassSedimentary"), StoneType.RockType.Sedimentary);
		WishBlocks.grassMetamorphic = register (new BlockGrass (Material.GROUND,9).setUnlocalizedName ("grassMetmorphic"), StoneType.RockType.Metamorphic);
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

	private static Block register (Block block,OreType type) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),type);
		return block;
	}
}
