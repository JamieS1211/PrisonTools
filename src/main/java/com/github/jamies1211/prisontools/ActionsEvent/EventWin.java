package com.github.jamies1211.prisontools.ActionsEvent;

import com.github.jamies1211.prisontools.ActionsGeneral.RankChanging;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 25-Jun-16.
 */
public class EventWin {
	public static void eventWin(String eventType, Player targetPlayer, Boolean first) {

		// One time code
		for (EnumSpecialEvent ev : EnumSpecialEvent.values()) {
			if (ev.toString().equalsIgnoreCase(eventType)) {
				if (first) {
					if (ev.rankGive) {
						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
								" " + eventPlayerWin + " " + ev.eventName + " " + eventPlayerWinFirstRank.replace("%newRank%", ev.rewardRank)));
						RankChanging.changePlayerRank(targetPlayer.getUniqueId().toString(), targetPlayer.getName(), ev.oldRank, ev.rewardRank);
					} else {
						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
								" " + eventPlayerWin + " " + ev.eventName + " " + eventPlayerWinFirst));
					}
				}

				String completedKitCommand = "kit give %kitName% %playerName%"
						.replace("%kitName%", ev.kitName)
						.replace("%playerName%", targetPlayer.getName());

				Sponge.getCommandManager().process(Sponge.getServer().getConsole(), completedKitCommand);
			}
		}
	}
}
