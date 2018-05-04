package com.github.jamies1211.prisontools.ActionsSafari;

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
public class CheckInSafari {

	//private static ConfigurationNode generalDataConfig;

	public static void checkPlayerHasSafariTime(Player player) {
		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		String configPlayerUUID = player.getUniqueId().toString();

		for (int currentSafari = 1; currentSafari <= 8; currentSafari++) {
			int secondsInSafari = 0;

			if (getPlayerNodeExists( configPlayerUUID, "safariData", "safari" + currentSafari)) { // If value not null.
				secondsInSafari = getPlayerNodeInt(configPlayerUUID, "safariData", "safari" + currentSafari);
			}

			String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "SAFARI");

			if ((CheckInSafari.checkPlayerInSafari(player, currentSafari))) { // If player is in safari.
				if (secondsInSafari <= 0) { // If time in safari is 0 (or less).
					Teleport.Spawn(player);
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTimeEnd
							.replace("%safari%", Integer.toString(currentSafari))));
				} else if (!player.hasPermission("prisontools.safari.access." + currentSafari)) { // If player should not be in that safari.
					Teleport.Spawn(player);
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNoAccess
							.replace("%safari%", Integer.toString(currentSafari))));
				} else if (restrictedItems != null) { // Player found to have restricted items in area.
					Teleport.Spawn(player);
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + restrictedItemSmuggle
							.replace("%restricted%", restrictedItems)));
				}
			}

			if (Arrays.asList(1, 2, 3, 4, 5, 10, 15, 30, 45, 60, 90, 120, 180, 300).contains(secondsInSafari)) {
				String timeString = SecondsToString.secondsToString(secondsInSafari);
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariTimeReminder
						.replace("%remainingTime%", timeString)
						.replace("%safari%", Integer.toString(currentSafari))));
			}
		}
	}

	public static boolean checkPlayerInSafari(Player player, int safariZone) {

		//generalDataConfig = GeneralDataConfig.getConfig().get();

		// Check player is inside safari bounds
		int x1 = getEventBounds("05 - SafariBounds", "safari" + safariZone, "Pos1", "X");
		int z1 = getEventBounds("05 - SafariBounds", "safari" + safariZone, "Pos1", "Z");
		int x2 = getEventBounds("05 - SafariBounds", "safari" + safariZone, "Pos2", "X");
		int z2 = getEventBounds("05 - SafariBounds", "safari" + safariZone, "Pos2", "Z");

		return PlayerInBounds.checkPlayerInBounds2D(player, x1, z1, x2, z2, 0);
	}

	public static void checkPlayerInSafariPadding(Player player) {

		for (int currentSafari = 1; currentSafari <= 8; currentSafari++) {
			// Get safari bounds
			int x1 = getEventBounds("05 - SafariBounds", "safari" + currentSafari, "Pos1", "X");
			int z1 = getEventBounds("05 - SafariBounds", "safari" + currentSafari, "Pos1", "Z");
			int x2 = getEventBounds("05 - SafariBounds", "safari" + currentSafari, "Pos2", "X");
			int z2 = getEventBounds("05 - SafariBounds", "safari" + currentSafari, "Pos2", "Z");

			if (PlayerInBounds.checkPlayerInBounds2D(player, x1, z1, x2, z2, 25)) {
				if (!checkPlayerInSafari(player, currentSafari)) {
					if (player.hasPermission("prisontools.safari.padding.bypass")) {
						player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariPaddingBypass));
					} else {
						Teleport.Spawn(player);
						player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariPaddingKick));
					}
				}
			}
		}
	}

	public static boolean inAnySafari(Player player) {

		return (CheckInSafari.checkPlayerInSafari(player, 1)) || (CheckInSafari.checkPlayerInSafari(player, 2))
				|| (CheckInSafari.checkPlayerInSafari(player, 3)) || (CheckInSafari.checkPlayerInSafari(player, 4))
				|| (CheckInSafari.checkPlayerInSafari(player, 5)) || (CheckInSafari.checkPlayerInSafari(player, 6))
				|| (CheckInSafari.checkPlayerInSafari(player, 7)) || (CheckInSafari.checkPlayerInSafari(player, 8));
	}
}
