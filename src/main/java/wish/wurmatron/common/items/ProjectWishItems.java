package wish.wurmatron.common.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import wish.wurmatron.api.WishAPI;
import wish.wurmatron.api.WishItems;
import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.StoneType.RockType;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.api.rock.ore.Ore;
import wish.wurmatron.common.items.crafting.ItemBrick;
import wish.wurmatron.common.items.crafting.ItemCrystal;
import wish.wurmatron.common.items.crafting.ItemDust;
import wish.wurmatron.common.items.crafting.ItemMeta;
import wish.wurmatron.common.items.crafting.ItemRock;
import wish.wurmatron.common.items.crafting.ItemShard;
import wish.wurmatron.common.items.crafting.ItemSludge;
import wish.wurmatron.common.items.weapons.WishSharpStoneTool;
import wish.wurmatron.common.registry.Registry;

public class ProjectWishItems extends WishItems {

  public static final Item.ToolMaterial ROCK = EnumHelper.addToolMaterial("rock", 1, 20, 5, 3, 0);
  public static final Item.ToolMaterial STEEL = EnumHelper
      .addToolMaterial("steel", 1, 950, 8, 5, 0);
  public static final Item.ToolMaterial COPPER = EnumHelper
      .addToolMaterial("copper", 1, 220, 2, 2, 0);
  public static final Item.ToolMaterial BRONZE = EnumHelper
      .addToolMaterial("bronze", 1, 280, 4, 2, 0);

  public static void registerItems() {
    createAndRegisterOresDrops();
    createAndRegisterGems();
    Registry.registerItem(brickIgneous = new ItemBrick(getRockTypes("itembrick", RockType.Igneous))
        .setUnlocalizedName("itemBrickIgneous"), "itemBrickIgneous");
    Registry.registerItem(
        brickMetamorphic = new ItemBrick(getRockTypes("itembrick", RockType.Metamorphic))
            .setUnlocalizedName("itemBrickMetamorphic"), "itemBrickMetamorphic");
    Registry.registerItem(
        brickSedimentary = new ItemBrick(getRockTypes("itembrick", RockType.Sedimentary))
            .setUnlocalizedName("itemBrickSedimentary"), "itemBrickSedimentary");
    Registry.registerItem(itemRock = new ItemRock(getRockTypes("rock")), "itemRock");
    Registry.registerItem(itemMeta = new ItemMeta(), "itemMeta");
    // Tools
    Registry.registerItem(
        stoneProspectPick = new ItemProspectPick(2, 4, ToolMaterial.STONE, new HashSet<>())
            .setUnlocalizedName("stoneProspectPick"), "stoneProspectPick");
    Registry.registerItem(
        ironProspectPick = new ItemProspectPick(4, 4, ToolMaterial.IRON, new HashSet<>())
            .setUnlocalizedName("ironProspectPick"), "ironProspectPick");
    Registry.registerItem(
        gemProspectPick = new ItemProspectPick(10, 4, ToolMaterial.DIAMOND, new HashSet<>())
            .setUnlocalizedName("gemProspectPick"), "gemProspectPick");
    Registry.registerItem(steelProspectPick = new ItemProspectPick(6, 4, STEEL, new HashSet<>())
        .setUnlocalizedName("steelProspectPick"), "steelProspectPick");
    Registry.registerItem(copperProspectPick = new ItemProspectPick(3, 4, COPPER, new HashSet<>())
        .setUnlocalizedName("copperProspectPick"), "copperProspectPick");
    Registry.registerItem(bronzeProspectPick = new ItemProspectPick(4, 4, COPPER, new HashSet<>())
        .setUnlocalizedName("bronzeProspectPick"), "bronzeProspectPick");
    Registry.registerItem(sharpStoneTool = new WishSharpStoneTool(ROCK, 4, 1), "sharpStoneTool");
  }

  private static String[] oresToString() {
    String[] temp = new String[WishAPI.oreRegistry.getOres().size()];
    for (int index = 0; index < WishAPI.oreRegistry.getOres().size(); index++) {
      temp[index] = WishAPI.oreRegistry.getOres().get(index).getUnlocalizedName();
    }
    return temp;
  }

  private static void createAndRegisterOresDrops() {
    oreDrops = new ArrayList<>();
    oreSludge = new ArrayList<>();
    oreCrystal = new ArrayList<>();
    oreShard = new ArrayList<>();
    for (Ore ore : WishAPI.oreRegistry.getOres()) {
      if (!ore.getUnlocalizedName().contains("gem")) {
        Item item = new ItemOre(ore.getUnlocalizedName());
        Registry.registerItem(item, "itemore" + ore.getUnlocalizedName(), ore);
        WishItems.oreDrops.add(item);
        Item sludge = new ItemSludge(ore.getUnlocalizedName())
            .setUnlocalizedName("sludge" + ore.getUnlocalizedName());
        Registry.registerItem(sludge, sludge.getUnlocalizedName().substring(5));
        WishItems.oreSludge.add(sludge);
        Item shard = new ItemShard(ore.getUnlocalizedName())
            .setUnlocalizedName("shard" + ore.getUnlocalizedName());
        Registry.registerItem(shard, shard.getUnlocalizedName().substring(5));
        WishItems.oreShard.add(shard);
      }
    }
    int amount = WishAPI.oreRegistry.getOres().size() / 16;
    oreDusts = new ArrayList<>();
    for (int index = 0; index < amount; index++) {
      Item dust = new ItemDust(Arrays.copyOfRange(oresToString(), index * 16, (index * 16) + 15));
      Registry.registerItem(dust, "dust" + index);
      WishItems.oreDusts.add(dust);
      Item crystal = new ItemCrystal(
          Arrays.copyOfRange(oresToString(), index * 16, (index * 16) + 15));
      Registry.registerItem(crystal, "crystal" + index);
      WishItems.oreCrystal.add(crystal);
    }
  }

  private static void createAndRegisterGems() {
    WishItems.gems = new ArrayList<>();
    for (Gem gem : WishAPI.gemRegistry.getGems()) {
      Item item = new ItemGem(gem);
      Registry.registerGem(item, "gem" + gem.getUnlocalizedName(), gem);
      WishItems.gems.add(item);
    }
  }

  private static String[] getRockTypes(String prefix, RockType type) {
    List<String> temp = new ArrayList<>();
    for (int index = 0; index < 9; index++) {
      temp.add(prefix + type.name().substring(0, 1).toUpperCase() + type.name().substring(1)
          .toLowerCase());
    }
    return temp.toArray(new String[0]);
  }

  private static String[] getRockTypes(String prefix) {
    List<String> temp = new ArrayList<>();
    for (int index = 0; index < StoneType.values().length; index++) {
      temp.add(prefix + StoneType.values()[index].name().substring(0, 1).toUpperCase() + StoneType.values()[index].name().substring(1)
          .toLowerCase());
    }
    return temp.toArray(new String[0]);
  }
}
