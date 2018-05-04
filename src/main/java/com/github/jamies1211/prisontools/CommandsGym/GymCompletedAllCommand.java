package com.github.jamies1211.prisontools.CommandsGym;

import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import com.github.jamies1211.prisontools.ActionsGeneral.RankChanging;
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

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSubNodeBoolean;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSubNodeInt;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setPlayerSubNode;
import static com.github.jamies1211.prisontools.Data.Messages.gymCompleteAll;
import static com.github.jamies1211.prisontools.Data.Messages.gymPrefix;

/**
 * Created by Jamie on 25-Jun-16.
 */
public class GymCompletedAllCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		Player targetPlayer = args.<Player>getOne("player").get();

		String configPlayerUUID = targetPlayer.getUniqueId().toString();
		String playerName = targetPlayer.getName();

		for (EnumGym gymEnum : EnumGym.values()) {

			String gym = gymEnum.name();

			if (!getPlayerSubNodeBoolean(configPlayerUUID, "gymData", "gym" + gym, "wintrue")) {
				setPlayerSubNode(configPlayerUUID, "gymData", "gym" + gym, "wintrue","0");
				setPlayerSubNode(configPlayerUUID, "gymData", "gym" + gym, "winfalse","0");
			}

			int timesWon = getPlayerSubNodeInt(configPlayerUUID, "gymData", "gym" + gym, "wintrue");
			int newTimesWon = timesWon + 1;

			if (newTimesWon == 1) {
				if (gymEnum.nextRank.equalsIgnoreCase("Free")) {
					RankChanging.addPlayerRank(configPlayerUUID, playerName, gymEnum.nextRank);
					Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "kit give free " + targetPlayer.getName());
				}
			}

			setPlayerSubNode(configPlayerUUID, "gymData", "gym" + gym, "wintrue",( newTimesWon));

		}

		MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymCompleteAll
				.replace("%player%", targetPlayer.getName())));
		PlayerDataConfig.getConfig().save();

		return CommandResult.success();
	}
}
