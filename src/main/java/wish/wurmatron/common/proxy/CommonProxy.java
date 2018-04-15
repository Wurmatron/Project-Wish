package wish.wurmatron.common.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import wish.wurmatron.ProjectWish;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.entity.EntityThrowingRock;

/**
 Server-Sided Proxy
 */
public class CommonProxy {

	public void preInit () {
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID, "rock"),EntityThrowingRock.class, "rock",0,ProjectWish.instance,64,10,true);
	}

	public void init () {
	}
}
