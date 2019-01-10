package wish.wurmatron.api.rock.gem;

import net.minecraft.item.ItemStack;
import wish.wurmatron.api.rock.StoneType;

import java.util.List;

/**
 Registry used to manage all Gems

 @see wish.wurmatron.api.WishAPI#gemRegistry for the instance */
public interface GemRegistry {

    /**
     Lists all the currently registed Gems

     @return A List of all the active gems
     */
    List <Gem> getGems ();

    /**
     Adds a gem instance to be used by the rest of the mod

     @param gem Gem instance to register

     @return true if the gem was created, false if not (possibly duplicate name)
     */
    boolean registerGem (Gem... gem);

    /**
     Removes a gem from the registry

     @param gem Gem instance to remove

     @return true if the gem was removed, false if not (does not exist)
     */
    boolean removeGem (Gem... gem);

    /**
     Removes a gem from the registry using its name

     @param names Gem names to remove

     @return true if the gem was removed, false if not (does not exist)
     */
    boolean removeGem (String... names);

    /**
     Easily get a list of certain Gems that can spawn in a certain rock type

     @param type Locates Gems that can spawn in this StoneType

     @return A list of the gems that can spawn in this StoneType
     */
    List <Gem> getGemsPerType (StoneType type);

    /**
     Locates an gem based on its name

     @param name Gem unlocalizedName
     */
    Gem getGemFromName (String name);

    ItemStack generateRandomGem (StoneType type,int tier);

    /**
     Generates a random gem based on a stoneType

     @param type The of stone the gem can spawn in
     */
    ItemStack generateRandomGem (StoneType type);
}
