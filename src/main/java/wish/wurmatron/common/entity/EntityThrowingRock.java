package wish.wurmatron.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.items.WishItems;

public class EntityThrowingRock extends EntityThrowable {

  private static final int DAMAGE = 50;

  public EntityThrowingRock(World worldIn) {
    super(worldIn);
  }

  public EntityThrowingRock(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }

  public EntityThrowingRock(World world, EntityLivingBase entity) {
    super(world, entity);
  }

  @Override
  protected float getGravityVelocity() {
    return inGround ? 0.0F : super.getGravityVelocity();
  }

  @Override
  protected void onImpact(RayTraceResult result) {
    if (result.entityHit != null && !inGround) {
      result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), DAMAGE);
    } else if (result.typeOfHit == RayTraceResult.Type.BLOCK && !world.isRemote) {
      Block block = world.getBlockState(result.getBlockPos()).getBlock();
      if (block == WishBlocks.stoneIgneous || block == WishBlocks.stoneMetamorphic) {
        world.spawnEntity(
            new EntityItem(world, result.getBlockPos().getX(), result.getBlockPos().getY() + 1,
                result.getBlockPos().getZ(), new ItemStack(WishItems.itemMeta, 1, 0)));
        setDead();
      } else {
        for (int index = 0; index < 10; index++) {
          world.spawnParticle(EnumParticleTypes.BLOCK_DUST, result.getBlockPos().getX(),
              result.getBlockPos().getY() + 2, result.getBlockPos().getZ(), 1, 1, 1);
        }
        setDead();
      }
    }
  }
}
