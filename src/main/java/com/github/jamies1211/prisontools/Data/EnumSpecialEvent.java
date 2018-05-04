package com.github.jamies1211.prisontools.Data;

/**
 * Created by Jamie on 03-Jul-16.
 */
public enum EnumSpecialEvent {

	//HumanReadableName, Stages, EventNodeIdentifier, KitName, RankReward, OldRank, NewRank
	BATTLETOWER ("The Battle Tower" , 10, "BATTLETOWER" , "BATTLETOWER" , true  , "Free"    , "Legend"),
	SKYTOWER    ("The Sky Tower"    , 8 , "SKYTOWER"    , "SKYTOWER"    , false , "EMPTY"   , "EMPTY"),
	AGATHA      ("Agatha's maze"    , 1 , "AGATHA"      , "AGATHA"      , false , "EMPTY"   , "EMPTY"),
	LORELEI     ("Lorelei's ship"   , 2 , "LORELEI"     , "LORELEI"     , false , "EMPTY"   , "EMPTY");

	public final String eventName;
	public final int stages;
	public final String eventNodeIdentifier;
	public final String kitName;
	public final boolean rankGive;
	public final String oldRank;
	public final String rewardRank;

	EnumSpecialEvent(String eventName, int stages, String eventNodeIdentifier, String kitName, boolean rankGive, String oldRank, String rewardRank) {
		this.eventName = eventName;
		this.stages = stages;
		this.eventNodeIdentifier = eventNodeIdentifier;
		this.kitName = kitName;
		this.rankGive = rankGive;
		this.oldRank = oldRank;
		this.rewardRank = rewardRank;
	}

	public static EnumSpecialEvent getSpecialEventData(String event) {
		for (EnumSpecialEvent ev : values()) {
			if (ev.name().equalsIgnoreCase(event)) {
				return ev;
			}
		}
		return null;
	}
}
