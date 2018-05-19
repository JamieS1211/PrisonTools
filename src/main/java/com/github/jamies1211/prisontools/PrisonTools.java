package com.github.jamies1211.prisontools;

/**
 * Created by Jamie on 07-May-16.
 */

import com.github.jamies1211.prisontools.ActionsConfig.GeneralDataConfig;
import com.github.jamies1211.prisontools.ActionsConfig.MysqlActions;
import com.github.jamies1211.prisontools.ActionsConfig.PlayerDataConfig;
import com.github.jamies1211.prisontools.ActionsConfig.PlayerMysqlDataInteraction;
import com.github.jamies1211.prisontools.ActionsEVTrain.ChangeEVTrainTime;
import com.github.jamies1211.prisontools.ActionsEVTrain.CheckInEVTrain;
import com.github.jamies1211.prisontools.ActionsEvent.CheckInEvent;
import com.github.jamies1211.prisontools.ActionsGeneral.*;
import com.github.jamies1211.prisontools.ActionsGym.CheckInGym;
import com.github.jamies1211.prisontools.ActionsGym.GymCooldown;
import com.github.jamies1211.prisontools.ActionsSafari.ChangeSafariTime;
import com.github.jamies1211.prisontools.ActionsSafari.CheckInSafari;
import com.github.jamies1211.prisontools.ActionsSafari.SafariButtonPress;
import com.github.jamies1211.prisontools.CommandsEVTrain.EVTrainHelpCommand;
import com.github.jamies1211.prisontools.CommandsEvent.EventHelpCommand;
import com.github.jamies1211.prisontools.CommandsGeneral.*;
import com.github.jamies1211.prisontools.CommandsGym.GymHelpCommand;
import com.github.jamies1211.prisontools.CommandsSafari.SafariHelpCommand;
import com.github.jamies1211.prisontools.Data.customData.EVTrain.EVTrainTokenData;
import com.github.jamies1211.prisontools.Data.customData.EVTrain.EVTrainTokenDataBuilder;
import com.github.jamies1211.prisontools.Data.customData.EVTrain.ImmutableEVTrainTokenData;
import com.github.jamies1211.prisontools.Data.EnumRestrictedUseItems;
import com.github.jamies1211.prisontools.Data.customData.Event.EventTokenData;
import com.github.jamies1211.prisontools.Data.customData.Event.EventTokenDataBuilder;
import com.github.jamies1211.prisontools.Data.customData.Event.ImmutableEventTokenData;
import com.github.jamies1211.prisontools.Data.customData.Gym.GymTokenData;
import com.github.jamies1211.prisontools.Data.customData.Gym.GymTokenDataBuilder;
import com.github.jamies1211.prisontools.Data.customData.Gym.ImmutableGymTokenData;
import com.github.jamies1211.prisontools.Data.customData.MyKeys;
import com.github.jamies1211.prisontools.Data.customData.Safari.ImmutableSafariTokenData;
import com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenData;
import com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenDataBuilder;
import com.github.jamies1211.prisontools.LinkCommands.*;
import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.*;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.hanging.ItemFrame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.github.jamies1211.prisontools.ActionsSafari.CheckInSafari.checkPlayerInSafariPadding;
import static com.github.jamies1211.prisontools.Data.Messages.*;

@Plugin(id = "prisontools", name = "Prison Tools", version = "3.0.0-1.12.2",
		description = "Adds various tools for Pixelmon Prison",
		authors = {"JamieS1211"},
		url = "http://pixelmonweb.officialtjp.com")
public class PrisonTools {

	@Inject
	private Logger logger;
	public static PrisonTools plugin;

	@Inject
	PluginContainer container;

	public Logger getLogger() {
		return this.logger;
	}

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	public static PrisonTools getPrisonTools() {
		return plugin;
	}

	public Path getConfigDir() {
		return configDir;
	}

	public ArrayList<UUID> tokenInteractionCooldown = new ArrayList<>();

	@SuppressWarnings("UnusedParameters")
	@Listener
	public void onPreInitialization(GamePreInitializationEvent event) {
		plugin = this;

		// Create config Directory for PrisonTools
		if (!Files.exists(configDir)) {
			try {
				Files.createDirectories(configDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Create general.conf
		GeneralDataConfig.getConfig().setup();
		// Create playerdata.conf
		PlayerDataConfig.getConfig().setup();
	}

	@SuppressWarnings("UnusedParameters")
	@Listener
	public void onServerLoadComplete(GameLoadCompleteEvent event) {

		Sponge.getScheduler().createTaskBuilder().execute(new Runnable() {
			public void run() {
				cycle();
			}
		}).async().interval(1, TimeUnit.SECONDS).name("PrisonToolsChecks").submit(plugin);

		Sponge.getScheduler().createTaskBuilder().execute(new Runnable() {
			public void run() {
				cycleSaveAsync();
			}
		}).async().interval(60, TimeUnit.SECONDS).name("PrisonToolsDatabaseSave").submit(plugin);
	}

	@SuppressWarnings("UnusedParameters")
	@Listener
	public void onServerStopping(GameStoppingServerEvent event) {
		MysqlActions.closeConnectionIfRequired();
	}

	@SuppressWarnings("UnusedParameters")
	@Listener
	public void onInitial(GameConstructionEvent event) {
		getLogger().info("PrisonTools custom data being registered");
	}

	@Listener
	public void onKeyRegister(GameRegistryEvent.Register<Key<?>> event) {
		MyKeys.dummy();
		event.register(MyKeys.EVENT_TYPE);
		event.register(MyKeys.IS_EVENT_TOKEN);
		event.register(MyKeys.EVTRAIN_TIME);
		event.register(MyKeys.EVTRAIN_ZONE);
		event.register(MyKeys.IS_EVTRAIN_TOKEN);
		event.register(MyKeys.IS_GYM_TOKEN);
		event.register(MyKeys.GYM_RANK);
		event.register(MyKeys.IS_SAFARI_TOKEN);
		event.register(MyKeys.SAFARI_TIME);
		event.register(MyKeys.SAFARI_ZONE);
	}

	@SuppressWarnings("UnusedParameters")
	@Listener
	public void onDataRegister(GameRegistryEvent.Register<DataRegistration<?, ?>> event) {
		DataRegistration.builder()
				.dataName("Event Token")
				.manipulatorId("eventtoken") // prefix is added for you and you can't add it yourself. This is the key without the plugin ID
				.dataClass(EventTokenData.class)
				.immutableClass(ImmutableEventTokenData.class)
				.builder(new EventTokenDataBuilder())
				.buildAndRegister(container);


		DataRegistration.builder()
				.dataName("EVTrain Token")
				.manipulatorId("evtraintoken") // prefix is added for you and you can't add it yourself. This is the key without the plugin ID
				.dataClass(EVTrainTokenData.class)
				.immutableClass(ImmutableEVTrainTokenData.class)
				.builder(new EVTrainTokenDataBuilder())
				.buildAndRegister(container);


		DataRegistration.builder()
				.dataName("Gym Token")
				.manipulatorId("gymtoken") // prefix is added for you and you can't add it yourself. This is the key without the plugin ID
				.dataClass(GymTokenData.class)
				.immutableClass(ImmutableGymTokenData.class)
				.builder(new GymTokenDataBuilder())
				.buildAndRegister(container);


		DataRegistration.builder()
				.dataName("Safari Token")
				.manipulatorId("safaritoken") // prefix is added for you and you can't add it yourself. This is the key without the plugin ID
				.dataClass(SafariTokenData.class)
				.immutableClass(ImmutableSafariTokenData.class)
				.builder(new SafariTokenDataBuilder())
				.buildAndRegister(container);
	}

	@SuppressWarnings("UnusedParameters")
	@Listener
	public void onServerStart(GameInitializationEvent event) {
		getLogger().info(startup);
		MysqlActions.startConnection();

		/** Prison tools (general) command */
		final CommandSpec prisonToolsCommand = CommandSpec.builder()
				.permission("prisontools.general")
				.description(Text.of("Shows prison tools command help"))
				.extendedDescription(Text.of("Use this command to list all the prison tools sub commands and how to use them"))
				.executor(new PrisonToolsHelpCommand())
				.children(GeneralSubCommands.getGeneralSubCommands())
				.build();
		Sponge.getCommandManager().register(this, prisonToolsCommand, "prisontools", "pt");


		/** Reboot Timer command */
		final CommandSpec rebootTimerCommand = CommandSpec.builder()
				//.permission("prisontools.general")
				.description(Text.of("Shows time till restart"))
				.extendedDescription(Text.of("Use this command to find out how long till next reboot"))
				.executor(new RebootTimerCommand())
				.build();
		Sponge.getCommandManager().register(this, rebootTimerCommand, "reboottimer", "rt");

		/** Rank Sync command */
		final CommandSpec rankSyncCommand = CommandSpec.builder()
				//.permission("prisontools.general")
				.description(Text.of("fixes rank based on gyms defeated"))
				.extendedDescription(Text.of("Use this command to fix your rank if it is incorrect"))
				.executor(new FixRankCommand())
				.build();
		Sponge.getCommandManager().register(this, rankSyncCommand, "fixrank");

		/** Safari command */
		final CommandSpec safariCommand = CommandSpec.builder()
				.description(Text.of("Shows safari command help"))
				.extendedDescription(Text.of("Use this command to list all the safari sub commands and how to use them"))
				.executor(new SafariHelpCommand())
				.children(SafariSubCommands.getSafariSubCommands())
				.build();
		Sponge.getCommandManager().register(this, safariCommand, "safari");

		/** EV Train command */
		final CommandSpec evTrainCommand = CommandSpec.builder()
				.description(Text.of("Shows EV Training area command help"))
				.extendedDescription(Text.of("Use this command to list all the EV Training area sub commands and how to use them"))
				.executor(new EVTrainHelpCommand())
				.children(EVTrainSubCommands.getEVTrainSubCommands())
				.build();
		Sponge.getCommandManager().register(this, evTrainCommand, "evtrain");

		/** Gym command */
		final CommandSpec gymCommand = CommandSpec.builder()
				.description(Text.of("Shows gym help"))
				.extendedDescription(Text.of("Use this command to get help for gyms"))
				.executor(new GymHelpCommand())
				.children(GymSubCommands.getGymSubCommands())
				.build();
		Sponge.getCommandManager().register(this, gymCommand, "gym");

		/** Event command */
		final CommandSpec eventCommand = CommandSpec.builder()
				.description(Text.of("Shows event help"))
				.extendedDescription(Text.of("Use this command to get help for events"))
				.executor(new EventHelpCommand())
				.children(EventSubCommands.getEventSubCommands())
				.build();
		Sponge.getCommandManager().register(this, eventCommand, "event");

		/** Restrict item command */
		final CommandSpec itemRestrictCommand = CommandSpec.builder()
				.permission("prisontools.restrict")
				.description(Text.of("Add items to the list of restricted items"))
				.extendedDescription(Text.of("Use this command to log player finishing a gym battle"))
				.arguments(
						GenericArguments.onlyOne(GenericArguments.string(Text.of("type"))))
				.executor(new AddRestrictedItemCommand())
				.build();
		Sponge.getCommandManager().register(this, itemRestrictCommand, "restrict");

		/** Help link commands */

		final CommandSpec forumHelpLink = CommandSpec.builder()
				.permission("prisontools.helplink.forum")
				.description(Text.of("Gives the player the URL for the forums"))
				.extendedDescription(Text.of("Gives the player the URL for the forums"))
				.executor(new ForumPageLink())
				.build();
		Sponge.getCommandManager().register(this, forumHelpLink, "forums", "forum");

		final CommandSpec ruleHelpLink = CommandSpec.builder()
				.permission("prisontools.helplink.rule")
				.description(Text.of("Gives the player the URL for the rules in details"))
				.extendedDescription(Text.of("Gives the player the URL for the rules in details"))
				.executor(new RulePageLink())
				.build();
		Sponge.getCommandManager().register(this, ruleHelpLink, "rulesall", "ruleinfo", "rulesinfo", "fullrules");

		final CommandSpec guidesHelpLink = CommandSpec.builder()
				.permission("prisontools.helplink.guide")
				.description(Text.of("Gives the player the URL for the guides forum"))
				.extendedDescription(Text.of("Gives the player the URL for the guides forum"))
				.executor(new GuidesPageLink())
				.build();
		Sponge.getCommandManager().register(this, guidesHelpLink, "guides", "guide");

		final CommandSpec gymHelpLink = CommandSpec.builder()
				.permission("prisontools.helplink.gym")
				.description(Text.of("Gives the player the URL for the gym info"))
				.extendedDescription(Text.of("Gives the player the URL for the gym info"))
				.executor(new GymsPageLink())
				.build();
		Sponge.getCommandManager().register(this, gymHelpLink, "gyminfo", "gyminformation", "gymsinfo", "gymsinformation");

		final CommandSpec donatorHelpLink = CommandSpec.builder()
				.permission("prisontools.helplink.donator")
				.description(Text.of("Gives the player the URL for the donate page"))
				.extendedDescription(Text.of("Gives the player the URL for the donate page"))
				.executor(new DonatorPageLink())
				.build();
		Sponge.getCommandManager().register(this, donatorHelpLink, "donate", "store");

		final CommandSpec commandsLink = CommandSpec.builder()
				.permission("prisontools.helplink.commands")
				.description(Text.of("Gives the player the URL for the commands info page"))
				.extendedDescription(Text.of("Gives the player the URL for the commands info page"))
				.executor(new CommandsPageLink())
				.build();
		Sponge.getCommandManager().register(this, commandsLink, "commands", "commandlist");

		final CommandSpec reportLink = CommandSpec.builder()
				.permission("prisontools.helplink.report")
				.description(Text.of("Gives the player the URL for the complaints forum section"))
				.extendedDescription(Text.of("Gives the player the URL for the complaints forum section"))
				.executor(new ReportPageLink())
				.build();
		Sponge.getCommandManager().register(this, reportLink, "report", "reportplayer", "reportplayers");

		final CommandSpec bugLink = CommandSpec.builder()
				.permission("prisontools.helplink.bug")
				.description(Text.of("Gives the player the URL for the bugs forum section"))
				.extendedDescription(Text.of("Gives the player the URL for the bugs forum section"))
				.executor(new BugPageLink())
				.build();
		Sponge.getCommandManager().register(this, bugLink, "bug", "bugs", "reportbug", "reportbugs");

		final CommandSpec pokelossLink = CommandSpec.builder()
				.permission("prisontools.helplink.pokeloss")
				.description(Text.of("Gives the player the URL for the pokeloss forum section"))
				.extendedDescription(Text.of("Gives the player the URL for the pokeloss forum section"))
				.executor(new PokelossPageLink())
				.build();
		Sponge.getCommandManager().register(this, pokelossLink, "pokeloss", "pokeglitch");

		final CommandSpec safarizoneLink = CommandSpec.builder()
				.permission("prisontools.helplink.safarizone")
				.description(Text.of("Gives the player the URL for the safarizone info page"))
				.extendedDescription(Text.of("Gives the player the URL for the complaints info page"))
				.executor(new SafariZonesPageLink())
				.build();
		Sponge.getCommandManager().register(this, safarizoneLink, "safarizone", "safarizones", "safaris", "safariinfo");

		final CommandSpec donatorgymsLink = CommandSpec.builder()
				.permission("prisontools.helplink.donatorgyms")
				.description(Text.of("Gives the player the URL for the donator gyms info page"))
				.extendedDescription(Text.of("Gives the player the URL for the donator gyms info page"))
				.executor(new DonatorGymsPageLink())
				.build();
		Sponge.getCommandManager().register(this, donatorgymsLink, "donatorgyms", "donorgyms", "donorgym", "donatorgym", "donorgyminfo", "donatorgyminfo");

		final CommandSpec customcodeLink = CommandSpec.builder()
				.permission("prisontools.helplink.customcode")
				.description(Text.of("Gives the player the URL for the custom code info page"))
				.extendedDescription(Text.of("Gives the player the URL for the custom code info page"))
				.executor(new CustomCodePageLink())
				.build();
		Sponge.getCommandManager().register(this, customcodeLink, "customcode");

		final CommandSpec siteLink = CommandSpec.builder()
				.permission("prisontools.helplink.site")
				.description(Text.of("Gives the player the URL for the website"))
				.extendedDescription(Text.of("Gives the player the URL for the website"))
				.executor(new MainWebsitePageLink())
				.build();
		Sponge.getCommandManager().register(this, siteLink, "site", "website");

		final CommandSpec pixelmonwikiLink = CommandSpec.builder()
				.permission("prisontools.helplink.pixelmonwiki")
				.description(Text.of("Gives the player the URL for the pixelmon wiki"))
				.extendedDescription(Text.of("Gives the player the URL for the pixelmon wiki"))
				.executor(new PixelmonWikiPageLink())
				.build();
		Sponge.getCommandManager().register(this, pixelmonwikiLink, "pixelmonwiki");

		final CommandSpec recentupdateLink = CommandSpec.builder()
				.permission("prisontools.helplink.recentupdate")
				.description(Text.of("Gives the player the URL for the recent pixelmon update page"))
				.extendedDescription(Text.of("Gives the player the URL for the recent pixelmon update page"))
				.executor(new RecentUpdatePageLink())
				.build();
		Sponge.getCommandManager().register(this, recentupdateLink, "recentupdate", "recent");

		final CommandSpec upcomingupdateLink = CommandSpec.builder()
				.permission("prisontools.helplink.upcomingupdate")
				.description(Text.of("Gives the player the URL for the upcoming update page"))
				.extendedDescription(Text.of("Gives the player the URL for the upcoming update page"))
				.executor(new UpcomingUpdatePageLink())
				.build();
		Sponge.getCommandManager().register(this, upcomingupdateLink, "upcomingupdate", "upcoming");

		final CommandSpec pixelmonbugLink = CommandSpec.builder()
				.permission("prisontools.helplink.pixelmonbug")
				.description(Text.of("Gives the player the URL for the pixelmon bug report page"))
				.extendedDescription(Text.of("Gives the player the URL for the pixelmon bug report page"))
				.executor(new PixelmonBugPageLink())
				.build();
		Sponge.getCommandManager().register(this, pixelmonbugLink, "pixelmonbug", "pixelmonbugs");

		final CommandSpec btinfoLink = CommandSpec.builder()
				.permission("prisontools.helplink.btinfo")
				.description(Text.of("Gives the player the URL for the battle tower info page"))
				.extendedDescription(Text.of("Gives the player the URL for the battle tower info page"))
				.executor(new BattletowerInfoPageLink())
				.build();
		Sponge.getCommandManager().register(this, btinfoLink, "btinfo", "battletower", "battletowerinfo");

		final CommandSpec stinfoLink = CommandSpec.builder()
				.permission("prisontools.helplink.stinfo")
				.description(Text.of("Gives the player the URL for the sky tower info page"))
				.extendedDescription(Text.of("Gives the player the URL for the sky tower info page"))
				.executor(new SkytowerInfoPageLink())
				.build();
		Sponge.getCommandManager().register(this, stinfoLink, "stinfo", "skytower", "skytowerinfo");

		final CommandSpec mineinfoLink = CommandSpec.builder()
				.permission("prisontools.helplink.mineinfo")
				.description(Text.of("Gives the player the URL for the mine info page"))
				.extendedDescription(Text.of("Gives the player the URL for the mine info page"))
				.executor(new MineInfoPageLink())
				.build();
		Sponge.getCommandManager().register(this, mineinfoLink, "mineinfo", "minesinfo");

		final CommandSpec suggestionsLink = CommandSpec.builder()
				.permission("prisontools.helplink.suggestions")
				.description(Text.of("Gives the player the URL for the suggestions forum section"))
				.extendedDescription(Text.of("Gives the player the URL for the suggestions forum section"))
				.executor(new SuggestionsPageLink())
				.build();
		Sponge.getCommandManager().register(this, suggestionsLink, "suggestions", "suggestion");

		final CommandSpec tradelimitsLink = CommandSpec.builder()
				.permission("prisontools.helplink.tradelimits")
				.description(Text.of("Gives the player the trade limit sections"))
				.extendedDescription(Text.of("Gives the player the trade limit sections"))
				.executor(new TradeLimitsCommand())
				.build();
		Sponge.getCommandManager().register(this, tradelimitsLink, "tradelimits", "tradelimit");

		final CommandSpec claimrulesLink = CommandSpec.builder()
				.permission("prisontools.helplink.claimrules")
				.description(Text.of("Gives the player the rules for claimed areas"))
				.extendedDescription(Text.of("Gives the player the rules for claimed areas"))
				.executor(new ClaimRulesCommand())
				.build();
		Sponge.getCommandManager().register(this, claimrulesLink, "claimrules", "ruleclaims");

		final CommandSpec stafflistLink = CommandSpec.builder()
				.permission("prisontools.helplink.stafflist")
				.description(Text.of("Gives the player the URL for the staff list page"))
				.extendedDescription(Text.of("Gives the player the URL for the staff list page"))
				.executor(new StaffPageLink())
				.build();
		Sponge.getCommandManager().register(this, stafflistLink, "staff", "stafflist");


		/** Backwards compatibility for changing rank up/down commands */

		if (GeneralDataConfig.getConfig().get().getNode("13 - RankCommands", "rankup").getValue() == null){
			getLogger().info("Adding default pex rankup command to prison tools config");
			GeneralDataConfig.getConfig().get().getNode("13 - RankCommands", "rankup").setValue("pex user %playerData% parent add %newRank%");
		}
		if (GeneralDataConfig.getConfig().get().getNode("13 - RankCommands", "derank").getValue() == null){
			getLogger().info("Adding default pex derank command to prison tools config");
			GeneralDataConfig.getConfig().get().getNode("13 - RankCommands", "derank").setValue("pex user %playerData% parent remove %oldRank%");
		}
	}

	@Listener
	public void onPlayerJoin (ClientConnectionEvent.Join event) {

		// Sets up event required variables
		Player player = event.getTargetEntity();
		String configPlayerUUID = player.getUniqueId().toString();

		Teleport.Spawn(player);

		Sponge.getScheduler().createTaskBuilder().execute(() -> {

			if (player.hasPermission("prisontools.testDatabaseConnection")) {
				PlayerMysqlDataInteraction.updateMemoryFromDatabase(configPlayerUUID);
			}

			PlayerJoin.PlayerJoinActions(configPlayerUUID, player);

		}).async().name("PlayerDataFromDatabase").submit(PrisonTools.plugin);
	}

	@Listener
	public void onPlayerQuit (ClientConnectionEvent.Disconnect event) {

		String configPlayerUUID = event.getTargetEntity().getUniqueId().toString();

		Sponge.getScheduler().createTaskBuilder().execute(() -> {

			if (event.getTargetEntity().hasPermission("prisontools.testDatabaseConnection")) {
				PlayerMysqlDataInteraction.updateDatabaseFromMemory(configPlayerUUID);
			}

		}).async().name("PlayerDataToDatabase").submit(PrisonTools.plugin);
	}

	/** Damage protection at spawn */
	@Listener
	public void onEntityDamage(DamageEntityEvent event) {
		if (event.getTargetEntity() instanceof Player) {
			Player player = ((Player) event.getTargetEntity());

			if (CheckInPrison.checkPlayerInSpawn(player)) {
				player.sendMessage(ChatTypes.ACTION_BAR, TextSerializers.FORMATTING_CODE.deserialize(spawnProtection));
				event.setCancelled(true);
			}
		}
	}

	/** Prevent interaction between tokens and item frames */
	@Listener
	public void onEntityInteraction(InteractEntityEvent.Secondary event) {
		if (event.getTargetEntity() instanceof ItemFrame) {
			Optional<Player> causePlayer = event.getCause().first(Player.class);

			if (causePlayer.isPresent()) {
				Player player = causePlayer.get();
				Optional<ItemStack> optionalItem = player.getItemInHand(HandTypes.MAIN_HAND);

				if (optionalItem.isPresent()) {
					ItemStack heldItem = optionalItem.get();

					if (heldItem.getItem().getName().equalsIgnoreCase("minecraft:paper")) {
						if (heldItem.get(SafariTokenData.class).isPresent() || heldItem.get(EVTrainTokenData.class).isPresent()
								|| heldItem.get(GymTokenData.class).isPresent() || heldItem.get(EventTokenData.class).isPresent()) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	/** Item interaction */
	@Listener
	public void onItemInteraction(InteractItemEvent event) {
		Optional<Player> causePlayer = event.getCause().first(Player.class);
		Player player = causePlayer.get();

		if (!((event instanceof InteractBlockEvent)
				|| (event instanceof InteractInventoryEvent)
				|| (event instanceof InteractItemEvent.Primary))) {

			if (player != null) {
				UUID playerUUID = player.getUniqueId();

				// Token interaction
				if (!tokenInteractionCooldown.contains(playerUUID)) {
					Sponge.getScheduler().createTaskBuilder().execute(new Runnable() {
						public void run() {
							TokenInteraction.tokenInteraction(player);

							tokenInteractionCooldown.add(playerUUID);

							Sponge.getScheduler().createTaskBuilder().execute(new Runnable() {
								public void run() {
									if (tokenInteractionCooldown.contains(playerUUID)) {
										tokenInteractionCooldown.remove(playerUUID);
									}
								}
							}).delay(500, TimeUnit.MILLISECONDS).name("TokenInteractionCooldownRemoval:" + playerUUID.toString()).submit(plugin);
						}

					}).delay(500, TimeUnit.MILLISECONDS).name("TokenInteractionCooldownRemoval:" + playerUUID.toString()).submit(plugin);
				}


				// No interactions with restricted items in their restricted zones.
				for (HandType hand : Arrays.asList(HandTypes.MAIN_HAND, HandTypes.OFF_HAND)) {
					if (player.getItemInHand(hand).isPresent()) {

						String itemInHandName = player.getItemInHand(hand).get().getItem().getName();
						EnumRestrictedUseItems itemEnum = EnumRestrictedUseItems.getRestrictedUseItems(itemInHandName);
						if (itemEnum != null) {
							if (!itemEnum.useAnywhere
									|| (CheckInPrison.checkPlayerInPrison(player) && !itemEnum.useInPrison)
									|| (CheckInSafari.inAnySafari(player) && !itemEnum.useInSafari)) {
								player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(cannotUserHere));
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	/** Interact with stuff e.g trade machine for trade system, fishing rods */
	@Listener
	public void onBlockInteraction(InteractBlockEvent event) {
		Optional<Player> causePlayer = event.getCause().first(Player.class);
		Player player = causePlayer.get();

		if (player != null) {

			BlockState state = event.getTargetBlock().getState();
			// Trade system to monitor how interactions with trade machines work.
			if (state.getType().getName().equalsIgnoreCase("pixelmon:trade_machine")) {

				if (player.hasPermission("prisontools.trade.allow")) { // If player has permission to trade globally

					if ((CheckInPrison.checkPlayerInPrison(player) && player.getLocation().getBlockY() > 60) || CheckInSafari.inAnySafari(player)) { // If player is selecting trade system trade machine

						if (player.hasPermission("prisontools.trade.4")) { // Free players.
							Teleport.PrisonTeleport(player, 6.5, 69, -5022.5, "West");
						} else if (player.hasPermission("prisontools.trade.3")) { // I-L rank players.
							Teleport.PrisonTeleport(player, 194.5, 58, 0.5, "East");
						} else if (player.hasPermission("prisontools.trade.2")) { // E-H rank players.
							Teleport.PrisonTeleport(player, 0.5, 58, -193.5, "North");
						} else if (player.hasPermission("prisontools.trade.1")) { // A-D rank players.
							Teleport.PrisonTeleport(player, -193.5, 58, 0.5, "West");
						} else {
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(unknownTradeGroup));
						}

						player.sendMessage(ChatTypes.ACTION_BAR, (TextSerializers.FORMATTING_CODE.deserialize(restrictedTradeMachine)));
						event.setCancelled(true);
					}
				} else {
					player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(noTradePermission));
					event.setCancelled(true);
				}
			} else if (state.getName().contains("minecraft:anvil[damage=")) {

				if (CheckInPrison.checkPlayerInPrison(player)) {
					String freshAnvilBlockStateString = state.getName().toString().replace("damage=1", "damage=0").replace("damage=2", "damage=0");

					DataContainer dataContainer = new MemoryDataContainer();
					DataContainer cont = dataContainer.set(DataQuery.of("BlockState"), freshAnvilBlockStateString);
					BlockState freshAnvil = BlockState.builder().build(cont).get();

					event.getTargetBlock().getLocation().get().setBlock(freshAnvil);
				}
			}
		}
	}

	private EconomyService economyService;
	private static PermissionService permissionService;

	@Listener
	public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
		if (event.getService().equals(EconomyService.class)) {
			economyService = (EconomyService) event.getNewProviderRegistration().getProvider();
		}

		if (event.getService().equals(PermissionService.class)) {
			permissionService = (PermissionService) event.getNewProviderRegistration().getProvider();
		}
	}

	public static PermissionService getPermissionService () {
		return permissionService;
	}

	/** Quick Join safari post */
	@Listener
	public void onSafariButtonPress(InteractBlockEvent event) {

		Optional<Player> causePlayer = event.getCause().first(Player.class);
		Player player = causePlayer.get();

		if (player != null) { // Ensure player is cause.
			BlockSnapshot targetBlock = event.getTargetBlock();

			SafariButtonPress.buttonPress(economyService, player, targetBlock);
		}
	}


	ArrayList<String> blocksBannedInPrison = new ArrayList<>(Arrays.asList("pixelmon:trade_machine"));
	ArrayList<String> blocksBannedEverywhere = new ArrayList<>(Arrays.asList("minecraft:unpowered_comparator", "pixelmon:poke_gift"));

	/** Placing items e.g trade machines to avoid trade system (Mines) */
	@Listener
	public void onBlockPlace(ChangeBlockEvent.Place event, @Root Player player) {

		for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {

			String transactionBlockString = transaction.getFinal().getState().getType().getId().toLowerCase();

			if (blocksBannedInPrison.contains(transactionBlockString)) {
				if (CheckInPrison.checkPlayerInPrison(player) || CheckInSafari.inAnySafari(player)) {
					if (player.hasPermission("prisontools.bypass")) {
						player.sendMessage(ChatTypes.ACTION_BAR, TextSerializers.FORMATTING_CODE.deserialize(restrictedBlockPlaceBypass));
						return;
					} else {
						player.sendMessage(ChatTypes.ACTION_BAR, TextSerializers.FORMATTING_CODE.deserialize(restrictedBlockPlace));
						event.setCancelled(true);
						return;
					}
				}
			} else if (blocksBannedEverywhere.contains(transactionBlockString)) {
				//players were able to use comparators to duplicate items, so now disabling them from being placed.
				player.sendMessage(ChatTypes.ACTION_BAR, TextSerializers.FORMATTING_CODE.deserialize(bannedBlockPlace));
				event.setCancelled(true);
				return;
			}
		}
	}

	/** Trapdoor fiddle protection */
	@Listener
	public void onBlockModify(ChangeBlockEvent.Modify event, @Root Player player) {

		for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
			if ((transaction.getFinal().getState().getType().equals(BlockTypes.TRAPDOOR)) && !player.hasPermission("prisontools.trapdoor")) {
				if (CheckInPrison.checkPlayerInPrison(player) || CheckInSafari.inAnySafari(player)) {

					if (player.hasPermission("prisontools.bypass")) {
						player.sendMessage(ChatTypes.ACTION_BAR, TextSerializers.FORMATTING_CODE.deserialize(trapdoorBypass));
						return;
					} else {
						player.sendMessage(ChatTypes.ACTION_BAR, TextSerializers.FORMATTING_CODE.deserialize(trapdoorError));
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}


	public void cycle() {

		UptimeHandler.uptimeIncrease();

		for (Player player : Sponge.getServer().getOnlinePlayers()) {

			if (player != null) {
				if (!player.hasPermission("prisontools.trade.4")) {
					if (!CheckInPrison.checkPlayerInPrison(player) && !CheckInSafari.inAnySafari(player)) {
						Teleport.Spawn(player);
					}
				}

				// Safari tasks
				ChangeSafariTime.reduceAllSafariOne(player); // Decrease safari time
				CheckInSafari.checkPlayerHasSafariTime(player); // Remind of remaining time and kick if no time
				checkPlayerInSafariPadding(player); // Kick out of safari padding

				// EV Train tasks
				ChangeEVTrainTime.reduceAllEVTrainOne(player); // Decrease evtrain time
				CheckInEVTrain.checkPlayerHasEVTrainTime(player); // Remind of remaining time and kick if no time

				// Gym tasks
				GymCooldown.decreaseCooldown(player); // Decrease gym cooldown time
				CheckInGym.checkPlayerHasPermission(player); // Kick out of gym if in gym with no permission

				// Event tasks
				CheckInEvent.checkPlayerHasPermission(player); // Kick out of event if no permission
			}
		}
	}

	public void cycleSaveAsync() {
		for (Player player : Sponge.getServer().getOnlinePlayers()) {
			if (player != null) {
				PlayerMysqlDataInteraction.updateDatabaseFromMemory(player.getUniqueId().toString());
			}
		}
	}
}