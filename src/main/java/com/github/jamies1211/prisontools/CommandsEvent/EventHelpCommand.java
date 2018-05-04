package com.github.jamies1211.prisontools.CommandsEvent;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.Data.Messages.eventHelpChallenge;

/**
 * Created by Jamie on 21-Jun-16.
 */
public class EventHelpCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventHelpChallenge));

		return CommandResult.success();
	}
}