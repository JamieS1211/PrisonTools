package com.github.jamies1211.prisontools.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jamie on 25-Jun-16.
 */
public class Lists {

	public static final List<String> centralRestrictedTypeList = new ArrayList<>(Arrays.asList(
			"SAFARI", "GYM", "EVTRAIN", "BATTLETOWER", "SKYTOWER", "AGATHA", "LORELEI"
	));

	public static HashMap<Integer, String> getMainRankIDs () {
		HashMap<Integer, String> mainRankMap = new HashMap<>();

		mainRankMap.put(0, "default");
		mainRankMap.put(1, "B");
		mainRankMap.put(2, "C");
		mainRankMap.put(3, "D");
		mainRankMap.put(4, "E");
		mainRankMap.put(5, "F");
		mainRankMap.put(6, "G");
		mainRankMap.put(7, "H");
		mainRankMap.put(8, "I");
		mainRankMap.put(9, "J");
		mainRankMap.put(10, "K");
		mainRankMap.put(11, "L");
		mainRankMap.put(12, "Free");
		mainRankMap.put(13, "Legend");

		return mainRankMap;
	}

	public static HashMap<Integer, String> getDonatorRankIDs () {
		HashMap<Integer, String> mainRankMap = new HashMap<>();

		mainRankMap.put(0, "Donator");
		mainRankMap.put(1, "Donator2");
		mainRankMap.put(2, "Donator3");
		mainRankMap.put(3, "Donator4");
		mainRankMap.put(4, "Donator5");
		mainRankMap.put(5, "Donator6");
		mainRankMap.put(6, "Donator7");

		return mainRankMap;
	}
}
