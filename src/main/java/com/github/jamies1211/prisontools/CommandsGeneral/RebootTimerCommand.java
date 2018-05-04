package com.github.jamies1211.prisontools.CommandsGeneral;

import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.Data.Messages.rebootTimerMessage;
import static com.github.jamies1211.prisontools.UptimeHandler.rebootTime;
import static com.github.jamies1211.prisontools.UptimeHandler.uptime;

public class RebootTimerCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(rebootTimerMessage
                .replace("%time%", SecondsToString.secondsToString(rebootTime - uptime))));
        
        return CommandResult.success();
    }
}

