package wish.wurmatron.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.common.utils.json.WishOre;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockOreType extends ItemBlock {

    private WishOre type;

    public ItemBlockOreType (Block block,WishOre type) {
        super (block);
        setMaxDamage (0);
        setHasSubtypes (true);
        setCreativeTab(ProjectWish.tabOre);
        this.type = type;
    }

    @Override
    public int getMetadata (int damage) {
        return damage;
    }

    @Override
    public String getItemStackDisplayName (ItemStack stack) {
        return I18n.translateToLocal ("tile.ore" + type.getUnlocalizedName () + ".name");
    }

    @Override
    public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> items) {
        if (tab == this.getCreativeTab ())
            for (int index = 0; index < type.getNames ().length; index++)
                items.add (new ItemStack (this,1,index));
    }

    @Override
    public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
        tip.add (TextFormatting.DARK_GRAY + I18n.translateToLocal ("stone." + type.getNames ()[stack.getItemDamage ()].toLowerCase () + ".name"));
    }
}
