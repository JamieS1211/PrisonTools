package com.github.jamies1211.prisontools.ActionsGym;

import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.ActionsGeneral.PlayerInBounds;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import com.github.jamies1211.prisontools.Data.EnumGym;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 15-May-16.
 */
public class CheckInGym {

	public static boolean checkPlayerInGym(Player player, String gymID) {

		//ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();

		// Check player is inside gym bounds
		int x1 = getEventBounds("07 - GymBounds", gymID, "Pos1", "X");
		int y1 = getEventBounds("07 - GymBounds", gymID, "Pos1", "Y");
		int z1 = getEventBounds("07 - GymBounds", gymID, "Pos1", "Z");
		int x2 = getEventBounds("07 - GymBounds", gymID, "Pos2", "X");
		int y2 = getEventBounds("07 - GymBounds", gymID, "Pos2", "Y");
		int z2 = getEventBounds("07 - GymBounds", gymID, "Pos2", "Z");

		return PlayerInBounds.checkPlayerInBounds3D(player, x1, y1, z1, x2, y2, z2, 0);
	}

	public static void checkPlayerHasPermission(Player player) {

		boolean gymsEnabled = false;
		if (getNodeExists("01 - GymsOn")) {
			gymsEnabled = getNodeBool("01 - GymsOn");
		}

		if (gymsEnabled) {

			boolean membersFeaturesOn = false;
			if (getNodeExists("01 - MembersFeaturesOn")) {
				membersFeaturesOn = getNodeBool("01 - MembersFeaturesOn");
			}

			for (EnumGym gym : EnumGym.values()) {

				if (gym.membersFeature == membersFeaturesOn) {
					String gymNode = gym.gymNodeIdentifier;
					String configPlayerUUID = player.getUniqueId().toString();
					Boolean allowedInside = false;

					if (getPlayerSubNodeExists(configPlayerUUID, "gymData", gymNode, "inside")) {
						allowedInside = getPlayerSubNodeBoolean(configPlayerUUID, "gymData", gymNode, "inside");
					}

					if (checkPlayerInGym(player, gymNode)) { // Player in gym.
						String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "GYM");

						if (!allowedInside) { // Player does not have permission
							Teleport.Spawn(player);
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymNotAllowed));
						} else if (restrictedItems != null) {
							Teleport.Spawn(player);
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + restrictedItemSmuggle
									.replace("%restricted%", restrictedItems)));
						}
					} else {
						if (allowedInside) { // Player has permission but has left.
							Sponge.getServer().getConsole().sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymExit
									.replace("%player%", player.getName())));
							setPlayerSubNode(configPlayerUUID, "gymData", gymNode, "inside", "false");
						}
					}
				}
			}
		}
	}
}
