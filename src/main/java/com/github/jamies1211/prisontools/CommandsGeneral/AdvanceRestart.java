package com.github.jamies1211.prisontools.CommandsGeneral;

import com.github.jamies1211.prisontools.UptimeHandler;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 * Created by Jamie on 25-Jun-16.
 */
public class AdvanceRestart implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if (UptimeHandler.advanceRestart()) {
			// Restart moved to 15 mins.
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("Server restart moved forward to 15 mins."));
		} else {
			// Restart not changed as was withing advance restart time.
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("Server restart is already within 15 mins so nothing has been changed."));
		}

		return CommandResult.success();
	}
}