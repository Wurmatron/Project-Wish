package wish.wurmatron.client.gui;


import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.config.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

/**
	* Used when displaying the config options via the forge "config" button
	*/
public class ConfigGui extends GuiConfig {

		public ConfigGui(GuiScreen ps) {
				super(ps, getConfigElements(), Global.MODID, "", false, false, ConfigHandler.global.getConfigFile().getName());
		}

		/**
			* Creates a list of Config Elements to display in the gui
			*
			* @see DummyConfigElement
			*/
		private static List<IConfigElement> getConfigElements() {
				List<IConfigElement> list = new ArrayList<>();
				list.add(new DummyConfigElement.DummyCategoryElement("General", ConfigHandler.global.getConfigFile().getName(), CategoryEntryGeneral.class));
				return list;
		}

		public static class CategoryEntryGeneral extends GuiConfigEntries.CategoryEntry {

				public CategoryEntryGeneral(GuiConfig screen, GuiConfigEntries entry, IConfigElement prop) {
						super(screen, entry, prop);
				}

				@Override
				protected GuiScreen buildChildScreen() {
						Configuration        configuration          = ConfigHandler.global;
						ConfigElement        cat_general            = new ConfigElement(configuration.getCategory(Configuration.CATEGORY_GENERAL));
						List<IConfigElement> propertiesOnThisScreen = cat_general.getChildElements();
						String               windowTitle            = configuration.toString();
						return new GuiConfig(owningScreen, propertiesOnThisScreen, owningScreen.modID, Configuration.CATEGORY_GENERAL, configElement.requiresWorldRestart() || owningScreen.allRequireWorldRestart, configElement.requiresMcRestart() || owningScreen.allRequireMcRestart, windowTitle);
				}
		}
}
