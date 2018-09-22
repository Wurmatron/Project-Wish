package wish.wurmatron.api.rock.ore;

import wish.wurmatron.api.rock.StoneType;

import java.util.List;

/**
 Registry used to manage all WishOre Generation

 @see wish.wurmatron.api.WishAPI#oreRegistry */
public interface OreRegistry {

    /**
     Lists all the currently registed ores

     @return A List of all the active ores
     */
    List <Ore> getOres ();

    /**
     Adds a ore instance to be used by the rest of the mod

     @param ore WishOre instance to register

     @return true if the gem was created, false if not (possibly duplicate name)
     */
    boolean registerOre (Ore... ore);

    /**
     Removes a ore from the registry

     @param ore WishOre instance to remove

     @return true if the ore was removed, false if not (does not exist)
     */
    boolean removeOre (Ore... ore);

    /**
     Removes a ore from the registry using its name

     @param names WishOre names to remove

     @return true if the ore was removed, false if not (does not exist)
     */
    boolean removeOre (String... names);

    /**
     Easily get a list of certain Gems that can spawn in a certain stone type

     @param type Locates Ores that can spawn in this StoneType

     @return A list of the ores that can spawn in this StoneType
     */
    List <Ore> getOresPerType (StoneType type);

    /**
     Easily get a list of certain Gems that can spawn in a certain rock type

     @param type Locates Gems that can spawn in this RockType

     @return A list of the gems that can spawn in this RockType
     */
    List <Ore> getOresPerType (StoneType.RockType type);

    /**
     Locates an ore based on its name

     @param name WishOre unlocalizedName
     */
    Ore getOreFromName (String name);
}
