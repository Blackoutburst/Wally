package utils;

import java.io.File;
import java.util.List;

import comparators.PlayerComparatorF;
import comparators.PlayerComparatorOverall;
import comparators.PlayerComparatorQ;
import comparators.PlayerComparatorRounds;
import comparators.PlayerComparatorTotal;
import comparators.PlayerComparatorWins;

public class LeaderboardList {
	
	public static List<LeaderboardPlayer> WINS = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
	public static List<LeaderboardPlayer> WALLS = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
	public static List<LeaderboardPlayer> Q = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
	public static List<LeaderboardPlayer> F = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
	public static List<LeaderboardPlayer> QF = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
	public static List<LeaderboardPlayer> OVERALL = GeneralUtils.generatePlayerList(new File("leaderboard"), true);

	public static void sort() {
		WINS.sort(new PlayerComparatorWins());
		WALLS.sort(new PlayerComparatorRounds());
		Q.sort(new PlayerComparatorQ());
		F.sort(new PlayerComparatorF());
		QF.sort(new PlayerComparatorTotal());
		OVERALL.sort(new PlayerComparatorOverall());
	}
	
	public static void init() {
		WINS = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
		WALLS = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
		Q = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
		F = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
		QF = GeneralUtils.generatePlayerList(new File("leaderboard"), false);
		OVERALL = GeneralUtils.generatePlayerList(new File("leaderboard"), true);
		
		sort();
	}
	
}
