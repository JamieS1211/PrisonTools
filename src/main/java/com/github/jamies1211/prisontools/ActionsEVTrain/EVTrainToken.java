package com.github.jamies1211.prisontools.ActionsEVTrain;

import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.Data.EVTrain.EVTrainTokenData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

import static com.github.jamies1211.prisontools.Data.EVTrain.EVTrainTokenData.EVTRAIN_TIME;
import static com.github.jamies1211.prisontools.Data.EVTrain.EVTrainTokenData.EVTRAIN_ZONE;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class EVTrainToken {

	public static void tokenInteraction(Player player, ItemStack itemInHand) {

		Optional<Double> evTrainTimeOptional = itemInHand.get(EVTrainTokenData.class).get().toContainer().getDouble(EVTRAIN_TIME.getQuery());
		Optional<Integer> evTrainZoneOptional = itemInHand.get(EVTrainTokenData.class).get().toContainer().getInt(EVTRAIN_ZONE.getQuery());

		if (evTrainTimeOptional.isPresent() && evTrainZoneOptional.isPresent()) {
			int time = evTrainTimeOptional.get().intValue();
			int zone = evTrainZoneOptional.get();

			if (player.hasPermission("prisontools.evarea.access." + zone)) {

				String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "Evtrain");

				if (restrictedItems != null) { // If has restricted items give a message mentioning they can't be taken in

					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainRestrictedItems
							.replace("%restricted%", restrictedItems)));
				}

				ChangeEVTrainTime.changeEVTrainTime(player, zone, time * 60);

				int endQuantity = itemInHand.getQuantity() - 1;
				itemInHand.setQuantity(endQuantity);
				if (endQuantity < 1) {
					player.setItemInHand(HandTypes.MAIN_HAND, null);
				} else {
					player.setItemInHand(HandTypes.MAIN_HAND, itemInHand);
				}
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainTokenActivation
						.replace("%time%", Integer.toString(time))
						.replace("%area%", Integer.toString(zone))));

				EVTrainTimeRemainingCheck.checkEVTrainCommand(player, zone); // Update with new time.

			} else {
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(evTrainPrefix + evTrainNoAccess
						.replace("%area%", Integer.toString(zone))));
			}
		}

	}
}
