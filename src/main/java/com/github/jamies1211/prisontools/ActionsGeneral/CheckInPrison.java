package com.github.jamies1211.prisontools.ActionsGeneral;

import org.spongepowered.api.entity.living.player.Player;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.getPrisonConfigInt;

/**
 * Created by Jamie on 15-May-16.
 */
public class CheckInPrison {

	public static boolean checkPlayerInPrison(Player player) {

		// Get prison bounds
		int x1 = getPrisonConfigInt("02 - PrisonBounds", "Pos1", "X");
		int z1 = getPrisonConfigInt("02 - PrisonBounds", "Pos1", "Z");
		int x2 = getPrisonConfigInt("02 - PrisonBounds", "Pos2", "X");
		int z2 = getPrisonConfigInt("02 - PrisonBounds", "Pos2", "Z");

		return PlayerInBounds.checkPlayerInBounds2D(player, x1, z1, x2, z2, 0);
	}

	public static boolean checkPlayerInSpawn(Player player) {

		// Get spawn point
		int x = 0;
		int z = 0;

		return PlayerInBounds.checkPlayerInBounds2D(player, x, z, x, z, 15);
	}
}
