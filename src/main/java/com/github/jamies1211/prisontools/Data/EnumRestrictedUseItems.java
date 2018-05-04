package com.github.jamies1211.prisontools.Data;

/**
 * Created by Jamie on 03-Jul-16.
 */
public enum EnumRestrictedUseItems {

	//EnumId             Item String                Use anywhere    Prison use      Safari use
	FishingRod      ("minecraft:fishing_rod"         , true         , false         , false),
	OldRod          ("pixelmon:old_rod"             , true         , false         , false),
	GoodRod         ("pixelmon:good_rod"            , true         , false         , false),
	SuperRod        ("pixelmon:super_rod"           , true         , false         , false),
	MineCart        ("minecraft:minecart"           , true         , false         , false),
	NormalBoat      ("minecraft:boat"               , true         , false         , false),
	SpruceBoat      ("minecraft:spruce_boat"        , true         , false         , false),
	BirchBoat       ("minecraft:birch_boat"         , true         , false         , false),
	JungleBoat      ("minecraft:jungle_boat"        , true         , false         , false),
	AcaciaBoat      ("minecraft:acacia_boat"        , true         , false         , false),
	DarkOakBoat     ("minecraft:dark_oak_boat"      , true         , false         , false),
	ChorusFruit     ("minecraft:chorus_fruit"       , true         , false         , false),
	EnderPearl      ("minecraft:ender_pearl"        , true         , false         , false);


	public final String itemString;
	public final boolean useAnywhere;
	public final boolean useInPrison;
	public final boolean useInSafari;

	EnumRestrictedUseItems(String itemString, boolean useAnywhere, boolean useInPrison, boolean useInSafari) {
		this.itemString = itemString;
		this.useAnywhere = useAnywhere;
		this.useInPrison = useInPrison;
		this.useInSafari = useInSafari;
	}

	public static EnumRestrictedUseItems getRestrictedUseItems(String enumID) {
		for (EnumRestrictedUseItems restrictedItemUseEnum : values()) {
			if (restrictedItemUseEnum.itemString.equalsIgnoreCase(enumID)) {
				return restrictedItemUseEnum;
			}
		}
		return null;
	}
}
