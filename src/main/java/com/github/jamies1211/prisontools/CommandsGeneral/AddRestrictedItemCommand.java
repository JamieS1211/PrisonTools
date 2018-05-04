package com.github.jamies1211.prisontools.CommandsGeneral;

import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.Arrays;

import static com.github.jamies1211.prisontools.ActionsConfig.ConfigDataInteraction.*;
import static com.github.jamies1211.prisontools.Data.Messages.*;

/**
 * Created by Jamie on 18-Jun-16.
 */
public class AddRestrictedItemCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if (src instanceof Player) {
			Player player = (Player) src;

			final String type = args.<String>getOne("type").get().toUpperCase();

			ArrayList<String> typeList = new ArrayList<>(Arrays.asList("SAFARI", "GYM", "EVTRAIN", "BATTLETOWER", "SKYTOWER", "AGATHA", "LORELEI"));
			if (typeList.contains(type)) {

				int currentSize = getNodeMapSize("12 - RestrictedItems", type);


				ItemStack stack = player.getItemInHand(HandTypes.MAIN_HAND).get();
				getConfigNode("12 - RestrictedItems", type, "item" + (currentSize + 1)).setValue(DataTranslators.CONFIGURATION_NODE.translate(stack.toContainer()));
				GeneralDataConfig.getConfig().save();
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(restrictedItemAdd
						.replace("%item%", stack.getItem().getName())
						.replace("%type%", type)));
			} else {
				src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(invalidRestrictType
						.replace("%types%", typeList.toString())));
			}
		} else {
			src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(playerCommandError));
		}
		return CommandResult.success();
	}
}