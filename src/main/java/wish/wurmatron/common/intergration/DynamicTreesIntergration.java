package wish.wurmatron.common.intergration;

import com.ferreusveritas.dynamictrees.TreeRegistry;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import wish.wurmatron.common.utils.LogHandler;

public class DynamicTreesIntergration {

	@Optional.Method (modid = "dynamictrees")
	public static void init () {
		LogHandler.info ("Loading DynamicTrees Intergration");
		for (DynamicTree tree : TreeRegistry.getTrees ()) {
			tree.getGrowingBranch ().setHarvestLevel ("axe",1);
			tree.getGrowingBranch ().setHardness (10);
		}
		MinecraftForge.EVENT_BUS.register (new DynamicTreesIntergration ());
	}
}
