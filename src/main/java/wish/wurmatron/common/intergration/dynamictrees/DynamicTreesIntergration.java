package wish.wurmatron.common.intergration.dynamictrees;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeAcacia;
import com.ferreusveritas.dynamictrees.trees.TreeBirch;
import com.ferreusveritas.dynamictrees.util.CompatHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.TreeType;
import wish.wurmatron.common.blocks.terra.BlockSapling;
import wish.wurmatron.common.blocks.terra.BlockWood;
import wish.wurmatron.common.utils.LogHandler;

import java.util.Random;

import static wish.wurmatron.common.blocks.terra.BlockWood.VARIANT;

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
