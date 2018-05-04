package com.github.jamies1211.prisontools.CommandsSafari;

import com.github.jamies1211.prisontools.ActionsSafari.SafariTimeRemainingCheck;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 14-May-16.
 */
public class SafariTimeCheckCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if (src instanceof Player) {
			Player player = (Player) src;
			int safariNumber = args.<Integer>getOne("zone").get();

			if (safariNumber >= 1 && safariNumber <= 8) {
				SafariTimeRemainingCheck.checkSafariCommand(player, safariNumber);
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNotExist
						.replace("%safari%", Integer.toString(safariNumber))));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
		}

		return CommandResult.success();
	}
}
