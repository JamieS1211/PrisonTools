package com.github.jamies1211.prisontools.ActionsGym;

import com.github.jamies1211.prisontools.ActionsGeneral.CheckRestrictedItem;
import com.github.jamies1211.prisontools.ActionsGeneral.SecondsToString;
import com.github.jamies1211.prisontools.ActionsGeneral.Teleport;
import com.github.jamies1211.prisontools.Data.EnumGym;
import com.github.jamies1211.prisontools.Data.Gym.GymTokenData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Gym.GymTokenData.GYM_RANK;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class GymToken {

	public static void tokenInteraction(Player player, ItemStack itemInHand) {


		boolean gymsEnabled = false;
		if (getNodeExists("01 - GymsOn")) {
			gymsEnabled = getNodeBool("01 - GymsOn");
		}

		if (gymsEnabled) {
			boolean membersFeaturesOn = false;
			if (getNodeExists("01 - MembersFeaturesOn")) {
				membersFeaturesOn = getNodeBool("01 - MembersFeaturesOn");
			}

			Optional<String> gymRankOptional = itemInHand.get(GymTokenData.class).get().toContainer().getString(GYM_RANK.getQuery());

			if (gymRankOptional.isPresent()) {

				String gymRank = gymRankOptional.get();
				EnumGym gymEnum = EnumGym.getGymData(gymRank);

				if (gymEnum != null) {

					if ((gymEnum.membersFeature && membersFeaturesOn) || (!gymEnum.membersFeature)) {

						if (player.hasPermission(gymEnum.permissionNode)) {
							String configPlayerUUID = player.getUniqueId().toString();
							int secondsLeftInCooldown = getPlayerSubNodeInt(configPlayerUUID, "gymData", "gymCooldown", "value");
							if (secondsLeftInCooldown == 0) {

								String restrictedItems = CheckRestrictedItem.hasRestrictedItem(player, "GYM");

								if (restrictedItems == null) {

									setPlayerSubNode(configPlayerUUID, "gymData", "gymCooldown", "value", 1800);

									String gymNode = gymEnum.gymNodeIdentifier;

									double x = getEventSpawnDouble("06 - GymSpawn", gymNode, "X");
									double y = getEventSpawnDouble("06 - GymSpawn", gymNode, "Y");
									double z = getEventSpawnDouble("06 - GymSpawn", gymNode, "Z");
									String facing = getEventSpawnFacing("06 - GymSpawn", gymNode, "Facing");

									int endQuantity = itemInHand.getQuantity() - 1;
									itemInHand.setQuantity(endQuantity);
									if (endQuantity < 1) {
										player.setItemInHand(HandTypes.MAIN_HAND, null);
									} else {
										player.setItemInHand(HandTypes.MAIN_HAND, itemInHand);
									}

									Sponge.getCommandManager().process(Sponge.getServer().getConsole(), pokehealCommand.replace("%player%", player.getName()));
									Teleport.PrisonTeleport(player, x, y, z, facing);
									setPlayerSubNode(configPlayerUUID, "gymData", gymNode, "inside", "true");

									MessageChannel.TO_CONSOLE.send(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymChallenge
											.replace("%player%", player.getName())
											.replace("%gym%", gymRank)));

									for (Player targetPlayer : Sponge.getServer().getOnlinePlayers()) {
										if (targetPlayer.getName().equalsIgnoreCase(player.getName())) {
											player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymTokenActivation
													.replace("%gym%", gymRank)));
											player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymGoodLuck));
										} else {
											targetPlayer.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymChallenge
													.replace("%player%", player.getName())
													.replace("%gym%", gymRank)));
											targetPlayer.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymGoodLuck));
										}
									}
								} else {
									player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymBannedItems
											.replace("%restricted%", restrictedItems)));
								}
							} else {
								player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymCooldown
										.replace("%cooldown%", SecondsToString.secondsToString(secondsLeftInCooldown))));
							}
						} else {
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + gymRankAccess
									.replace("%gym%", gymRank)));
						}
					} else {
						player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymPrefix + featureDisabled
								.replace("%gym%", gymRank)));
					}
				} else {
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymErrorToken
							.replace("%gym%", gymRank)));
				}
			}
		} else {
			player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(gymsDisabled));
		}
	}
}
