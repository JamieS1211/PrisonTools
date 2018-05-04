package com.github.jamies1211.prisontools.CommandsGeneral;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 * Created by Jamie on 10/01/2017.
 */
public class ClaimRulesCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("-------------------- &aClaim Rules &r---------------------"));
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a1:&r Do not invade other peoples claims without their permission."));
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a2:&r The claimowner can decide who enters the claim, even if you are trusted if he asks you to leave, do it."));
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a3:&r Do not sethome in other peoples claims without their permission."));
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a4:&r Do not claimhug (place claims right next too or around someone else's claim)."));
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a5:&r No griefing inside or around peoples claims."));
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a6:&r If you are trusted in a claim, don't take other peoples items without their permission."));
		src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("------------------------ < > -------------------------"));

		return CommandResult.success();
	}
}