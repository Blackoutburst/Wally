package utils;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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
	public static void createCompareImage(Canvas image, String player1, String player2) {
		image.drawImage("res/backgroundLarge.png", 0, 0, 1000, 520);
		
		image = drawLeftSide(image, player1, player2);
		image = drawRightSide(image, player2, player1);
		
		image.save("compare.png");
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
		
		
		image.drawImage("res/win.png", 20, 220, 30, 30);
		image.drawImage("res/wall.png", 20, 280, 30, 30);
		image.drawImage("res/q.png", 20, 340, 30, 30);
		image.drawImage("res/f.png", 20, 400, 30, 30);
		image.drawImage("res/qf.png", 20, 460, 30, 30);
		
		
		if (API.getUUID(player1).equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
		else if (API.getUUID(player1).equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 130, 110, 300, 85);
		else
			image.drawStringLeft(API.getName(player1), 130, 140, 44, Color.white);
		
		if (API.getUUID(player1).equals("640459a7a5c8417bbb06f6e6d28614a1"))
			image.drawImage("res/heart.png", 220, 156, 32, 28);
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, Color.white);
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		image.drawStringLeft("Wins: " + API.getWins(player1) + " (" + diff(player1, player2, Type.WINS) + ")",
		60, 245, 30, stringColor(player1, player2, Type.WINS));
		
		image.drawStringLeft("Walls cleared: " + API.getWalls(player1) + " (" + diff(player1, player2, Type.WALLS) + ")",
		60, 305, 30, stringColor(player1, player2, Type.WALLS));
		
		image.drawStringLeft("Qualification score: " + API.getQualification(player1) + " (" + diff(player1, player2, Type.Q) + ")",
		60, 365, 30, stringColor(player1, player2, Type.Q));
		
		image.drawStringLeft("Finals score: " + API.getFinals(player1) + " (" + diff(player1, player2, Type.F) + ")",
		60, 425, 30, stringColor(player1, player2, Type.F));
		
		image.drawStringLeft("Q/F score: " + API.getTotal(player1) + " (" + diff(player1, player2, Type.T) + ")",
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
		
		
		image.drawImage("res/win.png", 950, 220, 30, 30);
		image.drawImage("res/wall.png", 950, 280, 30, 30);
		image.drawImage("res/q.png", 950, 340, 30, 30);
		image.drawImage("res/f.png", 950, 400, 30, 30);
		image.drawImage("res/qf.png", 950, 460, 30, 30);
		
		if (API.getUUID(player1).equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 550, 110, 320, 80);
		else if (API.getUUID(player1).equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 570, 110, 300, 85);
		else
			image.drawStringRight(API.getName(player1), 870, 140, 44, Color.white);
		
		if (API.getUUID(player1).equals("640459a7a5c8417bbb06f6e6d28614a1"))
			image.drawImage("res/heart.png", 845, 156, 32, 28);
		
		image.drawStringRight(Stats.getSubTitle(uuid), 870, 180, 36, Color.white);
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 880, 100, 100, 100);
		
		image.drawStringRight("(" + diff(player1, player2, Type.WINS) + ") " + API.getWins(player1) + " :Wins",
		940, 245, 30, stringColor(player1, player2, Type.WINS));
		
		image.drawStringRight("(" + diff(player1, player2, Type.WALLS) + ") " + API.getWalls(player1) + " :Walls cleared",
		940, 305, 30, stringColor(player1, player2, Type.WALLS));
		
		image.drawStringRight("(" + diff(player1, player2, Type.Q) + ") " + API.getQualification(player1) + " :Qualification score",
		940, 365, 30, stringColor(player1, player2, Type.Q));
		
		image.drawStringRight("(" + diff(player1, player2, Type.F) + ") " + API.getFinals(player1) + " :Finals score",
		940, 425, 30, stringColor(player1, player2, Type.F));
		
		image.drawStringRight("(" + diff(player1, player2, Type.T) + ") " + API.getTotal(player1) + " :Q/F score",
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
		image.drawImage("res/backgroundLarge.png", 0, 0, 1000, 520);
		
		image = drawPLeftSide(image, player1Stats, player2Stats, player1Uuid, player1Name);
		image = drawPRightSide(image, player2Stats, player1Stats, player2Uuid, player2Name);
		
		image.save("pcompare.png");
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
		int lqf = l+q+f;
		int score = 0;
		
		if (q >= f && q >= wq && q >= l) score = q;
		if (f >= q && f >= wq && f >= l) score = f;
		if (wq >= f && wq >= q && wq >= l) score = wq;
		if (l >= f && l >= q && l >= wq) score = l;
		
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		
		
		image.drawImage("res/q.png", 20, 220, 30, 30);
		image.drawImage("res/f.png", 20, 280, 30, 30);
		image.drawImage("res/l.png", 20, 340, 30, 30);
		image.drawImage("res/wq.png", 20, 400, 30, 30);
		image.drawImage("res/qf.png", 20, 460, 30, 30);
		
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 130, 110, 300, 85);
		else
			image.drawStringLeft(name, 130, 140, 44, Color.white);
		
		if (uuid.equals("640459a7a5c8417bbb06f6e6d28614a1"))
			image.drawImage("res/heart.png", 220, 156, 32, 28);
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, Color.white);
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		image.drawStringLeft("Qualification score: " + q + " (" + pdiff(player1, player2, Type.Q) + ")",
		60, 245, 30, pstringColor(player1, player2, Type.Q));
		
		image.drawStringLeft("Finals score: " + f + " (" + pdiff(player1, player2, Type.F) + ")",
		60, 305, 30, pstringColor(player1, player2, Type.F));
		
		image.drawStringLeft("Lobby score: " + l + " (" + pdiff(player1, player2, Type.L) + ")",
		60, 365, 30, pstringColor(player1, player2, Type.L));
		
		image.drawStringLeft("Wide Qual score: " + wq + " (" + pdiff(player1, player2, Type.WQ) + ")",
		60, 425, 30, pstringColor(player1, player2, Type.WQ));
		
		image.drawStringLeft("L/Q/F score: " + lqf + " (" + pdiff(player1, player2, Type.LQF) + ")",
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
		int lqf = l+q+f;
		int score = 0;
		
		if (q >= f && q >= wq && q >= l) score = q;
		if (f >= q && f >= wq && f >= l) score = f;
		if (wq >= f && wq >= q && wq >= l) score = wq;
		if (l >= f && l >= q && l >= wq) score = l;
		
		image.drawImage(Canvas.getHeaderRight(score), 0, 0, 1000, 400);
		
		
		image.drawImage("res/win.png", 950, 220, 30, 30);
		image.drawImage("res/wall.png", 950, 280, 30, 30);
		image.drawImage("res/q.png", 950, 340, 30, 30);
		image.drawImage("res/f.png", 950, 400, 30, 30);
		image.drawImage("res/qf.png", 950, 460, 30, 30);
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 550, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 570, 110, 300, 85);
		else
			image.drawStringRight(name, 870, 140, 44, Color.white);
		
		if (uuid.equals("640459a7a5c8417bbb06f6e6d28614a1"))
			image.drawImage("res/heart.png", 845, 156, 32, 28);
		
		image.drawStringRight(Stats.getSubTitle(uuid), 870, 180, 36, Color.white);
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 880, 100, 100, 100);
		
		image.drawStringRight("(" + pdiff(player1, player2, Type.Q) + ") " + q + " :Qualification score",
		940, 245, 30, pstringColor(player1, player2, Type.Q));
		
		image.drawStringRight("(" + pdiff(player1, player2, Type.F) + ") " + f + " :Finals score",
		940, 305, 30, pstringColor(player1, player2, Type.F));
		
		image.drawStringRight("(" + pdiff(player1, player2, Type.L) + ") " + l + " :Lobby score",
		940, 365, 30, pstringColor(player1, player2, Type.L));
		
		image.drawStringRight("(" + pdiff(player1, player2, Type.WQ) + ") " + wq + " :Wide Qual score",
		940, 425, 30, pstringColor(player1, player2, Type.WQ));
		
		image.drawStringRight("(" + pdiff(player1, player2, Type.LQF) + ") " + lqf + " :L/Q/F score",
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
