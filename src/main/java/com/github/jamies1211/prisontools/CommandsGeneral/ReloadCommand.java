package com.github.jamies1211.prisontools.CommandsGeneral;

import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.Data.Messages.pluginReload;

/**
 * Created by Jamie on 14-May-16.
 */
public class ReloadCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		GeneralDataConfig.getConfig().load();
		PlayerDataConfig.getConfig().load();
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(pluginReload));
		return CommandResult.success();
	}
}
