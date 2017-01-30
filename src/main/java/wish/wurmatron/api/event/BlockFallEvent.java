package wish.wurmatron.api.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
	* Triggered when a block is falling due to gravity
	*
	* @see BlockFallEvent.Pre
	* @see BlockFallEvent.Post
	*/
public class BlockFallEvent {

		/**
			* Triggered before the block starts to fall
			*/
		@Cancelable
		public class Pre extends Event {

				private final World       world;
				private final BlockPos    pos;
				private final IBlockState state;

				/**
					* @param world World that this block is in
					* @param pos   Location of the block before it fell
					* @param state State / Block that has fallen
					*/
				public Pre(World world, BlockPos pos, IBlockState state) {
						this.world = world;
						this.pos = pos;
						this.state = state;
				}
		}

		/**
			* Triggered after the block has started to fall
			*/
		public class Post extends Event {

				private final World              world;
				private final BlockPos           pos;
				private final IBlockState        state;
				private       EntityFallingBlock entity;

				/**
					* @param world  World that this block is in
					* @param pos    Location of the block before it fell
					* @param state  State / Block that has fallen
					* @param entity Falling entity for Block
					*/
				public Post(World world, BlockPos pos, IBlockState state, EntityFallingBlock entity) {
						this.world = world;
						this.pos = pos;
						this.state = state;
						this.entity = entity;
				}
		}

}
