package com.github.jamies1211.prisontools.ActionsGeneral;

import com.github.jamies1211.prisontools.ActionsEVTrain.EVTrainToken;
import com.github.jamies1211.prisontools.ActionsEvent.EventToken;
import com.github.jamies1211.prisontools.ActionsGym.GymToken;
import com.github.jamies1211.prisontools.ActionsSafari.SafariToken;
import com.github.jamies1211.prisontools.Data.EVTrain.EVTrainTokenData;
import com.github.jamies1211.prisontools.Data.Event.EventTokenData;
import com.github.jamies1211.prisontools.Data.Gym.GymTokenData;
import com.github.jamies1211.prisontools.Data.Safari.SafariTokenData;
import com.github.jamies1211.prisontools.UptimeHandler;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

import static com.github.jamies1211.prisontools.Data.EVTrain.EVTrainTokenData.IS_EVTRAIN_TOKEN;
import static com.github.jamies1211.prisontools.Data.Event.EventTokenData.IS_EVENT_TOKEN;
import static com.github.jamies1211.prisontools.Data.Gym.GymTokenData.IS_GYM_TOKEN;
import static com.github.jamies1211.prisontools.Data.Messages.*;
import static com.github.jamies1211.prisontools.Data.Safari.SafariTokenData.IS_SAFARI_TOKEN;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class TokenInteraction {

	public static void tokenInteraction(Player player) {
		if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
			ItemStack itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get();
			if (itemInHand.getItem().getName().equalsIgnoreCase("minecraft:paper")) {

				if  (itemInHand.get(SafariTokenData.class).isPresent()) {
					Optional<Boolean> isSafariTokenOptional = itemInHand.get(SafariTokenData.class).get().toContainer().getBoolean(IS_SAFARI_TOKEN.getQuery());

					if (isSafariTokenOptional.get()) {
						SafariToken.tokenInteraction(player, itemInHand);
					}

				} else if (itemInHand.get(EVTrainTokenData.class).isPresent()) {
					Optional<Boolean> isEVTrainTokenOptional = itemInHand.get(EVTrainTokenData.class).get().toContainer().getBoolean(IS_EVTRAIN_TOKEN.getQuery());

					if (isEVTrainTokenOptional.get()) {
						EVTrainToken.tokenInteraction(player, itemInHand);
					}

				} else if (itemInHand.get(GymTokenData.class).isPresent()) {
					Optional<Boolean> isGymTokenOptional = itemInHand.get(GymTokenData.class).get().toContainer().getBoolean(IS_GYM_TOKEN.getQuery());

					if (isGymTokenOptional.get()) {
						if (!UptimeHandler.rebootingStage) {
							GymToken.tokenInteraction(player, itemInHand);
						} else {
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + featureDisabledRestart));
						}
					}

				} else if (itemInHand.get(EventTokenData.class).isPresent()) {
					Optional<Boolean> isEventTokenOptional = itemInHand.get(EventTokenData.class).get().toContainer().getBoolean(IS_EVENT_TOKEN.getQuery());

					if (isEventTokenOptional.get()) {
						if (!UptimeHandler.rebootingStage) {
							EventToken.tokenInteraction(player, itemInHand);
						} else {
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + featureDisabledRestart));
						}
					}

				}
			}
		}
	}

}
