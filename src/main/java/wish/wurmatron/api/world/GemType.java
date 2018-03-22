package wish.wurmatron.api.world;

/**
 Stores the values about the stone types
 */
public enum GemType {

	AMBER ("Amber",0,5),AMETHYST ("Amethyst",1,5),AQUAMARINE ("Aquamarine",2,4),BERYL ("Beryl",3,5),
	BLOODSTONE ("Bloodstone",4,1),CITRINE ("Citrine",5,5),DIAMOND ("Diamond",6,3),EMERALD ("Emerald",7,4),
	GARNET ("Garnet",8,5),JADE ("Jade",9,5),JASPER ("Jasper",10,5),MOONSTONE ("Moonstone",11,3),ONYX ("Onyx",12,4),
	OPAL ("Opal",13,5),PECTOLITE ("Pectolite",14,4),PERIDOT ("Peridot",15,5),QUARTZ ("Quartz",16,6),
	ROSE_QUARTZ ("Rose_Quartz",17,4),RUBY ("Ruby",18,5),SAPPHIRE ("Sapphire",19,5),TANZANITE ("Tanzanite",20,4),
	TOPAZ ("Topaz",21,5),TURQIOISE ("Turqioise",22,4),TOURMALINE ("Tourmaline",23,5),ZIRCON ("Zircon",24,3),
	MIXED ("mixed",25,0);

	private String name;
	private int id;
	private int chance;

	GemType (String name,int id,int chance) {
		this.name = name;
		this.id = id;
		this.chance = chance;
	}

	public String getName () {
		return name;
	}

	public int getId () {
		return id;
	}

	public int getChance () {
		return chance;
	}

	/**
	 Grade of Gem
	 */
	public enum GRADE {
		D ("D",128),C ("C",64),B ("B",32),A ("A",16),AA ("AA",4),S ("S",1);

		private int chance;
		private String name;

		GRADE (String name,int chance) {
			this.name = name;
			this.chance = chance;
		}

		public int getChance () {
			return chance;
		}

		public String getName () {
			return name;
		}
	}
}
