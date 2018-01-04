package wish.wurmatron.api.world;

import net.minecraft.util.IStringSerializable;

public enum TreeType implements IStringSerializable {

	ASH ("ash",0),ASPEN ("aspen",1),BIRCH ("birch",2),CEDAR ("cedar",3),ELM ("elm",4),MAPLE ("maple",5),OAK ("oak",6),PINE ("pine",7),SPRUCE ("spruce",8),SYCAMORE ("sycamore",9),FIR ("fir",10),ARCACIA ("arcacia",11),SEQUOIA ("sequoia",12),REDWOOD ("redwood",13),DOGWOOD ("dogwood",14);

	private String name;
	private int meta;

	TreeType (String name,int meta) {
		this.name = name;
		this.meta = meta;
	}

	public static TreeType from (int meta) {
		if (meta < 0 || meta > values ().length)
			meta = 0;
		for (TreeType type : values ())
			if (type.getMeta () == meta)
				return type;
		return ASH;
	}

	public String getName () {
		return name;
	}

	public int getMeta () {
		return meta;
	}

	public String toString () {
		return name.toLowerCase ();
	}
}
