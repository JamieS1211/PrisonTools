package com.github.jamies1211.prisontools.ActionsGeneral;

import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static com.github.jamies1211.prisontools.Data.Lists.centralRestrictedTypeList;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class CheckRestrictedItem {

	public static String hasRestrictedItem (Player player, String type) {

		ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
		type = type.toUpperCase();

		if (centralRestrictedTypeList.contains(type)) {

			ArrayList<String> disallowed = new ArrayList<>();

			//TODO Move this to DataConfigInteract
			Set<Object> listOfItems = new TreeSet<>(generalDataConfig.getNode("12 - RestrictedItems", type).getChildrenMap().keySet());

			for (Object key : listOfItems) {
				ItemStack stack;
				stack = ItemStack.builder().fromContainer(DataTranslators.CONFIGURATION_NODE.translate(GeneralDataConfig.getConfig().get().getNode("12 - RestrictedItems", type, key))).build();
				ItemType item = stack.getItem();
				String itemString = item.getName();
				Boolean hasItem = false;

				if (player.getInventory().query(item).peek().isPresent()) {
					hasItem = true;
				} else if (player.getItemInHand(HandTypes.OFF_HAND).isPresent()) {
					if (player.getItemInHand(HandTypes.OFF_HAND).get().getItem().getName().equalsIgnoreCase(itemString)) {
						hasItem = true;
					}
				}

				if (hasItem) {
					itemString = itemString.replace("minecraft:", ""); // Removes name junk from minecraft.
					itemString = itemString.replace("pixelmon:", ""); // Removes name junk from pixelmon.
					itemString = itemString.replace("item.", ""); // Removes name junk from being an item.
					itemString = itemString.replace("_", " "); // Replaces underscore with spaces.
					itemString = itemString.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"); // Places space between lower and uppercase letters.

					disallowed.add(itemString);
				}
			}

			if (disallowed.isEmpty()) {
				return null;
			} else {
				return disallowed.toString();
			}
		} else {
			return "&cA restricted item check was attempted with an invalid type";
		}
	}
}
