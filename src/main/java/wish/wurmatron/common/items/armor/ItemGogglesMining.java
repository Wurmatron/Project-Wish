package wish.wurmatron.common.items.armor;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.items.ProjectWishItems;
import wish.wurmatron.common.network.GuiHandler;
import wish.wurmatron.common.network.NetworkHandler;
import wish.wurmatron.common.network.packets.OpenGuiMessage;

public class ItemGogglesMining extends ItemArmor {

  public static volatile boolean armorDetection = false;

  public ItemGogglesMining(ArmorMaterial materialIn, int renderIndexIn,
      EntityEquipmentSlot equipmentSlotIn) {
    super(materialIn, renderIndexIn, equipmentSlotIn);
    setCreativeTab(ProjectWish.tabMisc);
    setUnlocalizedName("gogglesMining");
  }

  @Override
  public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
    super.onArmorTick(world, player, itemStack);
    if (world.isRemote) {
      armorDetection = true;
    }
  }

  @Override
  public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack) {
    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && !entity.world.isRemote) {
      NetworkHandler
          .sendToServer(new OpenGuiMessage(GuiHandler.GOGGLES_FILTER, entity.getPosition()));
    }
    return super.onEntitySwing(entity, stack);
  }

  @Nullable
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot,
      String type) {
    if (stack != ItemStack.EMPTY && stack.hasTagCompound() && stack.getTagCompound()
        .hasKey("range")) {
      return Global.MODID + ":" + "textures/models/mininggoggles_" + stack.getTagCompound()
          .getInteger("range") + ".png";
    }
    return Global.MODID + ":" + "textures/models/mininggoggles.png";
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    items.add(create(4));
    items.add(create(6));
    items.add(create(12));
    items.add(create(16));
    items.add(create(24));
    items.add(create(32));
    items.add(create(64));
  }

  public static ItemStack create(int range) {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("range", range);
    ItemStack stack = new ItemStack(ProjectWishItems.helmetMining, 1, 0);
    stack.setTagCompound(nbt);
    return stack;
  }

  public static int getRange(ItemStack stack) {
    if (stack != ItemStack.EMPTY && stack.hasTagCompound() && stack.getTagCompound()
        .hasKey("range")) {
      return stack.getTagCompound().getInteger("range");
    }
    return 0;
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tip,
      ITooltipFlag flag) {
    if (stack != ItemStack.EMPTY && stack.hasTagCompound() && stack.getTagCompound()
        .hasKey("range")) {
      tip.add(TextFormatting.GOLD + I18n.translateToLocal("tooltip.range.name") + " " + stack
          .getTagCompound().getInteger("range"));
    }
  }
}
