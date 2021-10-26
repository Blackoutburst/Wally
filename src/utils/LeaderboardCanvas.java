package utils;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import core.RolesManager;


public class LeaderboardCanvas {

	/**
	 * Create leaderboard image
	 * @param image
	 * @param data
	 */
	public static void createLeaderboardImage(Canvas image, int page, Leaderboard lb) {
		int f = lb.getPlayers().get((10 * page)).finals;
		int q = lb.getPlayers().get((10 * page)).qualification;
		int score = q > f ? q : f;
		
		
		image.drawImage("res/backgroundXL.png", 0, 0, 1000, 770);
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int y = 0;
		
		image.drawStringCenter(lb.getCanvasName(), 502, 142, 60, Color.white.darker().darker().darker());
		image.drawStringCenter(lb.getCanvasName(), 500, 140, 60, Color.white);
		
		
		for (int i = (10 * page); i < (10 * page) + 10; i++) {
			if (i >= lb.getPlayers().size()) break;
			LeaderboardPlayer player = lb.getPlayers().get(i);
			String str = "(#" + (i + 1) + ") " + player.name + " - " + formatter.format(lb.getPlayerStat(i));
			Color c = (y % 2) == 0 ? RolesManager.getRoleColor(score) : Color.white;
			image.drawStringCenter(str, 502, 202 + (60 * y), 40, c.darker().darker().darker());
			image.drawStringCenter(str, 500, 200 + (60 * y), 40, c);
			y++;
		}
		
		image.save("lead.png");
	}
	
	/**
	 * Create leaderboard image
	 * @param image
	 * @param data
	 */
	public static void createPLeaderboardImage(Canvas image, int page, PracticeLeaderboard lb) {
		int q = lb.getPlayers().get((10 * page)).finals;
		int f = lb.getPlayers().get((10 * page)).qualification;
		int wq = lb.getPlayers().get((10 * page)).wideQualification;
		int l = lb.getPlayers().get((10 * page)).lobby;
		int score = 0;
		
		if (q >= f && q >= wq && q >= l) score = q;
		if (f >= q && f >= wq && f >= l) score = f;
		if (wq >= f && wq >= q && wq >= l) score = wq;
		if (l >= f && l >= q && l >= wq) score = l;
		
		
		image.drawImage("res/backgroundXL.png", 0, 0, 1000, 770);
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.drawImage("res/practiceHeader.png", 0, 0, 1000, 400);
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int y = 0;
		
		image.drawStringCenter(lb.getCanvasName(), 502, 142, 60, Color.white.darker().darker().darker());
		image.drawStringCenter(lb.getCanvasName(), 500, 140, 60, Color.white);
		
		
		for (int i = (10 * page); i < (10 * page) + 10; i++) {
			if (i >= lb.getPlayers().size()) break;
			PracticeLeaderboardPlayer player = lb.getPlayers().get(i);
			String str = "(#" + (i + 1) + ") " + player.name + " - " + formatter.format(lb.getPlayerStat(i));
			Color c = (y % 2) == 0 ? RolesManager.getRoleColor(score) : Color.white;
			image.drawStringCenter(str, 502, 202 + (60 * y), 40, c.darker().darker().darker());
			image.drawStringCenter(str, 500, 200 + (60 * y), 40, c);
			y++;
		}
		
		image.save("plead.png");
	}
}
