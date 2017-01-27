package wish.wurmatron.common.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;

/**
	* Default (Wish) world type
	*/
public class WishWorldType extends WorldType {

		public WishWorldType(String name) {
				super(name);
		}

		@Override
		public IChunkGenerator getChunkGenerator(World world, final String generatorOptions) {
				return new WishChunkProvider(world);
		}

		@Override
		public float getCloudHeight() {
				return 255f;
		}
}
