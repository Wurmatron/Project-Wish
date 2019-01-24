package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.blocks.stone.BrickWish;
import wish.wurmatron.common.blocks.stone.CobbleWish;
import wish.wurmatron.common.blocks.stone.SmoothWish;
import wish.wurmatron.common.blocks.stone.StoneWish;
import wish.wurmatron.common.blocks.terra.GravelWish;
import wish.wurmatron.common.blocks.terra.SandWish;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.tile.TileEntityOre;
import wish.wurmatron.common.utils.json.WishOre;

import java.util.ArrayList;

public class ProjectWishBlocks extends WishBlocks {

    public static void registerBlocks () {
        createAndRegisterOres ();
        registerTiles();
        WishBlocks.stoneIgneous = Registry.registerBlock(new StoneWish(Material.GROUND, 9), "stoneIgneous", RockType.Igneous);
        WishBlocks.stoneMetamorphic = Registry.registerBlock(new StoneWish(Material.GROUND, 9), "stoneMetamorphic", RockType.Metamorphic);
        WishBlocks.stoneSedimentary = Registry.registerBlock(new StoneWish(Material.GROUND, 9), "stoneSedimentary", RockType.Sedimentary);
        WishBlocks.cobbleIgneous = Registry.registerBlock(new CobbleWish(Material.GROUND, 9), "cobbleIgneous", RockType.Igneous);
        WishBlocks.cobbleMetamorphic = Registry.registerBlock(new CobbleWish(Material.GROUND, 9), "cobbleMetamorphic", RockType.Metamorphic);
        WishBlocks.cobbleSedimentary = Registry.registerBlock(new CobbleWish(Material.GROUND, 9), "cobbleSedimentary", RockType.Sedimentary);
        WishBlocks.brickIgneous = Registry.registerBlock(new BrickWish(Material.GROUND, 9), "brickIgneous", RockType.Igneous);
        WishBlocks.brickMetamorphic = Registry.registerBlock(new BrickWish(Material.GROUND, 9), "brickMetamorphic", RockType.Metamorphic);
        WishBlocks.brickSedimentary = Registry.registerBlock(new BrickWish(Material.GROUND, 9), "brickSedimentary", RockType.Sedimentary);
        WishBlocks.smoothIgneous = Registry.registerBlock(new SmoothWish(Material.GROUND, 9), "smoothIgneous", RockType.Igneous);
        WishBlocks.smoothMetamorphic = Registry.registerBlock(new SmoothWish(Material.GROUND, 9), "smoothMetamorphic", RockType.Metamorphic);
        WishBlocks.smoothSedimentary = Registry.registerBlock(new SmoothWish(Material.GROUND, 9), "smoothSedimentary", RockType.Sedimentary);
        WishBlocks.gravelIgneous = Registry.registerBlock(new GravelWish(Material.GROUND, 9), "gravelIgneous", RockType.Igneous);
        WishBlocks.gravelMetamorphic = Registry.registerBlock(new GravelWish(Material.GROUND, 9), "gravelMetamorphic", RockType.Metamorphic);
        WishBlocks.gravelSedimentary = Registry.registerBlock(new GravelWish(Material.GROUND, 9), "gravelSedimentary", RockType.Sedimentary);
        WishBlocks.sandIgneous = Registry.registerBlock(new SandWish(Material.GROUND, 9), "sandIgneous", RockType.Igneous);
        WishBlocks.sandMetamorphic = Registry.registerBlock(new SandWish(Material.GROUND, 9), "sandMetamorphic", RockType.Metamorphic);
        WishBlocks.sandSedimentary = Registry.registerBlock(new SandWish(Material.GROUND, 9), "sandSedimentary", RockType.Sedimentary);
        WishBlocks.rockMetamorphic = Registry.registerBlock(new BlockRock(Material.GROUND), "rockMetamorphic");
        WishBlocks.rockSedimentary = Registry.registerBlock(new BlockRock(Material.GROUND), "rockSedimentary");
        WishBlocks.rockIgneous = Registry.registerBlock(new BlockRock(Material.GROUND), "rockIgneous");
        WishBlocks.stick = Registry.registerBlock(new BlockStick(Material.CLOTH), "stick");
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TileEntityOre.class, new ResourceLocation(Global.MODID,"tileOre"));
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
