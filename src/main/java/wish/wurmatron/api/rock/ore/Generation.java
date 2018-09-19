package wish.wurmatron.api.rock.ore;

/**
 Controls how any ore is generated within the word

 @see Ore#getGenerationStyle() */
public interface Generation {

	/**
	 Minimum Amount of ore per vein
	 */
	int getMininumOre ();

	/**
	 Max Amount of ore per vein
	 */
	int getMaxiumOre ();

	/**
	 Chance this ore will spawn
	 (Based on total value of all chances
	 */
	int getChance ();

	/**
	 How the vein is generated
	 */
	Style getStyle ();

	/**
	 Chances how the ore vein is created / generated
	 */
	enum Style {

	}
}
