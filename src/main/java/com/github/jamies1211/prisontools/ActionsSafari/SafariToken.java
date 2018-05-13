package com.github.jamies1211.prisontools.ActionsSafari;

import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

import static com.github.jamies1211.prisontools.Data.Messages.*;
import static com.github.jamies1211.prisontools.Data.customData.MyKeys.SAFARI_TIME;
import static com.github.jamies1211.prisontools.Data.customData.MyKeys.SAFARI_ZONE;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class SafariToken {

	public static void tokenInteraction(Player player, ItemStack itemInHand) {

		Optional<Double> safariTimeOptional = itemInHand.get(SafariTokenData.class).get().toContainer().getDouble(SAFARI_TIME.getQuery());
		Optional<Integer> safariZoneOptional = itemInHand.get(SafariTokenData.class).get().toContainer().getInt(SAFARI_ZONE.getQuery());

		if (safariTimeOptional.isPresent() && safariZoneOptional.isPresent()) {
			int time = safariTimeOptional.get().intValue();
			int zone = safariZoneOptional.get();

			if (player.hasPermission("prisontools.safari.access." + zone)) {

				String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "Safari");

				if (restrictedItems != null) { // If has restricted items give a message mentioning they can't be taken in

					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariRestrictedItems
							.replace("%restricted", restrictedItems)));
				}

				ChangeSafariTime.changeSafariTime(player, zone, time * 60);

				int endQuantity = itemInHand.getQuantity() - 1;
				itemInHand.setQuantity(endQuantity);
				if (endQuantity < 1) {
					player.setItemInHand(HandTypes.MAIN_HAND, null);
				} else {
					player.setItemInHand(HandTypes.MAIN_HAND, itemInHand);
				}
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariTokenActivation
						.replace("%tokenTime%", Integer.toString(time))
						.replace("%safari%", Integer.toString(zone))));

				SafariTimeRemainingCheck.checkSafariCommand(player, zone); // Update with new time.


			} else {
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(safariPrefix + safariNoAccess
						.replace("%safari%", Integer.toString(zone))));
			}
		}

	}
}
