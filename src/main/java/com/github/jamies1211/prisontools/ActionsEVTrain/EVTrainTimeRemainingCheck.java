package com.github.jamies1211.prisontools.ActionsEVTrain;

import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeInt;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 11-May-16.
 */
public class EVTrainTimeRemainingCheck {

	public static void checkEVTrainCommand(Player player, int evTrainNumber) {

		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		String playerUUID = player.getUniqueId().toString();

		int currentValue = 0;

		//if (playerDataConfig.getNode("Players", playerUUID, "evTrainData", "area" + evTrainNumber).getValue() != null) {
		if (getPlayerNodeExists(playerUUID,"evTrainData","area" + evTrainNumber)){
			currentValue = getPlayerNodeInt(playerUUID, "evTrainData", ("area" + evTrainNumber));
			//currentValue = playerDataConfig.getNode("Players", playerUUID, "evTrainData", ("area" + evTrainNumber)).getInt();
		}

		if (currentValue == 0) {
			player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNoTime
					.replace("%area%", Integer.toString(evTrainNumber))));
		} else {
			String currentTimeString = SecondsToString.secondsToString(currentValue);
			player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainTimeCheck
					.replace("%remainingTime%", currentTimeString)
					.replace("%area%", Integer.toString(evTrainNumber))));
		}
	}

}
