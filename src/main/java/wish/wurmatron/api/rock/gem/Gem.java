package wish.wurmatron.api.rock.gem;

import wish.wurmatron.api.rock.StoneType;

/**
 * A basic item that drops from rocks
 */
public interface Gem {

  /**
   * Unlocalized Name of the gem
   */
  String getUnlocalizedName();

  /**
   * Chance this gem has to spawn comparted to all the rest of the gems
   */
  int getChance();

  /**
   * StoneType's this Gem can spawn within
   */
  StoneType[] getStoneTypes();

  enum GRADE {
    D("D", 128), C("C", 64), B("B", 32), A("A", 16), AA("AA", 4), S("S", 1);

    private int chance;
    private String name;

    GRADE(String name, int chance) {
      this.name = name;
      this.chance = chance;
    }

    public int getChance() {
      return chance;
    }

    public String getName() {
      return name;
    }
  }
}
