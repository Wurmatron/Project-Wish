package wish.wurmatron.common.events;

import net.darkhax.orestages.api.OreTiersAPI;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.common.blocks.BlockOre;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.blocks.stone.BlockStone;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.items.ItemGem;
import wish.wurmatron.common.tile.TileOre;
import wish.wurmatron.common.utils.LogHandler;
import wish.wurmatron.common.utils.Registry;

import java.util.ArrayList;
import java.util.List;

public class WorldEvents {

	public static List <Item> gemItems = new ArrayList <> ();

	private static boolean canDropOre (IBlockState state,EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			if (OreTiersAPI.getStageInfo (state) != null)
				return OreTiersAPI.hasStage (player,OreTiersAPI.getStageInfo (state).getFirst ());
		return false;
	}

	@SubscribeEvent
	public void onBlockBreak (BlockEvent.BreakEvent e) {
		if (e.getWorld ().rand.nextInt (ConfigHandler.gemRarity) == 0 && GemType.values ().length > 0 && e.getWorld ().getBlockState (e.getPos ()).getBlock () instanceof BlockStone) {
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
		if (ConfigHandler.fun && e.getState ().getBlock () == WishBlocks.stoneSedimentary && e.getState ().getValue (BlockRockType.TYPE) == 7 && e.getWorld ().rand.nextInt (100) == 0)
			e.getWorld ().createExplosion (null,e.getPos ().getX (),e.getPos ().getY (),e.getPos ().getZ (),1.5f,true);
	}

	@SubscribeEvent
	public void onBreakSpeed (PlayerEvent.BreakSpeed e) {
		if (e.getState ().getMaterial () == Material.WOOD) {
			if (e.getEntityPlayer ().getHeldItemMainhand () == ItemStack.EMPTY)
				e.setCanceled (true);
			else if (e.getEntityPlayer ().getHeldItemMainhand ().getItem () instanceof ItemAxe || e.getEntityPlayer ().getHeldItemMainhand ().getItem ().getUnlocalizedName ().contains ("hatchet") || e.getEntityPlayer ().getHeldItemMainhand ().getItem ().getUnlocalizedName ().contains ("axe") || e.getEntityPlayer ().getHeldItemMainhand ().getItem ().getUnlocalizedName ().contains ("mattock")) {
			} else
				e.setCanceled (true);
		}
	}

	@SubscribeEvent
	public void oreGenEvent (OreGenEvent.GenerateMinable e) {
		if (!(e.getGenerator () instanceof WorldGenOreHelper))
			e.setResult (Event.Result.DENY);
	}

	@SubscribeEvent
	public void onBlockBreakOre (BlockEvent.BreakEvent e) {
		if (e.getState () != null && e.getState ().getBlock () instanceof BlockOre) {
			TileOre tile = ((TileOre) e.getWorld ().getTileEntity (e.getPos ()));
			if (Loader.isModLoaded ("orestages") && canDropOre (e.getState (),e.getPlayer ()))
				e.getWorld ().spawnEntity (new EntityItem (e.getWorld (),e.getPos ().getX () + .5,e.getPos ().getY () + .5,e.getPos ().getZ () + .5,new ItemStack (Registry.itemOre.get (tile.getOreType ()),1,((TileOre) e.getWorld ().getTileEntity (e.getPos ())).getTier ())));
			else if (!Loader.isModLoaded ("orestages"))
				e.getWorld ().spawnEntity (new EntityItem (e.getWorld (),e.getPos ().getX () + .5,e.getPos ().getY () + .5,e.getPos ().getZ () + .5,new ItemStack (Registry.itemOre.get (tile.getOreType ()),1,((TileOre) e.getWorld ().getTileEntity (e.getPos ())).getTier ())));
		}
	}
}
