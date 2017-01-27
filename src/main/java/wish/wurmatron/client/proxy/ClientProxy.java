package wish.wurmatron.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.blocks.WishBlocks;
import wish.wurmatron.api.world.StoneType;
import wish.wurmatron.common.proxy.CommonProxy;

/**
	* Client-Side Proxy
	*/
public class ClientProxy extends CommonProxy {

		@Override
		public void preInit() {
				createStoneVariants(0);
		}

		private void createStoneVariants(int stage) {
				if (stage == 0) {
						for (StoneType type : StoneType.values()) {
								if (type.getType() == StoneType.RockType.Sedimentary)
										ModelBakery.registerItemVariants(Item.getItemFromBlock(WishBlocks.stoneSedimentary), new ResourceLocation(Global.MODID, "stone_" + type.getName().toLowerCase()));
								else if (type.getType() == StoneType.RockType.Metamorphic)
										ModelBakery.registerItemVariants(Item.getItemFromBlock(WishBlocks.stoneMetamorphic), new ResourceLocation(Global.MODID, "stone_" + type.getName().toLowerCase()));
								else if (type.getType() == StoneType.RockType.Igneous)
										ModelBakery.registerItemVariants(Item.getItemFromBlock(WishBlocks.stoneIgneous), new ResourceLocation(Global.MODID, "stone_" + type.getName().toLowerCase()));
						}
				} else if (stage == 1) {
						for (StoneType type : StoneType.values()) {
								if (type.getType() == StoneType.RockType.Sedimentary)
										Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.makeItemStack(Global.MODID + ":" + WishBlocks.stoneSedimentary.getUnlocalizedName().substring(5), 0, 1, "").getItem(), type.getId(), new ModelResourceLocation(Global.MODID + ":stone_" + type.getName().toLowerCase(), "inventory"));
								else if (type.getType() == StoneType.RockType.Metamorphic)
										Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.makeItemStack(Global.MODID + ":" + WishBlocks.stoneMetamorphic.getUnlocalizedName().substring(5), 0, 1, "").getItem(), type.getId(), new ModelResourceLocation(Global.MODID + ":stone_" + type.getName().toLowerCase(), "inventory"));
								else if (type.getType() == StoneType.RockType.Igneous)
										Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.makeItemStack(Global.MODID + ":" + WishBlocks.stoneIgneous.getUnlocalizedName().substring(5), 0, 1, "").getItem(), type.getId(), new ModelResourceLocation(Global.MODID + ":stone_" + type.getName().toLowerCase(), "inventory"));
						}
				}
		}

		@Override
		public void init() {
				createStoneVariants(1);
		}
}
