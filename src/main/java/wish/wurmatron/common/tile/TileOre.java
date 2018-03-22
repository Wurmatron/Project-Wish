package wish.wurmatron.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.common.blocks.BlockOre;
import wish.wurmatron.common.utils.Registry;

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
		try {
			oreType = OreType.valueOf (nbt.getString ("type"));
		} catch (IllegalArgumentException e) {
			oreType = OreType.ANTHRACITE;
		}
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

	@SubscribeEvent
	public void onBlockBreak (BlockEvent.BreakEvent e) {
		if (e.getState ().getBlock () instanceof BlockOre)
			e.getWorld ().spawnEntity (new EntityItem (e.getWorld (),e.getPos ().getX (),e.getPos ().getY () + .5,e.getPos ().getZ (),new ItemStack (Registry.itemOre.get (oreType),1,((TileOre) e.getWorld ().getTileEntity (e.getPos ())).getTier ())));
	}
}
