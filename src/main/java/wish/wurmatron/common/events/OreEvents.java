package wish.wurmatron.common.events;


import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import wish.wurmatron.api.Global;

@EventBusSubscriber(modid = Global.MODID)
public class OreEvents {

//  @SubscribeEvent
//  public void onBlockBreak(BreakEvent e) {
//    if (!e.getWorld().isRemote && e.getState() != null) {
//      if (e.getState().getBlock() instanceof TileOre) {
//        if (!e.getPlayer().getHeldItemMainhand().isEmpty()) {
//          ItemStack drop = getOreDrop(e.getWorld(), e.getPos(), e.getState(), e.getPlayer());
//          if (drop != ItemStack.EMPTY && e.getPlayer().getHeldItemMainhand()
//              .canHarvestBlock(e.getState())) {
//            if (e.getPlayer() != null) {
//              if (!e.getPlayer().inventory.addItemStackToInventory(drop)) {
//                e.getWorld().spawnEntity(
//                    new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY() + .5,
//                        e.getPos().getZ(), drop));
//              }
//            } else {
//              e.getWorld().spawnEntity(
//                  new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY() + .5,
//                      e.getPos().getZ(), drop));
//            }
//          }
//        }
//      }
//    }
//  }
//
//  public static ItemStack getOreDrop(World world, BlockPos pos, IBlockState state,
//      EntityPlayer player) {
//    TileEntityOre tile = ((TileEntityOre) world.getTileEntity(pos));
//    if (tile != null) {
//      if (Loader.isModLoaded("orestages")) {
//        if (canDropOre(state, player)) {
//          return new ItemStack(Registry.itemOre.get(tile.getOreType()), 1, tile.getTier());
//        }
//      } else {
//        return new ItemStack(Registry.itemOre.get(tile.getOreType()), 1, tile.getTier());
//      }
//    }
//    return ItemStack.EMPTY;
//  }
//
//  private static boolean canDropOre(IBlockState state, EntityPlayer player) {
//    if (player instanceof EntityPlayerMP) {
//      if (OreTiersAPI.getStageInfo(state) != null) {
//        return GameStageHelper.getPlayerData(player)
//            .hasStage(OreTiersAPI.getStageInfo(state).getFirst());
//      }
//    }
//    return true;
//  }
//
//  @SubscribeEvent
//  public void oreGenEvent(OreGenEvent.GenerateMinable e) {
//    if (!(e.getGenerator() instanceof OreGeneration)) {
//      e.setResult(Event.Result.DENY);
//    }
//  }
}
