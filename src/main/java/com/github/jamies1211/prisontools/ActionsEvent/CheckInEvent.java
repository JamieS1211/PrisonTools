package com.github.jamies1211.prisontools.ActionsEvent;

import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.ActionsGeneral.PlayerInBounds;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSubNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSubNodeInt;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setPlayerSubNode;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 15-May-16.
 */
public class CheckInEvent {

	public static boolean checkPlayerInEvent(Player player, String eventType, int stage) {

		// Add bounds if not already added
		if(!getSubNodeBoundsExists("11 - EventBounds", eventType, "stage" + stage, "Pos1", "X")){
		//if (generalDataConfig.getNode("11 - EventBounds", eventType, "stage" + stage, "Pos1", "X").getValue() == null) {

			setBounds("11 - EventBounds", eventType, "stage" + stage, "Pos1", "X","0");
			setBounds("11 - EventBounds", eventType, "stage" + stage, "Pos1", "Y","0");
			setBounds("11 - EventBounds", eventType, "stage" + stage, "Pos1", "Z","0");
			setBounds("11 - EventBounds", eventType, "stage" + stage, "Pos2", "X","0");
			setBounds("11 - EventBounds", eventType, "stage" + stage, "Pos2", "Y","0");
			setBounds("11 - EventBounds", eventType, "stage" + stage, "Pos2", "Z","0");
		}

		// Check player is inside event bounds

		int x1 = getSubNodeBounds("11 - EventBounds", eventType, "stage" + stage, "Pos1", "X");
		int y1 = getSubNodeBounds("11 - EventBounds", eventType, "stage" + stage, "Pos1", "Y");
		int z1 = getSubNodeBounds("11 - EventBounds", eventType, "stage" + stage, "Pos1", "Z");
		int x2 = getSubNodeBounds("11 - EventBounds", eventType, "stage" + stage, "Pos2", "X");
		int y2 = getSubNodeBounds("11 - EventBounds", eventType, "stage" + stage, "Pos2", "Y");
		int z2 = getSubNodeBounds("11 - EventBounds", eventType, "stage" + stage, "Pos2", "Z");

		return PlayerInBounds.checkPlayerInBounds3D(player, x1, y1, z1, x2, y2, z2, 0);
	}

	public static void checkPlayerHasPermission(Player player) {

		boolean eventsEnabled = false;
		if (getNodeExists("01 - EventsOn")) {
			eventsEnabled = getNodeBool("01 - EventsOn");
		}

		if (eventsEnabled) {

			for (EnumSpecialEvent event : EnumSpecialEvent.values()) {

				String eventName = event.name();
				String configPlayerUUID = player.getUniqueId().toString();
				int stageAllowedInside = 0; // Denotes not allowed in any stage

				if (getPlayerSubNodeExists(configPlayerUUID, "eventData", eventName, "inside")) {
					stageAllowedInside = getPlayerSubNodeInt(configPlayerUUID, "eventData", eventName, "inside");
				}

				EnumSpecialEvent currentSpecialEvent = EnumSpecialEvent.getSpecialEventData(eventName); // Get enum data for the special event.

				// If player not inside stage they have permission for set them as not inside event.
				if (stageAllowedInside != 0) {
					if (!checkPlayerInEvent(player, eventName, stageAllowedInside)) {
						Sponge.getServer().getConsole().sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventExit
								.replace("%player%", player.getName())
								.replace("%event%", EnumSpecialEvent.getSpecialEventData(eventName).eventName)));
						setPlayerSubNode(configPlayerUUID, "eventData", eventName, "inside", "0");
					}
				}

				// Kicks player out of event stage if they are not supposed to be inside.
				for (int currentStage = 1; currentStage <= currentSpecialEvent.stages; currentStage++) { // For every stage that exists for the event.

					if (checkPlayerInEvent(player, eventName, currentStage)) { // Player in event at current stage.
						String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, eventName);

						// Kicks players who don't have permission to be in that stage and players who have restricted items.
						if (currentStage != stageAllowedInside) { // Player does not have permission.
							Teleport.Spawn(player);
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventNotAllowed));
						} else if (restrictedItems != null) { // Player has a restricted item.
							Teleport.Spawn(player);
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + restrictedItemSmuggle
									.replace("%restricted%", restrictedItems)));
						}
					}
				}
			}
		}
	}
}
