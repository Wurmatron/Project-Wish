package wish.wurmatron.common.items;

import net.minecraft.item.Item;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.common.utils.Registry;

public class WishModItems {

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
		////////////////////////////////
		WishItems.orePetalite = registerOre (new ItemOre ("Petalite"),OreType.PETALITE);
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
