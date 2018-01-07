package wish.wurmatron.common.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.common.utils.Registry;

public class WishModItems {

	public static final Item.ToolMaterial ROCK = EnumHelper.addToolMaterial ("rock",1,20,1,50,0);

	public static void registerItems () {
		// Gems
		WishItems.gemAmber = register (new ItemGem (GemType.AMBER));
		WishItems.gemAmethyst = register (new ItemGem (GemType.AMETHYST));
		WishItems.gemAquamarine = register (new ItemGem (GemType.AQUAMARINE));
		WishItems.gemBeryl = register (new ItemGem (GemType.BERYL));
		WishItems.gemBloodstone = register (new ItemGem (GemType.BLOODSTONE));
		WishItems.gemCitrine = register (new ItemGem (GemType.CITRINE));
		WishItems.gemDiamond = register (new ItemGem (GemType.DIAMOND));
		WishItems.gemEmerald = register (new ItemGem (GemType.EMERALD));
		WishItems.gemGarnet = register (new ItemGem (GemType.GARNET));
		WishItems.gemJade = register (new ItemGem (GemType.JADE));
		WishItems.gemJasper = register (new ItemGem (GemType.JASPER));
		WishItems.gemMoonstone = register (new ItemGem (GemType.MOONSTONE));
		WishItems.gemOnyx = register (new ItemGem (GemType.ONYX));
		WishItems.gemOpal = register (new ItemGem (GemType.OPAL));
		WishItems.gemPectolite = register (new ItemGem (GemType.PECTOLITE));
		WishItems.gemPeridot = register (new ItemGem (GemType.PERIDOT));
		WishItems.gemQuartz = register (new ItemGem (GemType.QUARTZ));
		WishItems.gemRoseQuartz = register (new ItemGem (GemType.ROSE_QUARTZ));
		WishItems.gemRuby = register (new ItemGem (GemType.RUBY));
		WishItems.gemSapphire = register (new ItemGem (GemType.SAPPHIRE));
		WishItems.gemTanzanite = register (new ItemGem (GemType.TANZANITE));
		WishItems.gemTopaz = register (new ItemGem (GemType.TOPAZ));
		WishItems.gemTurqioise = register (new ItemGem (GemType.TURQIOISE));
		WishItems.gemTourmaline = register (new ItemGem (GemType.TOURMALINE));
		WishItems.gemZircon = register (new ItemGem (GemType.ZIRCON));
		// Ore
		WishItems.orePetalite = registerOre (new ItemOre ("Petalite"),OreType.PETALITE);
		WishItems.oreLignite = registerOre (new ItemOre ("Lignite"),OreType.LIGNITE);
		WishItems.oreBituminous = registerOre (new ItemOre ("Bituminous"),OreType.BITUMINOUS);
		WishItems.oreAnthracite = registerOre (new ItemOre ("Anthracite"),OreType.ANTHRACITE);
		WishItems.oreMagnesite = registerOre (new ItemOre ("Magnesite"),OreType.MAGNESITE);
		WishItems.oreBauxite = registerOre (new ItemOre ("Bauxite"),OreType.BAUXITE);
		WishItems.oreRutile = registerOre (new ItemOre ("Rutile"),OreType.RUTILE);
		WishItems.oreIlmenite = registerOre (new ItemOre ("Ilmenite"),OreType.Ilmenite);
		WishItems.oreChromite = registerOre (new ItemOre ("Chromite"),OreType.CHROMITE);
		WishItems.oreMagnetite = registerOre (new ItemOre ("Magnetite"),OreType.MAGNETITE);
		WishItems.oreHematite = registerOre (new ItemOre ("Hematite"),OreType.HEMATITE);
		WishItems.oreLimonite = registerOre (new ItemOre ("Limonite"),OreType.LIMONITE);
		WishItems.oreSiderite = registerOre (new ItemOre ("Siderite"),OreType.SIDERITE);
		WishItems.oreCassiterite = registerOre (new ItemOre ("Cassiterite"),OreType.CASSITERITE);
		WishItems.oreCobaltite = registerOre (new ItemOre ("Cobaltite"),OreType.COBALTITE);
		WishItems.oreTetrahedrite = registerOre (new ItemOre ("Tetrahedrite"),OreType.TETRAHEDRITE);
		WishItems.oreMalachite = registerOre (new ItemOre ("Malachite"),OreType.MALACHITE);
		WishItems.oreSphalerite = registerOre (new ItemOre ("Sphalerite"),OreType.SPHALERITE);
		WishItems.oreGold = registerOre (new ItemOre ("Gold"),OreType.GOLD);
		WishItems.oreGalena = registerOre (new ItemOre ("Galena"),OreType.GALENA);
		WishItems.oreBismuthinite = registerOre (new ItemOre ("Bismuthinite"),OreType.BISMUTHINITE);
		WishItems.oreMonazite = registerOre (new ItemOre ("Monazite"),OreType.MONAZITE);
		WishItems.oreUranium = registerOre (new ItemOre ("Uranium"),OreType.URANIUM);
		WishItems.oreGarnierite = registerOre (new ItemOre ("Garnierite"),OreType.GARNIERITE);
		WishItems.orePentlandite = registerOre (new ItemOre ("Pentlandite"),OreType.PENTALANDITE);
		// Rocks
		WishItems.itemThrowingRock = register (new ItemThrowingRock ());
		WishItems.itemMeta = register (new ItemMeta ());
		// Tools
		WishItems.toolSharpRockOnAStick = register (new WishSharpStoneTool (ROCK,50,1));
	}

	private static Item register (Item item) {
		Registry.registerItem (item,item.getUnlocalizedName ().substring (5));
		return item;
	}

	private static Item registerOre (Item item,OreType type) {
		Registry.registerItem (item,item.getUnlocalizedName ().substring (5),type);
		return item;
	}
}
