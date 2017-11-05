package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.utils.LogHandler;
import wish.wurmatron.common.utils.Registry;

import java.util.ArrayList;
import java.util.List;

public class BlockOre extends BlockRockType {

	private OreType type;

	public BlockOre (Material material,OreType type) {
		super (material);
		this.type = type;
		setCreativeTab (ProjectWish.BLOCKS);
	}

	@Override
	public void getSubBlocks (CreativeTabs tab,NonNullList <ItemStack> list) {
		for (int m = 0; m < getOreNames (type).length; m++)
			list.add (new ItemStack (this,1,m));
	}

	@Override
	public ItemStack getPickBlock (IBlockState state,RayTraceResult target,World world,BlockPos pos,EntityPlayer player) {
		return new ItemStack (Registry.blockItems.get (this),1,getMetaFromState (world.getBlockState (pos)));
	}

	public static String[] getOreNames (OreType type) {
		List<String> names = new ArrayList<> ();
		if (type.getOreType ().length > 0)
			for (StoneType.RockType oreType : type.getOreType ())
				for (StoneType rock : StoneType.values ())
					if (oreType.equals (rock.getType ()))
						if (!names.contains (rock.getName ()))
							names.add (rock.getName ());
		for (StoneType oreType : type.getStoneType ())
			for (StoneType rock : StoneType.values ())
				if (oreType.equals (rock.getType ()))
					if (!names.contains (rock.getName ()))
						names.add (rock.getName ());
		return names.toArray (new String[0]);
	}
}