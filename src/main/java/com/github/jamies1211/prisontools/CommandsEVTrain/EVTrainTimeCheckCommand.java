package com.github.jamies1211.prisontools.CommandsEVTrain;

import com.github.jamies1211.prisontools.ActionsEVTrain.EVTrainTimeRemainingCheck;
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
public class EVTrainTimeCheckCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if (src instanceof Player) {
			Player player = (Player) src;
			int zone = args.<Integer>getOne("zone").get();

			if (zone >= 1 && zone <= 3) {
				EVTrainTimeRemainingCheck.checkEVTrainCommand(player, zone);
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNotExist
						.replace("%area%", Integer.toString(zone))));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
		}

		return CommandResult.success();
	}
}
