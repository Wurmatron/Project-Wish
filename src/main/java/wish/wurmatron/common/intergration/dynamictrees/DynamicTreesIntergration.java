package wish.wurmatron.common.intergration.dynamictrees;

import com.ferreusveritas.dynamictrees.trees.Species;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import wish.wurmatron.common.utils.LogHandler;

public class DynamicTreesIntergration {

	private static boolean loaded = false;

	@Optional.Method (modid = "dynamictrees")
	public static void init () {
		LogHandler.info ("Loading DynamicTrees Intergration");
		for (Species tree : Species.REGISTRY.getValues ()) {
			if (tree != null && tree.getTree () != null && tree.getTree ().getDynamicBranch () != null) {
				tree.getTree ().getDynamicBranch ().setHarvestLevel ("axe",1);
				tree.getTree ().getDynamicBranch ().setHardness (10);
				loaded = true;
			}
		}
		if (!loaded) {
			LogHandler.info ("Failed to load Dynamic Trees Intergration!");
		} else
			MinecraftForge.EVENT_BUS.register (new DynamicTreesIntergration ());
	}

}
