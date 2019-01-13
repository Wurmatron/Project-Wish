package wish.wurmatron.common.items.armor;

import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.network.GuiHandler;
import wish.wurmatron.common.network.NetworkHandler;
import wish.wurmatron.common.network.packets.OpenGuiMessage;

public class ItemGogglesMining extends ItemArmor {

  public static final int RANGE = 32;
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
    return Global.MODID + ":" + "textures/models/mininggoggles.png";
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    super.getSubItems(tab, items);
  }


  public ItemStack create(int range) {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("range", range);
    ItemStack stack = new ItemStack(this,1,0);
    stack.setTagCompound(nbt);
    return stack;
  }
}
