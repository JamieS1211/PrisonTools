package com.github.jamies1211.prisontools.CommandsSafari;

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
public class SafariJoinCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		//ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		if (src instanceof Player) {
			Player player = (Player) src;
			String configPlayerUUID = player.getUniqueId().toString();

			int safariNumber = args.<Integer>getOne("zone").get();

			if (safariNumber >= 1 && safariNumber <= 8) {
				if (player.hasPermission("prisontools.safari.access." + safariNumber)) {
					int value = getPlayerNodeInt(configPlayerUUID, "safariData", "safari" + safariNumber);

					if (value > 0) {
						String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "SAFARI");
						if (restrictedItems == null) {
							double targetX = getEventSpawnDouble("04 - SafariSpawn", "safari" + safariNumber, "X");
							double targetY = getEventSpawnDouble("04 - SafariSpawn", "safari" + safariNumber, "Y");
							double targetZ = getEventSpawnDouble("04 - SafariSpawn", "safari" + safariNumber, "Z");
							String targetFacing = getEventSpawnFacing("04 - SafariSpawn", "safari" + safariNumber, "Facing");
							Teleport.PrisonTeleport(player, targetX, targetY, targetZ, targetFacing);
						} else {
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariRestrictedItemsJoin
									.replace("%restricted%", restrictedItems)));
						}
					} else {
						src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNoTime
								.replace("%safari%", Integer.toString(safariNumber))));
					}
				} else {
					src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNoAccess
							.replace("%safari%", Integer.toString(safariNumber))));
				}
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNotExist
						.replace("%safari%", Integer.toString(safariNumber))));
			}
			return CommandResult.success();
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
			return CommandResult.empty();
		}
	}
}
