package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.registry.Registry;

public class ProjectWishBlocks extends WishBlocks {

	public static void registerBlocks () {
		createAndRegisterOres ();
	}

	private static void createAndRegisterOres () {
		for (Ore ore : WishAPI.oreRegistry.getOres ()) {
			Block block = new TileOre (ore).setUnlocalizedName ("ore" + ore.getUnlocalizedName ());
			Block b = Registry.registerBlock (block,"ore" + ore.getUnlocalizedName (),ore);
			IBlockState state = b.getDefaultState ();
		}
	}
}
