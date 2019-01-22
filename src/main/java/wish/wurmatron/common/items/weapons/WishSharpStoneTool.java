package wish.wurmatron.common.items.weapons;

import net.minecraft.item.ItemAxe;
import wish.wurmatron.ProjectWish;

public class WishSharpStoneTool extends ItemAxe {

  public WishSharpStoneTool(ToolMaterial material, float damage, float speed) {
    super(material, damage, speed);
    setMaxStackSize(1);
    setCreativeTab(ProjectWish.tabMisc);
    setUnlocalizedName("sharpStoneTool");
  }
}
