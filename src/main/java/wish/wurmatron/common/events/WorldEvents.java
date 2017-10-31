package wish.wurmatron.common.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.common.blocks.stone.BlockStone;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.items.ItemGem;

import java.util.ArrayList;
import java.util.List;

public class WorldEvents {

	public static List <Item> gemItems = new ArrayList <> ();

	@SubscribeEvent
	public void onBlockBreak (BlockEvent.BreakEvent e) {
		if (e.getWorld ().rand.nextInt (Settings.gemRarity) == 0 && GemType.values ().length > 0 && e.getWorld ().getBlockState (e.getPos ()).getBlock () instanceof BlockStone) {
			int max = 0;
			for (GemType gem : GemType.values ())
				max += gem.getChance ();
			int id = (int) (Math.random () * max);
			for (Item gem : gemItems)
				if (gem instanceof ItemGem) {
					int gemRand = ((ItemGem) gem).type.getChance ();
					if (id < gemRand) {
						e.getWorld ().spawnEntity (new EntityItem (e.getWorld (),e.getPos ().getX (),e.getPos ().getY (),e.getPos ().getZ (),ItemGem.getRandomGemGrade (gem)));
						return;
					}
					id -= ((ItemGem) gem).type.getChance ();
				}
		}
	}
}
