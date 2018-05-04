package com.github.jamies1211.prisontools.ActionsGeneral;

/**
 * Created by Jamie on 16-May-16.
 */
public class SecondsToString {

	public static String secondsToString(int totalSeconds) {

		final int seconds = totalSeconds % 60; // Seconds more then int minutes.
		final int minutes = ((totalSeconds - seconds) / 60) % 60; // Minutes rounded down.
		final int hours = ((((totalSeconds - seconds) / 60) - minutes) / 60) % 24; // Hours rounded down.
		final int days = ((((((totalSeconds - seconds) / 60) - minutes) / 60) - hours) / 24) % 7; // Days rounded down.
		final int weeks = ((((((((totalSeconds - seconds) / 60) - minutes) / 60) - hours) / 24) - days) / 7); // Days rounded down.


		String secondUnit = "seconds";
		String minuteUnit = "minutes";
		String hourUnit = "hours";
		String dayUnit = "days";
		String weekUnit = "weeks";

		String secondSpacing = " ";
		String minuteSpacing = " ";
		String hoursSpacing = " ";
		String daySpacing = " ";

		String secondsString = Integer.toString(seconds);
		String minutesString = Integer.toString(minutes);
		String hoursString = Integer.toString(hours);
		String daysString = Integer.toString(days);
		String weeksString = Integer.toString(weeks);

		if (seconds == 1) { // Removes plural if not needed.
			secondUnit = "second";
		} else if (seconds == 0 && (weeks != 0 || days != 0|| hours!= 0 || minutes != 0)) {
			secondsString = "";
			secondUnit = "";
			secondSpacing = "";
		}

		if (minutes == 1) { // Removes plural if not needed.
			minuteUnit = "minute";
		} else if (minutes == 0) {
			minutesString = "";
			minuteUnit = "";
			minuteSpacing = "";
		}

		if (hours == 1) { // Removes plural if not needed.
			hourUnit = "hour";
		} else if (hours == 0) {
			hoursString = "";
			hourUnit = "";
			hoursSpacing = "";
		}

		if (days == 1) { // Removes plural if not needed.
			dayUnit = "day";
		} else if (days == 0) {
			daysString = "";
			dayUnit = "";
			daySpacing = "";
		}

		if (weeks == 1) { // Removes plural if not needed.
			weekUnit = "week";
		} else if (weeks == 0) {
			weeksString = "";
			weekUnit = "";
		}

		return weeksString + weekUnit +
				daySpacing + daysString + dayUnit +
				hoursSpacing + hoursString + hourUnit +
				minuteSpacing + minutesString + minuteUnit +
				secondSpacing + secondsString + secondUnit;
	}
}
