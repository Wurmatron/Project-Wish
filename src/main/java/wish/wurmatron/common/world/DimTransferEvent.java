package wish.wurmatron.common.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import joptsimple.internal.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import wish.wurmatron.api.interfaces.IDimensionFall;
import wish.wurmatron.common.utils.LogHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class DimTransferEvent {

	private static HashMap <Integer, DimFall> travelData = new HashMap <> ();
	private static final Gson gson = new GsonBuilder ().setPrettyPrinting ().create ();

	public static void loadData (File location) {
		File loc = new File (location + File.separator + "Dimension");
		if (loc.exists () && loc.listFiles ().length > 0) {
			for (File file : loc.listFiles ())
				if (file.isFile () && file.getName ().endsWith (".json")) {
					try {
						DimFall temp = gson.fromJson (Strings.join (Files.readAllLines (file.toPath ()),""),DimFall.class);
						if (travelData.containsKey (temp.dimensionID))
							LogHandler.warn ("More Than 1 Dimension Travels have been registed for " + temp.dimensionID + " it will not be registed!");
						else
							travelData.put (temp.startDimID,temp);
					} catch (IOException e) {
						e.printStackTrace ();
					}
				}
		}
	}

	@SubscribeEvent
	public void onPlayerTick (TickEvent.PlayerTickEvent e) {
		if (e.side == Side.SERVER && e.phase == TickEvent.Phase.START && e.player.world.getWorldTime () % 10 == 0 && travelData.size () > 0 && e.player.posY <= 0) {
			if (travelData.containsKey (e.player.dimension)) {
				DimFall data = travelData.get (e.player.dimension);
				if (e.player.posY < data.yTravel) {
					e.player.addPotionEffect (new PotionEffect (MobEffects.NAUSEA,100,1));
					e.player.addPotionEffect (new PotionEffect (MobEffects.BLINDNESS,100,1));
					teleportPlayerTo ((EntityPlayerMP) e.player,((EntityPlayerMP) e.player).posX,data.spawnHeight,((EntityPlayerMP) e.player).posZ,data.dimensionID);
					e.player.getEntityData ().setInteger ("Traveled",data.fallDamage);
				}
			}
		}
	}

	// https://github.com/aidancbrady/Mekanism
	// Created by aidancbrady
	public static void teleportPlayerTo (EntityPlayerMP player,double x,double y,double z,int dimID) {
		if (player.dimension != dimID) {
			int id = player.dimension;
			WorldServer oldWorld = player.mcServer.getWorld (player.dimension);
			player.dimension = dimID;
			WorldServer newWorld = player.mcServer.getWorld (player.dimension);
			player.connection.sendPacket (new SPacketRespawn (player.dimension,player.world.getDifficulty (),newWorld.getWorldInfo ().getTerrainType (),player.interactionManager.getGameType ()));
			oldWorld.removeEntityDangerously (player);
			player.isDead = false;

			if (player.isEntityAlive ()) {
				newWorld.spawnEntity (player);
				player.setLocationAndAngles (x + 0.5,y + 1,z + 0.5,player.rotationYaw,player.rotationPitch);
				newWorld.updateEntityWithOptionalForce (player,false);
				player.setWorld (newWorld);
			}

			player.mcServer.getPlayerList ().preparePlayer (player,oldWorld);
			player.connection.setPlayerLocation (x + 0.5,y + 1,z + 0.5,player.rotationYaw,player.rotationPitch);
			player.interactionManager.setWorld (newWorld);
			player.mcServer.getPlayerList ().updateTimeAndWeatherForPlayer (player,newWorld);
			player.mcServer.getPlayerList ().syncPlayerInventory (player);

			for (PotionEffect potioneffect : player.getActivePotionEffects ()) {
				player.connection.sendPacket (new SPacketEntityEffect (player.getEntityId (),potioneffect));
			}

			player.connection.sendPacket (new SPacketSetExperience (player.experience,player.experienceTotal,player.experienceLevel)); // Force XP sync

			FMLCommonHandler.instance ().firePlayerChangedDimensionEvent (player,id,dimID);
		} else {
			player.connection.setPlayerLocation (x + 0.5,y + 1,z + 0.5,player.rotationYaw,player.rotationPitch);
		}
	}

	@SubscribeEvent
	public void onPlayerFall (LivingFallEvent e) {
		if (e.getEntity () instanceof EntityPlayer & e.getEntity ().getEntityData ().getInteger ("Traveled") >= 0) {
			e.getEntity ().attackEntityFrom (DamageSource.FALL,e.getEntity ().getEntityData ().getInteger ("Traveled"));
			e.getEntity ().getEntityData ().removeTag ("Traveled");
			e.setCanceled (true);
		}
	}

	public static class DimFall implements IDimensionFall {

		private int startDimID;
		private int yTravel;
		private int spawnHeight;
		private int dimensionID;
		private int fallDamage;

		public DimFall (int startDimID,int yTravel,int spawnHeight,int dimensionID,int fallDamage) {
			this.startDimID = startDimID;
			this.yTravel = yTravel;
			this.spawnHeight = spawnHeight;
			this.dimensionID = dimensionID;
			this.fallDamage = fallDamage;
		}

		@Override
		public int getY () {
			return yTravel;
		}

		@Override
		public int getSpawnHeight () {
			return spawnHeight;
		}

		@Override
		public int getDimension () {
			return dimensionID;
		}

		@Override
		public int getFallDamage () {
			return fallDamage;
		}

		@Override
		public int getStartingDimensionID () {
			return startDimID;
		}
	}

}
