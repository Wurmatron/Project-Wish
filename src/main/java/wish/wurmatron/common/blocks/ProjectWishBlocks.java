package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.utils.WishOre;

import java.util.ArrayList;

public class ProjectWishBlocks extends WishBlocks {

    public static void registerBlocks () {
        createAndRegisterOres ();
    }

    private static void createAndRegisterOres () {
        WishBlocks.oreBlocks = new ArrayList <> ();
        for (Ore ore : WishAPI.oreRegistry.getOres ()) {
            Block block = new TileOre (ore).setUnlocalizedName ("ore" + ore.getUnlocalizedName ());
            Block b = Registry.registerBlock (block,"ore" + ore.getUnlocalizedName (),(WishOre) ore);
            WishBlocks.oreBlocks.add (b);
        }
    }
}
