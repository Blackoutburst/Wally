package utils;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import comparators.PlayerComparatorF;
import comparators.PlayerComparatorQ;
import comparators.PlayerComparatorRounds;
import comparators.PlayerComparatorTotal;
import comparators.PlayerComparatorWins;
import core.RolesManager;

public class CompareCanvas {

	enum Type {
		WINS,
		WALLS,
		Q,
		F,
		T,
		LQF,
		WQ,
		WF,
		L
	}
	
	public static void createMultiCompareImage(Canvas image, String[] players) {
		List<LeaderboardPlayer> pls = new ArrayList<>();
		
		for (String data : players)
			pls.add(new LeaderboardPlayer(data, true));
		pls.sort(new PlayerComparatorF());
		int f = pls.get(0).finals;
		pls.sort(new PlayerComparatorQ());
		int q = pls.get(0).qualification;
		int score = Math.max(q, f);
		
		image.drawImageFromMemory(LoadedRessources.BACKGROUND_ENDL, 0, 80, 1000, image.height);
		image.drawImageFromMemory(Canvas.getHeader(score), 0, 0, 1000, 400);
		
		drawWins(image, pls);
		drawWalls(image, pls);
		drawQ(image, pls);
		drawF(image, pls);
		drawQF(image, pls);
		
		image.save("compare.png");
	}
	
	private static String localDiff(int p1, int p2) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		return (formatter.format(p1 - p2));
	}
	
	private static void drawWins(Canvas image, List<LeaderboardPlayer> players) {
		players.sort(new PlayerComparatorWins());
		
		image.drawStringCenter("Wins", 100, 152, 60, Color.white.darker().darker().darker());
		image.drawStringCenter("Wins", 100, 150, 60, Color.white);
		
		int i = 0;
		for (LeaderboardPlayer p : players) {
			i++;
			int f = p.finals;
			int q = p.qualification;
			int score = Math.max(q, f);
			
			Color c = RolesManager.getRoleColor(score);
			
			if (p.name.length() > 8)
				p.name = p.name.substring(0, 8);
			
			image.drawStringCenter(p.name, 100, 52 + (180 * i), 40, c.darker().darker().darker());
			image.drawStringCenter(p.name, 100, 50 + (180 * i), 40, c);
			
			
			String diff;
			if ((i) >= players.size()) {
				diff = "("+localDiff(p.wins, players.get(i-2).wins)+")";
			} else {
				diff = "("+localDiff(p.wins, players.get(i).wins)+")";
			}
			
			image.drawStringCenter(p.getWins(), 100, 52 + (180 * i) + 60, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(p.getWins(), 100, 50 + (180 * i) + 60, 40, Color.white);
			
			image.drawStringCenter(diff, 100, 52 + (180 * i) + 120, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(diff, 100, 50 + (180 * i) + 120, 40, Color.white);
		}
	}
	
	private static void drawWalls(Canvas image, List<LeaderboardPlayer> players) {
		players.sort(new PlayerComparatorRounds());
		
		image.drawStringCenter("Walls", 300, 152, 60, Color.white.darker().darker().darker());
		image.drawStringCenter("Walls", 300, 150, 60, Color.white);
		
		int i = 0;
		for (LeaderboardPlayer p : players) {
			i++;
			int f = p.finals;
			int q = p.qualification;
			int score = Math.max(q, f);
			
			Color c = RolesManager.getRoleColor(score);
			image.drawStringCenter(p.name, 300, 52 + (180 * i), 40, c.darker().darker().darker());
			image.drawStringCenter(p.name, 300, 50 + (180 * i), 40, c);
			
			
			String diff;
			if ((i) >= players.size()) {
				diff = "("+localDiff(p.walls, players.get(i-2).walls)+")";
			} else {
				diff = "("+localDiff(p.walls, players.get(i).walls)+")";
			}
			
			image.drawStringCenter(p.getWalls(), 300, 52 + (180 * i) + 60, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(p.getWalls(), 300, 50 + (180 * i) + 60, 40, Color.white);
			
			image.drawStringCenter(diff, 300, 52 + (180 * i) + 120, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(diff, 300, 50 + (180 * i) + 120, 40, Color.white);
		}
	}
	
	private static void drawQ(Canvas image, List<LeaderboardPlayer> players) {
		players.sort(new PlayerComparatorQ());
		
		image.drawStringCenter("Q", 500, 152, 60, Color.white.darker().darker().darker());
		image.drawStringCenter("Q", 500, 150, 60, Color.white);
		
		int i = 0;
		for (LeaderboardPlayer p : players) {
			i++;
			int f = p.finals;
			int q = p.qualification;
			int score = Math.max(q, f);
			
			Color c = RolesManager.getRoleColor(score);
			image.drawStringCenter(p.name, 500, 52 + (180 * i), 40, c.darker().darker().darker());
			image.drawStringCenter(p.name, 500, 50 + (180 * i), 40, c);
			
			
			String diff;
			if ((i) >= players.size()) {
				diff = "("+localDiff(p.qualification, players.get(i-2).qualification)+")";
			} else {
				diff = "("+localDiff(p.qualification, players.get(i).qualification)+")";
			}
			
			image.drawStringCenter(p.getQualification(), 500, 52 + (180 * i) + 60, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(p.getQualification(), 500, 50 + (180 * i) + 60, 40, Color.white);
			
			image.drawStringCenter(diff, 500, 52 + (180 * i) + 120, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(diff, 500, 50 + (180 * i) + 120, 40, Color.white);
		}
	}
	
	private static void drawF(Canvas image, List<LeaderboardPlayer> players) {
		players.sort(new PlayerComparatorF());
		
		image.drawStringCenter("F", 700, 152, 60, Color.white.darker().darker().darker());
		image.drawStringCenter("F", 700, 150, 60, Color.white);
		
		int i = 0;
		for (LeaderboardPlayer p : players) {
			i++;
			int f = p.finals;
			int q = p.qualification;
			int score = Math.max(q, f);
			
			Color c = RolesManager.getRoleColor(score);
			image.drawStringCenter(p.name, 700, 52 + (180 * i), 40, c.darker().darker().darker());
			image.drawStringCenter(p.name, 700, 50 + (180 * i), 40, c);
			
			
			String diff;
			if ((i) >= players.size()) {
				diff = "("+localDiff(p.finals, players.get(i-2).finals)+")";
			} else {
				diff = "("+localDiff(p.finals, players.get(i).finals)+")";
			}
			
			image.drawStringCenter(p.getFinals(), 700, 52 + (180 * i) + 60, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(p.getFinals(), 700, 50 + (180 * i) + 60, 40, Color.white);
			
			image.drawStringCenter(diff, 700, 52 + (180 * i) + 120, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(diff, 700, 50 + (180 * i) + 120, 40, Color.white);
		}
	}
	
	private static void drawQF(Canvas image, List<LeaderboardPlayer> players) {
		players.sort(new PlayerComparatorTotal());
		
		image.drawStringCenter("QF", 900, 152, 60, Color.white.darker().darker().darker());
		image.drawStringCenter("QF", 900, 150, 60, Color.white);
		
		int i = 0;
		for (LeaderboardPlayer p : players) {
			i++;
			int f = p.finals;
			int q = p.qualification;
			int score = Math.max(q, f);
			
			Color c = RolesManager.getRoleColor(score);
			image.drawStringCenter(p.name, 900, 52 + (180 * i), 40, c.darker().darker().darker());
			image.drawStringCenter(p.name, 900, 50 + (180 * i), 40, c);
			
			
			String diff;
			if ((i) >= players.size()) {
				diff = "("+localDiff(p.total, players.get(i-2).total)+")";
			} else {
				diff = "("+localDiff(p.total, players.get(i).total)+")";
			}
			
			image.drawStringCenter(p.getTotal(), 900, 52 + (180 * i) + 60, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(p.getTotal(), 900, 50 + (180 * i) + 60, 40, Color.white);
			
			image.drawStringCenter(diff, 900, 52 + (180 * i) + 120, 40, Color.white.darker().darker().darker());
			image.drawStringCenter(diff, 900, 50 + (180 * i) + 120, 40, Color.white);
		}
	}
	
	public static void createCompareImage(Canvas image, String player1, String player2) {
		setBackground(image, player1, player2);

		drawLeftSide(image, player1, player2);
		drawRightSide(image, player2, player1);

		image.save("compare.png");
	}
	
	private static void setBackground(Canvas image, String player1, String player2) {
		Canvas half = new Canvas(500, 520);
		
		half.drawImage(GeneralUtils.getCustomBackground(API.getUUID(player1)), 0, 0, 1000, GeneralUtils.hasCustomBackground(API.getUUID(player1)) ? 440 : 520);
		half.save("half.png");
		
		Canvas half2 = new Canvas(500, 520);
		
		half2.drawImage(GeneralUtils.getCustomBackground(API.getUUID(player2)), -500, 0, 1000, GeneralUtils.hasCustomBackground(API.getUUID(player2)) ? 440 : 520);
		half2.save("half2.png");
		
		image.drawCustomBackground("half.png", 0, GeneralUtils.hasCustomBackground(API.getUUID(player1)) ? 80 : 0, 500, 520);
		image.drawCustomBackground("half2.png", 500, GeneralUtils.hasCustomBackground(API.getUUID(player2)) ? 80 : 0, 500, 520);
	}
	
	/**
	 * Draw the left side of the compare canvas
	 */
	private static void drawLeftSide(Canvas image, String player1, String player2) {
		String uuid = API.getUUID(player1);
		String uuid2 = API.getUUID(player2);
		int f = API.getFinalsToInt(player1);
		int q = API.getQualificationToInt(player1);
		int score = Math.max(q, f);
		
		image.drawImageFromMemory(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));

		image.drawImageFromMemory(LoadedRessources.WINS, 20, 215, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WALLS, 20, 265, 30, 30);
		image.drawImageFromMemory(LoadedRessources.Q, 20, 315, 30, 30);
		image.drawImageFromMemory(LoadedRessources.F, 20, 365, 30, 30);
		image.drawImageFromMemory(LoadedRessources.QF, 20, 415, 30, 30);
		image.drawImageFromMemory(LoadedRessources.RANK, 20, 465, 30, 30);

		if (API.getUUID(player1).equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImageFromMemory(LoadedRessources.BLACKOUT, 130, 110, 320, 80);
		else if (API.getUUID(player1).equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImageFromMemory(LoadedRessources.VENOM, 130, 110, 300, 85);
		else {
			image.drawStringLeft(API.getName(player1), 132, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft(API.getName(player1), 130, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 132, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, GeneralUtils.getFontColor(uuid));
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		image.drawStringLeft("Wins: " + API.getWins(player1) + " (" + diff(player1, player2, Type.WINS) + ")",
		62, 242, 30, stringColor(player1, player2, Type.WINS).darker().darker().darker());
		image.drawStringLeft("Wins: " + API.getWins(player1) + " (" + diff(player1, player2, Type.WINS) + ")",
		60, 240, 30, stringColor(player1, player2, Type.WINS));
		
		image.drawStringLeft("Walls: " + API.getWalls(player1) + " (" + diff(player1, player2, Type.WALLS) + ")",
		62, 297, 30, stringColor(player1, player2, Type.WALLS).darker().darker().darker());
		image.drawStringLeft("Walls: " + API.getWalls(player1) + " (" + diff(player1, player2, Type.WALLS) + ")",
		60, 295, 30, stringColor(player1, player2, Type.WALLS));

		image.drawStringLeft("Qualification: " + API.getQualification(player1) + " (" + diff(player1, player2, Type.Q) + ")",
		62, 347, 30, stringColor(player1, player2, Type.Q).darker().darker().darker());
		image.drawStringLeft("Qualification: " + API.getQualification(player1) + " (" + diff(player1, player2, Type.Q) + ")",
		60, 345, 30, stringColor(player1, player2, Type.Q));

		image.drawStringLeft("Finals: " + API.getFinals(player1) + " (" + diff(player1, player2, Type.F) + ")",
		62, 397, 30, stringColor(player1, player2, Type.F).darker().darker().darker());
		image.drawStringLeft("Finals: " + API.getFinals(player1) + " (" + diff(player1, player2, Type.F) + ")",
		60, 395, 30, stringColor(player1, player2, Type.F));

		image.drawStringLeft("Q/F: " + API.getTotal(player1) + " (" + diff(player1, player2, Type.T) + ")",
		62, 447, 30, stringColor(player1, player2, Type.T).darker().darker().darker());
		image.drawStringLeft("Q/F: " + API.getTotal(player1) + " (" + diff(player1, player2, Type.T) + ")",
		60, 445, 30, stringColor(player1, player2, Type.T));

		int wlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WINS);
		int rlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WALLS);
		int qlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.Q);
		int flb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.F);
		int tlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.QF);

		float p1rank = wlb + rlb + qlb + flb + tlb;
		p1rank = (p1rank / 5) + 1;

		wlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.WINS);
		rlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.WALLS);
		qlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.Q);
		flb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.F);
		tlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.QF);

		float p2rank = wlb + rlb + qlb + flb + tlb;
		p2rank = (p2rank / 5) + 1;

		NumberFormat nf = new DecimalFormat("0.00");
		DecimalFormat formatter = (DecimalFormat) nf;

		image.drawStringLeft("Overall Rank: " + formatter.format(p1rank) + " (" + pOveralldiff(p2rank, p1rank) + ")",
				62, 497, 30, pOverallstringColor(p2rank, p1rank).darker().darker().darker());
		image.drawStringLeft("Overall Rank: " + formatter.format(p1rank) + " (" + pOveralldiff(p2rank, p1rank) + ")",
				60, 495, 30, pOverallstringColor(p2rank, p1rank));

	}
	
	/**
	 * Draw the left side of the compare canvas
	 */
	private static void drawRightSide(Canvas image, String player1, String player2) {
		String uuid = API.getUUID(player1);
		String uuid2 = API.getUUID(player2);
		int f = API.getFinalsToInt(player1);
		int q = API.getQualificationToInt(player1);
		int score = Math.max(q, f);
		
		image.drawImageFromMemory(Canvas.getHeaderRight(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));

		image.drawImageFromMemory(LoadedRessources.WINS, 950, 215, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WALLS, 950, 265, 30, 30);
		image.drawImageFromMemory(LoadedRessources.Q, 950, 315, 30, 30);
		image.drawImageFromMemory(LoadedRessources.F, 950, 365, 30, 30);
		image.drawImageFromMemory(LoadedRessources.QF, 950, 415, 30, 30);
		image.drawImageFromMemory(LoadedRessources.RANK, 950, 465, 30, 30);

		if (API.getUUID(player1).equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImageFromMemory(LoadedRessources.BLACKOUT, 550, 110, 320, 80);
		else if (API.getUUID(player1).equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImageFromMemory(LoadedRessources.VENOM, 570, 110, 300, 85);
		else {
			image.drawStringRight(API.getName(player1), 872, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringRight(API.getName(player1), 870, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		image.drawStringRight(Stats.getSubTitle(uuid), 872, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringRight(Stats.getSubTitle(uuid), 870, 180, 36, GeneralUtils.getFontColor(uuid));
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 880, 100, 100, 100);
		
		image.drawStringRight("(" + diff(player1, player2, Type.WINS) + ") " + API.getWins(player1) + " :Wins",
		942, 242, 30, stringColor(player1, player2, Type.WINS).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.WINS) + ") " + API.getWins(player1) + " :Wins",
		940, 240, 30, stringColor(player1, player2, Type.WINS));

		image.drawStringRight("(" + diff(player1, player2, Type.WALLS) + ") " + API.getWalls(player1) + " :Walls",
		942, 297, 30, stringColor(player1, player2, Type.WALLS).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.WALLS) + ") " + API.getWalls(player1) + " :Walls",
		940, 295, 30, stringColor(player1, player2, Type.WALLS));

		image.drawStringRight("(" + diff(player1, player2, Type.Q) + ") " + API.getQualification(player1) + " :Qualification",
		942, 347, 30, stringColor(player1, player2, Type.Q).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.Q) + ") " + API.getQualification(player1) + " :Qualification",
		940, 345, 30, stringColor(player1, player2, Type.Q));
		
		image.drawStringRight("(" + diff(player1, player2, Type.F) + ") " + API.getFinals(player1) + " :Finals",
		942, 397, 30, stringColor(player1, player2, Type.F).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.F) + ") " + API.getFinals(player1) + " :Finals",
		940, 395, 30, stringColor(player1, player2, Type.F));

		image.drawStringRight("(" + diff(player1, player2, Type.T) + ") " + API.getTotal(player1) + " :Q/F",
		942, 447, 30, stringColor(player1, player2, Type.T).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.T) + ") " + API.getTotal(player1) + " :Q/F",
		940, 445, 30, stringColor(player1, player2, Type.T));

		int wlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WINS);
		int rlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WALLS);
		int qlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.Q);
		int flb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.F);
		int tlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.QF);

		float p1rank = wlb + rlb + qlb + flb + tlb;
		p1rank = (p1rank / 5) + 1;

		wlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.WINS);
		rlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.WALLS);
		qlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.Q);
		flb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.F);
		tlb = GeneralUtils.getLBPosToIntFast(uuid2, LeaderboardList.QF);

		float p2rank = wlb + rlb + qlb + flb + tlb;
		p2rank = (p2rank / 5) + 1;

		NumberFormat nf = new DecimalFormat("0.00");
		DecimalFormat formatter = (DecimalFormat) nf;

		image.drawStringRight("(" + pOveralldiff(p2rank, p1rank) + ") " + formatter.format(p1rank) + " :Overall Rank",
				942, 497, 30, pOverallstringColor(p2rank, p1rank).darker().darker().darker());
		image.drawStringRight("(" + pOveralldiff(p2rank, p1rank) + ") " + formatter.format(p1rank) + " :Overall Rank",
				940, 495, 30, pOverallstringColor(p2rank, p1rank));

	}
	
	/**
	 * Get string color if value is positive negative or null
	 */
	private static Color stringColor(String p1, String p2, Type t) {
		Color color;
		
		color = diff(p1, p2, t).charAt(0) == '-' ? new Color(255, 80, 80): new Color(80, 255, 80);
		if (diff(p1, p2, t).charAt(0) == '0') color = Color.white;
		return (color);
	}
	
	private static String diff(String p1, String p2, Type type) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int d1;
		int d2;
		
		switch(type) {
			case WALLS:
				d1 = API.getWallsToInt(p1);
				d2 = API.getWallsToInt(p2);
			break;
			case Q: 
				d1 = API.getQualificationToInt(p1);
				d2 = API.getQualificationToInt(p2);
			break;
			case F: 
				d1 = API.getFinalsToInt(p1);
				d2 = API.getFinalsToInt(p2);
			break;
			case T: 
				d1 = API.getTotalToInt(p1);
				d2 = API.getTotalToInt(p2);
			break;
			default: 
				d1 = API.getWinsToInt(p1);
				d2 = API.getWinsToInt(p2);
			break;
		}
		return (formatter.format(d1 - d2));
	}
	
	public static void createPCompareImage(Canvas image, String player1Uuid, String player2Uuid, String player1Stats, String player2Stats, String player1Name, String player2Name) {
		image.drawImageFromMemory(LoadedRessources.BACKGROUND_COMPARE, 0, 0, 1000, 640);

		drawPLeftSide(image, player1Stats, player2Stats, player1Uuid, player1Name, player2Uuid);
		drawPRightSide(image, player2Stats, player1Stats, player2Uuid, player2Name, player1Uuid);

		image.save("pcompare.png");
	}

	/**
	 * Draw the left side of the compare canvas
	 */
	private static void drawPLeftSide(Canvas image, String player1, String player2, String uuid, String name, String uuid2) {
		final int q = PracticeAPI.getQualificationToInt(player1);
		final int f = PracticeAPI.getFinalsToInt(player1);
		final int wq = PracticeAPI.getWideQualificationToInt(player1);
		final int wf = PracticeAPI.getWideFinalsToInt(player1);
		final int l = PracticeAPI.getLobbyToInt(player1);
		final int lqf = l+q+f+wq;
		int score = 0;

		if (q >= f && q >= wq && q >= l && q >= wf) score = q;
		if (f >= q && f >= wq && f >= l && f >= wf) score = f;
		if (wq >= f && wq >= q && wq >= l && wq >= wf) score = wq;
		if (l >= f && l >= q && l >= wq && l >= wf) score = l;
		if (wf >= f && wf >= q && wf >= wq && wf >= l) score = wf;

		image.drawImageFromMemory(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImageFromMemory(LoadedRessources.Q, 20, 220, 30, 30);
		image.drawImageFromMemory(LoadedRessources.F, 20, 280, 30, 30);
		image.drawImageFromMemory(LoadedRessources.L, 20, 340, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WQ, 20, 400, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WF, 20, 460, 30, 30);
		image.drawImageFromMemory(LoadedRessources.QF, 20, 520, 30, 30);
		image.drawImageFromMemory(LoadedRessources.RANK, 20, 580, 30, 30);
		
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImageFromMemory(LoadedRessources.BLACKOUT, 130, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImageFromMemory(LoadedRessources.VENOM, 130, 110, 300, 85);
		else {
			image.drawStringLeft(name, 132, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft(name, 130, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 132, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, GeneralUtils.getFontColor(uuid));
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);

		image.drawStringLeft("Qualification: " + q + " (" + pdiff(player1, player2, Type.Q) + ")",
		62, 247, 30, pstringColor(player1, player2, Type.Q).darker().darker().darker());
		image.drawStringLeft("Qualification: " + q + " (" + pdiff(player1, player2, Type.Q) + ")",
		60, 245, 30, pstringColor(player1, player2, Type.Q));

		image.drawStringLeft("Finals: " + f + " (" + pdiff(player1, player2, Type.F) + ")",
		62, 307, 30, pstringColor(player1, player2, Type.F).darker().darker().darker());
		image.drawStringLeft("Finals: " + f + " (" + pdiff(player1, player2, Type.F) + ")",
		60, 305, 30, pstringColor(player1, player2, Type.F));

		image.drawStringLeft("Lobby: " + l + " (" + pdiff(player1, player2, Type.L) + ")",
		62, 367, 30, pstringColor(player1, player2, Type.L).darker().darker().darker());
		image.drawStringLeft("Lobby: " + l + " (" + pdiff(player1, player2, Type.L) + ")",
		60, 365, 30, pstringColor(player1, player2, Type.L));

		image.drawStringLeft("Wide Q: " + wq + " (" + pdiff(player1, player2, Type.WQ) + ")",
		62, 427, 30, pstringColor(player1, player2, Type.WQ).darker().darker().darker());
		image.drawStringLeft("Wide Q: " + wq + " (" + pdiff(player1, player2, Type.WQ) + ")",
		60, 425, 30, pstringColor(player1, player2, Type.WQ));

		image.drawStringLeft("Wide F: " + wf + " (" + pdiff(player1, player2, Type.WF) + ")",
				62, 487, 30, pstringColor(player1, player2, Type.WF).darker().darker().darker());
		image.drawStringLeft("Wide F: " + wf + " (" + pdiff(player1, player2, Type.WF) + ")",
				60, 485, 30, pstringColor(player1, player2, Type.WF));
		
		image.drawStringLeft("Total: " + lqf + " (" + pdiff(player1, player2, Type.LQF) + ")",
		62, 547, 30, pstringColor(player1, player2, Type.LQF).darker().darker().darker());
		image.drawStringLeft("Total: " + lqf + " (" + pdiff(player1, player2, Type.LQF) + ")",
		60, 545, 30, pstringColor(player1, player2, Type.LQF));

		int qlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.PQ);
		int flb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.PF);
		int llb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.L);
		int wlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.WQ);
		int wflb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.WF);
		int tlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.LQF);

		float p1rank = qlb + flb + llb + wlb + tlb + wflb;
		p1rank = (p1rank / 6) + 1;

		qlb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.PQ);
		flb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.PF);
		llb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.L);
		wlb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.WQ);
		wflb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.WF);
		tlb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.LQF);

		float p2rank = qlb + flb + llb + wlb + tlb + wflb;
		p2rank = (p2rank / 6) + 1;

		NumberFormat nf = new DecimalFormat("0.00");
		DecimalFormat formatter = (DecimalFormat) nf;

		image.drawStringLeft("Overall Rank: " + formatter.format(p1rank) + " (" + pOveralldiff(p2rank, p1rank) + ")",
				62, 607, 30, pOverallstringColor(p2rank, p1rank).darker().darker().darker());
		image.drawStringLeft("Overall Rank: " + formatter.format(p1rank) + " (" + pOveralldiff(p2rank, p1rank) + ")",
				60, 605, 30, pOverallstringColor(p2rank, p1rank));

	}
	
	/**
	 * Draw the left side of the compare canvas
	 */
	private static void drawPRightSide(Canvas image, String player1, String player2, String uuid, String name, String uuid2) {
		final int q = PracticeAPI.getQualificationToInt(player1);
		final int f = PracticeAPI.getFinalsToInt(player1);
		final int wq = PracticeAPI.getWideQualificationToInt(player1);
		final int wf = PracticeAPI.getWideFinalsToInt(player1);
		final int l = PracticeAPI.getLobbyToInt(player1);
		final int lqf = l+q+f+wq;
		int score = 0;

		if (q >= f && q >= wq && q >= l && q >= wf) score = q;
		if (f >= q && f >= wq && f >= l && f >= wf) score = f;
		if (wq >= f && wq >= q && wq >= l && wq >= wf) score = wq;
		if (l >= f && l >= q && l >= wq && l >= wf) score = l;
		if (wf >= f && wf >= q && wf >= wq && wf >= l) score = wf;
		
		image.drawImageFromMemory(Canvas.getHeaderRight(score), 0, 0, 1000, 400);
		image.drawImageFromMemory(LoadedRessources.PHEADER, 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));

		image.drawImageFromMemory(LoadedRessources.Q, 950, 220, 30, 30);
		image.drawImageFromMemory(LoadedRessources.F, 950, 280, 30, 30);
		image.drawImageFromMemory(LoadedRessources.L, 950, 340, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WQ, 950, 400, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WF, 950, 460, 30, 30);
		image.drawImageFromMemory(LoadedRessources.QF, 950, 520, 30, 30);
		image.drawImageFromMemory(LoadedRessources.RANK, 950, 580, 30, 30);
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImageFromMemory(LoadedRessources.BLACKOUT, 550, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImageFromMemory(LoadedRessources.VENOM, 570, 110, 300, 85);
		else {
			image.drawStringRight(name, 872, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringRight(name, 870, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		image.drawStringRight(Stats.getSubTitle(uuid), 872, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringRight(Stats.getSubTitle(uuid), 870, 180, 36, GeneralUtils.getFontColor(uuid));
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 880, 100, 100, 100);

		image.drawStringRight("(" + pdiff(player1, player2, Type.Q) + ") " + q + " :Qualification",
		942, 247, 30, pstringColor(player1, player2, Type.Q).darker().darker().darker());
		image.drawStringRight("(" + pdiff(player1, player2, Type.Q) + ") " + q + " :Qualification",
		940, 245, 30, pstringColor(player1, player2, Type.Q));

		image.drawStringRight("(" + pdiff(player1, player2, Type.F) + ") " + f + " :Finals",
		942, 307, 30, pstringColor(player1, player2, Type.F).darker().darker().darker());
		image.drawStringRight("(" + pdiff(player1, player2, Type.F) + ") " + f + " :Finals",
		940, 305, 30, pstringColor(player1, player2, Type.F));

		image.drawStringRight("(" + pdiff(player1, player2, Type.L) + ") " + l + " :Lobby",
		942, 367, 30, pstringColor(player1, player2, Type.L).darker().darker().darker());
		image.drawStringRight("(" + pdiff(player1, player2, Type.L) + ") " + l + " :Lobby",
		940, 365, 30, pstringColor(player1, player2, Type.L));

		image.drawStringRight("(" + pdiff(player1, player2, Type.WQ) + ") " + wq + " :Wide Q",
		942, 427, 30, pstringColor(player1, player2, Type.WQ).darker().darker().darker());
		image.drawStringRight("(" + pdiff(player1, player2, Type.WQ) + ") " + wq + " :Wide Q",
		940, 425, 30, pstringColor(player1, player2, Type.WQ));

		image.drawStringRight("(" + pdiff(player1, player2, Type.WF) + ") " + wf + " :Wide F",
				942, 487, 30, pstringColor(player1, player2, Type.WF).darker().darker().darker());
		image.drawStringRight("(" + pdiff(player1, player2, Type.WF) + ") " + wf + " :Wide F",
				940, 485, 30, pstringColor(player1, player2, Type.WF));
		
		image.drawStringRight("(" + pdiff(player1, player2, Type.LQF) + ") " + lqf + " :Total",
		942, 547, 30, pstringColor(player1, player2, Type.LQF).darker().darker().darker());
		image.drawStringRight("(" + pdiff(player1, player2, Type.LQF) + ") " + lqf + " :Total",
		940, 545, 30, pstringColor(player1, player2, Type.LQF));

		int qlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.PQ);
		int flb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.PF);
		int llb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.L);
		int wlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.WQ);
		int wflb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.WF);
		int tlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.LQF);

		float p1rank = qlb + flb + llb + wlb + tlb + wflb;
		p1rank = (p1rank / 6) + 1;

		qlb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.PQ);
		flb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.PF);
		llb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.L);
		wlb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.WQ);
		wflb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.WF);
		tlb = GeneralUtils.getPLBPosToIntFast(uuid2, LeaderboardList.LQF);

		float p2rank = qlb + flb + llb + wlb + tlb + wflb;
		p2rank = (p2rank / 6) + 1;

		NumberFormat nf = new DecimalFormat("0.00");
		DecimalFormat formatter = (DecimalFormat) nf;

		image.drawStringRight("(" + pOveralldiff(p2rank, p1rank) + ") " + formatter.format(p1rank) + " :Overall Rank",
				942, 607, 30, pOverallstringColor(p2rank, p1rank).darker().darker().darker());
		image.drawStringRight("(" + pOveralldiff(p2rank, p1rank) + ") " + formatter.format(p1rank) + " :Overall Rank",
				940, 605, 30, pOverallstringColor(p2rank, p1rank));

	}

	private static Color pOverallstringColor(float score1, float score2) {
		Color color;

		color = pOveralldiff(score1, score2).charAt(0) == '-' ? new Color(255, 80, 80): new Color(80, 255, 80);
		if (pOveralldiff(score1, score2).charAt(0) == '0') color = Color.white;
		return (color);
	}

	private static String pOveralldiff(float p1, float p2) {
		NumberFormat nf = new DecimalFormat("0.00");
		DecimalFormat formatter = (DecimalFormat) nf;
		return (formatter.format(p1 - p2));
	}

	/**
	 * Get string color if value is positive negative or null
	 */
	private static Color pstringColor(String p1, String p2, Type t) {
		Color color;
		
		color = pdiff(p1, p2, t).charAt(0) == '-' ? new Color(255, 80, 80): new Color(80, 255, 80);
		if (pdiff(p1, p2, t).charAt(0) == '0') color = Color.white;
		return (color);
	}
	
	private static String pdiff(String p1, String p2, Type type) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int d1;
		int d2;
		
		switch(type) {
			case Q: 
				d1 = PracticeAPI.getQualificationToInt(p1);
				d2 = PracticeAPI.getQualificationToInt(p2);
			break;
			case F:
				d1 = PracticeAPI.getFinalsToInt(p1);
				d2 = PracticeAPI.getFinalsToInt(p2);
			break;
			case WQ: 
				d1 = PracticeAPI.getWideQualificationToInt(p1);
				d2 = PracticeAPI.getWideQualificationToInt(p2);
			break;
			case WF:
				d1 = PracticeAPI.getWideFinalsToInt(p1);
				d2 = PracticeAPI.getWideFinalsToInt(p2);
				break;
			case L: 
				d1 = PracticeAPI.getLobbyToInt(p1);
				d2 = PracticeAPI.getLobbyToInt(p2);
			break;
			case LQF: 
				d1 = PracticeAPI.getQualificationToInt(p1) + PracticeAPI.getFinalsToInt(p1) + PracticeAPI.getLobbyToInt(p1);
				d2 = PracticeAPI.getQualificationToInt(p2) + PracticeAPI.getFinalsToInt(p2) + PracticeAPI.getLobbyToInt(p2);
			break;
			default: 
				d1 = 0;
				d2 = 0;
			break;
		}
		return (formatter.format(d1 - d2));
	}
}
