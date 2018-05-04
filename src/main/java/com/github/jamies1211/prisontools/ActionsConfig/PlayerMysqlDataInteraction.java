package com.github.jamies1211.prisontools.ActionsConfig;

import com.github.jamies1211.prisontools.Data.EnumGym;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jamie on 04/01/2017.
 */
public class PlayerMysqlDataInteraction {

	// Sync data between server and database

	public static void updateDatabaseFromMemory (String configPlayerUUID) {
		// Sync safari data
		HashMap<Integer, Integer> safariDataMap = new HashMap<>();

		for (Object key : PlayerConfigDataInteraction.getPlayerNodeChildrenMap(configPlayerUUID, "safariData").keySet()) {
			String safariZone = key.toString();
			int safariID = Integer.valueOf(safariZone.replace("safari", ""));
			int value = PlayerConfigDataInteraction.getPlayerNodeInt(configPlayerUUID, "safariData", safariZone);

			safariDataMap.put(safariID, value);
		}
		setBulkSafariTimes(configPlayerUUID, safariDataMap);

		// Sync evtrain data
		HashMap<Integer, Integer> evTrainDataMap = new HashMap<>();

		for (Object key : PlayerConfigDataInteraction.getPlayerNodeChildrenMap(configPlayerUUID, "evTrainData").keySet()) {
			String evArea = key.toString();
			int evAreaID = Integer.valueOf(evArea.replace("area", ""));
			int value = PlayerConfigDataInteraction.getPlayerNodeInt(configPlayerUUID, "evTrainData", evArea);

			evTrainDataMap.put(evAreaID, value);
		}
		setBulkEVTrainTimes(configPlayerUUID, evTrainDataMap);


		// Sync gym data
		HashMap<String, HashMap<String, Object>> gymDataMap = new HashMap<>();
		for (Object key : PlayerConfigDataInteraction.getPlayerNodeChildrenMap(configPlayerUUID, "gymData").keySet()) {

			String gymNode = key.toString();
			HashMap<String, Object> gymNodeDataMap = new HashMap<>();

			for (Object variable : PlayerConfigDataInteraction.getPlayerSubNodeChildrenMap(configPlayerUUID, "gymData", gymNode).keySet()) {
				String variableString = variable.toString();
				Object value = PlayerConfigDataInteraction.getPlayerSubNodeValue(configPlayerUUID, "gymData", gymNode, variableString);

				gymNodeDataMap.put(variableString, value);
			}

			gymDataMap.put(gymNode, gymNodeDataMap);

		}
		setGymBulkData(configPlayerUUID, gymDataMap);

		// Sync special event data
		HashMap<String, HashMap<String, HashMap<String, Integer>>> eventDataMap = new HashMap<>();
		for (Object key : PlayerConfigDataInteraction.getPlayerNodeChildrenMap(configPlayerUUID, "eventData").keySet()) {

			String eventNode = key.toString();
			HashMap<String, HashMap<String, Integer>> eventNodeDataMap = new HashMap<>();

			for (Object stage : PlayerConfigDataInteraction.getPlayerSubNodeChildrenMap(configPlayerUUID, "eventData", eventNode).keySet()) {
				String stageString = stage.toString();

				if (!stageString.equals("inside")) {
					HashMap<String, Integer> eventStatsDataMap = new HashMap<>();

					for (Object variable : PlayerConfigDataInteraction.getPlayerSuperSubNodeChildrenMap(configPlayerUUID, "eventData", eventNode, stageString).keySet()) {
						String variableString = variable.toString();
						int value = PlayerConfigDataInteraction.getPlayerSuperSubNodeInt(configPlayerUUID, "eventData", eventNode, stageString, variableString);

						eventStatsDataMap.put(variableString, value);
					}

					eventNodeDataMap.put(stageString, eventStatsDataMap);

				} else {
					HashMap<String, Integer> insideDataMap = new HashMap<>();
					int value = PlayerConfigDataInteraction.getPlayerSubNodeInt(configPlayerUUID, "eventData", eventNode, "inside");
					insideDataMap.put("inside", value);

					eventNodeDataMap.put("inside", insideDataMap);
				}
			}

			eventDataMap.put(eventNode, eventNodeDataMap);

		}
		setEventBulkData(configPlayerUUID, eventDataMap);


		// Sync username data
		HashMap<Integer, String> usernameDataMap = new HashMap<>();

		for (Object key : PlayerConfigDataInteraction.getPlayerNodeChildrenMap(configPlayerUUID, "usernameData").keySet()) {

			if (!(key.toString().equalsIgnoreCase("usernameCount"))) {
				String usernameIndex = key.toString();

				int usernameID = Integer.valueOf(usernameIndex.replace("username", ""));
				String value = PlayerConfigDataInteraction.getPlayerNodeString(configPlayerUUID, "usernameData", usernameIndex);

				usernameDataMap.put(usernameID, value);
			}
		}
		setBulkUsernames(configPlayerUUID, usernameDataMap);

		setUsernameCount(configPlayerUUID, PlayerConfigDataInteraction.getPlayerNodeInt(configPlayerUUID, "usernameData", "usernameCount"));
	}

	public static void updateMemoryFromDatabase (String configPlayerUUID) {

		if (MysqlActions.doesEntryExist(configPlayerUUID)) {
			// Sync safari data
			JsonObject safariDataJson = MysqlActions.playerJSONMapGetting(configPlayerUUID, "safariData");

			if (safariDataJson.entrySet() != null && !safariDataJson.entrySet().isEmpty()) {
				for (Map.Entry<String, JsonElement> key : safariDataJson.entrySet()) {
					String safariName = key.getKey();
					Integer value = key.getValue().getAsInt();

					PlayerConfigDataInteraction.setPlayerNode(configPlayerUUID, "safariData", safariName, value);
				}
			}


			// Sync evtrain data
			JsonObject evtrainDataJson = MysqlActions.playerJSONMapGetting(configPlayerUUID, "evTrainData");

			if (evtrainDataJson.entrySet() != null && !evtrainDataJson.entrySet().isEmpty()) {
				for (Map.Entry<String, JsonElement> key : evtrainDataJson.entrySet()) {
					String evAreaName = key.getKey();
					Integer value = key.getValue().getAsInt();

					PlayerConfigDataInteraction.setPlayerNode(configPlayerUUID, "evTrainData", evAreaName, value);
				}
			}


			// Sync gym data
			JsonObject gymDataJson = MysqlActions.playerJSONMapGetting(configPlayerUUID, "gymData");

			if (gymDataJson.entrySet() != null && !gymDataJson.entrySet().isEmpty()) {
				for (Map.Entry<String, JsonElement> key : gymDataJson.entrySet()) {
					String gymNode = key.getKey();
					JsonObject gymData = key.getValue().getAsJsonObject();

					for (Map.Entry<String, JsonElement> variable : gymData.entrySet()) {
						String valueName = variable.getKey();

						if (valueName.equalsIgnoreCase("inside")) {
							PlayerConfigDataInteraction.setPlayerSubNode(configPlayerUUID, "gymData", gymNode, valueName, variable.getValue().getAsString());
						} else {
							PlayerConfigDataInteraction.setPlayerSubNode(configPlayerUUID, "gymData", gymNode, valueName, variable.getValue().getAsInt());
						}
					}
				}
			}


			// Sync special event data
			JsonObject eventDataJson = MysqlActions.playerJSONMapGetting(configPlayerUUID, "eventData");

			if (eventDataJson.entrySet() != null && !eventDataJson.entrySet().isEmpty()) {
				for (Map.Entry<String, JsonElement> key : eventDataJson.entrySet()) {
					String eventNode = key.getKey();
					JsonObject stageData = key.getValue().getAsJsonObject();

					for (Map.Entry<String, JsonElement> stageKey : stageData.entrySet()) {
						String stage = stageKey.getKey();

						if (stage.equalsIgnoreCase("inside")) {
							PlayerConfigDataInteraction.setPlayerSubNode(configPlayerUUID, "eventData", eventNode, "inside", stageKey.getValue().getAsInt());
						} else {
							JsonObject stageInternalData = stageKey.getValue().getAsJsonObject();

							for (Map.Entry<String, JsonElement> variable : stageInternalData.entrySet()) {
								String valueName = variable.getKey();
								int value = variable.getValue().getAsInt();

								PlayerConfigDataInteraction.setPlayerSuperSubNode(configPlayerUUID, "eventData", eventNode, stage, valueName, value);
							}
						}
					}
				}
			}


			// Sync username data
			JsonObject usernameDataJson = MysqlActions.playerJSONMapGetting(configPlayerUUID, "usernameData");

			if (usernameDataJson.entrySet() != null && !usernameDataJson.entrySet().isEmpty()) {

				for (Map.Entry<String, JsonElement> key : usernameDataJson.entrySet()) {
					String usernameIndex = key.getKey();

					if (usernameIndex.equalsIgnoreCase("usernameCount")) {
						Integer value = key.getValue().getAsInt();

						PlayerConfigDataInteraction.setPlayerNode(configPlayerUUID, "usernameData", usernameIndex, value);
					} else {
						String username = key.getValue().getAsString();

						PlayerConfigDataInteraction.setPlayerNode(configPlayerUUID, "usernameData", usernameIndex, username);
					}
				}
			}
		}
	}



	// Safari Data

	public static Integer getSafariTime (String configPlayerUUID, int safariID) {
		String safariZone = "safari" + safariID;

		JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "safariData", safariZone);
		if (jsonElement != null) {
			return jsonElement.getAsInt();
		}

		return null;
	}

	public static void setSafariTime (String configPlayerUUID, int safariID, int value) {
		String safariZone = "safari" + safariID;

		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "safariData").get("safariData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		updatedJSON.add(safariZone, new JsonPrimitive(value));

		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "safariData", updatedJSONString);
	}

	public static void setBulkSafariTimes (String configPlayerUUID, HashMap<Integer, Integer> safariValueMap) {

		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "safariData").get("safariData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		for (Integer safariID : safariValueMap.keySet()) {
			String safariZone = "safari" + safariID;
			Integer value = safariValueMap.get(safariID);

			updatedJSON.add(safariZone, new JsonPrimitive(value));
		}


		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "safariData", updatedJSONString);
	}




	// EV train Data

	public static Integer getEVTrainTime (String configPlayerUUID, int evAreaID) {
		String evArea = "area" + evAreaID;

		JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "evTrainData", evArea);
		if (jsonElement != null) {
			return jsonElement.getAsInt();
		}

		return null;
	}

	public static void setEVTrainTime (String configPlayerUUID, int evAreaID, int value) {
		String evArea = "area" + evAreaID;

		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "evTrainData").get("evTrainData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		updatedJSON.add(evArea, new JsonPrimitive(value));

		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "evTrainData", updatedJSONString);
	}

	public static void setBulkEVTrainTimes (String configPlayerUUID, HashMap<Integer, Integer> evTrainValueMap) {


		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "evTrainData").get("evTrainData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		for (Integer evAreaID : evTrainValueMap.keySet()) {
			String evArea = "area" + evAreaID;
			Integer value = evTrainValueMap.get(evAreaID);

			updatedJSON.add(evArea, new JsonPrimitive(value));
		}

		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "evTrainData", updatedJSONString);
	}



	// Gym Data

	public static Integer getGymCooldown (String configPlayerUUID) {
		JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "gymData", "gymCooldown");
		if (jsonElement != null) {
			return jsonElement.getAsInt();
		}

		return null;
	}

	public static void setGymCooldown (String configPlayerUUID, int value) {

		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "evTrainData").get("evTrainData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		updatedJSON.add("gymCooldown", new JsonPrimitive(value));

		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "gymData", updatedJSONString);
	}

	public static Boolean getInsideGym (String configPlayerUUID, String gymID) {
		EnumGym gymEnum = EnumGym.getGymData(gymID);

		if (gymEnum != null) {
			String gymNode = gymEnum.gymNodeIdentifier;

			JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "gymData", gymNode);

			if (jsonElement != null) {
				JsonElement internalJsonElement = new JsonParser().parse(jsonElement.toString());

				if (internalJsonElement instanceof JsonObject) {
					JsonObject json = (JsonObject) jsonElement;
					return json.get("inside").getAsBoolean();
				}
			}
		}

		return null;
	}

	public static void setInsideGym (String configPlayerUUID, String gymID, Boolean inside) {
		EnumGym gymEnum = EnumGym.getGymData(gymID);

		if (gymEnum != null) {
			String gymNode = gymEnum.gymNodeIdentifier;


			String oldJSON = "";
			Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "gymData").get("gymData");

			if (rawReply != null) {
				oldJSON = (String) rawReply;
			}

			JsonObject updatedJSON = new JsonObject();

			if (!oldJSON.isEmpty()) {
				updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
			}

			JsonObject variableValue = new JsonObject();
			variableValue.add("inside", new JsonPrimitive(inside));

			updatedJSON.add(gymNode, variableValue);

			String updatedJSONString = updatedJSON.toString();

			MysqlActions.playerJsonSetting(configPlayerUUID, "gymData", updatedJSONString);
		}
	}

	public static Integer getGymStat (String configPlayerUUID, String gymID, Boolean win) {
		EnumGym gymEnum = EnumGym.getGymData(gymID);

		if (gymEnum != null) {
			String gymNode = gymEnum.gymNodeIdentifier;
			String variable = "win" + win.toString();

			JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "gymData", gymNode);

			if (jsonElement != null) {
				JsonElement internalJsonElement = new JsonParser().parse(jsonElement.toString());

				if (internalJsonElement instanceof JsonObject) {
					JsonObject json = (JsonObject) jsonElement;
					return json.get(variable).getAsInt();
				}
			}
		}

		return null;
	}

	public static void setGymStat (String configPlayerUUID, String gymID, Boolean win, int value) {
		EnumGym gymEnum = EnumGym.getGymData(gymID);

		if (gymEnum != null) {
			String gymNode = gymEnum.gymNodeIdentifier;
			String variable = "win" + win.toString();


			String oldJSON = "";
			Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "gymData").get("gymData");

			if (rawReply != null) {
				oldJSON = (String) rawReply;
			}

			JsonObject updatedJSON = new JsonObject();

			if (!oldJSON.isEmpty()) {
				updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
			}

			JsonObject variableValue = new JsonObject();
			variableValue.add(variable, new JsonPrimitive(value));

			updatedJSON.add(gymNode, variableValue);

			String updatedJSONString = updatedJSON.toString();

			MysqlActions.playerJsonSetting(configPlayerUUID, "gymData", updatedJSONString);
		}
	}

	public static void setGymBulkData (String configPlayerUUID, HashMap<String, HashMap<String, Object>> gymDataMap) {
		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "gymData").get("gymData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		for (Object key : gymDataMap.keySet()) {
			String gymNode = key.toString();

			JsonObject variableValue = new JsonObject();

			for (Object variable : gymDataMap.get(key).keySet()) {

				String variableString = variable.toString();
				Object value = gymDataMap.get(key).get(variable);

				if (variableString.equals("inside") && (value instanceof String)) {
					value = Boolean.valueOf((String) value);
				}

				if (value instanceof Boolean) {
					variableValue.add(variableString, new JsonPrimitive((boolean) value));
				} else if (value instanceof String) {
					variableValue.add(variableString, new JsonPrimitive((String) value));
				} else if (value instanceof Integer) {
					variableValue.add(variableString, new JsonPrimitive((int) value));
				} else if (value instanceof Character) {
					variableValue.add(variableString, new JsonPrimitive((char) value));
				}
			}

			updatedJSON.add(gymNode, variableValue);
		}


		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "gymData", updatedJSONString);
	}



	// Event Data

	public static Integer getInsideEvent (String configPlayerUUID, String eventID) {
		EnumSpecialEvent specialEvent = EnumSpecialEvent.getSpecialEventData(eventID);

		if (specialEvent != null) {
			String eventNode = specialEvent.eventNodeIdentifier;

			JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "eventData", eventNode);

			if (jsonElement != null) {
				JsonElement internalJsonElement = new JsonParser().parse(jsonElement.toString());

				if (internalJsonElement instanceof JsonObject) {
					JsonObject json = (JsonObject) jsonElement;
					return json.get("inside").getAsInt();
				}
			}
		}

		return null;
	}

	public static void setInsideEvent (String configPlayerUUID, String eventID, int stageInside) {
		EnumSpecialEvent specialEvent = EnumSpecialEvent.getSpecialEventData(eventID);

		if (specialEvent != null) {
			String eventNode = specialEvent.eventNodeIdentifier;


			String oldJSON = "";
			Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "eventData").get("eventData");

			if (rawReply != null) {
				oldJSON = (String) rawReply;
			}

			JsonObject updatedJSON = new JsonObject();

			if (!oldJSON.isEmpty()) {
				updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
			}

			JsonObject variableValue = new JsonObject();
			variableValue.add("inside", new JsonPrimitive(stageInside));

			updatedJSON.add(eventNode, variableValue);

			String updatedJSONString = updatedJSON.toString();

			MysqlActions.playerJsonSetting(configPlayerUUID, "eventData", updatedJSONString);
		}
	}

	public static Integer getEventStat (String configPlayerUUID, String eventID, int stage, Boolean win) {
		EnumSpecialEvent specialEventEnum = EnumSpecialEvent.getSpecialEventData(eventID);

		if (specialEventEnum != null) {
			String eventNode = specialEventEnum.eventNodeIdentifier;
			String stageName = "stage" + String.valueOf(stage);
			String variable = "win" + win.toString();

			JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "eventData", eventNode);

			if (jsonElement != null) {
				JsonElement stageJsonElement = new JsonParser().parse(jsonElement.toString());

				if (stageJsonElement instanceof JsonObject) {
					JsonObject stageJsonObject = (JsonObject) stageJsonElement;
					JsonElement jsonStageElement = stageJsonObject.get(stageName);

					if (jsonStageElement != null) {
						JsonElement internalJsonElement = new JsonParser().parse(jsonStageElement.toString());

						if (internalJsonElement instanceof JsonObject) {
							JsonObject json = (JsonObject) internalJsonElement;
							return json.get(variable).getAsInt();
						}
					}
				}
			}
		}

		return null;
	}

	public static void setEventStat (String configPlayerUUID, String eventID, int stage, Boolean win, int value) {
		EnumSpecialEvent specialEventEnum = EnumSpecialEvent.getSpecialEventData(eventID);

		if (specialEventEnum != null) {
			String eventNode = specialEventEnum.eventNodeIdentifier;
			String stageName = "stage" + String.valueOf(stage);
			String variable = "win" + win.toString();


			String oldJSON = "";
			Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "eventData").get("eventData");

			if (rawReply != null) {
				oldJSON = (String) rawReply;
			}

			JsonObject updatedJSON = new JsonObject();

			if (!oldJSON.isEmpty()) {
				updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
			}

			String oldStageJSON = "";
			Object rawStageReply = updatedJSON.get(eventNode);

			if (rawStageReply != null) {
				oldStageJSON = rawStageReply.toString();
			}

			JsonObject updatedStageJSON = new JsonObject();

			if (!oldStageJSON.isEmpty()) {
				updatedStageJSON = (JsonObject) new JsonParser().parse(oldStageJSON);
			}

			JsonObject variableValue = new JsonObject();

			if (!oldStageJSON.isEmpty()) {
				variableValue = (JsonObject) new JsonParser().parse(updatedStageJSON.get(stageName).toString());
			}

			variableValue.add(variable, new JsonPrimitive(value));

			updatedStageJSON.add(stageName, variableValue);

			updatedJSON.add(eventNode, updatedStageJSON);

			String updatedJSONString = updatedJSON.toString();

			MysqlActions.playerJsonSetting(configPlayerUUID, "eventData", updatedJSONString);
		}
	}


		public static void setEventBulkData (String configPlayerUUID, HashMap<String, HashMap<String, HashMap<String, Integer>>> eventDataMap) {
		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "eventData").get("eventData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		for (Object key : eventDataMap.keySet()) {
			String eventNode = key.toString();
			JsonObject eventData = new JsonObject();

			for (Object stageKey : eventDataMap.get(key).keySet()) {
				String stage = stageKey.toString();
				JsonObject stageValue = new JsonObject();

				if (!stage.equalsIgnoreCase("inside")) {
					for (Object variable : eventDataMap.get(key).get(stageKey).keySet()) {

						String variableString = variable.toString();
						Object value = eventDataMap.get(key).get(stageKey).get(variable);

						if (value instanceof Boolean) {
							stageValue.add(variableString, new JsonPrimitive((boolean) value));
						} else if (value instanceof String) {
							stageValue.add(variableString, new JsonPrimitive((String) value));
						} else if (value instanceof Integer) {
							stageValue.add(variableString, new JsonPrimitive((int) value));
						} else if (value instanceof Character) {
							stageValue.add(variableString, new JsonPrimitive((char) value));
						}
					}

					eventData.add(stage, stageValue);
				} else {

					Object value = eventDataMap.get(key).get("inside").get("inside");

					if (value instanceof Integer) {
						eventData.add("inside", new JsonPrimitive((int) value));
					}
				}
			}

			updatedJSON.add(eventNode, eventData);
		}


		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "eventData", updatedJSONString);
	}




	// Username Data

	public static Integer getUsernameCount (String configPlayerUUID) {

		JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "usernameData", "usernameCount");
		if (jsonElement != null) {
			return jsonElement.getAsInt();
		}

		return null;
	}

	public static void setUsernameCount (String configPlayerUUID, int value) {

		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "usernameData").get("usernameData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		updatedJSON.add("usernameCount", new JsonPrimitive(value));

		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "usernameData", updatedJSONString);
	}

	public static String getUsernameFromIndex (String configPlayerUUID, int index) {
		String usernameID = "username" + index;

		JsonElement jsonElement = MysqlActions.playerJSONValueGetting(configPlayerUUID, "usernameData", usernameID);
		if (jsonElement != null) {
			return jsonElement.getAsString();
		}

		return null;
	}

	public static void setUsernameFromIndex (String configPlayerUUID, int index, String value) {
		String usernameID = "username" + index;

		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "usernameData").get("usernameData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		updatedJSON.add(usernameID, new JsonPrimitive(value));

		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "usernameData", updatedJSONString);
	}

	public static void setBulkUsernames (String configPlayerUUID, HashMap<Integer, String> usernameMap) {

		String oldJSON = "";
		Object rawReply = MysqlActions.playerJSONRawGetting(configPlayerUUID, "usernameData").get("usernameData");

		if (rawReply != null) {
			oldJSON = (String) rawReply;
		}

		JsonObject updatedJSON = new JsonObject();

		if (!oldJSON.isEmpty()) {
			updatedJSON = (JsonObject) new JsonParser().parse(oldJSON);
		}

		for (Integer usernameID : usernameMap.keySet()) {
			String usernameIndex = "username" + usernameID;
			String username = usernameMap.get(usernameID);

			updatedJSON.add(usernameIndex, new JsonPrimitive(username));
		}

		String updatedJSONString = updatedJSON.toString();

		MysqlActions.playerJsonSetting(configPlayerUUID, "usernameData", updatedJSONString);

	}
}
