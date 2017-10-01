package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.BlockCobble;
import wish.wurmatron.common.blocks.stone.BlockStone;
import wish.wurmatron.common.utils.Registry;

/**
 Initalizes all the mods blocks for use in WishBlocks

 @see WishBlocks */
public class WishModBlocks {

	public static void registerBlocks () {
		WishBlocks.stoneSedimentary = register (new BlockStone (Material.ROCK,9).setUnlocalizedName ("stoneSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.stoneMetamorphic = register (new BlockStone (Material.ROCK,9).setUnlocalizedName ("stoneMetamorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.stoneIgneous = register (new BlockStone (Material.ROCK,9).setUnlocalizedName ("stoneIgneous"),StoneType.RockType.Igneous);
		WishBlocks.cobbleSedimentary = register (new BlockCobble (Material.ROCK,9).setUnlocalizedName ("cobbleSedimentary"),StoneType.RockType.Sedimentary);
		WishBlocks.cobbleMetamorphic = register (new BlockCobble (Material.ROCK,9).setUnlocalizedName ("cobbleMetamorphic"),StoneType.RockType.Metamorphic);
		WishBlocks.cobbleIgneous = register (new BlockCobble (Material.ROCK,9).setUnlocalizedName ("cobbleIgneous"),StoneType.RockType.Igneous);
	}

	private static Block register (Block block,StoneType.RockType type) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),type);
		return block;
	}
}
