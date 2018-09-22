package wish.wurmatron.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.blocks.utils.BlockRockType;


public class TileOre extends BlockRockType {

    public Ore type;

    public TileOre (Ore type) {
        super (Material.IRON);
        this.type = type;
        setCreativeTab (CreativeTabs.COMBAT);
        setHardness (1.5f);
        setResistance (30f);
        setHarvestLevel ("pickaxe",1);
        setSoundType (SoundType.STONE);
    }

    @Override
    public BlockRenderLayer getBlockLayer () {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
