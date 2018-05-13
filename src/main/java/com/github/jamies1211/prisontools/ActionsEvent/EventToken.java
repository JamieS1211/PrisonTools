package com.github.jamies1211.prisontools.ActionsEvent;

import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import com.github.jamies1211.prisontools.Data.customData.Event.EventTokenData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setPlayerSubNode;
import static com.github.jamies1211.prisontools.Data.Messages.*;
import static com.github.jamies1211.prisontools.Data.customData.MyKeys.EVENT_TYPE;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class EventToken {

	public static void tokenInteraction(Player player, ItemStack itemInHand) {

		if (!getNodeBool("01 - EventsOn")) {
			player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + featureDisabled));
			return;
		}

		Optional<String> eventTypeOptional = itemInHand.get(EventTokenData.class).get().toContainer().getString(EVENT_TYPE.getQuery());

		if (eventTypeOptional.isPresent()) {

			String eventType = eventTypeOptional.get();
			EnumSpecialEvent currentEvent = EnumSpecialEvent.getSpecialEventData(eventType);

			if (currentEvent != null) {
				if (player.hasPermission("prisontools.event.challenge." + eventType.toLowerCase())) {
					String configPlayerUUID = player.getUniqueId().toString();

					String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, eventType);

					if (restrictedItems == null) {

						if (!getSpawnExists("10 - EventSpawn", eventType, "stage1spawn", "X")) {
							setEventSpawnValue("10 - EventSpawn", eventType, "stage1spawn", "X", "0");
							setEventSpawnValue("10 - EventSpawn", eventType, "stage1spawn", "Y", "0");
							setEventSpawnValue("10 - EventSpawn", eventType, "stage1spawn", "Z", "0");
							setEventSpawnValue("10 - EventSpawn", eventType, "stage1spawn", "Facing", "North");
							GeneralDataConfig.getConfig().save();
						}

						double x = getEventSpawnDouble("10 - EventSpawn", eventType, "stage1spawn", "X");
						double y = getEventSpawnDouble("10 - EventSpawn", eventType, "stage1spawn", "Y");
						double z = getEventSpawnDouble("10 - EventSpawn", eventType, "stage1spawn", "Z");
						String facing = getEventSpawnFacing("10 - EventSpawn", eventType, "stage1spawn", "Facing");

						int endQuantity = itemInHand.getQuantity() - 1;
						itemInHand.setQuantity(endQuantity);
						if (endQuantity < 1) {
							player.setItemInHand(HandTypes.MAIN_HAND, null);
						} else {
							player.setItemInHand(HandTypes.MAIN_HAND, itemInHand);
						}

						Sponge.getCommandManager().process(Sponge.getServer().getConsole(), pokehealCommand
								.replace("%player%", player.getName()));
						Teleport.PrisonTeleport(player, x, y, z, facing);
						setPlayerSubNode(configPlayerUUID, "eventData", eventType, "inside", "1"); // Denotes inside for stage 1.

						// Announces to server player has challenged the event.
						MessageChannel.TO_CONSOLE.send(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventChallenge
								.replace("%event%", currentEvent.eventName)
								.replace("%player%", player.getName())));

						for (Player targetPlayer : Sponge.getServer().getOnlinePlayers()) {
							if (targetPlayer.getName().equalsIgnoreCase(player.getName())) {
								player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventGoodLuck
										.replace("%event%", currentEvent.eventName.replace("The", "the"))));
							} else {
								targetPlayer.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventChallenge
										.replace("%event%", currentEvent.eventName)
										.replace("%player%", player.getName())));
							}
						}
					} else {
						player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventBannedItems
								.replace("%event%", currentEvent.eventName.replace("The", "the"))
								.replace("%restricted%", restrictedItems)));
					}
				} else {
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventNoPermission
							.replace("%event%", currentEvent.eventName.replace("The", "the"))));
				}

			} else {
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(eventPrefix + eventInvalidEvent));
			}
		}
	}

}
