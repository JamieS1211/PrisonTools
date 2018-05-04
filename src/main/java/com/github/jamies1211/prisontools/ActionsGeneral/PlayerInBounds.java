package com.github.jamies1211.prisontools.ActionsGeneral;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Created by Jamie on 02-Jul-16.
 */
public class PlayerInBounds {

	public static boolean checkPlayerInBounds3D (Player player, int x1, int y1, int z1, int x2, int y2, int z2, int expansion) {

		Location<World> location = player.getLocation();

		return checkPlayerInBounds3D(location, x1, y1, z1, x2, y2, z2, expansion);
	}

	public static boolean checkPlayerInBounds3D (Location<World> location, int x1, int y1, int z1, int x2, int y2, int z2, int expansion) {

		int yLarge = y1 + 1;
		int ySmall = y2;


		if (y1 < y2) {
			yLarge = y2 + 1;
			ySmall = y1;
		}

		return checkPlayerInBounds2D(location, x1, z1, x2, z2, expansion) &&
				location.getY() < (yLarge + expansion) && location.getY() > (ySmall - expansion);
	}


	public static boolean checkPlayerInBounds2D (Player player, int x1, int z1, int x2, int z2, int expansion) {

		Location<World> location = player.getLocation();

		return checkPlayerInBounds2D(location, x1, z1, x2, z2, expansion);
	}

	public static boolean checkPlayerInBounds2D (Location<World> location, int x1, int z1, int x2, int z2, int expansion) {
		int xLarge = x1 + 1; // Plus one is so that if player is inside outskirts of listed blocks.
		int xSmall = x2;

		if (x1 < x2) {
			xLarge = x2 +1;
			xSmall = x1;
		}

		int zLarge = z1 + 1;
		int zSmall = z2;


		if (z1 < z2) {
			zLarge = z2 + 1;
			zSmall = z1;
		}

		return location.getX() < (xLarge + expansion) && location.getX() > (xSmall - expansion) &&
				location.getZ() < (zLarge + expansion) && location.getZ() > (zSmall - expansion);
	}
}
