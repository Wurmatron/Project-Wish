package wish.wurmatron;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.client.proxy.ClientProxy;
import wish.wurmatron.common.blocks.WishModBlocks;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.config.Settings;
import wish.wurmatron.common.farming.CropEvent;
import wish.wurmatron.common.proxy.CommonProxy;
import wish.wurmatron.common.utils.Registry;
import wish.wurmatron.common.world.WorldHandler;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, guiFactory = Global.GUI_FACTORY, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

	@Mod.Instance (Global.MODID)
	public static ProjectWish instance;

	@SidedProxy (serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent e) {
		ConfigHandler.init (e.getSuggestedConfigurationFile ());
		MinecraftForge.EVENT_BUS.register (new Registry ());
		MinecraftForge.EVENT_BUS.register (new ClientProxy ());
		WishModBlocks.registerBlocks ();
		proxy.preInit ();
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent e) {
		WorldHandler.handleWorldOverride ();
		proxy.init ();
		MinecraftForge.EVENT_BUS.register (new CropEvent ());
	}
}
