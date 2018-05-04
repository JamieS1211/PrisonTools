package com.github.jamies1211.prisontools.CommandsGym;

import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import com.github.jamies1211.prisontools.ActionsGeneral.PlayerRankTools;
import com.github.jamies1211.prisontools.ActionsGeneral.RankChanging;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import com.github.jamies1211.prisontools.Data.EnumGym;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 14-May-16.
 */
public class GymEndCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		Player targetPlayer = args.<Player>getOne("player").get();
		String gym = args.<String>getOne("gym").get().toUpperCase();
		Boolean win = args.<Boolean>getOne("win").get();

		String configPlayerUUID = targetPlayer.getUniqueId().toString();
		String playerName = targetPlayer.getName();
		EnumGym gymEnum = EnumGym.getGymData(gym);

		if (gymEnum != null) {

			String currentRank = gymEnum.currentRank;
			String gymNode = gymEnum.gymNodeIdentifier;

			if (!getPlayerSubNodeExists(configPlayerUUID, "gymData", gymNode, "wintrue")) {
				setPlayerSubNode(configPlayerUUID, "gymData", gymNode, "wintrue",0);
				setPlayerSubNode(configPlayerUUID, "gymData", gymNode, "winfalse",0);
				PlayerDataConfig.getConfig().save();
			}

			int timesWon = getPlayerSubNodeInt(configPlayerUUID, "gymData", gymNode, "wintrue");
			int timesLost = getPlayerSubNodeInt(configPlayerUUID, "gymData",gymNode, "winfalse");

			int newTimesWon = timesWon;
			int newTimesLost = timesLost;

			boolean cheat = !getPlayerSubNodeBoolean(configPlayerUUID, "gymData", gymNode, "inside");

			if (win && !cheat) { // Player wins and has not cheated.
				newTimesWon++;

				String nextRank = gymEnum.nextRank;

				if (newTimesWon > 1) { // Win again
					if (PlayerRankTools.doesPlayerInheritRank(configPlayerUUID, nextRank)) { // No rankup

						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymPlayerWinAgainNoRank
								.replace("%player%", targetPlayer.getName())
								.replace("%gym%", gym)));
					} else { // Rankup
						RankChanging.changePlayerRank(configPlayerUUID, playerName, currentRank, nextRank);

						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymPlayerWinAgainRank
								.replace("%player%", targetPlayer.getName())
								.replace("%gym%", gym)
								.replace("%rank%", nextRank)));
					}
				} else { // Win first
					if (PlayerRankTools.doesPlayerInheritRank(configPlayerUUID, nextRank)) { // No rankup

						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymPlayerWinFirstNoRank
								.replace("%player%", targetPlayer.getName())
								.replace("%gym%", gym)));
					} else { // Rankup
						RankChanging.changePlayerRank(configPlayerUUID, playerName, currentRank, nextRank);

						MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymPlayerWinFirstRank
								.replace("%player%", targetPlayer.getName())
								.replace("%gym%", gym)
								.replace("%rank%", nextRank)));
					}


					if (nextRank.equalsIgnoreCase("Free")) {
						Sponge.getCommandManager().process(Sponge.getServer().getConsole(), freeKitGiveCommand.replace("%player%", targetPlayer.getName()));
					}
				}

				setPlayerSubNode(configPlayerUUID, "gymData", gymNode, "wintrue", newTimesWon);

			} else { // Player loses.
				newTimesLost++;

				if (cheat) {
					MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymPlayerCheat
							.replace("%player%", targetPlayer.getName())
							.replace("%gym%", gym) + " " + gym.toUpperCase()));
				} else {
					MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymPlayerLoss
							.replace("%player%", targetPlayer.getName())
							.replace("%gym%", gym) + " " + gym.toUpperCase()));
				}

				setPlayerSubNode(configPlayerUUID, "gymData", gymNode, "winfalse",newTimesLost);
			}
			// Stat printout.

			// Sets player as no longer being in gym if they haven't already
			setPlayerSubNode(configPlayerUUID, "gymData", gymNode, "inside","false");


			String winForm = "wins";
			if (newTimesWon == 1) {
				winForm = "win";
			}

			String lossForm = "losses";
			if (newTimesLost == 1) {
				lossForm = "loss";
			}

			String percentage = "(Win percentage: 100%)";
			if (newTimesLost != 0) { // Prevent dividing by 0. If losses are 0 percentage set as 100%.
				percentage = "(Win percentage: " + ((newTimesWon * 100) / (newTimesWon + newTimesLost)) + "%)";
			}

			MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymStats
							.replace("%player%", targetPlayer.getName())
							.replace("%gym%", gym.toUpperCase())
							.replace("%win%", Integer.toString(newTimesWon))
							.replace("%winForm%", winForm)
							.replace("%loss%", Integer.toString(newTimesLost))
							.replace("%lossForm%", lossForm)
							.replace("%percentageAddon%", percentage)));

			Teleport.Spawn(targetPlayer);
			PlayerDataConfig.getConfig().save();
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymRankInvalid));
		}

		return CommandResult.success();


	}
}
