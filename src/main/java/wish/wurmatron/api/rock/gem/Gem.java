package wish.wurmatron.api.rock.gem;

import wish.wurmatron.api.rock.StoneType;

/**
 A basic item that drops from rocks
 */
public interface Gem {

    /**
     Unlocalized Name of the gem
     */
    String getUnlocalizedName ();

    /**
     Chance this gem has to spawn comparted to all the rest of the gems
     */
    double getChance ();

    /**
     StoneType's this Gem can spawn within
     */
    StoneType[] getStoneTypes ();
}
