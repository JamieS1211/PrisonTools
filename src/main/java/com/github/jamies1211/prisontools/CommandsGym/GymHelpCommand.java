package com.github.jamies1211.prisontools.CommandsGym;

import com.github.jamies1211.prisontools.Data.EnumGym;
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
 * Created by Jamie on 21-Jun-16.
 */
public class GymHelpCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			List<String> allowedGymFights = new ArrayList<>();

			for (EnumGym gymEnum : EnumGym.values()) {
				if (player.hasPermission(gymEnum.permissionNode)) {
					allowedGymFights.add(gymEnum.name());
				}
			}

			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymHelpAccess
					.replace("%allowedGyms%", allowedGymFights.toString())));
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymHelpChallenge));

		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
			return CommandResult.empty();
		}

		return CommandResult.success();
	}
}