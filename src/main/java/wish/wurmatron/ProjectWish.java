package wish.wurmatron;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.items.WishItems;
import wish.wurmatron.api.world.GemType;
import wish.wurmatron.api.world.OreType;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.blocks.WishModBlocks;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.entity.EntityThrowingRock;
import wish.wurmatron.common.events.OreWorldGenerator;
import wish.wurmatron.common.events.WorldEvents;
import wish.wurmatron.common.farming.CropEvent;
import wish.wurmatron.common.items.WishModItems;
import wish.wurmatron.common.proxy.CommonProxy;
import wish.wurmatron.common.tile.TileOre;
import wish.wurmatron.common.utils.Registry;
import wish.wurmatron.common.world.DimTransferEvent;
import wish.wurmatron.common.world.RandomizeRockTypeEvent;

import java.lang.reflect.Field;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, guiFactory = Global.GUI_FACTORY, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

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
	@Mod.Instance (Global.MODID)
	public static ProjectWish instance;
	@SidedProxy (serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
	public static CommonProxy proxy;

	public static RandomizeRockTypeEvent randRockType;

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent e) {
		ConfigHandler.init (e.getSuggestedConfigurationFile ());
		MinecraftForge.EVENT_BUS.register (new Registry ());
		WishModBlocks.registerBlocks ();
		WishModItems.registerItems ();
		//		if (Loader.isModLoaded ("dynamictrees"))
		//			DynamicTreesIntergration.init ();
		proxy.preInit ();
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"throwingRock"),EntityThrowingRock.class,"throwingRock",0,instance,64,10,true);
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent e) {
		proxy.init ();
		MinecraftForge.EVENT_BUS.register (new CropEvent ());
		MinecraftForge.EVENT_BUS.register (new WorldEvents ());
		MinecraftForge.EVENT_BUS.register (new DimTransferEvent ());
		randRockType = new RandomizeRockTypeEvent();
		MinecraftForge.EVENT_BUS.register (randRockType);
		MinecraftForge.ORE_GEN_BUS.register (new WorldEvents ());
		MinecraftForge.EVENT_BUS.register (new TileOre ());
		if (Settings.oreDictionary) {
			for (Block block : Registry.blocks)
				if (block.getUnlocalizedName ().contains ("stone"))
					OreDictionary.registerOre ("stone",block);
				else if (block.getUnlocalizedName ().contains ("cobble"))
					OreDictionary.registerOre ("cobblestone",block);
				else if (block.getUnlocalizedName ().contains ("sand"))
					OreDictionary.registerOre ("sand",block);
				else if (block.getUnlocalizedName ().contains ("gravel"))
					OreDictionary.registerOre ("gravel",block);
				else if (block.getUnlocalizedName ().contains ("log"))
					OreDictionary.registerOre ("logWood",block);
				else if (block.getUnlocalizedName ().contains ("plank"))
					OreDictionary.registerOre ("plankWood",block);
			for (GemType gem : GemType.values ())
				for (int index = 0; index < 6; index++)
					OreDictionary.registerOre ("gem" + index,new ItemStack (Registry.gemItems.get (gem),1,index));
			for (int index = 0; index < StoneType.values ().length; index++)
				OreDictionary.registerOre ("rock",new ItemStack (WishItems.itemRock,1,index));
		}
		ForgeModContainer.logCascadingWorldGeneration = false;
		GameRegistry.registerWorldGenerator (new OreWorldGenerator (),0);
	}

	@Mod.EventHandler
	public void onPostInit (FMLPostInitializationEvent e) {
		for (OreType ore : OreType.values ())
			try {
				Field field = WishBlocks.class.getDeclaredField ("ore" + ore.getName ());
				Block block = (Block) field.get (WishBlocks.oreAnthracite);
				int veinSize = 0;
				if (ore.getGenerationType () == OreType.GenType.LARGE_CLUSTER)
					veinSize = 200 / ore.getRarity ();
				else if (ore.getGenerationType () == OreType.GenType.SMALL_CLUSTER)
					veinSize = 100 / ore.getRarity ();
				else if (ore.getGenerationType () == OreType.GenType.SINGLE)
					veinSize = 1;
				OreWorldGenerator.add (new OreWorldGenerator ().new OreGen (new int[] {-1,1},10,128,ore,veinSize,block.getDefaultState ()));
			} catch (NoSuchFieldException | IllegalAccessException f) {
				f.printStackTrace ();
			}
	}

	@Mod.EventHandler
	public void onServerLoading (FMLServerStartingEvent e) {
		ConfigHandler.loadCustomSettings ();
	}
}
