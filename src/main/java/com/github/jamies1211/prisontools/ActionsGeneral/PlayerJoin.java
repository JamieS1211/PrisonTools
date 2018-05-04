package com.github.jamies1211.prisontools.ActionsGeneral;

import com.github.jamies1211.prisontools.ActionsConfig.AddNewPlayer;
import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import com.github.jamies1211.prisontools.PrisonTools;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class PlayerJoin {

	public static void PlayerJoinActions (String configPlayerUUID, Player player) {

		// Adds gym cooldown if not already present
		if (!getPlayerSubNodeExists(configPlayerUUID, "gymData", "gymCooldown", "value")) {
			setPlayerSubNode(configPlayerUUID, "gymData", "gymCooldown", "value","0");
		}

		// Check if player has a username count (true if logged on before).
		if (getPlayerNodeExists(configPlayerUUID, "usernameData", "usernameCount")) {
			int usernameCount = getPlayerNodeInt(configPlayerUUID, "usernameData", "usernameCount");

			if (getPlayerNodeExists(configPlayerUUID, "usernameData", "username" + usernameCount)) {
				if (getPlayerNodeString(configPlayerUUID, "usernameData", "username" + usernameCount).equals(player.getName())) { // Using same username.
					PrisonTools.plugin.getLogger().info(playerUUIDPrefix + configPlayerUUID + " " + playerSameUsername);
				} else {
					usernameCount++;
					setAdditionalPlayerUsernames(configPlayerUUID, player, usernameCount);
					PlayerDataConfig.getConfig().save();
					PrisonTools.plugin.getLogger().info(playerUUIDPrefix + configPlayerUUID + " " + playerNewUsername); // Player has new username.
				}
			}

			// Announce to all players of old usernames.
			if (usernameCount > 1) {
				String message = infoPrefix + otherNameList
						.replace("%player%", player.getName()); // Sets up new message.
				 for (int currentNameNumber = usernameCount - 1; currentNameNumber > 0; currentNameNumber--) { // Adds all username in ascending order of use with punctuation.
					if (currentNameNumber == 1) {
						message = message + getPlayerNodeString(configPlayerUUID, "usernameData", "username" + currentNameNumber) + ".";
					} else {
						message = message + getPlayerNodeString(configPlayerUUID, "usernameData", "username" + currentNameNumber) + ", ";
					}
				}
				MessageChannel.TO_ALL.send((TextSerializers.FORMATTING_CODE.deserialize(message)));
			}


			SendMessages.sendTitleMessage(player, returnJoinMessage.replace("%player%", player.getName()), SendMessages.randomiseWelcomeSubText());

		} else { // Gives player starting kit and adds them to config on first join.
			AddNewPlayer.addNewPlayerData(configPlayerUUID, player);
			Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "kit give start " + player.getName());
			PlayerDataConfig.getConfig().save();
			PrisonTools.plugin.getLogger().info(playerUUIDPrefix + configPlayerUUID + " " + addedToConfig);

			SendMessages.sendTitleMessage(player, firstJoinWelcome.replace("%player%", player.getName()), firstJoinSubWelcome);
		}
	}
}
