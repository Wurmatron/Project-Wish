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
	public static final String GEM_RARITY = "gemRarity";
	public static final String GEM_RARITY_COMMMENT = "Rarity of Gems Droping From Stone (Higher = More Rare, Lower = Less Rare)";
	public static final String ORE_DICT = "oreDictionary";
	public static final String ORE_DICT_COMMENT = "Add Block and Items To Forge OreDictionary";
	public static final String ORE_RARITY = "oreRarity";
	public static final String ORE_RARITY_COMMENT = "Rarity Of Ore (Higher is More, but also increases generation time)";
	public static final String ROCKS_PER_CHUNK_COMMENT = "The amount of rocks per chunk";
	public static final String STICKS_PER_CHUNK_COMMENT = "The amount of sticks per chunk";
	public static final String FUN_BLOCK = "funBlocks";
	public static final String FUN_BLOCK_COMMENT = "Don't you like fun?";
	public static final String OVERRIDE_ORE = "overrideOre";
	public static final String OVERRIDE_ORE_COMMENT = "Remove other Ores?";
	public static final String CRYSTAL_ENABLED_COMMENT = "Are Crystals Enabled?";
	public static final String SHARD_ENABLED_COMMENT = "Are Shards Enabled?";
	public static final String SLUDGE_ENABLED_COMMENT = "Is Sludge Enabled?";
}
