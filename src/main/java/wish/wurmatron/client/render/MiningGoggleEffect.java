package wish.wurmatron.client.render;

import static wish.wurmatron.common.items.armor.ItemGogglesMining.RANGE;
import static wish.wurmatron.common.items.armor.ItemGogglesMining.armorDetection;

import java.awt.Color;
import java.util.*;
import java.util.stream.Collectors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraftforge.oredict.OreDictionary;
import wish.wurmatron.ConfigHandler;
import wish.wurmatron.common.blocks.TileOre;
import wish.wurmatron.common.items.armor.ItemGogglesMining;

public class MiningGoggleEffect {

  private static List<RenderOre> oreTargets = new ArrayList<>();
  private static HashMap<String, Integer> filterCache = new HashMap<>();

  @SubscribeEvent
  public void renderInWorld(RenderWorldLastEvent e) {
    for (RenderOre target : oreTargets) {
      target.render(e.getPartialTicks());
    }
  }

  @SubscribeEvent
  public void onClientTick(ClientTickEvent e) {
    if (e.side == Side.CLIENT && armorDetection) {
      EntityPlayer player = Minecraft.getMinecraft().player;
      if (player != null && player.world != null
          && player.world.getWorldTime() % ConfigHandler.gogglesUpdateFrequency == 0) {
        if (!checkArmor(player)) {
          armorDetection = false;
          return;
        }
        MiningGoggleEffect.oreTargets.clear();
        Iterable<BlockPos> blocksToTest = BlockPos
            .getAllInBox((int) player.posX - RANGE, (int) player.posY - RANGE,
                (int) player.posZ - RANGE, (int) player.posX + RANGE, (int) player.posY + RANGE,
                (int) player.posZ + RANGE);
        for (BlockPos pos : blocksToTest) {
          validatePos(player.world, pos, player);
        }
      }
    } else if (!MiningGoggleEffect.oreTargets.isEmpty()) {
      MiningGoggleEffect.oreTargets.clear();
    }
  }

  private boolean checkArmor(EntityPlayer player) {
    return player.inventory.armorItemInSlot(3) != ItemStack.EMPTY && player.inventory
        .armorItemInSlot(3).getItem() instanceof ItemGogglesMining;
  }

  private void validatePos(World world, BlockPos pos, EntityPlayer player) {
    if (world.getBlockState(pos).getBlock() instanceof TileOre) {
      int oreColor = getColorForOre(player.inventory.armorItemInSlot(3).getTagCompound(),
          world.getBlockState(pos), world.getTileEntity(pos));
      if (oreColor != -1) {
        MiningGoggleEffect.oreTargets
            .add(new RenderOre(world, player, pos, world.getTileEntity(pos),
                oreColor));
      }
    }
  }

  private static final int[] ORE_COLOR = new int[]{Color.WHITE.getRGB(), Color.ORANGE.getRGB(),
      Color.magenta.getRGB(), 0xadd8e6, Color.YELLOW.getRGB(), 0xbfff00, Color.PINK.getRGB(),
      Color.GRAY.getRGB(), Color.LIGHT_GRAY.getRGB(), Color.CYAN.getRGB(), 0xEE82EE,
      Color.BLUE.getRGB(), 0xCD853F, Color.GREEN.getRGB(), Color.RED.getRGB(),
      Color.BLACK.getRGB()};

  private static int getColorForOre(NBTTagCompound filterData, IBlockState block,
      TileEntity entity) {
    int oreColor = getOreData(filterData, block);
    if (oreColor!= -1) {
      return ORE_COLOR[oreColor];
    }
    return -1;
  }

  private static int getOreData(NBTTagCompound filterData, IBlockState block) {
    if (filterData == null || filterData.hasNoTags()) {
      return -1;
    }
    filterCache.clear();
    for (int index = 0; index < 16; index++) {
      if (!filterData.getString("color" + index).contains("9")) {
        String[] filter = filterData.getString("color" + index).replaceAll("9", "").split(";");
        for (String f : filter) {
          filterCache.put(f, index);
        }
      }
    }
    for (String name : getBlockNames(block)) {
      if (filterCache.containsKey(name)) {
        return filterCache.get(name);
      }
    }
    return -1;
  }

  private static String[] getBlockNames(IBlockState block) {
    ItemStack itemBlock = new ItemStack(Item.getItemFromBlock(block.getBlock()), 1, 0);
    return Arrays.stream(OreDictionary.getOreIDs(itemBlock))
        .mapToObj(OreDictionary::getOreName).collect(Collectors.toList()).toArray(new String[0]);
  }
}
