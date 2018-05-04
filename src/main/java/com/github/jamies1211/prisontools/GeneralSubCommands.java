package com.github.jamies1211.prisontools;

import com.github.jamies1211.prisontools.CommandsGeneral.*;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jamie on 18-Jun-16.
 */
@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class GeneralSubCommands {

	public static HashMap<List<String>, CommandSpec> getGeneralSubCommands() {
		final HashMap<List<String>, CommandSpec> generalSubCommands = new HashMap<>();

		generalSubCommands.put(Arrays.asList("addoldname"), CommandSpec.builder()
				.permission("prisontools.general.add.old.name")
				.description(Text.of("Adds a string as the oldest name for a player"))
				.extendedDescription(Text.of("/prisontools addoldname [player] [oldName]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.string(Text.of("oldName"))))
				.executor(new AddOldNameCommand())
				.build());

		generalSubCommands.put(Arrays.asList("advancerestart"), CommandSpec.builder()
				.permission("prisontools.general.restart.advance")
				.description(Text.of("Advances the restart to 15 minutes"))
				.extendedDescription(Text.of("/prisontools advancerestart"))
				.executor(new AdvanceRestart())
				.build());

		generalSubCommands.put(Arrays.asList("reload"), CommandSpec.builder()
				.permission("prisontools.general.reload")
				.description(Text.of("Reloads config"))
				.extendedDescription(Text.of("/prisontools reload"))
				.executor(new ReloadCommand())
				.build());

		generalSubCommands.put(Arrays.asList("save"), CommandSpec.builder()
				.permission("prisontools.general.save")
				.description(Text.of("Saves config"))
				.extendedDescription(Text.of("/prisontools save"))
				.executor(new SaveCommand())
				.build());

		generalSubCommands.put(Arrays.asList("help"), CommandSpec.builder()
				.permission("prisontools.general")
				.description(Text.of("Shows prison tools command help"))
				.extendedDescription(Text.of("Use this command to list all the prison tools sub commands and how to use them"))
				.executor(new PrisonToolsHelpCommand())
				.build());

		return generalSubCommands;
	}

}
