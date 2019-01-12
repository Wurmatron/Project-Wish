package wish.wurmatron.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

public class GuiColorFilter extends Gui {

  public static int ID_COUNTER = 0;

  public GuiTextField text;
  private int ID;

  public GuiColorFilter() {
    this.text = new GuiTextField(ID, Minecraft.getMinecraft().fontRenderer, 0, 0, 162, 17);
    this.text.setMaxStringLength(255);
  }

  public void init() {
    this.ID = ID_COUNTER++;
  }

  public void draw() {
    Minecraft.getMinecraft().renderEngine.bindTexture(GuiGogglesFilter.BACKGROUND);
    drawTexturedModalRect(text.x - 22, text.y - 2, 1, 158, 187, 22);
    drawTexturedModalRect(text.x - 18, text.y + 2, ID * 14, 183, 14, 14);
  }

  public int getID() {
    return ID;
  }
}
