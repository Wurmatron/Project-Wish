package wish.wurmatron.common.registry;


import static wish.wurmatron.api.rock.StoneType.GRANITE;
import static wish.wurmatron.api.rock.StoneType.PEGMATITE;
import static wish.wurmatron.api.rock.StoneType.RHYOLITE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.api.rock.gem.GemRegistry;

import java.util.*;
import wish.wurmatron.common.items.ItemGem;
import wish.wurmatron.common.utils.json.WishGem;

public class WishGemRegistry implements GemRegistry {

  private static List<Gem> Gems = new ArrayList<>();

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private static HashMap<StoneType, List<Gem>> gemTypeCache = new HashMap<>();
  private static HashMap<String, Gem> GemCache = new HashMap<>();
  private static File GemSaveDir = new File(
      Loader.instance().getConfigDir() + File.separator + Global.NAME.replaceAll(" ", "")
          + File.separator + "Gems");

  @Override
  public List<Gem> getGems() {
    return Gems;
  }

  @Override
  public boolean registerGem(Gem... Gem) {
    for (Gem o : Gem) {
      if (!Gems.contains(o)) {
        Gems.add(o);
        GemCache.put(o.getUnlocalizedName(), o);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeGem(Gem... Gem) {
    for (Gem o : Gem) {
      if (Gems.contains(o)) {
        GemCache.remove(o);
        Gems.remove(o);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeGem(String... names) {
    for (String name : names) {
      Gem Gem = getGemFromName(name);
      if (Gem != null) {
        return removeGem(Gem);
      }
    }
    return false;
  }

  @Override
  public List<Gem> getGemsPerType(StoneType type) {
    if (gemTypeCache.containsKey(type)) {
      return gemTypeCache.getOrDefault(type, new ArrayList<>());
    } else {
      List<Gem> stoneCache = new ArrayList<>();
      Gems.forEach(Gem -> Arrays.stream(Gem.getStoneTypes()).filter(ty -> ty == type).map(ty -> Gem)
          .forEach(stoneCache::add));
      gemTypeCache.put(type, stoneCache);
      return stoneCache;
    }
  }

  @Override
  public Gem getGemFromName(String name) {
    if (GemCache.containsKey(name)) {
      return GemCache.get(name);
    }
    return null;
  }

  private Gem loadGem(File file) {
    if (file.getName().endsWith(".json")) {
      try {
        Gem Gem = GSON.fromJson(new FileReader(file), WishGem.class);
        if (Gem != null) {
          registerGem(Gem);
        }
      } catch (Exception e) {
        ProjectWish.logger.warn(file.getName() + " was unable to be read as an WishGem type!");
      }
    } else {
      ProjectWish.logger.warn(file.getName() + " does not end with .json so it was not loaded!");
    }
    return null;
  }

  public void loadAllGems() {
    if (GemSaveDir.exists()) {
      for (File file : Objects.requireNonNull(GemSaveDir.listFiles())) {
        Gem Gem = loadGem(file);
        if (Gem != null) {
          registerGem(Gem);
        }
      }
    } else {
      GemSaveDir.mkdirs();
      createDefaultGems();
      loadAllGems();
    }
  }

  public static void saveGem(Gem Gem) {
    File file = new File(GemSaveDir + File.separator + Gem.getUnlocalizedName() + ".json");
    if (!file.exists()) {
      try {
        file.createNewFile();
        FileUtils.writeLines(file, Collections.singleton(GSON.toJson(Gem)));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public ItemStack generateRandomGem(StoneType type, int tier) {
    List<Gem> possibleGems = getGemsPerType(type);
    if (possibleGems.size() > 0) {
      Collections.shuffle(possibleGems);
      return new ItemStack(Registry.gemItems.get(possibleGems.get(0)), tier);
    }
    return null;
  }

  @Override
  public ItemStack generateRandomGem(StoneType type) {
    List<Gem> possibleGems = getGemsPerType(type);
    if (possibleGems.size() > 0) {
      Collections.shuffle(possibleGems);
      return ItemGem.getRandomGemGrade(Registry.gemItems.get(possibleGems.get(0)));
    }
    return null;
  }

  public void createDefaultGems() {
    new WishGem("topaz", 1, new StoneType[]{GRANITE, RHYOLITE, PEGMATITE});
  }
}
