package com.github.jamies1211.prisontools.ActionsGeneral;

import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import org.spongepowered.api.Sponge;

/**
 * Created by Jamie on 01-Aug-16.
 */
public class RankChanging {
	public static void changePlayerRank (String playerUUIDString, String playerName, String oldRank, String nextRank) {
		if (!oldRank.equalsIgnoreCase("EMPTY")) {
			removePlayerRank(playerUUIDString, playerName, oldRank);
		}

		if (!nextRank.equalsIgnoreCase("EMPTY")) {
			addPlayerRank(playerUUIDString, playerName, nextRank);
		}
	}

	public static void addPlayerRank (String playerUUIDString, String playerName, String nextRank) {

		Sponge.getCommandManager().process(Sponge.getServer().getConsole(),
				GeneralDataConfig.getConfig().get().getNode("13 - RankCommands", "rankup").getString()
						.replace("%playerUUID%", playerUUIDString)
						.replace("%playerName%", playerName)
						.replace("%newRank%", nextRank));
	}

	public static void removePlayerRank (String playerUUIDString, String playerName, String oldRank) {

		Sponge.getCommandManager().process(Sponge.getServer().getConsole(),
				GeneralDataConfig.getConfig().get().getNode("13 - RankCommands", "derank").getString()
						.replace("%playerUUID%", playerUUIDString)
						.replace("%playerName%", playerName)
						.replace("%oldRank%", oldRank));
	}
}
