package com.github.jamies1211.prisontools.ActionsConfig;

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
public class PlayerDataConfig {

	private static PlayerDataConfig config = new PlayerDataConfig();

	public static PlayerDataConfig getConfig() {
		return config;
	}

	private Path configFile = Paths.get(PrisonTools.getPrisonTools().getConfigDir() + "/playerdata.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;

	public void setup() {
		if (!Files.exists(configFile)) {
			try {
				Files.createFile(configFile);
				load();
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
		try {
			configNode = configLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PlayerConfigDataInteraction.updatePlayerdata();
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
}
