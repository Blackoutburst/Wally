package utils;

import java.io.File;
import java.util.List;

import comparators.*;

public class LeaderboardList {
	
	public static List<LeaderboardPlayer> WINS = null;
	public static List<LeaderboardPlayer> WALLS = null;
	public static List<LeaderboardPlayer> Q = null;
	public static List<LeaderboardPlayer> F = null;
	public static List<LeaderboardPlayer> QF = null;
	public static List<LeaderboardPlayer> OVERALL = null;

	public static List<PracticeLeaderboardPlayer> PQ = null;
	public static List<PracticeLeaderboardPlayer> PF = null;
	public static List<PracticeLeaderboardPlayer> WQ = null;
	public static List<PracticeLeaderboardPlayer> WF = null;
	public static List<PracticeLeaderboardPlayer> L = null;
	public static List<PracticeLeaderboardPlayer> LQF = null;
	public static List<PracticeLeaderboardPlayer> POVERALL = null;


	public static void sort() {
		WINS.sort(new PlayerComparatorWins());
		WALLS.sort(new PlayerComparatorRounds());
		Q.sort(new PlayerComparatorQ());
		F.sort(new PlayerComparatorF());
		QF.sort(new PlayerComparatorTotal());
		OVERALL.sort(new PlayerComparatorOverall());
	}

	public static void pSort() {
		PQ.sort(new PlayerComparatorPQ());
		PF.sort(new PlayerComparatorPF());
		WQ.sort(new PlayerComparatorWQ());
		WF.sort(new PlayerComparatorWF());
		L.sort(new PlayerComparatorL());
		LQF.sort(new PlayerComparatorLQF());
		POVERALL.sort(new PlayerComparatorPOverall());
	}

	public static void pInit() {
		PQ = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), false);
		PF = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), false);
		WQ = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), false);
		WF = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), false);
		L = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), false);
		LQF = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), false);
		POVERALL = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), true);

		pSort();
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
