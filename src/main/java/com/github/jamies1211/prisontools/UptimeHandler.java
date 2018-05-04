package com.github.jamies1211.prisontools;

import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import com.github.jamies1211.prisontools.ActionsGeneral.DelayedCommand;
import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import com.github.jamies1211.prisontools.ActionsGeneral.SendMessages;

import java.util.ArrayList;
import java.util.Arrays;

import static com.github.jamies1211.prisontools.Data.Messages.rebootTimerMessage;
import static com.github.jamies1211.prisontools.Data.Messages.rebootTimerMessageCloseInfo;
import static com.github.jamies1211.prisontools.Data.Messages.rebootTimerMessageCloseWarning;

/**
 * Created by Jamie on 16-Aug-16.
 */
public class UptimeHandler {

	public static int uptime = 0;
	public static int rebootTime = 6*60*60; // 12 hours
	public static int gymDisabledBuffer = 15*60; // 15 mins
	public static ArrayList<Integer> remindTimes = new ArrayList<>(Arrays.asList(5, 10, 15, 30, 60, 90, 120, 300, 600, 900, 1800, 2700, 3600));
	public static boolean rebootingStage = false;
	public static int stopAttempts = 0;

	public static void uptimeIncrease () {
		if (uptime >= rebootTime) {
			if (stopAttempts % 15 == 0) {
				GeneralDataConfig.getConfig().save();
				PlayerDataConfig.getConfig().save();
				DelayedCommand.delayedCommand(0, "kickall Scheduled server reboot.");
				DelayedCommand.delayedCommand(250, "save-all");
				DelayedCommand.delayedCommand(500, "stop Scheduled server reboot.");
			}

			stopAttempts++;
		}

		if (uptime == (rebootTime - gymDisabledBuffer)) {
			SendMessages.messageToAllPlayers(3, rebootTimerMessageCloseInfo
					.replace("%time%", SecondsToString.secondsToString(rebootTime - uptime)));
			rebootingStage = true;
		}

		if (remindTimes.contains(rebootTime - uptime)) {
			if (uptime < (rebootTime - gymDisabledBuffer)) {
				SendMessages.messageToAllPlayers(3, rebootTimerMessageCloseWarning
						.replace("%time%", SecondsToString.secondsToString(rebootTime - uptime))
						.replace("%closeTime%", SecondsToString.secondsToString(rebootTime - gymDisabledBuffer - uptime)));
			} else {
				SendMessages.messageToAllPlayers(3, rebootTimerMessage
						.replace("%time%", SecondsToString.secondsToString(rebootTime - uptime)));
			}
		}

		uptime ++;
	}

	public static boolean advanceRestart() {
		if (uptime < rebootTime - gymDisabledBuffer) {
			uptime = (rebootTime - gymDisabledBuffer);
			return true;
		}

		return false;
	}
}
