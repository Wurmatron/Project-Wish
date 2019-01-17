package wish.wurmatron.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.orestages.api.OreTiersAPI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.common.blocks.TileOre;

public class ItemProspectPick extends ItemTool {

  private static final int RADIUS = 12;
  private static final int RADIUS_FACING = 20;
  private static final int FINE = 2;

  public ItemProspectPick(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn,
      Set<Block> effectiveBlocksIn) {
    super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
    setCreativeTab(ProjectWish.tabMisc);
  }

  @Override
  public void onCreated(ItemStack stack, World world, EntityPlayer player) {
    super.onCreated(stack, world, player);
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setString("mode", Type.NORMAL.name());
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tip,
      ITooltipFlag adv) {
    Type mode = getMode(stack);
    tip.add(I18n.translateToLocal("tooltip.mode.name").replaceAll("%MODE%",
        mode.name().substring(0, 1) + mode.name().substring(1, mode.name().length())
            .toLowerCase()));
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    if (player.isSneaking()) {
      if (player.getHeldItem(hand).hasTagCompound()) {
        player.getHeldItem(hand).getTagCompound()
            .setString("mode", switchMode(getMode(player.getHeldItem(hand))).name());
      } else {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("mode", Type.NORMAL.name());
        player.getHeldItem(hand).setTagCompound(nbt);
      }
      if (player.world.isRemote) {
        player.sendMessage(new TextComponentString(I18n.translateToLocal("chat.mode.switch")
            .replaceAll("%MODE%", getMode(player.getHeldItem(hand)).name())));
      }
    }
    return super.onItemRightClick(world, player, hand);
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
      EnumFacing facing, float x, float y, float z) {
    if (world.isRemote) {
      return EnumActionResult.PASS;
    }
    player.getHeldItem(hand).damageItem(1, player);
    HashMap<String, Integer> areaData = scanArea(player.getHeldItem(hand), world, pos, facing,
        player);
    player.sendMessage(new TextComponentString(I18n.translateToLocal("chat.found")));
    for (String oreType : areaData.keySet()) {
      player.sendMessage(new TextComponentString(formatDisplay(oreType, areaData.get(oreType))));
    }
    return EnumActionResult.SUCCESS;
  }

  private String formatDisplay(String type, int amount) {
    if (amount >= 200) {
      return I18n.translateToLocal("chat.motherload.name")
          .replaceAll("%ORE%", type.replaceAll("ore", ""));
    } else if (amount >= 150) {
      return I18n.translateToLocal("chat.huge.name")
          .replaceAll("%ORE%", type.replaceAll("ore", ""));
    } else if (amount >= 100) {
      return I18n.translateToLocal("chat.large.name")
          .replaceAll("%ORE%", type.replaceAll("ore", ""));
    } else if (amount >= 50) {
      return I18n.translateToLocal("chat.medium.name")
          .replaceAll("%ORE%", type.replaceAll("ore", ""));
    } else if (amount >= 25) {
      return I18n.translateToLocal("chat.small.name")
          .replaceAll("%ORE%", type.replaceAll("ore", ""));
    } else if (amount >= 10) {
      return I18n.translateToLocal("chat.traces.name")
          .replaceAll("%ORE%", type.replaceAll("ore", ""));
    }
    return "Error finding ore!";
  }

  private HashMap<String, Integer> scanArea(ItemStack stack, World world, BlockPos pos,
      EnumFacing facing, EntityPlayer player) {
    HashMap<String, Integer> scanData = new HashMap<>();
    Type currentMode = getMode(stack);
    int facingIndex = facing.getIndex();
    int radius = currentMode == Type.NORMAL ? RADIUS : RADIUS / FINE;
    int radiusFacing = currentMode == Type.NORMAL ? RADIUS_FACING : RADIUS_FACING / FINE;
    if (facingIndex == 0) {
      for (int x = -radius; x < radius; x++) {
        for (int y = radiusFacing; y > 0; y--) {
          for (int z = -radius; z < radius; z++) {
            BlockPos currentPos = pos.add(x, y, z);
            if (world.getBlockState(currentPos).getBlock() instanceof TileOre && canSee(
                world.getBlockState(currentPos), player)) {
              Block block = world.getBlockState(currentPos).getBlock();
              scanData.put(block.getLocalizedName(),
                  scanData.getOrDefault(block.getLocalizedName(), 0) + 1);
            }
          }
        }
      }
    } else if (facingIndex == 1) {
      for (int x = -radius; x < radius; x++) {
        for (int y = 0; y < radiusFacing; y++) {
          for (int z = -radius; z < radius; z++) {
            BlockPos currentPos = pos.add(x, y, z);
            if (world.getBlockState(currentPos).getBlock() instanceof TileOre && canSee(
                world.getBlockState(currentPos), player)) {
              Block block = world.getBlockState(currentPos).getBlock();
              scanData.put(block.getLocalizedName(),
                  scanData.getOrDefault(block.getLocalizedName(), 0) + 1);
            }
          }
        }
      }
    } else if (facingIndex == 2) {
      for (int x = -radiusFacing; x < radiusFacing; x++) {
        for (int y = -radius; y < radius; y++) {
          for (int z = -radius; z < radius; z++) {
            BlockPos currentPos = pos.add(x, y, z);
            if (world.getBlockState(currentPos).getBlock() instanceof TileOre && canSee(
                world.getBlockState(currentPos), player)) {
              Block block = world.getBlockState(currentPos).getBlock();
              scanData.put(block.getLocalizedName(),
                  scanData.getOrDefault(block.getLocalizedName(), 0) + 1);
            }
          }
        }
      }
    } else if (facingIndex == 3) {
      for (int x = radiusFacing; x > 0; x--) {
        for (int y = -radius; y < radius; y++) {
          for (int z = -radius; z < radius; z++) {
            BlockPos currentPos = pos.add(x, y, z);
            if (world.getBlockState(currentPos).getBlock() instanceof TileOre && canSee(
                world.getBlockState(currentPos), player)) {
              Block block = world.getBlockState(currentPos).getBlock();
              scanData.put(block.getLocalizedName(),
                  scanData.getOrDefault(block.getLocalizedName(), 0) + 1);
            }
          }
        }
      }
    } else if (facingIndex == 4) {
      for (int x = -radius; x < radius; x++) {
        for (int y = -radius; y < radius; y++) {
          for (int z = -radiusFacing; z < radiusFacing; z++) {
            BlockPos currentPos = pos.add(x, y, z);
            if (world.getBlockState(currentPos).getBlock() instanceof TileOre && canSee(
                world.getBlockState(currentPos), player)) {
              Block block = world.getBlockState(currentPos).getBlock();
              scanData.put(block.getLocalizedName(),
                  scanData.getOrDefault(block.getLocalizedName(), 0) + 1);
            }
          }
        }
      }
    } else if (facingIndex == 5) {
      for (int x = -radius; x < radius; x++) {
        for (int y = -radius; y < radius; y++) {
          for (int z = radiusFacing; z > 0; z--) {
            BlockPos currentPos = pos.add(x, y, z);
            if (world.getBlockState(currentPos).getBlock() instanceof TileOre && canSee(
                world.getBlockState(currentPos), player)) {
              Block block = world.getBlockState(currentPos).getBlock();
              scanData.put(block.getLocalizedName(),
                  scanData.getOrDefault(block.getLocalizedName(), 0) + 1);
            }
          }
        }
      }
    }
    return scanData;
  }

  private Type getMode(ItemStack stack) {
    if (stack != ItemStack.EMPTY && stack.hasTagCompound()) {
      return Type.valueOf(stack.getTagCompound().getString("mode"));
    }
    return Type.NORMAL;
  }

  private Type switchMode(Type type) {
    if (type == Type.NORMAL) {
      return Type.FINE;
    }
    return Type.NORMAL;
  }

  public boolean canSee(IBlockState state, EntityPlayer player) {
    if (Loader.isModLoaded("orestages")) {
      if (OreTiersAPI.getStageInfo(state) != null && GameStageHelper.getPlayerData(player)
          .hasStage(OreTiersAPI.getStageInfo(state).getFirst())) {
        return true;
      } else {
        return !OreTiersAPI.hasReplacement(state);
      }
    }
    return true;
  }

  private enum Type {
    NORMAL, FINE
  }
}
