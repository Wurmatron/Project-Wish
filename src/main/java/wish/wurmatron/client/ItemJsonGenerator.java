package wish.wurmatron.client;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.FileUtils;

public class ItemJsonGenerator {

  public static void createBasicItemModel(String name) {
    File file = new File(OreJsonGenerator.TEMPLATE_LOCATION + File.separator + "%NAME%.json");
    try {
      List<String> lines = Files.readAllLines(file.toPath());
      List<String> data = new ArrayList<>();
      for (String line : lines) {
        line = line.replaceAll("%NAME%", name);
        if (line.contains("%NAME%")) {
          line = line.replaceAll("%NAME%", name);
          if (!line.contains("%")) {
            break;
          }
        }
        data.add(line);
      }
      FileUtils.writeLines(new File(
          OreJsonGenerator.SAVE_LOCATION + File.separator + "ITEM_MODELS" + File.separator + file
              .getName()
              .replaceAll("%NAME%", name)), data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void autoCreateModels(String name) {
    if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
      createBasicItemModel(name);
    }
  }
}
