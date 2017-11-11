package wish.wurmatron.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import wish.wurmatron.api.world.OreType;

public class TileOre extends TileEntity {

	private OreType oreType;
	private int tier;

	public TileOre () {
	}

	public TileOre (OreType type,int tier) {
		this.oreType = type;
		this.tier = tier;
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		oreType = OreType.valueOf (nbt.getString ("type"));
		tier = nbt.getInteger ("tier");
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		super.writeToNBT (nbt);
		nbt.setString ("type",oreType.name ());
		nbt.setInteger ("tier",tier);
		return nbt;
	}

	public int getTier () {
		return tier;
	}

}
