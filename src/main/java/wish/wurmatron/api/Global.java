package wish.wurmatron.api;

/**
 Stores most of the mods values
 */
public class Global {

	// @Mod
	public static final String MODID = "wish";
	public static final String NAME = "Project Wish";
	public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES = "";
	public static final String GUI_FACTORY = "wish.wurmatron.common.config.GuiFactory";
	public static final String JSON_UPDATE = "";
	// Proxy
	public static final String SERVER_PROXY = "wish.wurmatron.common.proxy.CommonProxy";
	public static final String CLIENT_PROXY = "wish.wurmatron.client.proxy.ClientProxy";
	// Config
	public static final String DEBUG = "debug";
	public static final String DEBUG_COMMENT = "Enable debug mode";
	public static final String DISABLE_DEFAULT_WORLDTYPE = "disableDefaultWorldType";
	public static final String DISABLE_DEFAULT_WORLDTYPE_COMMENT = "Disable the \"Default\" world type";
	public static final String CROP_GROWTH = "cropGrowth";
	public static final String CROP_GROWTH_COMMENT = "Chance the crop has of growing";
	public static final String GRAVITY_UPDATE = "gravityUpdate";
	public static final String GRAVITY_UPDATE_COMMENT = "Ticks Between Gravity Checks (Higher increases performance but slows down gravity updates)";
	// World Settings
	public static final String WISH_WORLDTYPE = "wish";
}
