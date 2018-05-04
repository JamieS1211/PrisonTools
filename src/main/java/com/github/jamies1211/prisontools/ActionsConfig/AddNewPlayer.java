package com.github.jamies1211.prisontools.ActionsConfig;

import org.spongepowered.api.entity.living.player.Player;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setNewPlayerNodes;

/**
 * Created by Jamie on 11-May-16.
 */
public class AddNewPlayer {

	public static void addNewPlayerData(String configPlayerUUID, Player player) {

		setNewPlayerNodes(configPlayerUUID,player);
	}

}
