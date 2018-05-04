package com.github.jamies1211.prisontools.ActionsSafari;

import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import com.github.jamies1211.prisontools.PrisonTools;
import org.spongepowered.api.entity.living.player.Player;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerNodeInt;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setPlayerNode;

/**
 * Created by Jamie on 11-May-16.
 */
public class ChangeSafariTime {

	public static void changeSafariTime(Player player, int safariNumber, int changeValue) {

		//ConfigurationNode playerDataConfig = PlayerDataConfig.getConfig().get();
		String configPlayerUUID = player.getUniqueId().toString();

		if (getPlayerNodeExists(configPlayerUUID, "safariData", "safari" + safariNumber)) {
			int startValue = getPlayerNodeInt(configPlayerUUID, "safariData", "safari" + safariNumber);
			int endValue = startValue + changeValue;
			if (endValue >= 0) {
				setPlayerNode(configPlayerUUID, "safariData", "safari" + safariNumber,endValue);
			}
		} else if (safariNumber >= 1 && safariNumber <= 8) {
			PrisonTools.plugin.getLogger().info("Tried to lower safari time for: " + player.getName() + " but no value exists yet for that player. Value being added...");
			setPlayerNode(configPlayerUUID, "safariData", "safari" + safariNumber,0);
			PlayerDataConfig.getConfig().save();
		} else {
			PrisonTools.plugin.getLogger().info("Tried to lower safari time invalid safari number was given.");
		}
	}

	public static void reduceAllSafariOne(Player player) {
		for (int currentSafari = 1; currentSafari <= 8; currentSafari++) {
			changeSafariTime(player, currentSafari, -1);
		}
	}
}
