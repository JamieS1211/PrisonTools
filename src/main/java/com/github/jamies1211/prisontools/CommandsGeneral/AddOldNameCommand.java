package com.github.jamies1211.prisontools.CommandsGeneral;

import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
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
 * Created by Jamie on 25-Jun-16.
 */
public class AddOldNameCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		Player targetPlayer = args.<Player>getOne("player").get();
		String oldName = args.<String>getOne("oldName").get();

		String configPlayerUUID = targetPlayer.getUniqueId().toString();


		int currentNameCount = getPlayerNodeInt(configPlayerUUID, "usernameData", "usernameCount");

		for (int currentIndex = currentNameCount; currentIndex > 0; currentIndex--) {
			int nextIndex = currentIndex + 1;

			// Gets then deletes old data.
			String oldData = getPlayerNodeString(configPlayerUUID, "usernameData", "username" + currentIndex);
			removePlayerNodeChild(configPlayerUUID, "usernameData","username", currentIndex);

			// Places old data as 1 slot more recent.
			setPlayerNode(configPlayerUUID, "usernameData", "username" + nextIndex,oldData);
		}


		setPlayerNode(configPlayerUUID, "usernameData", "usernameCount",( currentNameCount + 1));
		setPlayerNode(configPlayerUUID, "usernameData", "username" + 1,oldName);

		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(addedOldName
				.replace("%oldName%", oldName)
				.replace("%player%", targetPlayer.getName())));

		PlayerDataConfig.getConfig().save();

		return CommandResult.success();
	}
}