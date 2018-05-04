package com.github.jamies1211.prisontools.CommandsGym;

import com.github.jamies1211.prisontools.ActionsGeneral.GenerateToken;
import com.github.jamies1211.prisontools.Data.EnumGym;
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
public class GenerateGymTokenCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		String rank = args.<String>getOne("gym").get().toUpperCase();

		if (src instanceof Player) {
			Player player = (Player) src;
			EnumGym gym = EnumGym.getGymData(rank);

			if (gym != null) {

				ItemStack token = GenerateToken.generateGymToken(rank);

				player.getInventory().offer(token);
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + tokenGenerateGym.replace("%gym%", rank)));
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymNotExist.replace("%gym%", rank)));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + playerCommandError));
		}

		return CommandResult.success();
	}
}