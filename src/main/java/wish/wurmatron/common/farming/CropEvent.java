package wish.wurmatron.common.farming;


import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.ConfigHandler;

public class CropEvent {

  @SubscribeEvent
  public void cropGrowEvent(BlockEvent.CropGrowEvent.Pre e) {
    if (e.getWorld().rand.nextFloat() >= ConfigHandler.cropGrowth) {
      e.setResult(Event.Result.DENY);
    }
  }

  @SubscribeEvent
  public void boneMeatEvent(BonemealEvent e) {
    if (e.getWorld().rand.nextFloat() >= ConfigHandler.cropGrowth) {
      e.setResult(Event.Result.DENY);
    }
  }
}
