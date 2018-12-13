package wish.wurmatron.common.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import wish.wurmatron.client.OreJsonGenerator;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.api.rock.ore.Generation;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.api.rock.ore.OreRegistry;
import wish.wurmatron.common.utils.WishGeneration;
import wish.wurmatron.common.utils.WishOre;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WishOreRegistry implements OreRegistry {

  private static List<Ore> ores = new ArrayList<>();

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private static HashMap<StoneType.RockType, List<Ore>> rockTypeCache = new HashMap<>();
  private static HashMap<StoneType, List<Ore>> stoneTypeCache = new HashMap<>();
  private static HashMap<String, Ore> oreCache = new HashMap<>();
  private static File oreSaveDir = new File(
      Loader.instance().getConfigDir() + File.separator + Global.NAME.replaceAll(" ", "")
          + File.separator + "Ores");

  @Override
  public List<Ore> getOres() {
    return ores;
  }

  @Override
  public boolean registerOre(Ore... ore) {
    for (Ore o : ore) {
      if (!ores.contains(o)) {
        if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
          OreJsonGenerator.autoCreate(o);
        }
        ores.add(o);
        oreCache.put(o.getUnlocalizedName(), o);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeOre(Ore... ore) {
    for (Ore o : ore) {
      if (ores.contains(o)) {
        oreCache.remove(o);
        ores.remove(o);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeOre(String... names) {
    for (String name : names) {
      Ore ore = getOreFromName(name);
      if (ore != null) {
        return removeOre(ore);
      }
    }
    return false;
  }

  @Override
  public List<Ore> getOresPerType(StoneType type) {
    if (stoneTypeCache.containsKey(type)) {
      return stoneTypeCache.getOrDefault(type, new ArrayList<>());
    } else {
      List<Ore> stoneCache = new ArrayList<>();
      ores.forEach(ore -> Arrays.stream(ore.getStoneTypes()).filter(ty -> ty == type).map(ty -> ore)
          .forEach(stoneCache::add));
      stoneTypeCache.put(type, stoneCache);
      return stoneCache;
    }
  }

  @Override
  public List<Ore> getOresPerType(StoneType.RockType type) {
    if (rockTypeCache.containsKey(type)) {
      return rockTypeCache.getOrDefault(type, new ArrayList<>());
    } else {
      List<Ore> rockCache = new ArrayList<>();
      ores.forEach(ore -> Arrays.stream(ore.getRockTypes()).filter(ty -> ty == type).map(ty -> ore)
          .forEach(rockCache::add));
      rockTypeCache.put(type, rockCache);
      return rockCache;
    }
  }

  @Override
  public Ore getOreFromName(String name) {
    if (oreCache.containsKey(name)) {
      return oreCache.get(name);
    }
    return null;
  }

  private Ore loadOre(File file) {
    if (file.getName().endsWith(".json")) {
      try {
        Ore ore = GSON.fromJson(new FileReader(file), WishOre.class);
        if (ore != null) {
          registerOre(ore);
        }
      } catch (Exception e) {
        ProjectWish.logger.warn(file.getName() + " was unable to be read as an WishOre type!");
      }
    } else {
      ProjectWish.logger.warn(file.getName() + " does not end with .json so it was not loaded!");
    }
    return null;
  }

  public void loadAllOres() {
    if (oreSaveDir.exists()) {
      for (File file : Objects.requireNonNull(oreSaveDir.listFiles())) {
        Ore ore = loadOre(file);
        if (ore != null) {
          registerOre(ore);
        }
      }
    } else {
      oreSaveDir.mkdirs();
      createDefaultOres();
      loadAllOres();
    }
  }

  private void saveOre(Ore ore) {
    File file = new File(oreSaveDir + File.separator + ore.getUnlocalizedName() + ".json");
    if (!file.exists()) {
      try {
        file.createNewFile();
        FileUtils.writeLines(file, Collections.singleton(GSON.toJson(ore)));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static final int[] LARGE = new int[]{8, 125};
  private static final int[] MEDIUM = new int[]{6, 75};

  public void createDefaultOres() {
    // Iron
    Ore hematiteOre = new WishOre("hematite",
        new StoneType.RockType[]{StoneType.RockType.Sedimentary}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(LARGE[0], LARGE[1], 7, Generation.Style.CLUSTER));
    saveOre(hematiteOre);
    Ore magnetiteOre = new WishOre("magnetite",
        new StoneType.RockType[]{RockType.Igneous}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(LARGE[0], LARGE[1], 5, Generation.Style.CLUSTER));
    saveOre(magnetiteOre);
    Ore limoniteOre = new WishOre("limonite",
        new StoneType.RockType[]{RockType.Sedimentary}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(LARGE[0], LARGE[1], 5, Generation.Style.CLUSTER));
    saveOre(limoniteOre);
    Ore sideriteOre = new WishOre("siderite",
        new StoneType.RockType[]{RockType.Metamorphic}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(MEDIUM[0], MEDIUM[1], 3, Generation.Style.CLUSTER));
    saveOre(sideriteOre);
  }
}
