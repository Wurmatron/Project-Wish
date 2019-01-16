package wish.wurmatron.common.events;


import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.orestages.api.OreTiersAPI;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.common.blocks.TileOre;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.registry.WishOreRegistry;
import wish.wurmatron.common.tile.TileEntityOre;
import wish.wurmatron.common.utils.OreGeneration;

@EventBusSubscriber(modid = Global.MODID)
public class OreEvents {

  @SubscribeEvent
  public void onBlockBreak(BreakEvent e) {
    if (!e.getWorld().isRemote && e.getState() != null) {
      if (e.getState().getBlock() instanceof TileOre) {
        if (!e.getPlayer().getHeldItemMainhand().isEmpty()) {
          ItemStack drop = getOreDrop(e.getWorld(), e.getPos(), e.getState(), e.getPlayer());
          if (drop != null && drop != ItemStack.EMPTY && e.getPlayer().getHeldItemMainhand()
              .canHarvestBlock(e.getState())) {
            if (e.getPlayer() != null) {
              if (!e.getPlayer().inventory.addItemStackToInventory(drop)) {
                e.getWorld().spawnEntity(
                    new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY() + .5,
                        e.getPos().getZ(), drop));
              }
            } else {
              e.getWorld().spawnEntity(
                  new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY() + .5,
                      e.getPos().getZ(), drop));
            }
          }
        }
      }
    }
  }

  public static ItemStack getOreDrop(World world, BlockPos pos, IBlockState state,
      EntityPlayer player) {
    TileEntityOre tile = ((TileEntityOre) world.getTileEntity(pos));
    if (tile != null) {
      if (Loader.isModLoaded("orestages")) {
        if (canDropOre(state, player)) {
          if (tile.getOreType().getUnlocalizedName().contains("gem")) {
            if (WishOreRegistry.isType(RockType.Igneous, tile)) {
              return WishAPI.gemRegistry
                  .generateRandomGem(StoneType.GABBRO, 2 + world.rand.nextInt(2));
            } else if (WishOreRegistry.isType(RockType.Metamorphic, tile)) {
              return WishAPI.gemRegistry
                  .generateRandomGem(StoneType.GNEISS, 2 + world.rand.nextInt(2));
            } else if (WishOreRegistry.isType(RockType.Sedimentary, tile)) {
              return WishAPI.gemRegistry
                  .generateRandomGem(StoneType.CONGLOMERATE, 2 + world.rand.nextInt(2));
            }
          }
          return new ItemStack(Registry.itemOre.get(tile.getOreType()), 1, tile.getTier());
        }
      } else {
        if (tile.getOreType().getUnlocalizedName().contains("gem")) {
          if (WishOreRegistry.isType(RockType.Igneous, tile)) {
            return WishAPI.gemRegistry
                .generateRandomGem(StoneType.GABBRO, 1 + world.rand.nextInt(2));
          } else if (WishOreRegistry.isType(RockType.Metamorphic, tile)) {
            return WishAPI.gemRegistry
                .generateRandomGem(StoneType.GNEISS, 1 + world.rand.nextInt(2));
          } else if (WishOreRegistry.isType(RockType.Sedimentary, tile)) {
            return WishAPI.gemRegistry
                .generateRandomGem(StoneType.CONGLOMERATE, 1 + world.rand.nextInt(2));
          }
        }
        return new ItemStack(Registry.itemOre.get(tile.getOreType()), 1, tile.getTier());
      }
    }
    return ItemStack.EMPTY;
  }

  private static boolean canDropOre(IBlockState state, EntityPlayer player) {
    if (player instanceof EntityPlayerMP) {
      if (OreTiersAPI.getStageInfo(state) != null) {
        return GameStageHelper.getPlayerData(player)
            .hasStage(OreTiersAPI.getStageInfo(state).getFirst());
      }
    }
    return true;
  }

  @SubscribeEvent
  public void oreGenEvent(OreGenEvent.GenerateMinable e) {
    if (!(e.getGenerator() instanceof OreGeneration)) {
      e.setResult(Event.Result.DENY);
    }
  }
}
