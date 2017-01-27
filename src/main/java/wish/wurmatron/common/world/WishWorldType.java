package wish.wurmatron.common.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import wish.wurmatron.common.utils.LogHandler;

import java.util.Random;

public class WishWorldType extends WorldType {

		public WishWorldType(String name) {
				super(name);
		}

		@Override
		public IChunkGenerator getChunkGenerator(final World world, final String generatorOptions) {
				LogHandler.info("Generation Options: " + generatorOptions);
				return new WishChunkProvider(world, new Random().nextLong(),true,"");
		}
}
