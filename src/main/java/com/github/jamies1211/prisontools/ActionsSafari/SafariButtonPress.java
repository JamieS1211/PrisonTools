package com.github.jamies1211.prisontools.ActionsSafari;

import com.flowpowered.math.vector.Vector3i;
import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import com.github.jamies1211.prisontools.PrisonTools;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 25-Jul-16.
 */
public class SafariButtonPress {

	public static void buttonPress(EconomyService economyService, Player player, BlockSnapshot blockSnapshot) {

		ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
		String targetBlockName = blockSnapshot.getState().getType().getName();

		if (targetBlockName.equalsIgnoreCase("minecraft:stone_button") || targetBlockName.equalsIgnoreCase("minecraft:wooden_button")) { // Make sure buttons.

			Vector3i location = blockSnapshot.getLocation().get().getBlockPosition();

			//TODO remove config completely from this method
			for (Object key : generalDataConfig.getNode("12 - SafariButtons").getChildrenMap().keySet()) {

				// If button is a safari quick join button.
				if (getPrisonConfigInt("12 - SafariButtons", key.toString(), "X") == location.getX() &&
						getPrisonConfigInt("12 - SafariButtons", key.toString(), "Y") == location.getY() &&
						getPrisonConfigInt("12 - SafariButtons", key.toString(), "Z") == location.getZ()) {

					int zone = getPrisonConfigInt("12 - SafariButtons", key.toString(), "Zone");
					int time = getPrisonConfigInt("12 - SafariButtons", key.toString(), "Time");

					if (!player.hasPermission("prisontools.safari.access." + zone)) { // If player has not got access.
						player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNoAccess
								.replace("%safari%", Integer.toString(zone))));
						return;
					}


					String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "Safari");

					if (restrictedItems != null) { // If has restricted cancels and tells user.

						player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariRestrictedItems
								.replace("%restricted", restrictedItems)));
						return;
					}


					UniqueAccount account = economyService.getOrCreateAccount(player.getUniqueId()).get();
					Double price = getPrisonConfigDouble("12 - SafariButtons", key.toString(), "Price");

					//TODO check over
					if (account.withdraw(economyService.getDefaultCurrency(), new BigDecimal(price),  Cause.builder().append(PrisonTools.getPrisonTools()).build(EventContext.empty())).getResult() != ResultType.SUCCESS) {

						player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariQuickJoinNoMoney
								.replace("%price%", ("$" + new DecimalFormat("#,###,##0.00").format(price)))));

						return;
					}

					ChangeSafariTime.changeSafariTime(player, zone, time * 60);
					SafariTimeRemainingCheck.checkSafariCommand(player, zone); // Update with new time.


					double targetX = getEventSpawnDouble("04 - SafariSpawn", "safari" + zone, "X");
					double targetY = getEventSpawnDouble("04 - SafariSpawn", "safari" + zone, "Y");
					double targetZ = getEventSpawnDouble("04 - SafariSpawn", "safari" + zone, "Z");
					String targetFacing = getEventSpawnFacing("04 - SafariSpawn", "safari" + zone, "Facing");
					Teleport.PrisonTeleport(player, targetX, targetY, targetZ, targetFacing);

					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariQuickJoin
							.replace("%price%", ("$" + new DecimalFormat("#,###,##0.00").format(price)))
							.replace("%time%", Integer.toString(time))
							.replace("%safari%", Integer.toString(zone))));
				}
			}
		}
	}
}
