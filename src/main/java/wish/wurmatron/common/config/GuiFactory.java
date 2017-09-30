package wish.wurmatron.common.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import wish.wurmatron.client.gui.ConfigGui;

import java.util.Set;

/**
	* This gui factory is used to display the config settings while in-game
	*/
public class GuiFactory implements IModGuiFactory {

		@Override
		public void initialize(Minecraft instance) {}

	@Override
	public boolean hasConfigGui () {
		return true;
	}

	@Override
	public GuiScreen createConfigGui (GuiScreen ps) {
		return new ConfigGui (ps);
	}

	@Override
		public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
				return null;
		}
}
