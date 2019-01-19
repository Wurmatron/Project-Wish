package wish.wurmatron.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wish.wurmatron.api.Global;
import wish.wurmatron.api.WishBlocks;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.api.rock.gem.Gem.GRADE;
import wish.wurmatron.common.CommonProxy;
import wish.wurmatron.common.blocks.TileOre;
import wish.wurmatron.common.items.ItemGem;
import wish.wurmatron.common.items.ItemOre;
import wish.wurmatron.common.items.crafting.ItemBrick;
import wish.wurmatron.common.registry.Registry;
import wish.wurmatron.common.utils.json.WishOre;

public class ClientProxy extends CommonProxy {

  private static void createModel(Block block, int meta, String name) {
    ModelLoader.setCustomModelResourceLocation(Registry.blockItems.get(block), meta,
        new ModelResourceLocation(Global.MODID + ":" + name, "inventory"));
  }

  private static void createModel(Item item, String name) {
    createModel(item, 0, name);
  }

  private static void createModel(Item item, int meta, String name) {
    ModelLoader.setCustomModelResourceLocation(item, meta,
        new ModelResourceLocation(Global.MODID + ":" + name, "inventory"));
  }

  @Override
  public void preInit() {
    MinecraftForge.EVENT_BUS.register(new ClientProxy());
  }

  @Override
  public void init() {
  }

  @SubscribeEvent
  public void modelBakeEvent(ModelRegistryEvent e) {
    for (Block block : Registry.blocks) {
      if (block instanceof TileOre) {
        String[] metaData = ((WishOre) (((TileOre) block).ore)).getNames();
        for (int meta = 0; meta < metaData.length; meta++) {
          createModel(block, meta, block.getUnlocalizedName().substring(5) + "_" + metaData[meta]);
        }
      }
    }
    for (Item item : Registry.items) {
      if (item instanceof ItemOre) {
        for (int meta = 0; meta < ((ItemOre) item).SIZES; meta++) {
          createModel(item, meta, item.getUnlocalizedName().substring(5) + "_" + meta);
        }
      } else if (item instanceof ItemGem) {
        for (int meta = 0; meta < GRADE.values().length; meta++) {
          createModel(item, meta,
              item.getUnlocalizedName().substring(5) + "_" + Gem.GRADE.values()[meta].name()
                  .toLowerCase());
        }
      } else if (item instanceof ItemBrick) {
        for (int meta = 0; meta < 9; meta++) {
          createModel(item, meta, item.getUnlocalizedName().substring(5) + "_" + meta);
        }
      }
    }
    for (StoneType type : StoneType.values()) {
      if (type.getType() == StoneType.RockType.Metamorphic) {
        createModel(WishBlocks.stoneMetamorphic, type.getId(),
            "stone_" + type.getName().toLowerCase());
        createModel(WishBlocks.cobbleMetamorphic, type.getId(),
            "cobble_" + type.getName().toLowerCase());
        createModel(WishBlocks.smoothMetamorphic, type.getId(),
            "smooth_" + type.getName().toLowerCase());
        createModel(WishBlocks.brickMetamorphic, type.getId(),
            "brick_" + type.getName().toLowerCase());
        createModel(WishBlocks.gravelMetamorphic, type.getId(),
            "gravel_" + type.getName().toLowerCase());
        createModel(WishBlocks.sandMetamorphic, type.getId(),
            "sand_" + type.getName().toLowerCase());
      }
      if (type.getType() == StoneType.RockType.Sedimentary) {
        createModel(WishBlocks.stoneSedimentary, type.getId(),
            "stone_" + type.getName().toLowerCase());
        createModel(WishBlocks.cobbleSedimentary, type.getId(),
            "cobble_" + type.getName().toLowerCase());
        createModel(WishBlocks.smoothSedimentary, type.getId(),
            "smooth_" + type.getName().toLowerCase());
        createModel(WishBlocks.brickSedimentary, type.getId(),
            "brick_" + type.getName().toLowerCase());
        createModel(WishBlocks.gravelSedimentary, type.getId(),
            "gravel_" + type.getName().toLowerCase());
        createModel(WishBlocks.sandSedimentary, type.getId(),
            "sand_" + type.getName().toLowerCase());
      }
      if (type.getType() == StoneType.RockType.Igneous) {
        createModel(WishBlocks.stoneIgneous, type.getId(), "stone_" + type.getName().toLowerCase());
        createModel(WishBlocks.cobbleIgneous, type.getId(),
            "cobble_" + type.getName().toLowerCase());
        createModel(WishBlocks.smoothIgneous, type.getId(),
            "smooth_" + type.getName().toLowerCase());
        createModel(WishBlocks.brickIgneous, type.getId(),
            "brick_" + type.getName().toLowerCase());
        createModel(WishBlocks.gravelIgneous, type.getId(),
            "gravel_" + type.getName().toLowerCase());
        createModel(WishBlocks.sandIgneous, type.getId(),
            "sand_" + type.getName().toLowerCase());
      }
    }
  }

  @Override
  public EntityPlayer getPlayer(MessageContext ctx) {
    return (ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayer(ctx));
  }

  @Override
  public IThreadListener getThreadFromCTX(MessageContext ctx) {
    return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromCTX(ctx));
  }
}
