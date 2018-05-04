package com.github.jamies1211.prisontools.CommandsGym;

import com.github.jamies1211.prisontools.Data.EnumGym;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSubNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSubNodeInt;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 24-Jun-16.
 */
public class GymStatsCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		Player targetPlayer = args.<Player>getOne("player").get();
		String gym = args.<String>getOne("gym").get().toUpperCase();

		String configPlayerUUID = targetPlayer.getUniqueId().toString();
		EnumGym gymEnum = EnumGym.getGymData(gym);

		if (gymEnum != null) {

			String gymNode = gymEnum.gymNodeIdentifier;


			int timesWon = 0;
			int timesLost = 0;

			if (getPlayerSubNodeExists(configPlayerUUID, "gymData", gymNode, "wintrue")) {
				timesWon = getPlayerSubNodeInt(configPlayerUUID, "gymData", gymNode, "wintrue");
			}

			if (getPlayerSubNodeExists(configPlayerUUID, "gymData", gymNode, "winfalse")) {
				timesLost = getPlayerSubNodeInt(configPlayerUUID, "gymData", gymNode, "winfalse");
		}

			String winForm = "wins";
			if (timesWon == 1) {
				winForm = "win";
			}

			String lossForm = "losses";
			if (timesLost == 1) {
				lossForm = "loss";
			}

			String percentage = "(Win percentage: 100%)";
			if (timesLost != 0) { // Prevent dividing by 0. If losses are 0 percentage set as 100%.
				percentage = "(Win percentage: " + ((timesWon * 100) / (timesWon + timesLost)) + "%)";
			}

			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymStats
					.replace("%player%", targetPlayer.getName())
					.replace("%gym%", gym.toUpperCase())
					.replace("%win%", Integer.toString(timesWon))
					.replace("%winForm%", winForm)
					.replace("%loss%", Integer.toString(timesLost))
					.replace("%lossForm%", lossForm)
					.replace("%percentageAddon%", percentage)));
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymStatInvalid
					.replace("%gyms%", gymEnum.name())));
		}


		return CommandResult.success();
	}
}