package wish.wurmatron.common.utils.json;

import wish.wurmatron.api.rock.StoneType;
import wish.wurmatron.api.rock.gem.Gem;
import wish.wurmatron.common.registry.WishGemRegistry;

public class WishGem implements Gem {

  public String unlocalziedName;
  public int chance;
  public StoneType[] types;

  public WishGem(String unlocalziedName, int chance, StoneType[] types) {
    this.unlocalziedName = unlocalziedName;
    this.chance = chance;
    this.types = types;
    WishGemRegistry.saveGem(this);
  }

  @Override
  public String getUnlocalizedName() {
    return unlocalziedName;
  }

  @Override
  public int getChance() {
    return chance;
  }

  @Override
  public StoneType[] getStoneTypes() {
    return types;
  }
}
