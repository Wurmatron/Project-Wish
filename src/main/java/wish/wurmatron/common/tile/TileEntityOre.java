package wish.wurmatron.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.rock.ore.Ore;

public class TileEntityOre extends TileEntity {

  private Ore oreType;
  private int tier;

  public TileEntityOre() {
  }

  public TileEntityOre(Ore type, int tier) {
    this.oreType = type;
    this.tier = tier;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    oreType = WishAPI.oreRegistry.getOreFromName(nbt.getString("type"));
    tier = nbt.getInteger("tier");
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setString("type", oreType.getUnlocalizedName());
    nbt.setInteger("tier", tier);
    return super.writeToNBT(nbt);
  }

  public int getTier() {
    return tier;
  }

  public Ore getOreType() {
    return oreType;
  }


}
