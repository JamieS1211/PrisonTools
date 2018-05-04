package com.github.jamies1211.prisontools.ActionsGeneral;

import com.github.jamies1211.prisontools.PrisonTools;
import org.spongepowered.api.Sponge;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jamie on 27/11/2016.
 */
public class DelayedCommand {

	public static void delayedCommand(int milliseconds, String command) {
		Sponge.getScheduler().createTaskBuilder().execute(new Runnable() {
			public void run() {
				Sponge.getCommandManager().process(Sponge.getServer().getConsole(), command);
			}
		}).interval(milliseconds, TimeUnit.MILLISECONDS).name("Prison tools checking").submit(PrisonTools.plugin);
	}
}
