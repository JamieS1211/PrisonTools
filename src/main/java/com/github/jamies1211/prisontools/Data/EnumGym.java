package com.github.jamies1211.prisontools.Data;

/**
 * Created by Jamie on 03-Jul-16.
 */
public enum EnumGym {

	A       ("A Gym"        , "gymA"        , "A"           , "B"           , "prisontools.gym.challenge.A"         , false),
	B       ("B Gym"        , "gymB"        , "B"           , "C"           , "prisontools.gym.challenge.B"         , false),
	C       ("C Gym"        , "gymC"        , "C"           , "D"           , "prisontools.gym.challenge.C"         , false),
	D       ("D Gym"        , "gymD"        , "D"           , "E"           , "prisontools.gym.challenge.D"         , false),
	E       ("E Gym"        , "gymE"        , "E"           , "F"           , "prisontools.gym.challenge.E"         , false),
	F       ("F Gym"        , "gymF"        , "F"           , "G"           , "prisontools.gym.challenge.F"         , false),
	G       ("G Gym"        , "gymG"        , "G"           , "H"           , "prisontools.gym.challenge.G"         , false),
	H       ("H Gym"        , "gymH"        , "H"           , "I"           , "prisontools.gym.challenge.H"         , false),
	I       ("I Gym"        , "gymI"        , "I"           , "J"           , "prisontools.gym.challenge.I"         , false),
	J       ("J Gym"        , "gymJ"        , "J"           , "K"           , "prisontools.gym.challenge.J"         , false),
	K       ("K Gym"        , "gymK"        , "K"           , "L"           , "prisontools.gym.challenge.K"         , false),
	L       ("L Gym"        , "gymL"        , "L"           , "Free"        , "prisontools.gym.challenge.L"         , false),
	Donator1("Donator1 Gym" , "gymDonator1" , "Donator"     , "Donator2"    , "prisontools.gym.challenge.Donator1"  , true),
	Donator2("Donator2 Gym" , "gymDonator2" , "Donator2"    , "Donator3"    , "prisontools.gym.challenge.Donator2"  , true),
	Donator3("Donator3 Gym" , "gymDonator3" , "Donator3"    , "Donator4"    , "prisontools.gym.challenge.Donator3"  , true),
	Donator4("Donator4 Gym" , "gymDonator4" , "Donator4"    , "Donator5"    , "prisontools.gym.challenge.Donator4"  , true),
	Donator5("Donator5 Gym" , "gymDonator5" , "Donator5"    , "Donator6"    , "prisontools.gym.challenge.Donator5"  , true),
	Donator6("Donator6 Gym" , "gymDonator6" , "Donator6"    , "Donator7"    , "prisontools.gym.challenge.Donator6"  , true);


	public final String gymName;
	public final String gymNodeIdentifier;
	public final String currentRank;
	public final String nextRank;
	public final String permissionNode;
	public final boolean membersFeature;

	EnumGym(String gymName, String gymNodeIdentifier, String currentRank, String nextRank, String permissionNode, boolean membersFeature) {
		this.gymName = gymName;
		this.gymNodeIdentifier = gymNodeIdentifier;
		this.currentRank = currentRank;
		this.nextRank = nextRank;
		this.permissionNode = permissionNode;
		this.membersFeature = membersFeature;
	}

	public static EnumGym getGymData(String gymName) {
		for (EnumGym gymEnum : values()) {
			if (gymEnum.name().equalsIgnoreCase(gymName)) {
				return gymEnum;
			}
		}
		return null;
	}
}
