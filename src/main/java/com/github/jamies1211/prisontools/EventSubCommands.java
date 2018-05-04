package com.github.jamies1211.prisontools;

import com.github.jamies1211.prisontools.CommandsEvent.EventHelpCommand;
import com.github.jamies1211.prisontools.CommandsEvent.EventStageCommand;
import com.github.jamies1211.prisontools.CommandsEvent.EventStatsCommand;
import com.github.jamies1211.prisontools.CommandsEvent.GenerateEventTokenCommand;
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
public class EventSubCommands {

	public static HashMap<List<String>, CommandSpec> getEventSubCommands() {
		final HashMap<List<String>, CommandSpec> eventSubCommands = new HashMap<>();

		eventSubCommands.put(Arrays.asList("stage"), CommandSpec.builder()
				.permission("prisontools.event.stage")
				.description(Text.of("Mark a event stage as complete"))
				.extendedDescription(Text.of("Use this command to log a player finishing a stage in an event"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.string(Text.of("event"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("stage"))),
						GenericArguments.onlyOne(GenericArguments.bool(Text.of("win"))))
				.executor(new EventStageCommand())
				.build());

		eventSubCommands.put(Arrays.asList("generate"), CommandSpec.builder()
				.permission("prisontools.event.generate")
				.description(Text.of("Gives player a generated event token for the event specified"))
				.extendedDescription(Text.of("/event generate [event]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.string(Text.of("event"))))
				.executor(new GenerateEventTokenCommand())
				.build());

		eventSubCommands.put(Arrays.asList("stats"), CommandSpec.builder()
				.permission("prisontools.event.player.stats")
				.description(Text.of("Gets the stats of a player in the listed event"))
				.extendedDescription(Text.of("/event stats [event] [stage] [player]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.string(Text.of("event"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("stage"))),
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
				.executor(new EventStatsCommand())
				.build());

		eventSubCommands.put(Arrays.asList("help"), CommandSpec.builder()
				.description(Text.of("Shows event help"))
				.extendedDescription(Text.of("Use this command to get help for events"))
				.executor(new EventHelpCommand())
				.build());

		return eventSubCommands;
	}

}
