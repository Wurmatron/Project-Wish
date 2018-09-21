package wish.wurmatron.common.utils;

import wish.wurmatron.api.rock.ore.Generation;

public class WishGeneration implements Generation {

	private int minimumOre;
	private int maximumOre;
	private int chance;
	private Style style;

	public WishGeneration (int minimumOre,int maximumOre,int chance,Style style) {
		this.minimumOre = minimumOre;
		this.maximumOre = maximumOre;
		this.chance = chance;
		this.style = style;
	}

	@Override
	public int getMininumOre () {
		return minimumOre;
	}

	@Override
	public int getMaxiumOre () {
		return maximumOre;
	}

	@Override
	public int getChance () {
		return chance;
	}

	@Override
	public Style getStyle () {
		return style;
	}
}
