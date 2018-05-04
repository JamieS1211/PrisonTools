package com.github.jamies1211.prisontools.CommandsSafari;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 15-May-16.
 */
public class SafariHelpCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			List<String> allowedSafariZones = new ArrayList<>();

			for (int currentSafari = 1; currentSafari <= 8; currentSafari++) {
				if (player.hasPermission("prisontools.safari.access." + currentSafari)) {
					allowedSafariZones.add("safari:" + currentSafari);
				}
			}

			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariHelpAccess.replace("%safariList", allowedSafariZones.toString())));
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariHelpDetails));
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariHelpTime));
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariHelpJoin));

		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
			return CommandResult.empty();
		}

		return CommandResult.success();
	}

}
