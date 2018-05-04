package com.github.jamies1211.prisontools.CommandsSafari;

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
public class SafariSetTimeCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		Player targetPlayer = args.<Player>getOne("player").get();
		int safariNumber = args.<Integer>getOne("zone").get();
		int minutes = args.<Integer>getOne("minutes").get();

		if (safariNumber >= 1 && safariNumber <= 8) {
			if (targetPlayer.hasPermission("prisontools.safari.access." + safariNumber)) {
				if (minutes >= 0) {

					int endValue = minutes * 60;
					String endTimeString = SecondsToString.secondsToString(endValue);

					String configPlayerUUID = targetPlayer.getUniqueId().toString();
					setPlayerNode(configPlayerUUID, "safariData", "safari" + safariNumber,(endValue));

					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTimeYouSet
							.replace("%player%", targetPlayer.getName())
							.replace("%safari%", Integer.toString(safariNumber))
							.replace("%newTime%", endTimeString)));

					targetPlayer.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTimeBeenSet
							.replace("%safari%", Integer.toString(safariNumber))
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
