package wish.wurmatron.common.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import wish.wurmatron.api.rock.ore.Generation.Style;
import wish.wurmatron.client.OreJsonGenerator;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.api.rock.ore.Generation;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.api.rock.ore.OreRegistry;
import wish.wurmatron.common.utils.WishGeneration;
import wish.wurmatron.common.utils.json.WishOre;

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

  public static void saveOre(Ore ore) {
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

  private static final int[] EXTRA_LARGE = new int[]{12, 255};
  private static final int[] LARGE = new int[]{8, 125};
  private static final int[] MEDIUM = new int[]{6, 75};
  private static final int[] SMALL = new int[]{4, 32};

  public void createDefaultOres() {
    // Fe
    new WishOre("hematite",
        new StoneType.RockType[]{StoneType.RockType.Sedimentary}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(LARGE[0], LARGE[1], 7, Generation.Style.CLUSTER));
    new WishOre("magnetite",
        new StoneType.RockType[]{RockType.Igneous}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(LARGE[0], LARGE[1], 5, Generation.Style.CLUSTER));
    new WishOre("limonite",
        new StoneType.RockType[]{RockType.Sedimentary}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(LARGE[0], LARGE[1], 5, Generation.Style.CLUSTER));
    new WishOre("siderite",
        new StoneType.RockType[]{RockType.Metamorphic}, new StoneType[]{}, new String[]{},
        Integer.MIN_VALUE, Integer.MAX_VALUE,
        new WishGeneration(MEDIUM[0], MEDIUM[1], 3, Generation.Style.CLUSTER));
    // LI
    new WishOre("petalite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 4,
            Style.CLUSTER));
    // C
    new WishOre("lignite", new StoneType.RockType[]{RockType.Sedimentary},
        new WishGeneration(EXTRA_LARGE[0], EXTRA_LARGE[1], 8,
            Style.CLUSTER));
    new WishOre("bituminous", new StoneType.RockType[]{RockType.Metamorphic},
        new WishGeneration(LARGE[0], LARGE[1], 6,
            Style.CLUSTER));
    new WishOre("anthracite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 4,
            Style.CLUSTER));
    // Mg
    new WishOre("magnesite", new StoneType.RockType[]{RockType.Sedimentary},
        new WishGeneration(LARGE[0], LARGE[1], 3,
            Style.CLUSTER));
    // Al
    new WishOre("bauxite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(MEDIUM[0], MEDIUM[1], 4,
            Style.CLUSTER));
    // Ti
    new WishOre("ilmenite", new StoneType.RockType[]{RockType.Sedimentary},
        new WishGeneration(MEDIUM[0], MEDIUM[1], 3,
            Style.CLUSTER));
    // Cr
    new WishOre("chromite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(SMALL[0], SMALL[1], 2,
            Style.CLUSTER));
    // Co
    new WishOre("cobaltite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(MEDIUM[0], MEDIUM[1], 4,
            Style.CLUSTER));
    // Ni
    new WishOre("garnierite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 5,
            Style.CLUSTER));
    new WishOre("pentlandite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 5,
            Style.CLUSTER));
    // Cu
    new WishOre("tetrahedrite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 6,
            Style.CLUSTER));
    new WishOre("malachite", new StoneType.RockType[]{RockType.Metamorphic},
        new WishGeneration(LARGE[0], LARGE[1], 6,
            Style.CLUSTER));
    // Zn
    new WishOre("sphalerite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 5,
            Style.CLUSTER));
    // Ag
    new WishOre("argentite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 3,
            Style.CLUSTER));
    // W
    new WishOre("wolframite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(SMALL[0], SMALL[1], 2,
            Style.CLUSTER));
    // Ir / OS / Pt
    new WishOre("osmiridium", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(SMALL[0], SMALL[1], 2,
            Style.CLUSTER));
    new WishOre("iridosmine", new StoneType.RockType[]{RockType.Metamorphic},
        new WishGeneration(SMALL[0], SMALL[1], 1,
            Style.CLUSTER));
    // Pt
    new WishOre("nativeplatinum", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(SMALL[0], SMALL[1], 1,
            Style.CLUSTER));
    // Au
    new WishOre("nativegold", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(LARGE[0], LARGE[1], 3,
            Style.CLUSTER));
    // Pb
    new WishOre("galena", new StoneType.RockType[]{RockType.Metamorphic},
        new WishGeneration(LARGE[0], LARGE[1], 5,
            Style.CLUSTER));
    // Bi
    new WishOre("bismuthinite", new StoneType.RockType[]{RockType.Sedimentary},
        new WishGeneration(LARGE[0], LARGE[1], 4,
            Style.CLUSTER));
    // Nd, Y (Rare Earth), Tr
    new WishOre("monazite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(SMALL[0], SMALL[1], 2,
            Style.CLUSTER));
    // U
    new WishOre("uraninite", new StoneType.RockType[]{RockType.Igneous},
        new WishGeneration(MEDIUM[0], MEDIUM[1], 3,
            Style.CLUSTER), true);
    // Sn
    new WishOre("cassiterite", new StoneType.RockType[]{RockType.Sedimentary},
        new WishGeneration(LARGE[0], LARGE[1], 5,
            Style.CLUSTER));
  }
}
