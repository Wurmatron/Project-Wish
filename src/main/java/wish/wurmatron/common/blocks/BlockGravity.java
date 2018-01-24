package wish.wurmatron.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import wish.wurmatron.common.entity.EntityGravityBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockGravity extends Block {

	public BlockGravity (Material m) {
		super (m);
	}
}