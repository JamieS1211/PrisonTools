package com.github.jamies1211.prisontools.CommandsGym;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 24-Jun-16.
 */
public class ClearCooldownCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		Player targetPlayer = args.<Player>getOne("player").get();

		String configPlayerUUID = targetPlayer.getUniqueId().toString();
		if ((getPlayerSubNodeExists(configPlayerUUID, "gymData", "gymCooldown", "value")) &&
				getPlayerSubNodeInt( configPlayerUUID, "gymData", "gymCooldown", "value") == 0) {

			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + cooldownAlreadyClear
					.replace("%player%", targetPlayer.getName())));

		} else {
			setPlayerSubNode(configPlayerUUID, "gymData", "gymCooldown", "value",0);
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + cooldownSet
					.replace("%player%", targetPlayer.getName())));
			targetPlayer.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + cooldownCleared));
		}



		return CommandResult.success();


	}


}