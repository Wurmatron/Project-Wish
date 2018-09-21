package wish.wurmatron.common.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.minecraftforge.common.BiomeDictionary;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.ore.Generation;
import wish.wurmatron.api.rock.ore.Ore;

import java.util.List;
import java.util.ArrayList;

public class WishOre implements Ore {

	private String unlocalizedName;
	private StoneType.RockType[] rockTypes;
	private StoneType[] stoneTypes;
	private String[] biomes;
	private int minHeight;
	private int maxHeight;
	private WishGeneration generation;

	public WishOre (String unlocalizedName,StoneType.RockType[] rockTypes,StoneType[] stoneTypes,String[] biomes,int minHeight,int maxHeight,WishGeneration generation) {
		this.unlocalizedName = unlocalizedName;
		this.rockTypes = rockTypes;
		this.stoneTypes = stoneTypes;
		this.biomes = biomes;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.generation = generation;
	}

	@Override
	public String getUnlocalizedName () {
		return unlocalizedName;
	}

	@Override
	public StoneType.RockType[] getRockTypes () {
		return rockTypes;
	}

	@Override
	public StoneType[] getStoneTypes () {
		return stoneTypes;
	}

	@Override
	public Biome[] getBiomes () {
		return getBiomesFromString ();
	}

	private Biome[] getBiomesFromString () {
		List <Biome> biomeList = new ArrayList <> ();
		for (String name : biomes) {
			ResourceLocation data = getBiomeName (name);
			Biome biome = Biome.REGISTRY.getObject (data);
			if (biome != null) {
				biomeList.add (biome);
			}
		}
		return biomeList.toArray (new Biome[0]);
	}

	private ResourceLocation getBiomeName (String name) {
		if (name.contains (":")) {
			return new ResourceLocation (name.substring (0,name.indexOf (":")),name.substring (name.indexOf (":")));
		} else {
			return new ResourceLocation ("minecraft",name);
		}
	}

	@Override
	public WishGeneration getGenerationStyle () {
		return generation;
	}

	@Override
	public int[] getMinMaxHeight () {
		return new int[] {minHeight,maxHeight};
	}

	// TODO Finish once basic ore created
	@Override
	public IBlockState createOre (World world,StoneType type) {
		return null;
	}
}
