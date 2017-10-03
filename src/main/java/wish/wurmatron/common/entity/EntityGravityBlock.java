package wish.wurmatron.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.common.blocks.WishBlock;
import wish.wurmatron.common.blocks.stone.BlockRockType;
import wish.wurmatron.common.utils.LogHandler;

public class EntityGravityBlock extends EntityFallingBlock {

	private final BlockPos startPos;

	public EntityGravityBlock (World world,double x,double y,double z,IBlockState state) {
		super (world,x,y,z,state);
		startPos = new BlockPos (x,y,z);
	}

	@Override
	public void onUpdate () {
		if (ticksExisted > 1000)
			dropItem (Item.getItemFromBlock (getBlock ().getBlock ()),1);

		Block block = this.getBlock ().getBlock ();

		if (this.getBlock ().getMaterial () == Material.AIR) {
			this.setDead ();
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			if (this.fallTime++ == 0) {
				BlockPos blockpos = new BlockPos (this);

				if (this.world.getBlockState (blockpos).getBlock () == block) {
					this.world.setBlockToAir (blockpos);
				} else if (!this.world.isRemote) {
					this.setDead ();
					return;
				}
			}

			if (!this.hasNoGravity ()) {
				this.motionY -= 0.03999999910593033D;
			}

			this.move (MoverType.SELF,this.motionX,this.motionY,this.motionZ);

			if (!this.world.isRemote) {
				BlockPos blockpos1 = new BlockPos (this);
				boolean flag = this.getBlock ().getBlock () == Blocks.CONCRETE_POWDER;
				boolean flag1 = flag && this.world.getBlockState (blockpos1).getMaterial () == Material.WATER;
				double d0 = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;

				if (flag && d0 > 1.0D) {
					RayTraceResult raytraceresult = this.world.rayTraceBlocks (new Vec3d (this.prevPosX,this.prevPosY,this.prevPosZ),new Vec3d (this.posX,this.posY,this.posZ),true);

					if (raytraceresult != null && this.world.getBlockState (raytraceresult.getBlockPos ()).getMaterial () == Material.WATER) {
						blockpos1 = raytraceresult.getBlockPos ();
						flag1 = true;
					}
				}

				if (!this.onGround && !flag1) {
					if (this.fallTime > 100 && !this.world.isRemote && (blockpos1.getY () < 1 || blockpos1.getY () > 256) || this.fallTime > 600) {
						if (this.shouldDropItem && this.world.getGameRules ().getBoolean ("doEntityDrops")) {
							this.entityDropItem (new ItemStack (block,1,block.damageDropped (this.getBlock ())),0.0F);
						}

						this.setDead ();
					}
				} else {
					IBlockState iblockstate = this.world.getBlockState (blockpos1);

					if (this.world.isAirBlock (new BlockPos (this.posX,this.posY - 0.009999999776482582D,this.posZ))) //Forge: Don't indent below.
						if (!flag1 && BlockFalling.canFallThrough (this.world.getBlockState (new BlockPos (this.posX,this.posY - 0.009999999776482582D,this.posZ)))) {
							this.onGround = false;
							return;
						}

					this.motionX *= 0.699999988079071D;
					this.motionZ *= 0.699999988079071D;
					this.motionY *= -0.5D;

					if (iblockstate.getBlock () != Blocks.PISTON_EXTENSION) {
						this.setDead ();

						if (this.world.mayPlace (block,blockpos1,true,EnumFacing.UP,(Entity) null) && (flag1 || !BlockFalling.canFallThrough (this.world.getBlockState (blockpos1.down ()))) && this.world.setBlockState (blockpos1,getBlockForFall (startPos.getY () - getPosition ().getY ()),3)) {
							if (block instanceof BlockFalling) {
								((BlockFalling) block).onEndFalling (this.world,blockpos1,this.getBlock (),iblockstate);
							}

							if (this.tileEntityData != null && block.hasTileEntity (this.getBlock ())) {
								TileEntity tileentity = this.world.getTileEntity (blockpos1);

								if (tileentity != null) {
									NBTTagCompound nbttagcompound = tileentity.writeToNBT (new NBTTagCompound ());

									for (String s : this.tileEntityData.getKeySet ()) {
										NBTBase nbtbase = this.tileEntityData.getTag (s);

										if (!"x".equals (s) && !"y".equals (s) && !"z".equals (s)) {
											nbttagcompound.setTag (s,nbtbase.copy ());
										}
									}

									tileentity.readFromNBT (nbttagcompound);
									tileentity.markDirty ();
								}
							}
						} else if (this.shouldDropItem && this.world.getGameRules ().getBoolean ("doEntityDrops")) {
							this.entityDropItem (new ItemStack (block,1,block.damageDropped (this.getBlock ())),0.0F);
						}

					}
				}
			}

			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.9800000190734863D;
		}
	}

	private IBlockState getBlockForFall (float distance) {
		if (distance >= 10)
			return getCobbleBlock ().getDefaultState ().withProperty (BlockRockType.TYPE,getBlock ().getValue (BlockRockType.TYPE));
		return getBlock ();
	}

	private Block getCobbleBlock () {
		if (getBlock ().getBlock () == WishBlocks.stoneIgneous)
			return WishBlocks.cobbleIgneous;
		else if (getBlock ().getBlock () == WishBlocks.stoneMetamorphic)
			return WishBlocks.cobbleMetamorphic;
		else if (getBlock ().getBlock () == WishBlocks.stoneSedimentary)
			return WishBlocks.cobbleSedimentary;
		return null;
	}
}
