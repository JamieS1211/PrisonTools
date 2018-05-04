package com.github.jamies1211.prisontools;

import com.github.jamies1211.prisontools.CommandsSafari.*;
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
public class SafariSubCommands {

	public static HashMap<List<String>, CommandSpec> getSafariSubCommands() {
		final HashMap<List<String>, CommandSpec> safariSubCommands = new HashMap<>();

		safariSubCommands.put(Arrays.asList("time"), CommandSpec.builder()
				.permission("prisontools.safari.time.check")
				.description(Text.of("Check time remaining in safari"))
				.extendedDescription(Text.of("/safari time [zone]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))))
				.executor(new SafariTimeCheckCommand())
				.build());

		safariSubCommands.put(Arrays.asList("join"), CommandSpec.builder()
				.permission("prisontools.safari.join")
				.description(Text.of("Join a safari zone"))
				.extendedDescription(Text.of("/safari join [zone]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))))
				.executor(new SafariJoinCommand())
				.build());

		safariSubCommands.put(Arrays.asList("add"), CommandSpec.builder()
				.permission("prisontools.safari.add")
				.description(Text.of("Add safari time to player (in minutes)"))
				.extendedDescription(Text.of("/safari add [name] [zone] [time]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("minutes"))))
				.executor(new SafariAddTimeCommand())
				.build());

		safariSubCommands.put(Arrays.asList("set"), CommandSpec.builder()
				.permission("prisontools.safari.set")
				.description(Text.of("Set players safari time (in minutes)"))
				.extendedDescription(Text.of("/safari set [name] [zone] [time]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("minutes"))))
				.executor(new SafariSetTimeCommand())
				.build());

		safariSubCommands.put(Arrays.asList("generate"), CommandSpec.builder()
				.permission("prisontools.safari.generate")
				.description(Text.of("Gives player a generated safari token for the zone and time specified"))
				.extendedDescription(Text.of("/safari generate [zone] [time]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("zone"))),
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("minutes"))))
				.executor(new GenerateSafariTokenCommand())
				.build());

		safariSubCommands.put(Arrays.asList("help"), CommandSpec.builder()
				.description(Text.of("Shows safari command help"))
				.extendedDescription(Text.of("Use this command to list all the safari sub commands and how to use them"))
				.executor(new SafariHelpCommand())
				.build());

		return safariSubCommands;
	}

}
