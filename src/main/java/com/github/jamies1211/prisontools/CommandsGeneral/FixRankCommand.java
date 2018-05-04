package com.github.jamies1211.prisontools.CommandsGeneral;

import com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction;
import com.github.jamies1211.prisontools.ActionsGeneral.PlayerRankTools;
import com.github.jamies1211.prisontools.ActionsGeneral.RankChanging;
import com.github.jamies1211.prisontools.Data.EnumGym;
import com.github.jamies1211.prisontools.Data.Lists;
import com.github.jamies1211.prisontools.PrisonTools;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSuperSubNodeExists;
import static com.github.jamies1211.prisontools.ActionsConfig.PlayerConfigDataInteraction.getPlayerSuperSubNodeInt;
import static com.github.jamies1211.prisontools.Data.Messages.rankFixRequest;

public class FixRankCommand implements CommandExecutor {

    //this doesn't account for donator gyms and will need updating at that time
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        //make sure this is a player trying to fix their own rank
        if (src instanceof Player) {
            //get player info and config info
            Player player = (Player) src;
            String configPlayerUUID = player.getUniqueId().toString();
            String playerName = player.getName();
            Boolean playerIsDonator = player.hasPermission("synctools.sync");

            //TODO move messages to messages.java
            player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(rankFixRequest.replace("%player%", player.getName())));

            //Remove all possible ranks from player if they have any
            for (String rank : PlayerRankTools.getPlayerRankList(configPlayerUUID)) {
                if (!rank.equalsIgnoreCase("default") && !rank.equalsIgnoreCase("communityhelper")) {
                    RankChanging.removePlayerRank(configPlayerUUID, playerName, rank);
                }
            }

            HashMap<Integer, String> mainRankMap = Lists.getMainRankIDs();
            HashMap<Integer, String> donatorRankMap = Lists.getDonatorRankIDs();

            int mainRankID = 0;
            int donatorRankID = 0;


            for (int mainIterator = 0; mainIterator < (mainRankMap.size() - 1); mainIterator++) {
                if (mainIterator < 12) {
                    String gymRank = mainRankMap.get(mainIterator);

                    if (gymRank.equalsIgnoreCase("default")) {
                        gymRank = "A";
                    }

                    String gymNodeIdentifier = EnumGym.getGymData(gymRank).gymNodeIdentifier;

                    if (PlayerConfigDataInteraction.getPlayerSubNodeExists(configPlayerUUID, "gymData", gymNodeIdentifier, "wintrue")) { //check the wintrue variable exists
                        if (PlayerConfigDataInteraction.getPlayerSubNodeInt(configPlayerUUID, "gymData", gymNodeIdentifier, "wintrue") > 0) {
                            mainRankID = mainIterator + 1;
                        } else { // Gym not defeated.
                            break;
                        }
                    } else { // Gym node not found.
                        break;
                    }
                } else {

                    if (mainIterator == 12) {

                        if (getPlayerSuperSubNodeExists(configPlayerUUID, "eventData", "BATTLETOWER", "stage10", "wintrue")) {
                            if (getPlayerSuperSubNodeInt(configPlayerUUID, "eventData", "BATTLETOWER", "stage10", "wintrue") > 0) {
                                mainRankID = mainIterator + 1;
                                break;
                            }
                        }
                    }
                }
            }


            if (playerIsDonator) {
                for (int donatorIterator = 0; donatorIterator < (donatorRankMap.size() - 1); donatorIterator++) {
                    String gymRank = donatorRankMap.get(donatorIterator);

                    if (gymRank.equalsIgnoreCase("donator")) {
                        gymRank = "Donator1";
                    }

                    String gymNodeIdentifier = EnumGym.getGymData(gymRank).gymNodeIdentifier;

                    if (PlayerConfigDataInteraction.getPlayerSubNodeExists(configPlayerUUID, "gymData", gymNodeIdentifier, "wintrue")) { //check the wintrue variable exists
                        if (PlayerConfigDataInteraction.getPlayerSubNodeInt(configPlayerUUID, "gymData", gymNodeIdentifier, "wintrue") > 0) {
                            donatorRankID = donatorIterator + 1;
                        } else { // Gym not defeated.
                            break;
                        }
                    } else { // Gym node not found.
                        break;
                    }
                }
            }


            String newRank = Lists.getMainRankIDs().get(mainRankID);
            String newDonatorRank = Lists.getDonatorRankIDs().get(donatorRankID);


            //TODO move messages to messages.java
            if (!playerIsDonator) { // Not a donator
                player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&ePlayer&9 %player% &eshould be rank &a%rank%,&e based on gyms defeated. "
                        .replace("%player%", player.getName())
                        .replace("%rank%", String.valueOf(newRank))
                ));
            } else {
                player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&ePlayer&9 %player% &eshould be rank &a%rank% (Donator rank: %donatorRank%),&e based on gyms defeated. "
                        .replace("%player%", player.getName())
                        .replace("%rank%", String.valueOf(newRank))
                        .replace("%donatorRank%", String.valueOf(newDonatorRank))
                ));
            }

            player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&e Attempting to apply now. Please relog if incorrect rank is given, then contact Staff if still incorrect."));

            for (Player possibleAdmin : Sponge.getServer().getOnlinePlayers()) {
                if (possibleAdmin.hasPermission("essentialcmds.helpop.receive")) {//TODO move messages to messages.java
                    possibleAdmin.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(("&c[HelpOp Automated] &ePlayer&9 %player% &ehas requested rank fix, they should be rank&a %rank%,&e based on gyms defeated.")
                            .replace("%player%", player.getName())
                            .replace("%rank%", newRank)
                    ));
                }
            }


            //set the new rank in a task to delay.
            Sponge.getScheduler().createTaskBuilder().execute(new Runnable() {
                public void run() {
                    RankChanging.addPlayerRank(configPlayerUUID, playerName, newRank);

                    if (playerIsDonator) {
                        RankChanging.addPlayerRank(configPlayerUUID, playerName, newDonatorRank);
                    }
                }
            }).delay(500, TimeUnit.MILLISECONDS).name("Fixing rank for a player").submit(PrisonTools.plugin);
        }

        return CommandResult.success();
    }
}
