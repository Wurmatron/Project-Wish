package wish.wurmatron.common.events;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockStone;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.ConfigHandler;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.common.items.ItemGem;


public class GemEvents {

  public static List<Item> gemItems = new ArrayList<>();

  @SubscribeEvent
  public void onBlockBreak(BlockEvent.BreakEvent e) {
    if (WishAPI.gemRegistry.getGems().size() > 0 && e.getWorld().getBlockState(e.getPos())
        .getBlock() instanceof BlockStone) {
      if (e.getWorld().rand.nextInt(ConfigHandler.gemRarity) == 0
          || !e.getPlayer().getHeldItemMainhand().isEmpty()
          && EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FORTUNE, e.getPlayer()) > 0) {
        int fortureLvl = EnchantmentHelper
            .getMaxEnchantmentLevel(Enchantments.FORTUNE, e.getPlayer());
        if (fortureLvl == 0 || e.getWorld().rand
            .nextInt(ConfigHandler.gemRarity - ((ConfigHandler.gemRarity / 10) * fortureLvl))
            <= 0) {
          int max = 0;
          for (Gem gem : WishAPI.gemRegistry.getGems()) {
            max += gem.getChance();
          }
          int id = (int) (Math.random() * max);
          for (Item gem : gemItems) {
            if (gem instanceof ItemGem) {
              int gemRand = ((ItemGem) gem).type.getChance();
              if (id < gemRand) {
                e.getWorld().spawnEntity(
                    new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY(),
                        e.getPos().getZ(), ItemGem.getRandomGemGrade(gem)));
                return;
              }
              id -= ((ItemGem) gem).type.getChance();
            }
          }
        }
      }
    }
  }
}
