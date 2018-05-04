package com.github.jamies1211.prisontools.ActionsSafari;

import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeInt;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 11-May-16.
 */
public class SafariTimeRemainingCheck {

	public static void checkSafariCommand(Player player, int safariNumber) {

		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		String playerUUID = player.getUniqueId().toString();

		int currentValue = 0;

		if (getPlayerNodeExists(playerUUID, "safariData", "safari" + safariNumber)) {
			currentValue = getPlayerNodeInt(playerUUID, "safariData", ("safari" + safariNumber));
		}

		if (currentValue == 0) {
			player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNoTime
					.replace("%safari%", Integer.toString(safariNumber))));
		} else {
			String currentTimeString = SecondsToString.secondsToString(currentValue);
			player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTimeCheck
					.replace("%remainingTime%", currentTimeString)
					.replace("%safari%", Integer.toString(safariNumber))));
		}
	}

}
