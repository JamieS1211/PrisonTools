package com.github.jamies1211.prisontools.CommandsEvent;

import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import com.github.jamies1211.prisontools.ActionsEvent.EventWin;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 14-May-16.
 */
public class EventStageCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		Player targetPlayer = args.<Player>getOne("player").get();
		String eventType = args.<String>getOne("event").get().toUpperCase();
		int eventStage = args.<Integer>getOne("stage").get();
		Boolean win = args.<Boolean>getOne("win").get();

		String configPlayerUUID = targetPlayer.getUniqueId().toString();
		EnumSpecialEvent currentEvent = EnumSpecialEvent.getSpecialEventData(eventType);


		if (currentEvent != null) {

			int totalStages = currentEvent.stages;

			if ((eventStage >= 0) && (eventStage <= totalStages)) {

				int timesWon = 0;
				int timesLost = 0;

				if (getPlayerSuperSubNodeExists(configPlayerUUID, "eventData", eventType, "stage" + eventStage, "wintrue")) {
					timesWon = getPlayerSuperSubNodeInt(configPlayerUUID, "eventData", eventType, "stage" + eventStage, "wintrue");
				}


				if (getPlayerSuperSubNodeExists(configPlayerUUID, "eventData", eventType, "stage" + eventStage, "winfalse")) {
					timesLost = getPlayerSuperSubNodeInt(configPlayerUUID, "eventData", eventType, "stage" + eventStage, "winfalse");
				}

				int newTimesWon = timesWon;
				int newTimesLost = timesLost;

				boolean cheat = (getPlayerSubNodeInt(configPlayerUUID, "eventData", eventType, "inside") != eventStage);

				if (cheat) {
					win = false;
				}

				if (win) { // Player wins without cheating.
					newTimesWon++;

					if (newTimesWon > 1) { // Stage completion for multiple time.
						if (eventStage == totalStages) { // Player completed entire challenge again.
							MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
									" " + eventPlayerWin + " " + currentEvent.eventName + " " + eventPlayerWinAgain));
							// Run win code for event if was final stage and not first victory.
							EventWin.eventWin(eventType, targetPlayer, false);

						} else { // Player completed stage again.
							MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
									" " + eventPlayerWin + " " + "stage:" + eventStage + " of " + currentEvent.eventName + " " + eventPlayerStageCompleteAgain));
						}
					} else { // First completion of stage.
						if (eventStage == totalStages) { // Player completed entire challenge for first time.
							// Run win code for event if was final stage and first victory.
							EventWin.eventWin(eventType, targetPlayer, true);
						} else { // Player completed stage for first time.
							MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
									" " + eventPlayerWin + " " + "stage:" + eventStage + " of " + currentEvent.eventName + " " + eventPlayerStageCompleteFirst));
						}
					}

					setPlayerSuperSubNode(configPlayerUUID, "eventData", eventType, "stage" + eventStage, "wintrue", newTimesWon);

				} else { // Player loses or cheated.
					newTimesLost++;

					if (cheat){
						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
								" " + eventPlayerCheat + " " + "stage:" + eventStage + " of " + currentEvent.eventName));
					} else {
						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
								" " + eventPlayerLoss + " " + "stage:" + eventStage + " of " + currentEvent.eventName));
					}

					setPlayerSuperSubNode(configPlayerUUID, "eventData", eventType, "stage" + eventStage, "winfalse",newTimesLost);
				}

				// Stat printout.

				String winForm = "wins";
				if (newTimesWon == 1) {
					winForm = "win";
				}

				String lossForm = "losses";
				if (newTimesLost == 1) {
					lossForm = "loss";
				}

				String percentage = "(Win percentage: 100%)";
				if (newTimesLost != 0) { // Prevent dividing by 0. If losses are 0 percentage set as 100%.
					percentage = "(Win percentage: " + ((newTimesWon * 100) / (newTimesWon + newTimesLost)) + "%)";
				}

				MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
						" " + eventStats + " " + "stage:" + eventStage + " of " + currentEvent.eventName + ": " + newTimesWon + winForm + ", " + newTimesLost + lossForm +
						" " + percentage));

				if ((eventStage != totalStages) && (win)) { // If player wins on non final stage teleport to the next stage and set as inside next stage.
					int nextEventStage = eventStage + 1;

					// Sets player as no longer being in  eventType
					setPlayerSubNode( configPlayerUUID, "eventData", eventType, "inside",(nextEventStage));

					double X = getEventSpawnDouble("10 - EventSpawn", eventType, "stage" + nextEventStage + "spawn", "X");
					double Y = getEventSpawnDouble("10 - EventSpawn", eventType, "stage" + nextEventStage + "spawn", "Y");
					double Z = getEventSpawnDouble("10 - EventSpawn", eventType, "stage" + nextEventStage + "spawn", "Z");
					String facing = getEventSpawnFacing("10 - EventSpawn", eventType, "stage" + nextEventStage + "spawn", "Facing");

					Teleport.PrisonTeleport(targetPlayer, X, Y, Z, facing);
				} else { // If completed event or player lost teleport to spawn.

					// Sets player as no longer being in event at all.
					setPlayerSubNode(configPlayerUUID, "eventData", eventType, "inside","0");

					Teleport.Spawn(targetPlayer);

				}

				PlayerDataConfig.getConfig().save();
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventStageInvalid));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventTypeInvalid));
		}

		return CommandResult.success();

	}
}
