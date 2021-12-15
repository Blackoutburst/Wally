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
		L
	}
	
	/**
	 * Create compare image
	 * @param image
	 * @param data
	 */
	public static void createMultiCompareImage(Canvas image, String[] players) {
		List<LeaderboardPlayer> pls = new ArrayList<>();
		
		for (String data : players)
			pls.add(new LeaderboardPlayer(data, true));
		pls.sort(new PlayerComparatorF());
		int f = pls.get(0).finals;
		pls.sort(new PlayerComparatorQ());
		int q = pls.get(0).qualification;
		int score = q > f ? q : f;
		
		image.drawImage("res/backgroundEndL.png", 0, 80, 1000, image.height);
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		
		drawWins(image, pls);
		drawWalls(image, pls);
		drawQ(image, pls);
		drawF(image, pls);
		drawQF(image, pls);
		
		image.save("compare.png");
	}
	
	/**
	 * Return difference between two value
	 * @param stat1
	 * @param stat2
	 * @return
	 */
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
			int score = q > f ? q : f;
			
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
			int score = q > f ? q : f;
			
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
			int score = q > f ? q : f;
			
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
			int score = q > f ? q : f;
			
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
			int score = q > f ? q : f;
			
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
	
	/**
	 * Create compare image
	 * @param image
	 * @param data
	 */
	public static void createCompareImage(Canvas image, String player1, String player2) {
		setBackground(image, player1, player2);
		
		image = drawLeftSide(image, player1, player2);
		image = drawRightSide(image, player2, player1);
		
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
	 * @param image
	 * @return
	 */
	private static Canvas drawLeftSide(Canvas image, String player1, String player2) {
		String uuid = API.getUUID(player1);
		int f = API.getFinalsToInt(player1);
		int q = API.getQualificationToInt(player1);
		int score = q > f ? q : f;
		
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImage("res/win.png", 20, 220, 30, 30);
		image.drawImage("res/wall.png", 20, 280, 30, 30);
		image.drawImage("res/q.png", 20, 340, 30, 30);
		image.drawImage("res/f.png", 20, 400, 30, 30);
		image.drawImage("res/qf.png", 20, 460, 30, 30);
		
		
		if (API.getUUID(player1).equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
		else if (API.getUUID(player1).equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 130, 110, 300, 85);
		else {
			image.drawStringLeft(API.getName(player1), 132, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft(API.getName(player1), 130, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 132, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, GeneralUtils.getFontColor(uuid));
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		image.drawStringLeft("Wins: " + API.getWins(player1) + " (" + diff(player1, player2, Type.WINS) + ")",
				62, 247, 30, stringColor(player1, player2, Type.WINS).darker().darker().darker());
		image.drawStringLeft("Wins: " + API.getWins(player1) + " (" + diff(player1, player2, Type.WINS) + ")",
		60, 245, 30, stringColor(player1, player2, Type.WINS));
		
		image.drawStringLeft("Walls: " + API.getWalls(player1) + " (" + diff(player1, player2, Type.WALLS) + ")",
		62, 307, 30, stringColor(player1, player2, Type.WALLS).darker().darker().darker());
		image.drawStringLeft("Walls: " + API.getWalls(player1) + " (" + diff(player1, player2, Type.WALLS) + ")",
		60, 305, 30, stringColor(player1, player2, Type.WALLS));

		image.drawStringLeft("Qualification: " + API.getQualification(player1) + " (" + diff(player1, player2, Type.Q) + ")",
		62, 367, 30, stringColor(player1, player2, Type.Q).darker().darker().darker());
		image.drawStringLeft("Qualification: " + API.getQualification(player1) + " (" + diff(player1, player2, Type.Q) + ")",
		60, 365, 30, stringColor(player1, player2, Type.Q));

		image.drawStringLeft("Finals: " + API.getFinals(player1) + " (" + diff(player1, player2, Type.F) + ")",
		62, 427, 30, stringColor(player1, player2, Type.F).darker().darker().darker());
		image.drawStringLeft("Finals: " + API.getFinals(player1) + " (" + diff(player1, player2, Type.F) + ")",
		60, 425, 30, stringColor(player1, player2, Type.F));

		image.drawStringLeft("Q/F: " + API.getTotal(player1) + " (" + diff(player1, player2, Type.T) + ")",
		62, 487, 30, stringColor(player1, player2, Type.T).darker().darker().darker());
		image.drawStringLeft("Q/F: " + API.getTotal(player1) + " (" + diff(player1, player2, Type.T) + ")",
		60, 485, 30, stringColor(player1, player2, Type.T));
		
		return (image);
	}
	
	/**
	 * Draw the left side of the compare canvas
	 * @param image
	 * @return
	 */
	private static Canvas drawRightSide(Canvas image, String player1, String player2) {
		String uuid = API.getUUID(player1);
		int f = API.getFinalsToInt(player1);
		int q = API.getQualificationToInt(player1);
		int score = q > f ? q : f;
		
		image.drawImage(Canvas.getHeaderRight(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));

		
		image.drawImage("res/win.png", 950, 220, 30, 30);
		image.drawImage("res/wall.png", 950, 280, 30, 30);
		image.drawImage("res/q.png", 950, 340, 30, 30);
		image.drawImage("res/f.png", 950, 400, 30, 30);
		image.drawImage("res/qf.png", 950, 460, 30, 30);
		
		if (API.getUUID(player1).equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 550, 110, 320, 80);
		else if (API.getUUID(player1).equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 570, 110, 300, 85);
		else {
			image.drawStringRight(API.getName(player1), 872, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringRight(API.getName(player1), 870, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		image.drawStringRight(Stats.getSubTitle(uuid), 872, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringRight(Stats.getSubTitle(uuid), 870, 180, 36, GeneralUtils.getFontColor(uuid));
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 880, 100, 100, 100);
		
		image.drawStringRight("(" + diff(player1, player2, Type.WINS) + ") " + API.getWins(player1) + " :Wins",
		942, 247, 30, stringColor(player1, player2, Type.WINS).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.WINS) + ") " + API.getWins(player1) + " :Wins",
		940, 245, 30, stringColor(player1, player2, Type.WINS));

		image.drawStringRight("(" + diff(player1, player2, Type.WALLS) + ") " + API.getWalls(player1) + " :Walls",
		942, 307, 30, stringColor(player1, player2, Type.WALLS).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.WALLS) + ") " + API.getWalls(player1) + " :Walls",
		940, 305, 30, stringColor(player1, player2, Type.WALLS));

		image.drawStringRight("(" + diff(player1, player2, Type.Q) + ") " + API.getQualification(player1) + " :Qualification",
		942, 367, 30, stringColor(player1, player2, Type.Q).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.Q) + ") " + API.getQualification(player1) + " :Qualification",
		940, 365, 30, stringColor(player1, player2, Type.Q));
		
		image.drawStringRight("(" + diff(player1, player2, Type.F) + ") " + API.getFinals(player1) + " :Finals",
		942, 427, 30, stringColor(player1, player2, Type.F).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.F) + ") " + API.getFinals(player1) + " :Finals",
		940, 425, 30, stringColor(player1, player2, Type.F));

		image.drawStringRight("(" + diff(player1, player2, Type.T) + ") " + API.getTotal(player1) + " :Q/F",
		942, 487, 30, stringColor(player1, player2, Type.T).darker().darker().darker());
		image.drawStringRight("(" + diff(player1, player2, Type.T) + ") " + API.getTotal(player1) + " :Q/F",
		940, 485, 30, stringColor(player1, player2, Type.T));
		
		return (image);
	}
	
	/**
	 * Get string color if value is positive negative or null
	 * @param p1
	 * @param p2
	 * @return
	 */
	private static Color stringColor(String p1, String p2, Type t) {
		Color color = Color.white;
		
		color = diff(p1, p2, t).charAt(0) == '-' ? new Color(255, 80, 80): new Color(80, 255, 80);
		if (diff(p1, p2, t).charAt(0) == '0') color = Color.white;
		return (color);
	}
	
	/**
	 * Return difference between two value
	 * @param stat1
	 * @param stat2
	 * @return
	 */
	private static String diff(String p1, String p2, Type type) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int d1 = 0;
		int d2 = 0;
		
		switch(type) {
			case WINS: 
				d1 = Integer.valueOf(API.getWinsToInt(p1));
				d2 = Integer.valueOf(API.getWinsToInt(p2));
			break;
			case WALLS:
				d1 = Integer.valueOf(API.getWallsToInt(p1));
				d2 = Integer.valueOf(API.getWallsToInt(p2));
			break;
			case Q: 
				d1 = Integer.valueOf(API.getQualificationToInt(p1));
				d2 = Integer.valueOf(API.getQualificationToInt(p2));
			break;
			case F: 
				d1 = Integer.valueOf(API.getFinalsToInt(p1));
				d2 = Integer.valueOf(API.getFinalsToInt(p2));
			break;
			case T: 
				d1 = Integer.valueOf(API.getTotalToInt(p1));
				d2 = Integer.valueOf(API.getTotalToInt(p2));
			break;
			default: 
				d1 = Integer.valueOf(API.getWinsToInt(p1));
				d2 = Integer.valueOf(API.getWinsToInt(p2));
			break;
		}
		return (formatter.format(d1 - d2));
	}
	
	/**
	 * Create compare image
	 * @param image
	 * @param data
	 */
	public static void createPCompareImage(Canvas image, String player1Uuid, String player2Uuid, String player1Stats, String player2Stats, String player1Name, String player2Name) {
		setPracticeBackground(image, player1Uuid, player2Uuid);
		
		image = drawPLeftSide(image, player1Stats, player2Stats, player1Uuid, player1Name);
		image = drawPRightSide(image, player2Stats, player1Stats, player2Uuid, player2Name);
		
		image.save("pcompare.png");
	}
	
	private static void setPracticeBackground(Canvas image, String player1, String player2) {
		Canvas half = new Canvas(500, 520);
		
		half.drawImage(GeneralUtils.getCustomBackground(player1), 0, 0, 1000, GeneralUtils.hasCustomBackground(player1) ? 440 : 520);
		half.save("half.png");
		
		Canvas half2 = new Canvas(500, 520);
		
		half2.drawImage(GeneralUtils.getCustomBackground(player2), -500, 0, 1000, GeneralUtils.hasCustomBackground(player2) ? 440 : 520);
		half2.save("half2.png");
		
		image.drawCustomBackground("half.png", 0, GeneralUtils.hasCustomBackground(player1) ? 80 : 0, 500, 520);
		image.drawCustomBackground("half2.png", 500, GeneralUtils.hasCustomBackground(player2) ? 80 : 0, 500, 520);
	}
	
	/**
	 * Draw the left side of the compare canvas
	 * @param image
	 * @return
	 */
	private static Canvas drawPLeftSide(Canvas image, String player1, String player2, String uuid, String name) {
		int q = PracticeAPI.getQualificationToInt(player1);
		int f = PracticeAPI.getFinalsToInt(player1);
		int wq = PracticeAPI.getWideQualificationToInt(player1);
		int l = PracticeAPI.getLobbyToInt(player1);
		int lqf = l+q+f+wq;
		int score = 0;
		
		if (q >= f && q >= wq && q >= l) score = q;
		if (f >= q && f >= wq && f >= l) score = f;
		if (wq >= f && wq >= q && wq >= l) score = wq;
		if (l >= f && l >= q && l >= wq) score = l;
		
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImage("res/q.png", 20, 220, 30, 30);
		image.drawImage("res/f.png", 20, 280, 30, 30);
		image.drawImage("res/l.png", 20, 340, 30, 30);
		image.drawImage("res/wq.png", 20, 400, 30, 30);
		image.drawImage("res/qf.png", 20, 460, 30, 30);
		
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 130, 110, 300, 85);
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

		image.drawStringLeft("Wide Qual: " + wq + " (" + pdiff(player1, player2, Type.WQ) + ")",
		62, 427, 30, pstringColor(player1, player2, Type.WQ).darker().darker().darker());
		image.drawStringLeft("Wide Qual: " + wq + " (" + pdiff(player1, player2, Type.WQ) + ")",
		60, 425, 30, pstringColor(player1, player2, Type.WQ));
		
		image.drawStringLeft("L/Q/F/WQ: " + lqf + " (" + pdiff(player1, player2, Type.LQF) + ")",
		62, 487, 30, pstringColor(player1, player2, Type.LQF).darker().darker().darker());		
		image.drawStringLeft("L/Q/F/WQ: " + lqf + " (" + pdiff(player1, player2, Type.LQF) + ")",
		60, 485, 30, pstringColor(player1, player2, Type.LQF));
		
		return (image);
	}
	
	/**
	 * Draw the left side of the compare canvas
	 * @param image
	 * @return
	 */
	private static Canvas drawPRightSide(Canvas image, String player1, String player2, String uuid, String name) {
		int q = PracticeAPI.getQualificationToInt(player1);
		int f = PracticeAPI.getFinalsToInt(player1);
		int wq = PracticeAPI.getWideQualificationToInt(player1);
		int l = PracticeAPI.getLobbyToInt(player1);
		int lqf = l+q+f+wq;
		int score = 0;
		
		if (q >= f && q >= wq && q >= l) score = q;
		if (f >= q && f >= wq && f >= l) score = f;
		if (wq >= f && wq >= q && wq >= l) score = wq;
		if (l >= f && l >= q && l >= wq) score = l;
		
		image.drawImage(Canvas.getHeaderRight(score), 0, 0, 1000, 400);
		image.drawImage("res/practiceHeader.png", 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImage("res/q.png", 950, 220, 30, 30);
		image.drawImage("res/f.png", 950, 280, 30, 30);
		image.drawImage("res/l.png", 950, 340, 30, 30);
		image.drawImage("res/wq.png", 950, 400, 30, 30);
		image.drawImage("res/qf.png", 950, 460, 30, 30);
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 550, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 570, 110, 300, 85);
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

		image.drawStringRight("(" + pdiff(player1, player2, Type.WQ) + ") " + wq + " :Wide Qual",
		942, 427, 30, pstringColor(player1, player2, Type.WQ).darker().darker().darker());
		image.drawStringRight("(" + pdiff(player1, player2, Type.WQ) + ") " + wq + " :Wide Qual",
		940, 425, 30, pstringColor(player1, player2, Type.WQ));
		
		image.drawStringRight("(" + pdiff(player1, player2, Type.LQF) + ") " + lqf + " :L/Q/F/WQ",
		942, 487, 30, pstringColor(player1, player2, Type.LQF).darker().darker().darker());		
		image.drawStringRight("(" + pdiff(player1, player2, Type.LQF) + ") " + lqf + " :L/Q/F/WQ",
		940, 485, 30, pstringColor(player1, player2, Type.LQF));
		
		return (image);
	}
	
	/**
	 * Get string color if value is positive negative or null
	 * @param p1
	 * @param p2
	 * @return
	 */
	private static Color pstringColor(String p1, String p2, Type t) {
		Color color = Color.white;
		
		color = pdiff(p1, p2, t).charAt(0) == '-' ? new Color(255, 80, 80): new Color(80, 255, 80);
		if (pdiff(p1, p2, t).charAt(0) == '0') color = Color.white;
		return (color);
	}
	
	/**
	 * Return difference between two value
	 * @param stat1
	 * @param stat2
	 * @return
	 */
	private static String pdiff(String p1, String p2, Type type) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int d1 = 0;
		int d2 = 0;
		
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
