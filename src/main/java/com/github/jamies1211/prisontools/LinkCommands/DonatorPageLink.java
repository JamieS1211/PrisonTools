package com.github.jamies1211.prisontools.LinkCommands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by Jamie on 25-Jun-16.
 */
public class DonatorPageLink implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		String url = "pixelmonweb.officialtjp.com/shop";

		Optional<ClickAction.OpenUrl> clickAction = Optional.empty();

		try {
			clickAction = Optional.of(TextActions.openUrl(new URL("http://" + url)));
			Text message = (Text.builder()
					.append(TextSerializers.FORMATTING_CODE.deserialize("&8Please click the link to go to the donator page &9%url%".replace("%url%", url)))
					.onClick(clickAction.isPresent() ? clickAction.get() : null)
				.build());

			src.sendMessage(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return CommandResult.success();
	}
}