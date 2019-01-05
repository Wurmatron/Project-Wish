package wish.wurmatron.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.api.rock.ore.Ore;

public class OreJsonGenerator {

  private static final File TEMPLATE_LOCATION = new File(
      Loader.instance().getConfigDir().getParentFile().getParentFile() + File.separator + "src"
          + File.separator + "utils");
  private static final File SAVE_LOCATION = new File(TEMPLATE_LOCATION + File.separator + "output");

  public static void autoCreate(Ore ore) {
    List<String> stoneNames = new ArrayList<>();
    StoneType[] types = getTypes(ore);
    for (StoneType stone : types) {
      createBlockModel(ore.getUnlocalizedName(), stone);
      stoneNames.add(stone.getName().toLowerCase());
    }
    createBlockState(ore.getUnlocalizedName(), stoneNames.toArray(new String[0]));
    createItemModel(ore.getUnlocalizedName(), stoneNames.toArray(new String[0]));
    createItemTexture(ore.getUnlocalizedName());
  }

  private static void createBlockState(String name, String[] types) {
    File loc = new File(TEMPLATE_LOCATION + File.separator + "BLOCK_STATE");
    for (File file : Objects.requireNonNull(loc.listFiles())) {
      try {
        List<String> lines = Files.readAllLines(file.toPath());
        List<String> data = new ArrayList<>();
        for (String line : lines) {
          line = line.replaceAll("%NAME%", name);
          if (line.contains("%TYPE")) {
            for (int index = 0; index < types.length; index++) {
              String find = "%TYPE" + index + "%";
              line = line.replaceAll(find, types[index]);
              if (!line.contains("%")) {
                break;
              }
            }
          }
          data.add(line);
        }
        FileUtils.writeLines(
            new File(
                SAVE_LOCATION + File.separator + "BLOCK_STATE" + File.separator + file.getName()
                    .replaceAll("%NAME%", name)),
            data);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void createBlockModel(String name, StoneType type) {
    File loc = new File(TEMPLATE_LOCATION + File.separator + "MODEL_BLOCK");
    for (File file : Objects.requireNonNull(loc.listFiles())) {
      try {
        List<String> lines = Files.readAllLines(file.toPath());
        List<String> data = new ArrayList<>();
        for (String line : lines) {
          data.add(
              line.replaceAll("%NAME%", name).replaceAll("%TYPE%", type.getName().toLowerCase()));
        }
        FileUtils.writeLines(
            new File(
                SAVE_LOCATION + File.separator + "MODEL_BLOCK" + File.separator + file.getName()
                    .replaceAll("%NAME%", name)
                    .replaceAll("%TYPE%", type.getName().toLowerCase())),
            data);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void createItemModel(String name, String[] names) {
    File loc = new File(TEMPLATE_LOCATION + File.separator + "MODEL_ITEM");
    for (File file : Objects.requireNonNull(loc.listFiles())) {
      try {
        List<String> lines = Files.readAllLines(file.toPath());
        List<String> data = new ArrayList<>();
        String find = "";
        String helperName = "";
        for (String line : lines) {
          line = line.replaceAll("%NAME%", name);
          for (int index = 0; index < names.length; index++) {
            if (line.contains("%TYPE" + index + "%")) {
              find = "%TYPE" + index + "%";
              helperName = names[index];
              line = line.replaceAll("%TYPE" + index + "%", names[index]);
            }
          }
          data.add(line);
        }
        String fileName = file.getName().replaceAll("%NAME%", name).replaceAll(find, helperName);
        FileUtils.writeLines(
            new File(SAVE_LOCATION + File.separator + "MODEL_ITEM" + File.separator + fileName),
            data);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static StoneType[] getTypes(Ore ore) {
    List<StoneType> data = new ArrayList<>();
    if (ore.getRockTypes() != null && ore.getRockTypes().length > 0) {
      for (RockType rock : ore.getRockTypes()) {
        for (StoneType stone : StoneType.values()) {
          if (stone.getType() == rock) {
            data.add(stone);
          }
        }
      }
    } else {
      Collections.addAll(data, ore.getStoneTypes());
    }
    return data.toArray(new StoneType[0]);
  }

  private static void createItemTexture(String name) {
    File loc = new File(TEMPLATE_LOCATION + File.separator + "TEXTURES");
    for (File file : Objects.requireNonNull(loc.listFiles())) {
      String fileName = file.getName().replaceAll("%NAME%", name).replaceAll("%NAME%", name);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try {
        ImageIO.write(loadAndApplyTint(file, 50, 0, 50), "png", baos);
        byte[] imageData = baos.toByteArray();
        baos.flush();
        FileUtils.writeByteArrayToFile(
            new File(SAVE_LOCATION + File.separator + "TEXTURES" + File.separator + fileName),
            imageData);
        baos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static BufferedImage loadAndApplyTint(File file, int r, int g, int b) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(file);
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          if (!(image.getRGB(x, y) >> 24 == 0x00)) {
            image.setRGB(x, y, image.getRGB(x, y) + Integer.parseInt(+r + "" + g + "" + b));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }


}
