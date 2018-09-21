package wish.wurmatron.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import wish.wurmatron.api.rock.ore.Ore;

import java.util.Random;

public class TileOre extends Block {

	private Ore type;

	public TileOre(Ore type) {
		super(Material.IRON);
		this.type = type;
		setCreativeTab(CreativeTabs.COMBAT);
		setHardness(1.5f);
		setResistance(30f);
		setHarvestLevel("pickaxe", 1);
		setSoundType(SoundType.STONE);
	}
}
