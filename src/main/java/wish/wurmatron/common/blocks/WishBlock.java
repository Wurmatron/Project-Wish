package wish.wurmatron.common.blocks;

import net.minecraft.block.material.Material;
import wish.wurmatron.ProjectWish;

/**
 Basic block that most other wish blocks expand from
 */
public class WishBlock extends BlockGravity {

	public WishBlock (Material material) {
		super (material);
		setCreativeTab (ProjectWish.BLOCKS);
	}
}
