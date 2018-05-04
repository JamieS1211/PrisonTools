package com.github.jamies1211.prisontools;

import com.github.jamies1211.prisontools.CommandsGym.*;
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
public class GymSubCommands {

	public static HashMap<List<String>, CommandSpec> getGymSubCommands() {
		final HashMap<List<String>, CommandSpec> gymSubCommands = new HashMap<>();

		gymSubCommands.put(Arrays.asList("end"), CommandSpec.builder()
				.permission("prisontools.gym.end")
				.description(Text.of("End a gym battle"))
				.extendedDescription(Text.of("Use this command to log player finishing a gym battle"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.string(Text.of("gym"))),
						GenericArguments.onlyOne(GenericArguments.bool(Text.of("win"))))
				.executor(new GymEndCommand())
				.build());

		gymSubCommands.put(Arrays.asList("completeall"), CommandSpec.builder()
				.permission("prisontools.gym.complete.all")
				.description(Text.of("Mark player as having beaten all gyms at the same time"))
				.extendedDescription(Text.of("Mark player as having beaten all gyms at the same time"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
				.executor(new GymCompletedAllCommand())
				.build());

		gymSubCommands.put(Arrays.asList("generate"), CommandSpec.builder()
				.permission("prisontools.gym.generate")
				.description(Text.of("Gives player a generated gym token for the gym specified"))
				.extendedDescription(Text.of("/gym generate [gym]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.string(Text.of("gym"))))
				.executor(new GenerateGymTokenCommand())
				.build());

		gymSubCommands.put(Arrays.asList("clearcooldown"), CommandSpec.builder()
				.permission("prisontools.gym.clear.cooldown")
				.description(Text.of("Clears the cooldown time for the target player"))
				.extendedDescription(Text.of("/gym clearcooldown [player]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
				.executor(new ClearCooldownCommand())
				.build());

		gymSubCommands.put(Arrays.asList("stats"), CommandSpec.builder()
				.permission("prisontools.gym.player.stats")
				.description(Text.of("Gets the stats of a player in the listed gym"))
				.extendedDescription(Text.of("/gym stats [gym] [player]"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.string(Text.of("gym"))),
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
				.executor(new GymStatsCommand())
				.build());

		gymSubCommands.put(Arrays.asList("help"), CommandSpec.builder()
				.description(Text.of("Shows gym help"))
				.extendedDescription(Text.of("Use this command to get help for gyms"))
				.executor(new GymHelpCommand())
				.build());

		return gymSubCommands;
	}

}
