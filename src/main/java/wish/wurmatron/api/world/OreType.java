package wish.wurmatron.api.world;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.utils.Registry;

import java.util.ArrayList;
import java.util.List;

public enum OreType {

	PETALITE ("Petalite",WishBlocks.orePetalite,new StoneType.RockType[] {StoneType.RockType.Igneous},new StoneType[] {},new Biome[] {},3,GenType.SMALL_CLUSTER);

	// Lithium "Petalite"
	// Carbon "Peat", "Lignite", "Bituminous", "Anthracite"
	// Magnesium "magnesite (Special)"
	// Aluminum "Bauxite",
	// Titanium "Rutile", "Small Amounts of in Ilmenite"
	// Chromium "Chromite"
	// Iron "Magnetite", "Hematite", "Limonite", "Siderite"
	// Tin "Cassiterite"
	// Cobalt "Cobaltite"
	// Copper "Tetrahedrite", "Malachite",
	// Zinc "Sphalerite"
	// Gold "ore"
	// Lead "Galena"
	// Bismuth "Bismuthinite"
	// Neodymium "Monazite",
	// Thorium "Monazite byproduct"
	// Uranium "ore"
	// Nickel "Garnierite", "Pentlandite"

	private String name;
	public Block ore;
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
	OreType (String name,Block ore,StoneType.RockType[] oreType,StoneType[] stoneType,Biome[] biomes,int rarity,GenType genType) {
		this.name = name;
		this.ore = ore;
		this.oreType = oreType;
		this.stoneType = stoneType;
		this.biome = biomes;
		this.rarity = rarity;
		this.genType = genType;
	}

	public String getName () {
		return name;
	}

	public Block getOre () {
		return ore;
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
		SINGLE,SMALL_CLUSTER
	}
}
