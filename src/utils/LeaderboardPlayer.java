package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class LeaderboardPlayer {
	
	public int wins;
	public int walls;
	public int qualification;
	public int finals;
	public int total;
	public float overall;
	public String name;
	public String discord;
	public String uuid;
	
	public LeaderboardPlayer(int wins, int walls, int qualification, int finals, int total, float overall, String name, String discord, String uuid) {
		this.wins = wins;
		this.walls = walls;
		this.qualification = qualification;
		this.finals = finals;
		this.total = total;
		this.overall = overall;
		this.name = name;
		this.discord = discord;
		this.uuid = uuid;
	}

	public LeaderboardPlayer(String data, boolean fromAPI) {
		this.uuid = fromAPI ? API.getUUID(data) : Stats.getUUID(data);
		this.name = fromAPI ? API.getName(data) : Stats.getName(data);
		this.wins = fromAPI ? API.getWinsToInt(data) : Stats.getWinsToInt(data);
		this.walls = fromAPI ? API.getWallsToInt(data) : Stats.getWallsToInt(data);
		this.qualification = fromAPI ? API.getQualificationToInt(data) : Stats.getQualificationToInt(data);
		this.finals = fromAPI ? API.getFinalsToInt(data) : Stats.getFinalsToInt(data);
		this.total = fromAPI ? API.getTotalToInt(data) : Stats.getTotalToInt(data);
		try {this.discord = Stats.getDiscordId(data);} catch(Exception ignored) {}
	}
	
	
	/**
	 * Return player wins
	 */
	public String getWins() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = wins;
		return (formatter.format(value));
	}
	
	/**
	 * Return player walls cleared
	 */
	public String getWalls() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = walls;
		return (formatter.format(value));
	}
	
	/**
	 * Return player qualification score
	 */
	public String getQualification() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int	value = qualification;
		return (formatter.format(value));
	}
	
	/**
	 * Return player final score
	 */
	public String getFinals() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = finals;
		return (formatter.format(value));
	}
	
	/**
	 * Return player total score
	 */
	public String getTotal() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int q = qualification;
		int f = finals;
		return (formatter.format(q + f));
	}
	
}
