package wish.wurmatron.common.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.api.world.StoneType;

public class ItemBlockRockType extends ItemBlock {

  private StoneType.RockType type;

  public ItemBlockRockType(Block block, StoneType.RockType type) {
    super(block);
    setMaxDamage(0);
    setHasSubtypes(true);
    this.type = type;
  }

  @Override
  public int getMetadata(int damage) {
    return damage;
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tip,
      ITooltipFlag flag) {
    StoneType stoneType = StoneType.getRockFromMeta(type, stack.getItemDamage());
    if (stoneType != null) {
      tip.add(TextFormatting.GRAY + I18n
          .translateToLocal("tooltip." + type.name().toLowerCase() + ".name") + " | " + I18n
          .translateToLocal(
              "tooltip.stoneSubType." + stoneType.getSubType().name().substring(0, 1) + stoneType
                  .getSubType().name().toLowerCase().substring(1) + ".name"));
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    StoneType stoneType = StoneType.getRockFromMeta(type, stack.getItemDamage());
    if (stoneType != null) {
      if (stack.getItem().getRegistryName().getResourcePath().startsWith("stone")) {
        return I18n.translateToLocal("item.stone" + stoneType.getName() + ".name");
      }
      if (stack.getItem().getRegistryName().getResourcePath().startsWith("cobble")) {
        return I18n.translateToLocal("item.stone" + stoneType.getName() + ".name") + " " + I18n
            .translateToLocal("display.cobblestone.name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("smooth")) {
        return I18n.translateToLocal("display.smooth.name") + " " + I18n
            .translateToLocal("item.stone" + stoneType.getName() + ".name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("brick")) {
        return I18n.translateToLocal("item.stone" + stoneType.getName() + ".name") + " " + I18n
            .translateToLocal("display.brick.name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("chisel")) {
        return I18n.translateToLocal("display.chisel.name") + " " + I18n
            .translateToLocal("item.stone" + stoneType.getName() + ".name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("dirt")) {
        return I18n.translateToLocal("item.stone" + stoneType.getName() + ".name") + " " + I18n
            .translateToLocal("item.dirt.name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("grass")) {
        return I18n.translateToLocal("item.stone" + stoneType.getName() + ".name") + " " + I18n
            .translateToLocal("item.grass.name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("sand")) {
        return I18n.translateToLocal("item.stone" + stoneType.getName() + ".name") + " " + I18n
            .translateToLocal("item.sand.name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("gravel")) {
        return I18n.translateToLocal("item.stone" + stoneType.getName() + ".name") + " " + I18n
            .translateToLocal("item.gravel.name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("rock")) {
        return I18n.translateToLocal("item.rock" + stoneType.getName() + ".name");
      } else if (stack.getItem().getRegistryName().getResourcePath().startsWith("stick")) {
        return I18n.translateToLocal("item.stick.name");
      }
    }
    if (stack.getItem().getRegistryName().getResourcePath().startsWith("rock")) {
      return I18n.translateToLocal(
          "item." + stack.getItem().getRegistryName().getResourcePath() + ".name");
    }
    return I18n.translateToLocal("item.stoneError.name");
  }
}
