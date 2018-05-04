package com.github.jamies1211.prisontools.ActionsEVTrain;

import com.github.jamies1211.prisontools.PrisonTools;
import org.spongepowered.api.entity.living.player.Player;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerEVTrainTime;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.playerEVTrainTimeExist;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.setPlayerEVTrainTime;

/**
 * Created by Jamie on 11-May-16.
 */
public class ChangeEVTrainTime {

	public static void changeEVTrainTime(Player player, int evTrainArea, int changeValue) {

		if (playerEVTrainTimeExist(player, evTrainArea)) {
			int startValue = getPlayerEVTrainTime(player,evTrainArea);
			int endValue = startValue + changeValue;
			if (endValue >= 0) {
				setPlayerEVTrainTime(player,evTrainArea,endValue);
			}
		} else if (evTrainArea >= 1 && evTrainArea <= 3) {
			PrisonTools.plugin.getLogger().info("Tried to lower ev train time for: " + player.getName() + " but no value exists yet for that player.  Value being added...");

			setPlayerEVTrainTime(player, evTrainArea, 0);
		} else {
			PrisonTools.plugin.getLogger().info("Tried to lower ev train time invalid area number was given.");
		}
	}

	public static void reduceAllEVTrainOne(Player player) {
		for (int currentEVTrainArea = 1; currentEVTrainArea <= 3; currentEVTrainArea++) {
			changeEVTrainTime(player, currentEVTrainArea, -1);
		}
	}
}
