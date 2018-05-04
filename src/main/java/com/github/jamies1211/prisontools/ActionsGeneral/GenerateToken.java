package com.github.jamies1211.prisontools.ActionsGeneral;

import com.github.jamies1211.prisontools.Data.EVTrain.EVTrainTokenData;
import com.github.jamies1211.prisontools.Data.EnumGym;
import com.github.jamies1211.prisontools.Data.EnumSpecialEvent;
import com.github.jamies1211.prisontools.Data.Event.EventTokenData;
import com.github.jamies1211.prisontools.Data.Gym.GymTokenData;
import com.github.jamies1211.prisontools.Data.Safari.SafariTokenData;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * @author Jamie
 * Created by Jamie on 13-May-16.
 */
public class GenerateToken {

	public static ItemStack generateSafariToken(int minutes, int safariNumber) {
		ItemStack safariToken = ItemStack.builder().itemType(ItemTypes.PAPER).build();
		safariToken.offer(Keys.DISPLAY_NAME, Text.of(TextColors.BLUE, minutes + "mins in safari: " + safariNumber));
		safariToken.offer(new SafariTokenData(true, (double) minutes, safariNumber));

		return safariToken;
	}

	public static ItemStack generateEVTrainToken(int minutes, int zone) {
		ItemStack evTrainToken = ItemStack.builder().itemType(ItemTypes.PAPER).build();
		evTrainToken.offer(Keys.DISPLAY_NAME, Text.of(TextColors.BLUE, minutes + "mins in EV area: " + zone));
		evTrainToken.offer(new EVTrainTokenData(true, (double) minutes, zone));

		return evTrainToken;
	}

	public static ItemStack generateGymToken(String gymRank) {
		ItemStack gymToken = ItemStack.builder().itemType(ItemTypes.PAPER).build();
		EnumGym gym = EnumGym.getGymData(gymRank);

		if (gym != null) {
			gymToken.offer(Keys.DISPLAY_NAME, Text.of(TextColors.BLUE, "Gym [" + gymRank +  "] token"));
			gymToken.offer(new GymTokenData(true, gymRank));
		} else {
			gymToken.offer(Keys.DISPLAY_NAME, Text.of(TextColors.BLUE, "Invalid gym letter supplied to method"));
		}
		return gymToken;
	}

	public static ItemStack generateEventToken(String eventType) {
		ItemStack eventToken = ItemStack.builder().itemType(ItemTypes.PAPER).build();
		EnumSpecialEvent currentEvent = EnumSpecialEvent.getSpecialEventData(eventType);

		if (currentEvent != null) {
			eventToken.offer(Keys.DISPLAY_NAME, Text.of(TextColors.BLUE, "Event [" + eventType +  "] token"));
			eventToken.offer(new EventTokenData(true, eventType));
		} else {
			eventToken.offer(Keys.DISPLAY_NAME, Text.of(TextColors.BLUE, "Invalid event type supplied to method"));
		}
		return eventToken;
	}

}
