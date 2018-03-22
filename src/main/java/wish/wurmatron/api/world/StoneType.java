package wish.wurmatron.api.world;

/**
 Stores the values about the stone types
 */
public enum StoneType {

	CONGLOMERATE ("Conglomerate",0,RockType.Sedimentary,SubType.CLASTIC),
	SANDSTONE ("Sandstone",1,RockType.Sedimentary,SubType.CLASTIC),SHALE ("Shale",2,RockType.Sedimentary,SubType.CLASTIC),
	LIMESTONE ("Limestone",3,RockType.Sedimentary,SubType.CHEMICAL),
	DOLOMITE ("Dolomite",4,RockType.Sedimentary,SubType.CHEMICAL),SALT ("Salt",5,RockType.Sedimentary,SubType.CHEMICAL),
	CHERT ("Chert",6,RockType.Sedimentary,SubType.CHEMICAL),
	DIATOMITE ("Diatomite",7,RockType.Sedimentary,SubType.BIOCHEMICAL),
	CLAYSTONE ("Claystone",8,RockType.Sedimentary,SubType.CLASTIC),
	GNEISS ("Gneiss",0,RockType.Metamorphic,SubType.FOLIATED),
	PHYLLITE ("Phyllite",1,RockType.Metamorphic,SubType.FOLIATED),
	SCHIST ("Schist",2,RockType.Metamorphic,SubType.FOLIATED),SLATE ("Slate",3,RockType.Metamorphic,SubType.FOLIATED),
	MARBLE ("Marble",4,RockType.Metamorphic,SubType.NONFOLIATED),
	QUARTZITE ("Quartzite",5,RockType.Metamorphic,SubType.NONFOLIATED),
	GRANULITE ("Granulite",6,RockType.Metamorphic,SubType.NONFOLIATED),
	ECLOGITE ("Eclogite",7,RockType.Metamorphic,SubType.NONFOLIATED),
	SKARN ("Skarn",8,RockType.Metamorphic,SubType.NONFOLIATED),GABBRO ("Gabbro",0,RockType.Igneous,SubType.INTRUSIVE),
	GRANITE ("Granite",1,RockType.Igneous,SubType.INTRUSIVE),PEGMATITE ("Pegmatite",2,RockType.Igneous,SubType.INTRUSIVE),
	DIORITE ("Diorite",3,RockType.Igneous,SubType.EXTRUSIVE),ANDESITE ("Andesite",4,RockType.Igneous,SubType.EXTRUSIVE),
	BASALT ("Basalt",5,RockType.Igneous,SubType.EXTRUSIVE),OBSIDIAN ("Obsidian",6,RockType.Igneous,SubType.EXTRUSIVE),
	RHYOLITE ("Rhyolite",7,RockType.Igneous,SubType.EXTRUSIVE),SCORIA ("Scoria",8,RockType.Igneous,SubType.EXTRUSIVE);

	private String name;
	private int id;
	private RockType type;
	private SubType subType;

	/**
	 @param name Name of the rock
	 @param id Meta / State while this in block form (Can be same for diffrent RockType)
	 @param type Type of the rock
	 @param subType Sub Type of the rock
	 */
	StoneType (String name,int id,RockType type,SubType subType) {
		this.name = name;
		this.id = id;
		this.type = type;
		this.subType = subType;
	}

	public String getName () {
		return name;
	}

	public RockType getType () {
		return type;
	}

	public SubType getSubType () {
		return subType;
	}

	public int getId () {
		return id;
	}

	public static StoneType getRockFromMeta (RockType type,int meta) {
		for (StoneType t : StoneType.values ())
			if (t.type == type && t.id == meta)
				return t;
		return null;
	}

	/**
	 Type of rock
	 */
	public enum RockType {
		Sedimentary,Metamorphic,Igneous
	}

	/**
	 Sub type of rock
	 */
	public enum SubType {
		CHEMICAL,BIOCHEMICAL,CLASTIC, // Sedimentary
		FOLIATED,NONFOLIATED,          // Metamorphic
		INTRUSIVE,EXTRUSIVE            // Igneous
	}
}
