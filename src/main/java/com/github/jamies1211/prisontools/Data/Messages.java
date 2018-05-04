package com.github.jamies1211.prisontools.Data;

/**
 * Created by Jamie on 20-May-16.
 */
public class Messages {

	/** General messages */
	public static final String startup = "Prison tools has started.";
	public static final String newConfigFile = "Created new configuration file as none detected!";
	public static final String loadedConfigFile = "Detected configuration file and loaded";
	public static final String configLoadError = "The default configuration could not be loaded or created!";
	public static final String playerUUIDPrefix = "Player with uuid:";
	public static final String playerSameUsername = "was already in config with same username";
	public static final String playerNewUsername = "was already in config but has changed their username since last time";
	public static final String infoPrefix = "&9[Info]: ";
	public static final String otherNameList = "%player% has been known as: ";
	public static final String addedToConfig = "added to config";
	public static final String spawnProtection = "&e&lSpawn protection prevented you taking damage.";
	public static final String restrictedTradeMachine = "&eThis is in a restricted area so have been teleported to your ranks trade area";
	public static final String unknownTradeGroup = "&&c&l[Error] &r&cYou have no detailed permissions for trading. Please contact a member of staff";
	public static final String noTradePermission = "&c&l[Error] &r&cYou have lost your privileges to trade pokemon via trade machine";
	public static final String restrictedBlockPlaceBypass = "&a&l[Bypass] &r&eYou have permissions allowing you to bypass protection";
	public static final String restrictedBlockPlace = "&c&l[Error] &r&cYou cannot place this here";
	public static final String bannedBlockPlace = "&c&l[Error] &r&cThis block has been banned from use due to exploits";
	public static final String trapdoorBypass = "&a&l[Bypass] &r&eYou can fiddle with trapdoors as you have bypass permissions";
	public static final String trapdoorError = "&c&l[Error] &r&cNo trapdoor fiddling";
	public static final String invalidToken = "&c&l[Error] &r&cThis is not a valid token";
	public static final String tokenGenerateSafari = "&eYou successfully generated a token for %time% in safari:%safari%";
	public static final String tokenGenerateGym = "&eYou successfully generated a token for gym:%gym%";
	public static final String tokenGenerateEVTrain = "&eYou successfully generated a token for %time% in EV area:%area%";
	public static final String tokenGenerateEvent = "&eYou successfully generated a token for %eventName%";
	public static final String restrictedItemSmuggle = "&cYou were carrying restricted items so have been put at spawn. Items:%restricted%";
	public static final String cannotUserHere = "&cYou cannot use that item here...";
	public static final String firstJoinWelcome = "&9Welcome to our server %player%!";
	public static final String firstJoinSubWelcome = "&ePlease ask for help if you need anything";
	public static final String returnJoinMessage = "&9Welcome back %player%!";
	public static final String returnJoinSubMessage1 = "&eNice to see you again!";
	public static final String returnJoinSubMessage2 = "&ePlease ask if you need any help";
	public static final String returnJoinSubMessage3 = "&eIf you have any ideas for improvements please let us know";
	public static final String featureDisabled = "&cSorry that feature is disabled on this server.";
	public static final String featureDisabledRestart = "&cSorry that feature is disabled until after an upcoming server restart.";
	public static final String rankFixRequest = "&ePlayer &9%player% &ehas requested rank fix";

	/** Command messages */
	public static final String pluginReload = "&9Prison tools reloaded";
	public static final String pluginSave = "&9Prison tools saved";
	public static final String playerCommandError = "&cThis command must be run by a player";

	public static final String generalHelpCommand = "&aCommands available: /safari, /gym, /prisontools";

	public static final String addedOldName = "&9You have successfully added %oldName% as the oldest name for %player%";

	public static final String invalidRestrictType = "&9That type is invalid. Valid types:%types%";
	public static final String restrictedItemAdd = "&9You have added %item% to the list of restricted items for %type%";

	public static final String rebootTimerMessage = "&cScheduled server reboot in %time%";
	public static final String rebootTimerMessageCloseWarning = "&cScheduled server reboot in %time%, gyms and events will close in %closeTime%";
	public static final String rebootTimerMessageCloseInfo = "&cScheduled server reboot in %time%. Gyms and events have been shutdown until after reboot";

	/** Safari related messages */
	public static final String safariPrefix = "&a&l[Safari]&r ";
	public static final String safariRestrictedItems = "&ePlease note you will not be allowed to enter safari carrying:%restricted%";
	public static final String safariRestrictedItemsJoin = "&cYou can't enter safari carrying:%restricted%";
	public static final String safariTimeEnd = "&aYour time in safari:%safari% has ended.";
	public static final String safariTimeYouChange = "&aYou have changed %player%'s time in safari:%safari% from %oldTime to %newTime%";
	public static final String safariTimeBeenChanged = "&aYour time in safari:%safari% has been changed from %oldTime% to %newTime%";
	public static final String safariTimeYouSet = "&aYou have set %player%'s time in safari:%safari% to %newTime%";
	public static final String safariTimeBeenSet = "&aYour time in safari:%safari% has been set to %newTime%";
	public static final String safariNoAccess = "&cYour rank does not have access to safari:%safari%";
	public static final String safariNegativeTime = "&c&l[Error]&r &cThe end result would be negative time";
	public static final String safariNotExist = "&esafari:%safari% does not exist.";
	public static final String safariTargetNoAccess = "&c%player% does not have access to safari:%safari%";
	public static final String safariNoTime = "&eYou have no time for safari:%safari% at the moment so you can't go there";
	public static final String safariTimeReminder = "&c&l[Reminder]:&r &eYou currently have only &c%remainingTime% &eleft in safari:%safari%";
	public static final String safariTimeCheck = "&eYou currently have %remainingTime% left in safari:%safari%";
	public static final String safariTokenActivation = "&eYou have activated a %tokenTime% mins token for safari:%safari%";
	public static final String safariQuickJoin = "&eYou have been charged %price% for %time% in safari:%safari% using the safari quick join button";
	public static final String safariQuickJoinNoMoney = "&cYou do not have enough money. This costs %price%";

	public static final String safariPaddingKick = "&cYou are not allowed to enter, exit or otherwise be in this area";
	public static final String safariPaddingBypass = "&cYou are in the outside area of a safari zone but are bypassing it";

	public static final String safariHelpAccess = "&eYou have access to %safariList%";
	public static final String safariHelpTime = "&eTo show time remaining in safari do: &9/safari time [safari number]";
	public static final String safariHelpJoin = "&eTo go to a safari zone do: &9/safari join [safari number]";
	public static final String safariHelpDetails = "&eTo get time in a safari zone you must buy a token for time in that zone, and right " +
			"click the token when held in your hand. While you are online that time will elapse. You can have time" +
			" for multiple safari zones at the same time but all will elapse at the normal rate";

	/** Gym related messages */
	public static final String gymPrefix = "&9&l[Gyms]&r ";
	public static final String gymRankInvalid = "&c&l[Error]&r&c Invalid gym rank";
	public static final String gymStatInvalid = "&cThat gym does not exist. List of gyms:%gyms%";
	public static final String gymCooldown = "&eYou can only participate in a gym battle every 30mins. You have to wait %cooldown% before you can take part in another gym battle";
	public static final String gymTokenActivation = "&eYou have activated a token for the %gym% rank gym leader";
	public static final String gymNotExist = "&eGym:%gym% does not exist.";
	public static final String gymStats = "&9%player%&e stats against %gym% gym: %win%%winForm%, %loss%%lossForm% %percentageAddon%";
	public static final String gymPlayerWinFirstRank = "&9%player%&e has defeated the %gym% for the first time and ranked up to %rank%";
	public static final String gymPlayerWinFirstNoRank = "&9%player%&e has defeated the %gym% for the first time but already had the reward rank. Please report this to a member of staff";
	public static final String gymPlayerWinAgainRank = "&9%player%&e has defeated the %gym% rank gym leader again and ranked up to %rank%";
	public static final String gymPlayerWinAgainNoRank = "&9%player%&e has defeated the %gym% rank gym leader again";
	public static final String gymPlayerLoss = "&9%player%&e has been defeated by the %gym% rank gym leader";
	public static final String gymPlayerCheat = "&9%player%&e has cheated on the %gym% rank gym leader";
	public static final String gymChallenge = "&a%player% has just challenged the %gym% gym leader!";
	public static final String gymGoodLuck = "&eGood luck!";
	public static final String gymRankAccess = "&cYour rank does not have access to challenge the %gym% rank gym leader";
	public static final String gymErrorToken = "&cThis is a valid token for %gym% however that gym does not exist";
	public static final String gymNotAllowed = "&cYou should not be in this area so have been moved to spawn";
	public static final String gymExit = "&e%player% has left the gym.";
	public static final String gymCompleteAll = "&9%player% completed all the gyms previously.";
	public static final String gymsDisabled = "&9Gyms are disabled at this time. Please try again later.";
	public static final String cooldownAlreadyClear= "&9Cooldown for %player% is already 0";
	public static final String cooldownSet = "&9Cooldown for %player% set to 0";
	public static final String cooldownCleared = "&9Your gym cooldown has been set to 0";

	public static final String gymBannedItems = "&cYou are not permitted inside the gym carrying: %restricted%";
	public static final String gymHelpAccess = "&eYou can currently challenge %allowedGyms%";
	public static final String gymHelpChallenge = "&eTo go to a gym buy a gym token for the gym you want at that ranks" +
			" gym shop then activate the token as usual";

	/** Event related messages */
	public static final String eventPrefix = "&9&l[SpecialEvents]&r ";
	public static final String eventNeverEndedStage = "has never completed this even stage so has no stat line against this stage";
	public static final String eventStageInvalid = "&c&l[Error]&r&c Invalid event stage";
	public static final String eventTypeInvalid = "&c&l[Error]&r&c Invalid event type";
	public static final String eventChallenge = "&e%event% has been challenged by %player%";
	public static final String eventGoodLuck = "&aYou have challenged %event%";
	public static final String eventBannedItems = "&cYou are not allowed to challenge %event% carrying: %restricted%";
	public static final String eventNoPermission = "&cYou do not have permission to challenge %event%";
	public static final String eventInvalidEvent = "&cThis token is for an invalid event. Please talk to a member of staff and keep this token";
	public static final String eventExit = "&e%player% has left %event%.";
	public static final String eventNotAllowed = "&cYou should not be in this area so have been moved to spawn";
	public static final String eventNotExist = "&eevent:%event% &edoes not exist.";
	public static final String eventStats = "&estats against";
	public static final String eventPlayerWin = "&ehas defeated";
	public static final String eventPlayerCheat = "&ehas cheated on";
	public static final String eventPlayerWinFirst = "for the first time!";
	public static final String eventPlayerWinFirstRank = "for the first time and has recieved the %newRank% rank!";
	public static final String eventPlayerWinAgain = "challenge again!!!";
	public static final String eventPlayerStageCompleteFirst = "for the first time!";
	public static final String eventPlayerStageCompleteAgain = "again!";
	public static final String eventPlayerLoss = "&ehas been defeated by";

	public static final String eventHelpChallenge = "&eWhen you are free rank there is a few special events you can take part in. To take part " +
			"go to the event start and purchase a token activating the token as normal.";

	/** EV Train related message */
	public static final String evTrainPrefix = "&9&l[EV-Training]&r ";
	public static final String evTrainRestrictedItems = "&ePlease note you will not be allowed to enter the EV training area carrying: ";
	public static final String evTrainRestrictedItemsJoin = "&cYou can't enter the EV training area carrying: %restricted%";
	public static final String evTrainTimeEnd = "&aYour time in EV area:%area% has ended.";
	public static final String evTrainTimeYouChange = "&aYou have changed %player%'s time in EV area:%area% from %oldTime% to %newTime%";
	public static final String evTrainTimeBeenChanged = "&aYour time in EV area:%area% has been changed from %oldTime% to %newTime%";
	public static final String evTrainTimeYouSet = "&aYou have set %player%'s time in EV area:%area% to %newTime%";
	public static final String evTrainTimeBeenSet = "&aYour time in EV area:%area% has been set to %newTime%";
	public static final String evTrainNoAccess = "&cYour rank does not have access to EV area:%area%";
	public static final String evTrainTimeReminder = "&c&l[Reminder]:&r &eYou currently have only &c%remainingTime% &eleft in EV area:%area%";
	public static final String evTrainNoTime = "&eYou have no time for EV area:%area% at the moment so you can't go there";
	public static final String evTrainTimeCheck = "&eYou currently have %remainingTime% left in EV area:%area%";
	public static final String evTrainTokenActivation = "&eYou have activated a %time% mins token for EV area:%area%";
	public static final String evTrainNegativeTime = "&c&l[Error]&r &cThe end result would be negative time";
	public static final String evTrainNotExist = "&eEV area:%area% does not exist.";
	public static final String evTrainTargetNoAccess = "%player% does not have access to EV area:%area%";

	public static final String evTrainHelpAccess = "&eYou have access to ";
	public static final String evTrainHelpTime = "&eTo show time remaining in an EV area do: &9/evtrain time [EV area number]";
	public static final String evTrainHelpJoin = "&eTo go to a EV area do: &9/evtrain join [EV area number]";
	public static final String evTrainHelpDetails = "&eTo get time in a EV area you must buy a token for time in that zone, and right " +
			"click the token when held in your hand. While you are online that time will elapse. You can have time" +
			" for multiple EV areas at the same time but all will elapse at the normal rate";


	public static final String pokehealCommand = "pokeheal %player%";
	public static final String freeKitGiveCommand = "kit give free %player%";
	public static final String freeServerSync = "sync free %command%";
	public static final String memberServerSync = "sync member %command%";
	//public static final String rankup = "pex user %playerData% parent add %newRank%";
	//public static final String derank = "pex user %playerData% parent remove %oldRank%";
}
