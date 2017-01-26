package wish.wurmatron;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.config.ConfigHandler;
import wish.wurmatron.common.proxy.CommonProxy;

/**
	* This is the base mod file for Project Wish
	*/
@Mod(modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, guiFactory = Global.GUI_FACTORY, updateJSON = Global.JSON_UPDATE)
public class ProjectWish {

		@Mod.Instance(Global.MODID)
		public static ProjectWish instance;

		@SidedProxy(serverSide = Global.SERVER_PROXY, clientSide = Global.CLIENT_PROXY)
		public static CommonProxy proxy;

		@Mod.EventHandler
		public void onPreInit(FMLPreInitializationEvent e) {
				ConfigHandler.init(e.getSuggestedConfigurationFile());
		}
}
