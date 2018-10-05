package wish.wurmatron.common.blocks.utils;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import wish.wurmatron.ProjectWish;

public class BlockRockType extends WishBlock {

    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 15);

    public BlockRockType(Material material) {
        super(material);
        setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0));
        setCreativeTab(ProjectWish.tabBlocks);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
}
