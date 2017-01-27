package wish.wurmatron.common.world;

import net.minecraft.world.WorldType;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.config.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldHandler {

		public static void handleWorldOverride() {
				if (Settings.disableDefaultWorldType) {
						WorldType.WORLD_TYPES[0] = new WishWorldType(Global.WISH_WORLDTYPE);
				} else {
						List<WorldType> worldTypes = new ArrayList<>();
						Collections.addAll(worldTypes, WorldType.WORLD_TYPES);
						WorldType.WORLD_TYPES[0] = new WishWorldType(Global.WISH_WORLDTYPE);
						for (int index = 1; index < WorldType.WORLD_TYPES.length; index++)
								if (worldTypes.get(index - 1) != null) WorldType.WORLD_TYPES[index] = worldTypes.get(index - 1);
				}
		}
}
