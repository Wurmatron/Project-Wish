package wish.wurmatron.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.tile.TileOre;
import wish.wurmatron.common.utils.Registry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockOre extends BlockRockType implements ITileEntityProvider {

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

	public static String[] getOreNames (OreType type) {
		List <String> names = new ArrayList <> ();
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

	@Override
	protected NonNullList <ItemStack> captureDrops (boolean start) {
		return super.captureDrops (start);
	}

	@Override
	public Item getItemDropped (IBlockState state,Random rand,int fortune) {
		return null;
	}

	@Override
	public void breakBlock (World world,BlockPos pos,IBlockState state) {
		TileOre ore = (TileOre) world.getTileEntity (pos);
		world.spawnEntity (new EntityItem (world,pos.getX (),pos.getY () + .5,pos.getZ (),new ItemStack (Registry.itemOre.get (type),1,ore.getTier ())));
	}

	@Override
	protected boolean hasGravity () {
		return false;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World world,int meta) {
		return new TileOre (type,world.rand.nextInt (2));
	}
}