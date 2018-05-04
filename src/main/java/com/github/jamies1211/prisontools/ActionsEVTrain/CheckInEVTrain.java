package com.github.jamies1211.prisontools.ActionsEVTrain;

import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.ActionsGeneral.PlayerInBounds;
import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeInt;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 15-May-16.
 */
public class CheckInEVTrain {

	public static boolean checkPlayerInEVTrain(Player player, int evTrainArea) {

		//ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();

		// Check player is inside ev area bounds
		int x1 = getEventBounds("09 - EVTrainBounds", Integer.toString(evTrainArea), "Pos1", "X");
		int y1 = getEventBounds("09 - EVTrainBounds", Integer.toString(evTrainArea), "Pos1", "Y");
		int z1 = getEventBounds("09 - EVTrainBounds", Integer.toString(evTrainArea), "Pos1", "Z");
		int x2 = getEventBounds("09 - EVTrainBounds", Integer.toString(evTrainArea), "Pos2", "X");
		int y2 = getEventBounds("09 - EVTrainBounds", Integer.toString(evTrainArea), "Pos2", "Y");
		int z2 = getEventBounds("09 - EVTrainBounds", Integer.toString(evTrainArea), "Pos2", "Z");

		return PlayerInBounds.checkPlayerInBounds3D(player, x1, y1, z1, x2, y2, z2, 0);
	}

	public static void checkPlayerHasEVTrainTime(Player player) {

		String configPlayerUUID = player.getUniqueId().toString();
		for (int currentArea = 1; currentArea <= 3; currentArea++) {

			int secondsInArea = 0;

			if (getPlayerNodeExists(configPlayerUUID, "evTrainData", "area" + currentArea)) { // If value not null.
				secondsInArea = getPlayerNodeInt(configPlayerUUID, "evTrainData", "area" + currentArea);
			}

			String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "Evtrain");

			if (CheckInEVTrain.checkPlayerInEVTrain(player, currentArea)) { // If player is in evtrain.
				if (secondsInArea <= 0) { // If time in ev Train is 0 (or less).
					Teleport.Spawn(player);
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainTimeEnd
							.replace("%area%", Integer.toString(currentArea))));
				} else if (!player.hasPermission("prisontools.evarea.access." + currentArea)) { // If player should not be in that evtrain.
					Teleport.Spawn(player);
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNoAccess
							.replace("%area%", Integer.toString(currentArea))));
				} else if (restrictedItems != null) { // Player found to have restricted items in area.
					Teleport.Spawn(player);
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + restrictedItemSmuggle
							.replace("%restricted%", restrictedItems)));
				}
			}

			if (Arrays.asList(1, 2, 3, 4, 5, 10, 15, 30, 45, 60, 90, 120, 180, 300).contains(secondsInArea)) {
				String timeString = SecondsToString.secondsToString(secondsInArea);
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainTimeReminder
						.replace("%remainingTime%", timeString)
						.replace("%area%", Integer.toString(currentArea))));
			}

		}
	}
}
