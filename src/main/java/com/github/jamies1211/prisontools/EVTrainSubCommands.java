package com.github.jamies1211.prisontools;

import com.github.jamies1211.prisontools.CommandsEVTrain.*;
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
public class EVTrainSubCommands {

	public static HashMap<List<String>, CommandSpec> getEVTrainSubCommands() {
		final HashMap<List<String>, CommandSpec> evTrainSubCommands = new HashMap<>();

		evTrainSubCommands.put(Arrays.asList("time"), CommandSpec.builder()
				.permission("prisontools.evarea.time.check")
				.description(Text.of("Check time remaining in EV Training area"))
				.extendedDescription(Text.of("/evtrain time [zone]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))))
				.executor(new EVTrainTimeCheckCommand())
				.build());

		evTrainSubCommands.put(Arrays.asList("join"), CommandSpec.builder()
				.permission("prisontools.evarea.join")
				.description(Text.of("Join a EV Training area zone"))
				.extendedDescription(Text.of("/evtrain join [zone]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))))
				.executor(new EVTrainJoinCommand())
				.build());

		evTrainSubCommands.put(Arrays.asList("add"), CommandSpec.builder()
				.permission("prisontools.evarea.add")
				.description(Text.of("Add EV Training area time to player (in minutes)"))
				.extendedDescription(Text.of("/evtrain add [name] [zone] [time]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("minutes"))))
				.executor(new EVTrainAddTimeCommand())
				.build());

		evTrainSubCommands.put(Arrays.asList("set"), CommandSpec.builder()
				.permission("prisontools.evarea.set")
				.description(Text.of("Set players EV Training area time (in minutes)"))
				.extendedDescription(Text.of("/evtrain set [name] [zone] [time]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("minutes"))))
				.executor(new EVTrainSetTimeCommand())
				.build());

		evTrainSubCommands.put(Arrays.asList("generate"), CommandSpec.builder()
				.permission("prisontools.evarea.generate")
				.description(Text.of("Gives player a generated EV Training area token for the zone and time specified"))
				.extendedDescription(Text.of("/evtrain generate [zone] [time]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("minutes"))))
				.executor(new GenerateEVTrainTokenCommand())
				.build());

		evTrainSubCommands.put(Arrays.asList("help"), CommandSpec.builder()
				.description(Text.of("Shows EV Training area command help"))
				.extendedDescription(Text.of("Use this command to list all the EV Training area sub commands and how to use them"))
				.executor(new EVTrainHelpCommand())
				.build());

		return evTrainSubCommands;
	}

}
