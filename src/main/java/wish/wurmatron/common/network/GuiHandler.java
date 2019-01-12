package wish.wurmatron.common.network;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import wish.wurmatron.client.gui.GuiGogglesFilter;

public class GuiHandler implements IGuiHandler {

  public static final int GOGGLES_FILTER = 0;

  @Nullable
  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    return new ContainerPlayer(player.inventory, false, player);
  }

  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    switch (ID) {
      case (GOGGLES_FILTER):
        return new GuiGogglesFilter(player.getHeldItemMainhand());
      default:
        return null;
    }
  }
}
