package com.github.jamies1211.prisontools.ActionsGym;

import org.spongepowered.api.entity.living.player.Player;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.*;

/**
 * Created by Jamie on 30-May-16.
 */
public class GymCooldown {

	public static void decreaseCooldown(Player player) {

		String configPlayerUUID = player.getUniqueId().toString();
		if (getPlayerSubNodeInt( configPlayerUUID, "gymData", "gymCooldown", "value") > 0) {

			int updatedTime = getPlayerSubNodeInt(configPlayerUUID, "gymData", "gymCooldown", "value") - 1;

			setPlayerSubNode(configPlayerUUID, "gymData", "gymCooldown", "value",updatedTime);

		} else if (getPlayerSubNodeInt(configPlayerUUID, "gymData", "gymCooldown", "value") < 0) {

			setPlayerSubNode(configPlayerUUID, "gymData", "gymCooldown", "value",0);
		}
	}
}
