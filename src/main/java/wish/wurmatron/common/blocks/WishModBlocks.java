package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.BlockIgneous;
import wish.wurmatron.common.blocks.stone.BlockMetamorphic;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.blocks.stone.BlockSedimentary;
import wish.wurmatron.common.items.ItemBlockRockType;
import wish.wurmatron.common.utils.Registry;

/**
 Initalizes all the mods blocks for use in WishBlocks

 @see WishBlocks */
public class WishModBlocks {

	public static void registerBlocks () {
		WishBlocks.stoneSedimentary = register (new BlockSedimentary (Material.ROCK),StoneType.RockType.Sedimentary);
		WishBlocks.stoneMetamorphic = register (new BlockMetamorphic (Material.ROCK),StoneType.RockType.Metamorphic);
		WishBlocks.stoneIgneous = register (new BlockIgneous (Material.ROCK),StoneType.RockType.Igneous);
	}

	private static Block register (Block block,StoneType.RockType type) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),type);
		return block;
	}

}
