package com.github.jamies1211.prisontools.CommandsEvent;

import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSuperSubNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSuperSubNodeInt;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 24-Jun-16.
 */
public class EventStatsCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		Player targetPlayer = args.<Player>getOne("player").get();
		String eventType = args.<String>getOne("event").get().toUpperCase();
		int eventStage = args.<Integer>getOne("stage").get();

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

				String winForm = "wins";
				if (timesWon == 1) {
					winForm = "win";
				}

				String lossForm = "losses";
				if (timesLost == 1) {
					lossForm = "loss";
				}

				String percentage = "(Win percentage: 100%)";
				if (timesLost != 0) { // Prevent dividing by 0. If losses are 0 percentage set as 100%.
					percentage = "(Win percentage: " + ((timesWon * 100) / (timesWon + timesLost)) + "%)";
				}

				if (timesWon > 0 || timesLost > 0) {
					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
							" " + eventStats + " " + "stage:" + eventStage + " of " + currentEvent.eventName + ": " + timesWon + winForm + ", " + timesLost + lossForm +
							" " + percentage));
				} else { // If player has never ended event stage return empty stat line
					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + "&9" + targetPlayer.getName() +
							" " + eventNeverEndedStage));
				}
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventStageInvalid));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix));
		}


		return CommandResult.success();
	}
}