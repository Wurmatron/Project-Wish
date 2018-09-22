package wish.wurmatron.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import wish.wurmatron.api.rock.StoneType;

public class ItemBlockRockType extends ItemBlock {

    public ItemBlockRockType (Block block,StoneType.RockType type) {
        super (block);
    }
}
