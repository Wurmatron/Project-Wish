package wish.wurmatron.api.rock.ore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;

/**
 Wrapper to store everything required to spawn a new ore type
 */
public interface Ore {

	/**
	 Unlocalized Name of this ore
	 */
	String getUnlocalizedName ();

	/**
	 RockType that this ore can spawn within
	 */
	RockType[] getRockTypes ();

	/**
	 StoneType this Ore can spawn in
	 if no value is set all RockTypes will be used
	 */
	StoneType[] getStoneTypes ();

	/**
	 Biomes this ore can spawn in

	 if no value is set it can spawn in everything
	 */
	Biome[] getBiomes ();

	/**
	 Changes / Controls how this ore is generated
	 */
	Generation getGenerationStyle ();

	/**
	 Minimum and Maxiumum Height this ore can spawn in
	 null or -1 means any
	 */
	int[] getMinMaxHeight ();

	/**
	 Places an ore into the world based on the StoneType
	 (If ores needs a tile make sure to add it here

	 @param world World that ore is used to spawn in
	 @param type StoneType this ore is spawning in
	 */
	IBlockState createOre (World world,StoneType type);

}
