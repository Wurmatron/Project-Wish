package wish.wurmatron.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.common.network.GuiHandler;
import wish.wurmatron.common.network.NetworkHandler;
import wish.wurmatron.common.network.packets.OpenGuiMessage;

public class ItemMiningGoogles extends ItemArmor {

  public static final int RANGE = 32;
  public static volatile boolean armorDetection = false;

  public ItemMiningGoogles(ArmorMaterial materialIn, int renderIndexIn,
      EntityEquipmentSlot equipmentSlotIn) {
    super(materialIn, renderIndexIn, equipmentSlotIn);
    setCreativeTab(ProjectWish.tabMisc);
    setUnlocalizedName("googlesMining");
  }

  @Override
  public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
    super.onArmorTick(world, player, itemStack);
    if (world.isRemote) {
      armorDetection = true;
    }
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,
      EnumHand hand) {
    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && !world.isRemote) {
      NetworkHandler
          .sendToServer(new OpenGuiMessage(GuiHandler.GOGGLES_FILTER, player.getPosition()));
    }
    return super.onItemRightClick(world, player, hand);
  }
}
