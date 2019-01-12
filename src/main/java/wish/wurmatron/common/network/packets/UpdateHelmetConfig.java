package wish.wurmatron.common.network.packets;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wish.wurmatron.common.items.armor.ItemGogglesMining;
import wish.wurmatron.common.network.utils.CustomMessage;

public class UpdateHelmetConfig extends CustomMessage.CustomtServerMessage<UpdateHelmetConfig> {

  private NBTTagCompound nbt;

  public UpdateHelmetConfig() {
  }

  public UpdateHelmetConfig(NBTTagCompound nbt) {
    this.nbt = nbt;
  }

  @Override
  protected void read(PacketBuffer buff) throws IOException {
    nbt = buff.readCompoundTag();
  }

  @Override
  protected void write(PacketBuffer buff) throws IOException {
    buff.writeCompoundTag(nbt);
  }

  @Override
  public void process(EntityPlayer player, Side side) {
    ItemStack stack = player.getHeldItemMainhand();
    if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemGogglesMining) {
      stack.setTagCompound(nbt);
    }
  }
}
