package wish.wurmatron.common.items.crafting;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemShard extends Item {

  public static final double[] WEIGHTS = new double[]{.2, .3, .5, .75, 1, 1.25, 2, 3, 4, 4.5, 5, 6,
      7.5, 10, 15, 20};
  public static List<Item> valid = new ArrayList<>();
  private String type;

  public ItemShard(String name) {
    this.type = name;
    setCreativeTab(CreativeTabs.MATERIALS);
    setHasSubtypes(true);
    setUnlocalizedName("shard" + type);
    valid.add(this);
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    return I18n.translateToLocal("item.shard" + type + ".name");
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tip,
      ITooltipFlag flag) {
    double weight = getWeight(stack.getItemDamage());
    tip.add(String.format("%-12s    %6skg %6s", I18n.translateToLocal("display.weight.name") + ": ",
        weight * 100, weight + " Ingot(s)"));
    tip.add(String
        .format("%-12s %6skg %6s", I18n.translateToLocal("display.totalWeight.name") + ": ",
            weight * 100 * stack.getCount(), weight * stack.getCount() + " Ingot(s)"));
  }

  private double getWeight(int damage) {
    return WEIGHTS[damage < WEIGHTS.length ? damage : 0];
  }
}
