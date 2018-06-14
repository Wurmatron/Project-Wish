package wish.wurmatron.api.world;

import net.minecraft.world.biome.Biome;

public enum OreType {

	PETALITE ("Petalite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},3,GenType.SMALL_CLUSTER),        // Litium
	LIGNITE ("Lignite",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new Biome[] {},3,GenType.HUGE_CLUSTER),      // Coal (1)
	BITUMINOUS ("Bituminous",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new Biome[] {},2,GenType.LARGE_CLUSTER),// Coal (2)
	ANTHRACITE ("Anthracite",new StoneType.RockType[] {StoneType.RockType.Metamorphic},new Biome[] {},1,GenType.LARGE_CLUSTER),// Coal (3)
	MAGNESITE ("Magnesite",new StoneType.RockType[] {StoneType.RockType.Metamorphic},new Biome[] {},1,GenType.SMALL_CLUSTER),  // Magnesium
	BAUXITE ("Bauxite",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new Biome[] {},3,GenType.LARGE_CLUSTER),          // Aluminum
	RUTILE ("Rutile",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new Biome[] {},3,GenType.SMALL_CLUSTER),            // Rare Earth Metals
	Ilmenite ("Ilmenite",new StoneType.RockType[] {StoneType.RockType.Metamorphic},new Biome[] {},3,GenType.SMALL_CLUSTER),        // Titanium
	CHROMITE ("Chromite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},2,GenType.SMALL_CLUSTER),        // Chrome
	MAGNETITE ("Magnetite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},2,GenType.LARGE_CLUSTER),      // Iron
	HEMATITE ("Hematite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},3,GenType.LARGE_CLUSTER),        // Iron
	LIMONITE ("Limonite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},3,GenType.LARGE_CLUSTER),        // Iron
	SIDERITE ("Siderite",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new Biome[] {},2,GenType.SMALL_CLUSTER),        // Poor Iron
	CASSITERITE ("Cassiterite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},4,GenType.LARGE_CLUSTER),  // Tin
	COBALTITE ("Cobaltite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},2,GenType.LARGE_CLUSTER),      // Cobalt
	TETRAHEDRITE ("Tetrahedrite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},5,GenType.HUGE_CLUSTER),// Copper
	MALACHITE ("Malachite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},5,GenType.HUGE_CLUSTER),      // Copper
	SPHALERITE ("Sphalerite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},4,GenType.LARGE_CLUSTER),    // Zinc
	GOLD ("Gold",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new Biome[] {},1,GenType.LARGE_CLUSTER),            // Gold
	GALENA ("Galena",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},4,GenType.LARGE_CLUSTER),            // Lead
	BISMUTHINITE ("Bismuthinite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},3,GenType.LARGE_CLUSTER),// Bismuth
	MONAZITE ("Monazite",new StoneType.RockType[] {StoneType.RockType.Metamorphic},new Biome[] {},4,GenType.SINGLE),           // Lots
	URANIUM ("Uranium",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},4,GenType.SMALL_CLUSTER),          // Uranium
	GARNIERITE ("Garnierite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},2,GenType.LARGE_CLUSTER),    // Nickel
	PENTALANDITE ("Pentlandite",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},2,GenType.SMALL_CLUSTER), // Iron / Nickel
	CINNABAR ("Cinnabar",new StoneType.RockType[] {StoneType.RockType.Igneous},new Biome[] {},2,GenType.LARGE_CLUSTER);        // Redstone
	// TODO Silver, Aridite, Minerals (Lapis), Platinum, Rich_Gem_Ore (Gem Stone),
	
	private String name;
	private StoneType.RockType[] oreType;
	private Biome[] biome;
	private int rarity;
	private GenType genType;

	/**
	 @param name Ore Name
	 @param oreType Types of Stone the Ore Can Generate In
	 @param biomes Biomes This Ore Can Spawn In
	 @param rarity Rarity Of The Ore
	 @param genType How This Ore Generates
	 */
	OreType (String name,StoneType.RockType[] oreType,Biome[] biomes,int rarity,GenType genType) {
		this.name = name;
		this.oreType = oreType;
		this.biome = biomes;
		this.rarity = rarity;
		this.genType = genType;
	}

	public String getName () {
		return name;
	}

	public StoneType.RockType[] getOreType () {
		return oreType;
	}

	public Biome[] getBiome () {
		return biome;
	}

	public int getRarity () {
		return rarity;
	}

	public GenType getGenerationType () {
		return genType;
	}

	/**
	 How The Ore Generates
	 */
	public enum GenType {
		SINGLE,SMALL_CLUSTER,LARGE_CLUSTER, HUGE_CLUSTER
	}
}
