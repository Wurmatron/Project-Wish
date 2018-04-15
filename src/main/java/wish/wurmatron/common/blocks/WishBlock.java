package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;

import java.util.Random;

/**
 Basic block that most other wish blocks expand from
 */
public class WishBlock extends Block {

	public WishBlock (Material material) {
		super (material);
		setCreativeTab (ProjectWish.BLOCKS);
	}
}
