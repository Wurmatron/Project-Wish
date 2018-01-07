package wish.wurmatron.api.world;

import net.minecraft.world.biome.Biome;

public enum OreType {

	PETALITE ("Petalite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.SMALL_CLUSTER),LIGNITE ("Lignite",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new StoneType[] {},new Biome[] {},3,GenType.LARGE_CLUSTER),BITUMINOUS ("Bituminous",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new StoneType[] {},new Biome[] {},2,GenType.LARGE_CLUSTER),ANTHRACITE ("Anthracite",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new StoneType[] {},new Biome[] {},1,GenType.LARGE_CLUSTER),MAGNESITE ("Magnesite",new StoneType.RockType[] {StoneType.RockType.Sedimentary},new StoneType[] {},new Biome[] {},1,GenType.SMALL_CLUSTER),BAUXITE ("Bauxite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.LARGE_CLUSTER),RUTILE ("Rutile",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.SMALL_CLUSTER),Ilmenite ("Ilmenite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.SMALL_CLUSTER),CHROMITE ("Chromite",new StoneType.RockType[] {},new StoneType[] {StoneType.GABBRO,StoneType.BASALT},new Biome[] {},2,GenType.SMALL_CLUSTER),MAGNETITE ("Magnetite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},2,GenType.LARGE_CLUSTER),HEMATITE ("Hematite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.LARGE_CLUSTER),LIMONITE ("Limonite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.LARGE_CLUSTER),SIDERITE ("Siderite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},2,GenType.SMALL_CLUSTER),CASSITERITE ("Cassiterite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},4,GenType.LARGE_CLUSTER),COBALTITE ("Cobaltite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},2,GenType.LARGE_CLUSTER),TETRAHEDRITE ("Tetrahedrite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},5,GenType.LARGE_CLUSTER),MALACHITE ("Malachite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},5,GenType.LARGE_CLUSTER),SPHALERITE ("Sphalerite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},4,GenType.LARGE_CLUSTER),GOLD ("Gold",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},2,GenType.LARGE_CLUSTER),GALENA ("Galena",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},4,GenType.LARGE_CLUSTER),BISMUTHINITE ("Bismuthinite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.LARGE_CLUSTER),MONAZITE ("Monazite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},4,GenType.SINGLE),URANIUM ("Uranium",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},4,GenType.SMALL_CLUSTER),GARNIERITE ("Garnierite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},2,GenType.LARGE_CLUSTER),PENTALANDITE ("Pentlandite",new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},2,GenType.SMALL_CLUSTER);

	private String name;
	private StoneType.RockType[] oreType;
	private StoneType[] stoneType;
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
	OreType (String name,StoneType.RockType[] oreType,StoneType[] stoneType,Biome[] biomes,int rarity,GenType genType) {
		this.name = name;
		this.oreType = oreType;
		this.stoneType = stoneType;
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

	public StoneType[] getStoneType () {
		return stoneType;
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
		SINGLE,SMALL_CLUSTER,LARGE_CLUSTER
	}
}
