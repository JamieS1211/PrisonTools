package com.github.jamies1211.prisontools.ActionsGeneral;

import com.github.jamies1211.prisontools.PrisonTools;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.SubjectReference;

import java.util.ArrayList;

/**
 * Created by Jamie on 10/01/2017.
 */
public class PlayerRankTools {

	//TODO check over
	public static ArrayList<String> getPlayerRankList (String configPlayerUUID) {
		PermissionService ps = PrisonTools.getPermissionService();

		ArrayList<String> rankList = new ArrayList<>();
		for (SubjectReference parent : ps.getUserSubjects().getSubject(configPlayerUUID).get().getParents()) {
			String rank = parent.getSubjectIdentifier();

			if (!rank.equalsIgnoreCase("default")) {
				rankList.add(rank);
			}
		}

		return rankList;
	}

	//TODO Check over
	public static ArrayList<String> getFullPlayerRankListLowercase(String configPlayerUUID) {
		PermissionService ps = PrisonTools.getPermissionService();

		ArrayList<String> rankList = new ArrayList<>();
		for (SubjectReference parent : ps.getUserSubjects().getSubject(configPlayerUUID).get().getParents()) {
			rankList.add(parent.getSubjectIdentifier().toLowerCase());
		}

		return rankList;
	}

	public static Boolean doesPlayerInheritRank (String configPlayerUUID, String rank) {
		return getFullPlayerRankListLowercase(configPlayerUUID).contains(rank.toLowerCase());
	}
}
