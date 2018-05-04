package com.github.jamies1211.prisontools.ActionsConfig;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Jamie on 04/01/2017.
 */
public class PlayerConfigDataInteraction {

	// TODO Complete so there is a getter and setter method for every configurable value taking inputs of data and
	// TODO inside the method determining the node so that in event of changing the nodes where something is stored
	// TODO this is the only file that needs to be edited




	public static void updatePlayerdata() {
		Map<Object, ? extends ConfigurationNode> playerMap = PlayerConfigDataInteraction.getAllPlayerMap();

		for (Object key : playerMap.keySet()) {

			String configPlayerUUID = key.toString();
			ArrayList<String> gymValues = new ArrayList<>(Arrays.asList("inside", "winfalse", "wintrue"));

			for (Object playerVariables : PlayerConfigDataInteraction.getPlayerChildrenMap(configPlayerUUID).keySet()) {

				if (playerVariables.toString().contains("gym") && !playerVariables.toString().contains("gymData")) {
					CommentedConfigurationNode config = PlayerDataConfig.getConfig().get();

					String primaryNode = playerVariables.toString();

					if (primaryNode.equalsIgnoreCase("gymCooldown")) {
						config.getNode("Players", configPlayerUUID, "gymData", primaryNode).setValue(config.getNode("Players", configPlayerUUID, primaryNode));
					} else {

						for (String node : gymValues) {
							config.getNode("Players", configPlayerUUID, "gymData",  primaryNode, node)
									.setValue(config.getNode("Players", configPlayerUUID, primaryNode, node).getValue());
						}

					}

					config.getNode("Players", configPlayerUUID).removeChild(playerVariables);
				}

			}
		}

		PlayerDataConfig.getConfig().save();
	}






	public static boolean playerEVTrainTimeExist (Player player, int evTrainArea) {
		String configPlayerUUID = player.getUniqueId().toString();

		return getPlayerNodeExists(configPlayerUUID, "evTrainData", "area" + evTrainArea);
	}

	public static int getPlayerEVTrainTime(Player player, int evTrainArea) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		String configPlayerUUID = player.getUniqueId().toString();

		return playerDataConfig.getNode("Players", configPlayerUUID, "evTrainData", "area" + evTrainArea).getInt();
	}

	public static void setPlayerEVTrainTime(Player player, int evTrainArea, int newTime) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		String configPlayerUUID = player.getUniqueId().toString();

		playerDataConfig.getNode("Players", configPlayerUUID, "evTrainData", "area" + evTrainArea).setValue(newTime);
	}

	public static int getUsernameCount(String configPlayerUUID) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", configPlayerUUID, "usernameData", "usernameCount").getInt() + 1;
	}

	public static void setAdditionalPlayerUsernames(String configPlayerUUID, Player player, int newUsernameCount) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		playerDataConfig.getNode("Players", configPlayerUUID, "usernameData", "usernameCount").setValue(newUsernameCount);
		playerDataConfig.getNode("Players", configPlayerUUID, "usernameData", "username" + newUsernameCount).setValue(player.getName());

	}

	public static void setNewPlayerNodes(String configPlayerUUID, Player player) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		playerDataConfig.getNode("Players", configPlayerUUID, "usernameData", "username1").setValue(player.getName());
		playerDataConfig.getNode("Players", configPlayerUUID, "usernameData", "usernameCount").setValue(1);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari1").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari2").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari3").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari4").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari5").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari6").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari7").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "safariData", "safari8").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "evTrainData", "area1").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "evTrainData", "area2").setValue(0);
		playerDataConfig.getNode("Players", configPlayerUUID, "evTrainData", "area3").setValue(0);
	}


	// TODO Split into check for each
	public static boolean getPlayerNodeExists(String PlayerUUID, String Node, String varToCheck) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		return playerDataConfig.getNode("Players", PlayerUUID, Node, varToCheck).getValue() != null;
	}

	// TODO Split into check for each
	public static boolean getPlayerSubNodeExists(String PlayerUUID, String Node, String subNode, String varToCheck) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();

		return playerDataConfig.getNode("Players", PlayerUUID, Node, subNode, varToCheck).getValue() != null;
	}

	// TODO Split into check for each
	public static boolean getPlayerSuperSubNodeExists(String PlayerUUID, String Node, String subNode,String superSubNode, String varToCheck) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID, Node, subNode, superSubNode, varToCheck).getValue() != null;
	}

	// TODO Split into check for each
	public static Object getPlayerSubNodeValue(String PlayerUUID, String Node, String subNode, String varToGet) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node, subNode, varToGet).getValue();
	}


	// TODO Split into check for each
	public static int getPlayerNodeInt(String PlayerUUID, String Node, String varToGet) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node,varToGet).getInt();
	}

	// TODO Split into check for each
	public static int getPlayerSubNodeInt(String PlayerUUID, String Node, String subNode, String varToGet) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node, subNode, varToGet).getInt();
	}

	// TODO Split into check for each
	public static int getPlayerSuperSubNodeInt(String PlayerUUID, String Node, String subNode,String superSubNode, String varToGet) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node, subNode,superSubNode, varToGet).getInt();
	}

	// TODO Split into check for each
	public static Map<Object, ? extends ConfigurationNode> getAllPlayerMap() {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players").getChildrenMap();
	}

		// TODO Split into check for each
	public static Map<Object, ? extends ConfigurationNode> getPlayerChildrenMap(String PlayerUUID) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID).getChildrenMap();
	}

	// TODO Split into check for each
	public static Map<Object, ? extends ConfigurationNode> getPlayerNodeChildrenMap(String PlayerUUID, String Node) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node).getChildrenMap();
	}

	// TODO Split into check for each
	public static Map<Object, ? extends ConfigurationNode> getPlayerSubNodeChildrenMap(String PlayerUUID, String Node, String subNode) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node, subNode).getChildrenMap();
	}

	// TODO Split into check for each
	public static Map<Object, ? extends ConfigurationNode> getPlayerSuperSubNodeChildrenMap(String PlayerUUID, String Node, String subNode, String superSubNode) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node, subNode,superSubNode).getChildrenMap();
	}

	// TODO Split into check for each
	public static Boolean getPlayerNodeBoolean(String PlayerUUID, String Node, String varToGet) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node,varToGet).getBoolean();
	}
	public static Boolean getPlayerSubNodeBoolean(String PlayerUUID, String Node, String subNode, String varToGet) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node,subNode,varToGet).getBoolean();
	}

	// TODO Split into check for each
	public static String getPlayerNodeString(String PlayerUUID, String Node, String varToGet) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		return playerDataConfig.getNode("Players", PlayerUUID,Node,varToGet).getString();
	}

	// TODO Split into check for each
	public static void setPlayerNode(String PlayerUUID, String Node, String varToSet, String Value) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		playerDataConfig.getNode("Players", PlayerUUID, Node, varToSet).setValue(Value);
	}

	// TODO Split into check for each
	public static void setPlayerNode(String PlayerUUID, String Node, String varToSet, Integer Value) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		playerDataConfig.getNode("Players", PlayerUUID, Node, varToSet).setValue(Value);
	}

	// TODO Split into check for each
	public static void setPlayerSubNode(String PlayerUUID, String Node, String subNode, String varToSet, String Value) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		playerDataConfig.getNode("Players", PlayerUUID, Node, subNode,varToSet).setValue(Value);
	}

	// TODO Split into check for each
	public static void setPlayerSubNode(String PlayerUUID, String Node, String subNode, String varToSet, int Value) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		playerDataConfig.getNode("Players", PlayerUUID, Node, subNode,varToSet).setValue(Value);
	}

	// TODO Split into check for each
	public static void setPlayerSuperSubNode(String PlayerUUID, String Node, String subNode, String superSubNode, String varToSet, int Value) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		playerDataConfig.getNode("Players", PlayerUUID, Node, subNode,superSubNode,varToSet).setValue(Value);
	}

	// TODO Split into check for each
	public static void removePlayerNodeChild(String PlayerUUID, String Node, String childNode, int index) {
		ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		playerDataConfig.getNode("Players", PlayerUUID, Node).removeChild(childNode + index);
	}
}
