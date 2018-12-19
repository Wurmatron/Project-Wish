package wish.wurmatron.common.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.api.rock.ore.Ore;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import wish.wurmatron.common.registry.WishOreRegistry;

public class WishOre implements Ore {

  private String unlocalizedName;
  private StoneType.RockType[] rockTypes;
  private StoneType[] stoneTypes;
  private String[] biomes;
  private int minHeight;
  private int maxHeight;
  private WishGeneration generation;

  public WishOre(String unlocalizedName, StoneType.RockType[] rockTypes, StoneType[] stoneTypes,
      String[] biomes, int minHeight, int maxHeight, WishGeneration generation) {
    this.unlocalizedName = unlocalizedName;
    this.rockTypes = rockTypes;
    this.stoneTypes = stoneTypes;
    this.biomes = biomes;
    this.minHeight = minHeight;
    this.maxHeight = maxHeight;
    this.generation = generation;
    WishOreRegistry.saveOre(this);
  }

  public WishOre(String unlocalizedName, RockType[] rockTypes, int minHeight, int maxHeight,
      WishGeneration generation) {
    this.stoneTypes = new StoneType[]{};
    this.biomes = new String[]{};
    this.unlocalizedName = unlocalizedName;
    this.rockTypes = rockTypes;
    this.minHeight = minHeight;
    this.maxHeight = maxHeight;
    this.generation = generation;
    WishOreRegistry.saveOre(this);
  }

  public WishOre(String unlocalizedName, RockType[] rockTypes,
      WishGeneration generation) {
    this.stoneTypes = new StoneType[]{};
    this.biomes = new String[]{};
    this.minHeight = Integer.MIN_VALUE;
    this.maxHeight = Integer.MAX_VALUE;
    this.unlocalizedName = unlocalizedName;
    this.rockTypes = rockTypes;
    this.generation = generation;
    WishOreRegistry.saveOre(this);
  }

  @Override
  public String getUnlocalizedName() {
    return unlocalizedName;
  }

  @Override
  public StoneType.RockType[] getRockTypes() {
    return rockTypes;
  }

  @Override
  public StoneType[] getStoneTypes() {
    return stoneTypes;
  }

  @Override
  public Biome[] getBiomes() {
    return getBiomesFromString();
  }

  private Biome[] getBiomesFromString() {
    List<Biome> biomeList = new ArrayList<>();
    for (String name : biomes) {
      ResourceLocation data = getBiomeName(name);
      Biome biome = Biome.REGISTRY.getObject(data);
      if (biome != null) {
        biomeList.add(biome);
      }
    }
    return biomeList.toArray(new Biome[0]);
  }

  private ResourceLocation getBiomeName(String name) {
    if (name.contains(":")) {
      return new ResourceLocation(name.substring(0, name.indexOf(":")),
          name.substring(name.indexOf(":")));
    } else {
      return new ResourceLocation("minecraft", name);
    }
  }

  @Override
  public WishGeneration getGenerationStyle() {
    return generation;
  }

  @Override
  public int[] getMinMaxHeight() {
    return new int[]{minHeight, maxHeight};
  }

  // TODO Finish once basic ore created
  @Override
  public IBlockState createOre(World world, StoneType type) {
    return null;
  }

  public String[] getNames() {
    List<String> names = new ArrayList<>();
    if (getStoneTypes() == null || getStoneTypes().length == 0) {
      for (StoneType.RockType rock : getRockTypes()) {
        for (StoneType type : StoneType.values()) {
          if (rock == type.getType()) {
            names.add(type.getName());
          }
        }
      }
    }
    if (names.size() > 16) {
      ProjectWish.logger.error(
          "Ore " + getUnlocalizedName() + " has more than 16 types, thus some of them have" + " "
              + "been removed");
    }
    return names.size() > 16 ? Arrays.copyOfRange(names.toArray(new String[0]), 0, 15)
        : names.toArray(new String[0]);
  }
}
