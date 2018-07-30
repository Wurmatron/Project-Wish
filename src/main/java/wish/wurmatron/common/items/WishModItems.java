package wish.wurmatron.common.items;

import java.util.HashSet;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.utils.Registry;

public class WishModItems {

  public static final Item.ToolMaterial ROCK = EnumHelper.addToolMaterial("rock", 1, 20, 1, 3, 0);
  public static final Item.ToolMaterial STEEL = EnumHelper
      .addToolMaterial("steel", 1, 950, 1, 5, 0);
  public static final Item.ToolMaterial COPPER = EnumHelper
      .addToolMaterial("rock", 1, 220, 1, 2, 0);
  public static final Item.ToolMaterial BRONZE = EnumHelper
      .addToolMaterial("rock", 1, 280, 1, 2, 0);

  public static void registerItems() {
    // Gems
    WishItems.gemAmber = registerGem(new ItemGem(GemType.AMBER));
    WishItems.gemAmethyst = registerGem(new ItemGem(GemType.AMETHYST));
    WishItems.gemAquamarine = registerGem(new ItemGem(GemType.AQUAMARINE));
    WishItems.gemBeryl = registerGem(new ItemGem(GemType.BERYL));
    WishItems.gemBloodstone = registerGem(new ItemGem(GemType.BLOODSTONE));
    WishItems.gemCitrine = registerGem(new ItemGem(GemType.CITRINE));
    WishItems.gemDiamond = registerGem(new ItemGem(GemType.DIAMOND));
    WishItems.gemEmerald = registerGem(new ItemGem(GemType.EMERALD));
    WishItems.gemGarnet = registerGem(new ItemGem(GemType.GARNET));
    WishItems.gemJade = registerGem(new ItemGem(GemType.JADE));
    WishItems.gemJasper = registerGem(new ItemGem(GemType.JASPER));
    WishItems.gemMoonstone = registerGem(new ItemGem(GemType.MOONSTONE));
    WishItems.gemOnyx = registerGem(new ItemGem(GemType.ONYX));
    WishItems.gemOpal = registerGem(new ItemGem(GemType.OPAL));
    WishItems.gemPectolite = registerGem(new ItemGem(GemType.PECTOLITE));
    WishItems.gemPeridot = registerGem(new ItemGem(GemType.PERIDOT));
    WishItems.gemQuartz = registerGem(new ItemGem(GemType.QUARTZ));
    WishItems.gemRoseQuartz = registerGem(new ItemGem(GemType.ROSE_QUARTZ));
    WishItems.gemRuby = registerGem(new ItemGem(GemType.RUBY));
    WishItems.gemSapphire = registerGem(new ItemGem(GemType.SAPPHIRE));
    WishItems.gemTanzanite = registerGem(new ItemGem(GemType.TANZANITE));
    WishItems.gemTopaz = registerGem(new ItemGem(GemType.TOPAZ));
    WishItems.gemTurqioise = registerGem(new ItemGem(GemType.TURQIOISE));
    WishItems.gemTourmaline = registerGem(new ItemGem(GemType.TOURMALINE));
    WishItems.gemZircon = registerGem(new ItemGem(GemType.ZIRCON));
    WishItems.gemMixed = registerGem(new ItemGem(GemType.MIXED));
    // Ore
    WishItems.orePetalite = registerOre(new ItemOre("Petalite"), OreType.PETALITE);
    WishItems.oreLignite = registerOre(new ItemOre("Lignite"), OreType.LIGNITE);
    WishItems.oreBituminous = registerOre(new ItemOre("Bituminous"), OreType.BITUMINOUS);
    WishItems.oreAnthracite = registerOre(new ItemOre("Anthracite"), OreType.ANTHRACITE);
    WishItems.oreMagnesite = registerOre(new ItemOre("Magnesite"), OreType.MAGNESITE);
    WishItems.oreBauxite = registerOre(new ItemOre("Bauxite"), OreType.BAUXITE);
    WishItems.oreRutile = registerOre(new ItemOre("Rutile"), OreType.RUTILE);
    WishItems.oreIlmenite = registerOre(new ItemOre("Ilmenite"), OreType.Ilmenite);
    WishItems.oreChromite = registerOre(new ItemOre("Chromite"), OreType.CHROMITE);
    WishItems.oreMagnetite = registerOre(new ItemOre("Magnetite"), OreType.MAGNETITE);
    WishItems.oreHematite = registerOre(new ItemOre("Hematite"), OreType.HEMATITE);
    WishItems.oreLimonite = registerOre(new ItemOre("Limonite"), OreType.LIMONITE);
    WishItems.oreSiderite = registerOre(new ItemOre("Siderite"), OreType.SIDERITE);
    WishItems.oreCassiterite = registerOre(new ItemOre("Cassiterite"), OreType.CASSITERITE);
    WishItems.oreCobaltite = registerOre(new ItemOre("Cobaltite"), OreType.COBALTITE);
    WishItems.oreTetrahedrite = registerOre(new ItemOre("Tetrahedrite"), OreType.TETRAHEDRITE);
    WishItems.oreMalachite = registerOre(new ItemOre("Malachite"), OreType.MALACHITE);
    WishItems.oreSphalerite = registerOre(new ItemOre("Sphalerite"), OreType.SPHALERITE);
    WishItems.oreGold = registerOre(new ItemOre("Gold"), OreType.GOLD);
    WishItems.oreGalena = registerOre(new ItemOre("Galena"), OreType.GALENA);
    WishItems.oreBismuthinite = registerOre(new ItemOre("Bismuthinite"), OreType.BISMUTHINITE);
    WishItems.oreMonazite = registerOre(new ItemOre("Monazite"), OreType.MONAZITE);
    WishItems.oreUranium = registerOre(new ItemOre("Uranium"), OreType.URANIUM);
    WishItems.oreGarnierite = registerOre(new ItemOre("Garnierite"), OreType.GARNIERITE);
    WishItems.orePentlandite = registerOre(new ItemOre("Pentlandite"), OreType.PENTALANDITE);
    WishItems.oreCinnabar = registerOre(new ItemOre("Cinnabar"), OreType.CINNABAR);
    WishItems.dustOre = register(new ItemDust());
    WishItems.oreSilver = registerOre(new ItemOre("NativeSilver"), OreType.NATIVE_SILVER);
    WishItems.orePlatinum = registerOre(new ItemOre("NativePlatinum"), OreType.NATIVE_PLATNIUM);
    WishItems.oreMythril = registerOre(new ItemOre("Mythril"), OreType.MYTHRIL);
    if (ConfigHandler.oreItems) {
      // Sludge
      WishItems.sludgeLitium = register(new ItemSludge("Litium"));
      WishItems.sludgeAluminum = register(new ItemSludge("Aluminum"));
      WishItems.sludgeMagnesium = register(new ItemSludge("Magnesium"));
      WishItems.sludgeTitanium = register(new ItemSludge("Titanium"));
      WishItems.sludgeIron = register(new ItemSludge("Iron"));
      WishItems.sludgeCobalt = register(new ItemSludge("Cobalt"));
      WishItems.sludgeTin = register(new ItemSludge("Tin"));
      WishItems.sludgeGold = register(new ItemSludge("Gold"));
      WishItems.sludgeLead = register(new ItemSludge("Lead"));
      WishItems.sludgeBismuth = register(new ItemSludge("Bismuth"));
      WishItems.sludgeNeodynium = register(new ItemSludge("Neodynium"));
      WishItems.sludgeYttrium = register(new ItemSludge("Yttrium"));
      WishItems.sludgeUranium = register(new ItemSludge("Uranium"));
      WishItems.sludgeThorium = register(new ItemSludge("Thorium"));
      WishItems.sludgeNickel = register(new ItemSludge("Nickel"));
      WishItems.sludgeCopper = register(new ItemSludge("Copper"));
      WishItems.sludgeZinc = register(new ItemSludge("Zinc"));
      // Crystal
      WishItems.crystalOre = register(new ItemCrystal(
          new String[]{"Litium", "Aluminum", "Magnesium", "Titanium", "Iron", "Cobalt", "Tin",
              "Gold", "Lead", "Bismuth", "Neodynium", "Yttrium", "Uranium", "Thorium", "Nickel",
              "Copper"}));
      // Shard
      WishItems.shardLitium = register(new ItemShard("Litium"));
      WishItems.shardAluminum = register(new ItemShard("Aluminum"));
      WishItems.shardMagnesium = register(new ItemShard("Magnesium"));
      WishItems.shardTitanium = register(new ItemShard("Titanium"));
      WishItems.shardIron = register(new ItemShard("Iron"));
      WishItems.shardCobalt = register(new ItemShard("Cobalt"));
      WishItems.shardTin = register(new ItemShard("Tin"));
      WishItems.shardGold = register(new ItemShard("Gold"));
      WishItems.shardLead = register(new ItemShard("Lead"));
      WishItems.shardBismuth = register(new ItemShard("Bismuth"));
      WishItems.shardNeodynium = register(new ItemShard("Neodynium"));
      WishItems.shardYttrium = register(new ItemShard("Yttrium"));
      WishItems.shardUranium = register(new ItemShard("Uranium"));
      WishItems.shardThorium = register(new ItemShard("Thorium"));
      WishItems.shardNickel = register(new ItemShard("Nickel"));
      WishItems.shardCopper = register(new ItemShard("Copper"));
      WishItems.shardZinc = register(new ItemShard("Zinc"));
    }
    // Rocks
    WishItems.itemRock = register(new ItemRock());
    WishItems.itemMeta = register(new ItemMeta());
    // Tools
    WishItems.toolSharpRockOnAStick = register(new WishSharpStoneTool(ROCK, 50, 1));
    WishItems.stoneProspectPick = register(
        new ItemProspectPick(2, 4, Item.ToolMaterial.STONE, new HashSet<>())
            .setUnlocalizedName("stoneProspectPick"));
    WishItems.ironProspectPick = register(
        new ItemProspectPick(4, 4, Item.ToolMaterial.IRON, new HashSet<>())
            .setUnlocalizedName("ironProspectPick"));
    WishItems.gemProspectPick = register(
        new ItemProspectPick(10, 4, Item.ToolMaterial.DIAMOND, new HashSet<>())
            .setUnlocalizedName("gemProspectPick"));
    WishItems.steelProspectPick = register(
        new ItemProspectPick(6, 4, STEEL, new HashSet<>()).setUnlocalizedName("steelProspectPick"));
    WishItems.copperProspectPick = register(new ItemProspectPick(3, 4, COPPER, new HashSet<>())
        .setUnlocalizedName("copperProspectPick"));
    WishItems.bronzeProspectPick = register(new ItemProspectPick(4, 4, BRONZE, new HashSet<>())
        .setUnlocalizedName("bronzeProspectPick"));
  }

  private static Item register(Item item) {
    Registry.registerItem(item, item.getUnlocalizedName().substring(5));
    return item;
  }

  private static Item registerGem(Item item) {
    Registry.registerGem(item, item.getUnlocalizedName().substring(5), ((ItemGem) (item)).type);
    return item;
  }

  private static Item registerOre(Item item, OreType type) {
    Registry.registerItem(item, item.getUnlocalizedName().substring(5), type);
    return item;
  }
}
