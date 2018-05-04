package com.github.jamies1211.prisontools.CommandsSafari;

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
public class GenerateSafariTokenCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		int safariNumber = args.<Integer>getOne("zone").get();
		int minutes = args.<Integer>getOne("minutes").get();

		if (src instanceof Player) {
			Player player = (Player) src;

			if (safariNumber >= 1 && safariNumber <= 8) {

				ItemStack token = GenerateToken.generateSafariToken(minutes, safariNumber);

				player.getInventory().offer(token);
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + tokenGenerateSafari
						.replace("%time%", SecondsToString.secondsToString(minutes * 60))
						.replace("%safari%", Integer.toString(safariNumber))));
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNotExist
						.replace("%safari%", Integer.toString(safariNumber))));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + playerCommandError));
		}

		return CommandResult.success();

	}
}
