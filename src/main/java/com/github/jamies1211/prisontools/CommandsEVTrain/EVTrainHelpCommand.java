package com.github.jamies1211.prisontools.CommandsEVTrain;

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
public class EVTrainHelpCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			List<String> allowedEVTrainZones = new ArrayList<>();

			for (int zone = 1; zone <= 3; zone++) {
				if (player.hasPermission("prisontools.evarea.access." + zone)) {
					allowedEVTrainZones.add("EV area:" + zone);
				}
			}

			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainHelpAccess + allowedEVTrainZones));
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainHelpDetails));
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainHelpTime));
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainHelpJoin));

		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
			return CommandResult.empty();
		}

		return CommandResult.success();
	}

}
