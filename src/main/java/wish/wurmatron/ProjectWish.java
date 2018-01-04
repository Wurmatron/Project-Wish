package wish.wurmatron;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.oredict.OreDictionary;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.common.blocks.WishModBlocks;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.entity.EntityThrowingRock;
import wish.wurmatron.common.events.WorldEvents;
import wish.wurmatron.common.farming.CropEvent;
import wish.wurmatron.common.intergration.dynamictrees.DynamicTreesIntergration;
import wish.wurmatron.common.items.WishModItems;
import wish.wurmatron.common.proxy.CommonProxy;
import wish.wurmatron.common.utils.Registry;
import wish.wurmatron.common.world.DimTransferEvent;
import wish.wurmatron.common.world.RandomizeRockTypeEvent;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, guiFactory = Global.GUI_FACTORY, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

	@Mod.Instance (Global.MODID)
	public static ProjectWish instance;

	@SidedProxy (serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
	public static CommonProxy proxy;

	public static final CreativeTabs BLOCKS = new CreativeTabs ("blocks") {
		@Override
		public ItemStack getTabIconItem () {
			return new ItemStack (WishBlocks.stoneIgneous,1,3);
		}

		@Override
		public boolean hasSearchBar () {
			return true;
		}
	};

	public static final CreativeTabs Items = new CreativeTabs ("items") {
		@Override
		public ItemStack getTabIconItem () {
			return new ItemStack (WishItems.gemBloodstone,1,1);
		}

		@Override
		public boolean hasSearchBar () {
			return true;
		}
	};

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent e) {
		ConfigHandler.init (e.getSuggestedConfigurationFile ());
		MinecraftForge.EVENT_BUS.register (new Registry ());
		WishModBlocks.registerBlocks ();
		WishModItems.registerItems ();
		proxy.preInit ();
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"throwingRock"),EntityThrowingRock.class,"throwingRock",0,instance,64,10,true);
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent e) {
		proxy.init ();
		MinecraftForge.EVENT_BUS.register (new CropEvent ());
		MinecraftForge.EVENT_BUS.register (new WorldEvents ());
		MinecraftForge.EVENT_BUS.register (new DimTransferEvent ());
		MinecraftForge.EVENT_BUS.register (new RandomizeRockTypeEvent ());
		if (Settings.oreDictionary) {
			for (Block[] block : Registry.treeBlock.values ()) {
				OreDictionary.registerOre ("logWood",new ItemStack (block[0],1,OreDictionary.WILDCARD_VALUE));
				OreDictionary.registerOre ("plankWood",new ItemStack (block[1],1,OreDictionary.WILDCARD_VALUE));
			}
			for (Block block : Registry.blocks)
				if (block.getUnlocalizedName ().contains ("stone"))
					OreDictionary.registerOre ("stone",block);
				else if (block.getUnlocalizedName ().contains ("dirt"))
					OreDictionary.registerOre ("dirt",block);
				else if (block.getUnlocalizedName ().contains ("grass"))
					OreDictionary.registerOre ("grass",block);
				else if (block.getUnlocalizedName ().contains ("cobble"))
					OreDictionary.registerOre ("cobblestone",block);
				else if (block.getUnlocalizedName ().contains ("sand"))
					OreDictionary.registerOre ("sand",block);
				else if (block.getUnlocalizedName ().contains ("gravel"))
					OreDictionary.registerOre ("gravel",block);
		}
	}

	@Mod.EventHandler
	public void onPostInit (FMLPostInitializationEvent e) {
		if (Loader.isModLoaded ("dynamictrees"))
			DynamicTreesIntergration.init ();
	}

	@Mod.EventHandler
	public void onServerLoading (FMLServerStartingEvent e) {
		ConfigHandler.loadCustomSettings ();
	}
}
