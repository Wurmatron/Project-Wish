package wish.wurmatron.common.blocks.terra;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.utils.Registry;

// TODO Gravity
public class BlockSand extends BlockRockType {

	private int amount;

	public BlockSand (Material material,int amount) {
		super (material);
		this.amount = amount;
		setHardness (1f);
		setResistance (3f);
		setHarvestLevel ("shovel",0);
		setSoundType (SoundType.SAND);
	}

	@Override
	public void getSubBlocks (CreativeTabs tab,NonNullList <ItemStack> list) {
		for (int m = 0; m < amount; m++)
			list.add (new ItemStack (this,1,m));
	}

	@Override
	public ItemStack getPickBlock (IBlockState state,RayTraceResult target,World world,BlockPos pos,EntityPlayer player) {
		return new ItemStack (Registry.blockItems.get (this),1,getMetaFromState (world.getBlockState (pos)));
	}

	public boolean canSustainPlant (IBlockState state,IBlockAccess world,BlockPos pos,EnumFacing direction,net.minecraftforge.common.IPlantable plantable) {
		IBlockState plant = plantable.getPlant (world,pos.offset (direction));
		net.minecraftforge.common.EnumPlantType plantType = plantable.getPlantType (world,pos.offset (direction));
		if (plant.getBlock () == net.minecraft.init.Blocks.CACTUS || plant.getBlock () == net.minecraft.init.Blocks.REEDS)
			return true;
		switch (plantType) {
			case Desert:
				return true;
			case Nether:
				return this == net.minecraft.init.Blocks.SOUL_SAND;
			case Crop:
				return this == net.minecraft.init.Blocks.FARMLAND;
			case Cave:
				return state.isSideSolid (world,pos,EnumFacing.UP);
			case Plains:
				return false;
			case Water:
				return state.getMaterial () == Material.WATER && state.getValue (BlockLiquid.LEVEL) == 0;
			case Beach:
				return (world.getBlockState (pos.east ()).getMaterial () == Material.WATER || world.getBlockState (pos.west ()).getMaterial () == Material.WATER || world.getBlockState (pos.north ()).getMaterial () == Material.WATER || world.getBlockState (pos.south ()).getMaterial () == Material.WATER);
		}
		return super.canSustainPlant (state,world,pos,direction,plantable);
	}
}
