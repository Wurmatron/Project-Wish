package wish.wurmatron.common.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.common.blocks.stone.BlockStone;
import wish.wurmatron.common.items.ItemGem;

import java.util.ArrayList;
import java.util.List;

public class WorldEvents {

	public static List <ItemStack> gemItems = new ArrayList <> ();

	@SubscribeEvent
	public void onBlockBreak (BlockEvent.BreakEvent e) {
		if (e.getWorld ().rand.nextInt (1000) == 0 && GemType.values ().length > 0 && e.getWorld ().getBlockState (e.getPos ()).getBlock () instanceof BlockStone) {
			int max = 0;
			for (GemType gem : GemType.values ())
				max += gem.getChance ();
			int id = (int) (Math.random () * max);
			for (ItemStack gem : gemItems)
				if (gem.getItem () instanceof ItemGem) {
					int gemRand = ((ItemGem) gem.getItem ()).type.getChance ();
					if (id < gemRand) {
						e.getWorld ().spawnEntity (new EntityItem (e.getWorld (),e.getPos ().getX (),e.getPos ().getY (),e.getPos ().getZ (),gem));
						return;
					}
					id -= ((ItemGem) gem.getItem ()).type.getChance ();
				}
		}
	}
}
