package com.github.jamies1211.prisontools.CommandsEVTrain;

import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeInt;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 14-May-16.
 */
public class EVTrainJoinCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		//ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		if (src instanceof Player) {
			Player player = (Player) src;
			String configPlayerUUID = player.getUniqueId().toString();

			int zone = args.<Integer>getOne("zone").get();

			if (zone >= 1 && zone <= 3) {
				if (player.hasPermission("prisontools.evarea.access." + zone)) {
					int value = getPlayerNodeInt(configPlayerUUID, "evTrainData", "area" + zone);

					if (value > 0) {
						String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "EVTRAIN");
						if (restrictedItems == null) {
							double targetX = getEventSpawnDouble("08 - EVTrainSpawn", Integer.toString(zone), "X");
							double targetY = getEventSpawnDouble("08 - EVTrainSpawn", Integer.toString(zone), "Y");
							double targetZ = getEventSpawnDouble("08 - EVTrainSpawn", Integer.toString(zone), "Z");
							String targetFacing = getEventSpawnFacing("08 - EVTrainSpawn", Integer.toString(zone), "Facing");
							Teleport.PrisonTeleport(player, targetX, targetY, targetZ, targetFacing);
						} else {
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainRestrictedItemsJoin
									.replace("%restricted%", restrictedItems)));
						}
					} else {
						src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNoTime
								.replace("%area%", Integer.toString(zone))));
					}
				} else {
					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNoAccess
							.replace("%area%", Integer.toString(zone))));
				}
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNotExist
						.replace("%area%", Integer.toString(zone))));
			}
			return CommandResult.success();
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
			return CommandResult.empty();
		}
	}
}
