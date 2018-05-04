package com.github.jamies1211.prisontools.CommandsEVTrain;

import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setPlayerNode;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 14-May-16.
 */
public class EVTrainSetTimeCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		Player targetPlayer = args.<Player>getOne("player").get();
		int zone = args.<Integer>getOne("zone").get();
		int minutes = args.<Integer>getOne("minutes").get();

		if (zone >= 1 && zone <= 3) {
			if (targetPlayer.hasPermission("prisontools.evarea.access." + zone)) {
				if (minutes >= 0) {

					int endValue = minutes * 60;
					String endTimeString = SecondsToString.secondsToString(endValue);

					String configPlayerUUID = targetPlayer.getUniqueId().toString();
					setPlayerNode(configPlayerUUID, "evTrainData", "area" + zone,( endValue));

					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainTimeYouSet
							.replace("%player%", targetPlayer.getName())
							.replace("%area%", Integer.toString(zone))
							.replace("%newTime%", endTimeString)));

					targetPlayer.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainTimeBeenSet
							.replace("%area%", Integer.toString(zone))
							.replace("%newTime%", endTimeString)));

				} else {
					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainNegativeTime));
				}
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainTargetNoAccess
						.replace("%player%", targetPlayer.getName())
						.replace("%area%", Integer.toString(zone))));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNotExist
					.replace("%area%", Integer.toString(zone))));
		}

		return CommandResult.success();

	}
}
