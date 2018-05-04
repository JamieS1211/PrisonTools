package com.github.jamies1211.prisontools.CommandsEVTrain;

import com.github.jamies1211.prisontools.ActionsGeneral.GenerateToken;
import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 14-May-16.
 */
public class GenerateEVTrainTokenCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		int zone = args.<Integer>getOne("zone").get();
		int minutes = args.<Integer>getOne("minutes").get();

		if (src instanceof Player) {
			Player player = (Player) src;

			if (zone >= 1 && zone <= 3) {

				ItemStack token = GenerateToken.generateEVTrainToken(minutes, zone);

				player.getInventory().offer(token);
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + tokenGenerateEVTrain
						.replace("%time%", SecondsToString.secondsToString(minutes * 60))
						.replace("%area%", Integer.toString(zone))));
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNotExist
						.replace("%area%", Integer.toString(zone))));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + playerCommandError));
		}

		return CommandResult.success();

	}
}
