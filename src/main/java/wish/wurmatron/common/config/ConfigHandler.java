package wish.wurmatron.common.config;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.utils.LogHandler;
import wish.wurmatron.common.world.DimTransferEvent;

import java.io.File;

public class ConfigHandler {

	public static File location;
	public static Configuration global;

	/**
	 Used to create configuration folder and files

	 @param file location to create the configuration directory
	 */
	public static void init (File file) {
		location = new File (file.getParent () + File.separator + Global.NAME);
		global = new Configuration (new File (location + File.separator + "Global.cfg"));
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
		syncConfig ();
	}

	/**
	 Updates all the config settings
	 */
	public static void syncConfig () {
		LogHandler.info ("Loading config");
		Settings.debug = global.getBoolean (Global.DEBUG,Configuration.CATEGORY_GENERAL,false,Global.DEBUG_COMMENT);
		Settings.disableDefaultWorldType = global.getBoolean (Global.DISABLE_DEFAULT_WORLDTYPE,Configuration.CATEGORY_GENERAL,true,Global.DISABLE_DEFAULT_WORLDTYPE_COMMENT);
		Settings.cropGrowth = global.getFloat (Global.CROP_GROWTH,Configuration.CATEGORY_GENERAL,0.5f,0f,1f,Global.CROP_GROWTH_COMMENT);
		Settings.gravityUpdate = global.getInt (Global.GRAVITY_UPDATE,Configuration.CATEGORY_GENERAL,5,1,100,Global.GRAVITY_UPDATE_COMMENT);
		Settings.gemRarity = global.getInt (Global.GEM_RARITY,Configuration.CATEGORY_GENERAL,1000,1,1000000,Global.GEM_RARITY_COMMMENT);
		Settings.oreDictionary = global.getBoolean (Global.ORE_DICT,Configuration.CATEGORY_GENERAL,true,Global.ORE_DICT_COMMENT);
		Settings.oreRarity = global.getInt (Global.ORE_RARITY,Configuration.CATEGORY_GENERAL,5,1,100,Global.ORE_RARITY_COMMENT);

		if (global.hasChanged ()) {
			global.save ();
			LogHandler.info ("Config Saved");
		}
	}

	public static void loadCustomSettings () {
		DimTransferEvent.loadData (location);
	}

	@SideOnly (Side.CLIENT)
	@SubscribeEvent
	public void configChanged (ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID ().equals (Global.MODID))
			syncConfig ();
	}
}
