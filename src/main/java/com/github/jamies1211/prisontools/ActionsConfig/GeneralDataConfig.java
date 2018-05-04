package com.github.jamies1211.prisontools.ActionsConfig;

import com.github.jamies1211.prisontools.Data.EnumGym;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import com.github.jamies1211.prisontools.PrisonTools;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 02-Jul-16.
 */
public class GeneralDataConfig {

	private static GeneralDataConfig config = new GeneralDataConfig();

	public static GeneralDataConfig getConfig() {
		return config;
	}

	private Path configFile = Paths.get(PrisonTools.getPrisonTools().getConfigDir() + "/general.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;

	public void setup() {
		if (!Files.exists(configFile)) {
			try {
				Files.createFile(configFile);
				load();
				enterData();
				save();
				PrisonTools.plugin.getLogger().info(newConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
				PrisonTools.plugin.getLogger().info(configLoadError);

			}
		} else {
			load();
			PrisonTools.plugin.getLogger().info(loadedConfigFile);
		}
	}

	public void load() {
		MysqlActions.closeConnectionIfRequired();

		try {
			configNode = configLoader.load();

			Object host = get().getNode("01 - MysqlHost").getValue();
			if (host == null) {
				enterDatabaseData();
				save();
			}

			MysqlActions.prisontoolsHost = get().getNode("01 - MysqlHost").getString();
			MysqlActions.prisontoolsDb = get().getNode("01 - MysqlDatabase").getString();
			MysqlActions.prisontoolsTable = get().getNode("01 - MysqlTable").getString();
			MysqlActions.prisontoolsUser = get().getNode("01 - MysqlUsername").getString();
			MysqlActions.prisontoolsPassword = get().getNode("01 - MysqlPassword").getString();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			configLoader.save(configNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CommentedConfigurationNode get() {
		return configNode;
	}

	public void enterData() {

		get().getNode("01 - ConfigVersion").setValue(1);
		get().getNode("01 - MembersFeaturesOn").setValue("false");
		get().getNode("01 - GymsOn").setValue("true");
		get().getNode("01 - EventsOn").setValue("true");

		enterDatabaseData();

		get().getNode("02 - PrisonBounds", "Pos1", "X").setValue("0");
		get().getNode("02 - PrisonBounds", "Pos1", "Z").setValue("0");
		get().getNode("02 - PrisonBounds", "Pos2", "X").setValue("0");
		get().getNode("02 - PrisonBounds", "Pos2", "Z").setValue("0");

		get().getNode("03 - Spawn", "SpawnX").setValue(3.5);
		get().getNode("03 - Spawn", "SpawnY").setValue(85);
		get().getNode("03 - Spawn", "SpawnZ").setValue(9.5);
		get().getNode("03 - Spawn", "SpawnDirection").setValue("West");

		for (int safariNumber = 1; safariNumber <= 8; safariNumber++) {
			get().getNode("04 - SafariSpawn", "safari" + safariNumber, "X").setValue(0.0);
			get().getNode("04 - SafariSpawn", "safari" + safariNumber, "Y").setValue(0.0);
			get().getNode("04 - SafariSpawn", "safari" + safariNumber, "Z").setValue(0.0);
			get().getNode("04 - SafariSpawn", "safari" + safariNumber, "Facing").setValue("North");

			get().getNode("05 - SafariBounds", "safari" + safariNumber, "Pos1", "X").setValue("0");
			get().getNode("05 - SafariBounds", "safari" + safariNumber, "Pos1", "Z").setValue("0");
			get().getNode("05 - SafariBounds", "safari" + safariNumber, "Pos2", "X").setValue("0");
			get().getNode("05 - SafariBounds", "safari" + safariNumber, "Pos2", "Z").setValue("0");
		}

		for (EnumGym currentGym : EnumGym.values()) {
			get().getNode("06 - GymSpawn", currentGym, "X").setValue("0");
			get().getNode("06 - GymSpawn", currentGym, "Y").setValue("0");
			get().getNode("06 - GymSpawn", currentGym, "Z").setValue("0");
			get().getNode("06 - GymSpawn", currentGym, "Facing").setValue("North");

			get().getNode("07 - GymBounds",currentGym, "Pos1", "X").setValue("0");
			get().getNode("07 - GymBounds",currentGym, "Pos1", "Y").setValue("0");
			get().getNode("07 - GymBounds",currentGym, "Pos1", "Z").setValue("0");
			get().getNode("07 - GymBounds",currentGym, "Pos2", "X").setValue("0");
			get().getNode("07 - GymBounds",currentGym, "Pos2", "Y").setValue("0");
			get().getNode("07 - GymBounds",currentGym, "Pos2", "Z").setValue("0");
		}

		for (int effortTrainValue = 1; effortTrainValue <= 3; effortTrainValue++) {
			get().getNode("08 - EVTrainSpawn", Integer.toString(effortTrainValue), "X").setValue("0");
			get().getNode("08 - EVTrainSpawn", Integer.toString(effortTrainValue), "Y").setValue("0");
			get().getNode("08 - EVTrainSpawn", Integer.toString(effortTrainValue), "Z").setValue("0");
			get().getNode("08 - EVTrainSpawn", Integer.toString(effortTrainValue), "Facing").setValue("North");

			get().getNode("09 - EVTrainBounds", Integer.toString(effortTrainValue), "Pos1", "X").setValue("0");
			get().getNode("09 - EVTrainBounds", Integer.toString(effortTrainValue), "Pos1", "Y").setValue("0");
			get().getNode("09 - EVTrainBounds", Integer.toString(effortTrainValue), "Pos1", "Z").setValue("0");
			get().getNode("09 - EVTrainBounds", Integer.toString(effortTrainValue), "Pos2", "X").setValue("0");
			get().getNode("09 - EVTrainBounds", Integer.toString(effortTrainValue), "Pos2", "Y").setValue("0");
			get().getNode("09 - EVTrainBounds", Integer.toString(effortTrainValue), "Pos2", "Z").setValue("0");
		}

		for (EnumSpecialEvent currentSpecialEvent : EnumSpecialEvent.values()) {
			for (int stage = 1; stage <= currentSpecialEvent.stages; stage ++) {
				get().getNode("10 - EventSpawn", currentSpecialEvent, "stage" + stage + "spawn", "X").setValue("0");
				get().getNode("10 - EventSpawn", currentSpecialEvent, "stage" + stage + "spawn", "Y").setValue("0");
				get().getNode("10 - EventSpawn", currentSpecialEvent, "stage" + stage + "spawn", "Z").setValue("0");
				get().getNode("10 - EventSpawn", currentSpecialEvent, "stage" + stage + "spawn", "Facing").setValue("North");


				get().getNode("11 - EventBounds", currentSpecialEvent, "stage" + stage, "Pos1", "X").setValue("0");
				get().getNode("11 - EventBounds", currentSpecialEvent, "stage" + stage, "Pos1", "Y").setValue("0");
				get().getNode("11 - EventBounds", currentSpecialEvent, "stage" + stage, "Pos1", "Z").setValue("0");
				get().getNode("11 - EventBounds", currentSpecialEvent, "stage" + stage, "Pos2", "X").setValue("0");
				get().getNode("11 - EventBounds", currentSpecialEvent, "stage" + stage, "Pos2", "Y").setValue("0");
				get().getNode("11 - EventBounds", currentSpecialEvent, "stage" + stage, "Pos2", "Z").setValue("0");
			}
		}
	}

	public void enterDatabaseData () {
		get().getNode("01 - MysqlHost").setValue("host");
		get().getNode("01 - MysqlDatabase").setValue("enterDatabase");
		get().getNode("01 - MysqlTable").setValue("enterTable");
		get().getNode("01 - MysqlUsername").setValue("enterUsername");
		get().getNode("01 - MysqlPassword").setValue("enterPassword");
	}
}
