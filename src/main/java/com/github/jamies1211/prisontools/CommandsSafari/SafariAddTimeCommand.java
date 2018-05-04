package com.github.jamies1211.prisontools.CommandsSafari;

import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeInt;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setPlayerNode;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 14-May-16.
 */
public class SafariAddTimeCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		Player targetPlayer = args.<Player>getOne("player").get();
		int safariNumber = args.<Integer>getOne("zone").get();
		int minutes = args.<Integer>getOne("minutes").get();

		if (safariNumber >= 1 && safariNumber <= 8) {
			if (targetPlayer.hasPermission("prisontools.safari.access." + safariNumber)) {

				String configPlayerUUID = targetPlayer.getUniqueId().toString();
				int startValue = getPlayerNodeInt(configPlayerUUID, "safariData", "safari" + safariNumber);
				int endValue = startValue + (minutes * 60);

				if (endValue >= 0) {
					setPlayerNode(configPlayerUUID, "safariData", "safari" + safariNumber,(endValue));

					String startTimeString = SecondsToString.secondsToString(startValue);
					String endTimeString = SecondsToString.secondsToString(endValue);

					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTimeYouChange
							.replace("%player%", targetPlayer.getName())
							.replace("%safari%", Integer.toString(safariNumber))
							.replace("%oldTime%", startTimeString)
							.replace("%newTime%", endTimeString)));

					targetPlayer.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTimeBeenChanged
							.replace("%safari%", Integer.toString(safariNumber))
							.replace("%oldTime%", startTimeString)
							.replace("%newTime%", endTimeString)));
				} else {
					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariNegativeTime));
				}
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTargetNoAccess
						.replace("%player%", targetPlayer.getName())
						.replace("%safari%", Integer.toString(safariNumber))));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNotExist
					.replace("%safari%", Integer.toString(safariNumber))));
		}
		return CommandResult.success();
	}
}
