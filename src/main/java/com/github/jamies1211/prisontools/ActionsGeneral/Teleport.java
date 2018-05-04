package com.github.jamies1211.prisontools.ActionsGeneral;

import org.spongepowered.api.entity.living.player.Player;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;

/**
 * Created by Jamie on 08-May-16.
 */
public class Teleport {

	public static void PrisonTeleport(Player player, double targetX, double targetY, double targetZ, String direction) {
		if (player != null) {

			double targetPitch = player.getRotation().getX();
			double targetYaw = player.getRotation().getY();
			double targetRoll = player.getRotation().getZ();

			// North 0, 180, 180
			// South 0, 0, 0
			// East 0, 270, 270
			// West 0, 90, 90

			if (direction.equalsIgnoreCase("North")) {
				targetPitch = 0;
				targetYaw = 180;
				targetRoll = 180;
			} else if (direction.equalsIgnoreCase("South")) {
				targetPitch = 0;
				targetYaw = 0;
				targetRoll = 0;
			} else if (direction.equalsIgnoreCase("East")) {
				targetPitch = 0;
				targetYaw = 270;
				targetRoll = 270;
			} else if (direction.equalsIgnoreCase("West")) {
				targetPitch = 0;
				targetYaw = 90;
				targetRoll = 90;
			} else {
				System.out.println("Teleport function direction not valid so unused");
			}

			double moveX = targetX - player.getLocation().getX();
			double moveY = targetY - player.getLocation().getY();
			double moveZ = targetZ - player.getLocation().getZ();

			double movePitch = targetPitch - player.getRotation().getX();
			double moveYaw = targetYaw - player.getRotation().getY();
			double moveRoll = targetRoll - player.getRotation().getZ();

			player.setLocationAndRotation(player.getLocation().add(moveX, moveY, moveZ),
					player.getRotation().add(movePitch, moveYaw, moveRoll));
		}
	}

	public static void Spawn(Player player) {

		//ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();

		Double spawnX = getSpawnDouble("03 - Spawn", "SpawnX");
		Double spawnY = getSpawnDouble("03 - Spawn", "SpawnY");
		Double spawnZ = getSpawnDouble("03 - Spawn", "SpawnZ");
		String spawnDirection = getSpawnFacing("03 - Spawn", "SpawnDirection");

		Teleport.PrisonTeleport(player, spawnX, spawnY, spawnZ, spawnDirection);
	}
}
