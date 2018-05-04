package com.github.jamies1211.prisontools.CommandsEvent;

import com.github.jamies1211.prisontools.ActionsGeneral.GenerateToken;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
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
public class GenerateEventTokenCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		String eventType = args.<String>getOne("event").get().toUpperCase();

		if (src instanceof Player) {
			Player player = (Player) src;
			EnumSpecialEvent currentEvent = EnumSpecialEvent.getSpecialEventData(eventType);

			if (currentEvent != null) {

				ItemStack token = GenerateToken.generateEventToken(eventType);

				player.getInventory().offer(token);
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + tokenGenerateEvent.replace("%eventName%", currentEvent.eventName)));
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventNotExist.replace("%event%", eventType)));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + playerCommandError));
		}

		return CommandResult.success();
	}
}