package utils;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Bot;
import net.dv8tion.jda.api.entities.Member;

public class StatsCanvas {

	/**
	 * Create stats image
	 * @param image
	 * @param data
	 */
	public static void createStatsImage(Canvas image, String data, String uuid) {
		final int f = API.getFinalsToInt(data);
		final int q = API.getQualificationToInt(data);
		final int score = q > f ? q : f;
		
		final String ap = API.getAP(data);
		final String name = API.getName(data);
		final Color color = GeneralUtils.getFontColor(uuid);
		final Color shadow = GeneralUtils.getFontColor(uuid).darker().darker().darker();
		
		if (GeneralUtils.hasCustomBackground(uuid)) {
			image.drawImage(GeneralUtils.getCustomBackground(uuid), 0, 80, 1000, 440);
		} else {
			image.drawImage(GeneralUtils.getCustomBackground(uuid), 0, 0, 1000, 520);
		}
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImage("res/win.png", 520, 125, 30, 30);
		image.drawImage("res/wall.png", 520, 185, 30, 30);
		image.drawImage("res/q.png", 520, 245, 30, 30);
		image.drawImage("res/f.png", 520, 305, 30, 30);
		image.drawImage("res/qf.png", 520, 365, 30, 30);
		image.drawImage("res/rank.png", 520, 425, 30, 30);
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 130, 110, 300, 85);
		else {
			image.drawStringLeft(name, 132, 142, 40, shadow);
			image.drawStringLeft(name, 130, 140, 40, color);
		}
		
		final String sub = Stats.getSubTitle(uuid);
		
		image.drawStringLeft(sub, 132, 182, 36, shadow);
		image.drawStringLeft(sub, 130, 180, 36, color);
		
		int lvl = (int) (1.0 + -8750.0 / 2500.0 + Math.sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (double)API.getLevelToInt(data)));
		
		final String discordId = GeneralUtils.getDiscordfromIGN(name);
		String discordNick = "N/A";
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);
			
			if (member.getEffectiveName().length() > 16) {
				discordNick = member.getEffectiveName().substring(0, 16)+"[...]" + "#" + member.getUser().getDiscriminator(); 
			} else {
				discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator(); 
			}
			discordNick = discordNick.replaceAll("@", "@ ");
		}
		
		if (!discordNick.equals("N/A")) {
			image.drawStringLeft(discordNick, 22, 252, 36, shadow);
			image.drawStringLeft("Level: " + lvl, 22, 312, 36, shadow);
			image.drawStringLeft("AP: " + ap, 22, 372, 36, shadow);
			
			image.drawStringLeft(discordNick, 20, 250, 36, color);
			image.drawStringLeft("Level: " + lvl, 20, 310, 36, color);
			image.drawStringLeft("AP: " + ap, 20, 370, 36, color);
		} else {
			image.drawStringLeft("Level: " + lvl, 22, 252, 36, shadow);
			image.drawStringLeft("AP: " + ap, 22, 312, 36, shadow);
			
			image.drawStringLeft("Level: " + lvl, 20, 250, 36, color);
			image.drawStringLeft("AP: " + ap, 20, 310, 36, color);
		}
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(new File("leaderboard"), true);
		final int wlb = GeneralUtils.getLBPosToInt(uuid, 'w', new ArrayList<LeaderboardPlayer>(lead));
		final int rlb = GeneralUtils.getLBPosToInt(uuid, 'r', new ArrayList<LeaderboardPlayer>(lead));
		final int qlb = GeneralUtils.getLBPosToInt(uuid, 'q', new ArrayList<LeaderboardPlayer>(lead));
		final int flb = GeneralUtils.getLBPosToInt(uuid, 'f', new ArrayList<LeaderboardPlayer>(lead));
		final int tlb = GeneralUtils.getLBPosToInt(uuid, 't', new ArrayList<LeaderboardPlayer>(lead));
		final int olb = GeneralUtils.getLBPosToInt(uuid, 'o', new ArrayList<LeaderboardPlayer>(lead));
		
		final String wins = "Wins: " + API.getWins(data) + ((wlb != 10000) ? " (#"+ (wlb+1) + ")" : "");
		final String walls = "Walls: " + API.getWalls(data) + ((rlb != 10000) ? " (#"+ (rlb+1) + ")" : "");
		final String qualification = "Qualification: " + API.getQualification(data) + ((qlb != 10000) ? " (#"+ (qlb+1) + ")" : "");
		final String finals = "Finals: " + API.getFinals(data) + ((flb != 10000) ? " (#"+ (flb+1) + ")" : "");
		final String tot = "Q/F: " + API.getTotal(data) + ((tlb != 10000) ? " (#"+ (tlb+1) + ")" : "");
		
		image.drawStringLeft(wins, 562, 152, 30, shadow);
		image.drawStringLeft(walls, 562, 212, 30, shadow);
		image.drawStringLeft(qualification, 562, 272, 30, shadow);
		image.drawStringLeft(finals, 562, 332, 30, shadow);
		image.drawStringLeft(tot, 562, 392, 30, shadow);
		
		image.drawStringLeft(wins, 560, 150, 30, color);
		image.drawStringLeft(walls, 560, 210, 30, color);
		image.drawStringLeft(qualification, 560, 270, 30, color);
		image.drawStringLeft(finals, 560, 330, 30, color);
		image.drawStringLeft(tot, 560, 390, 30, color);
		
		float rank = wlb + rlb + qlb + flb + tlb;
		rank = (rank / 5) + 1;
		
		if ((int)(rank) == 10001) {
			image.drawStringLeft("Overall rank: N/A", 562, 452, 30, shadow);
			image.drawStringLeft("Overall rank: N/A", 560, 450, 30, color);
		} else {
			image.drawStringLeft("Overall rank: #"+rank + ((olb != 10000) ? " (#"+ (olb+1) + ")" : ""), 562, 452, 30, shadow);
			image.drawStringLeft("Overall rank: #"+rank + ((olb != 10000) ? " (#"+ (olb+1) + ")" : ""), 560, 450, 30, color);
		}
		
		image.save("stats.png");
	}
	
	/**
	 * Create practice stats image
	 * @param image
	 * @param data
	 */
	public static void createPStatsImage(Canvas image, String uuid, String name, String data) {
		final int q = PracticeAPI.getQualificationToInt(data);
		final int f = PracticeAPI.getFinalsToInt(data);
		final int wq = PracticeAPI.getWideQualificationToInt(data);
		final int l = PracticeAPI.getLobbyToInt(data);
		int score = 0;
		final Color color = GeneralUtils.getFontColor(uuid);
		final Color shadow = GeneralUtils.getFontColor(uuid).darker().darker().darker();
		
		if (q >= f && q >= wq && q >= l) score = q;
		if (f >= q && f >= wq && f >= l) score = f;
		if (wq >= f && wq >= q && wq >= l) score = wq;
		if (l >= f && l >= q && l >= wq) score = l;
		
		if (GeneralUtils.hasCustomBackground(uuid)) {
			image.drawImage(GeneralUtils.getCustomBackground(uuid), 0, 80, 1000, 440);
		} else {
			image.drawImage(GeneralUtils.getCustomBackground(uuid), 0, 0, 1000, 520);
		}
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.drawImage("res/practiceHeader.png", 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImage("res/q.png", 520, 125, 30, 30);
		image.drawImage("res/f.png", 520, 185, 30, 30);
		image.drawImage("res/l.png", 520, 245, 30, 30);
		image.drawImage("res/wq.png", 520, 305, 30, 30);
		image.drawImage("res/qf.png", 520, 365, 30, 30);
		image.drawImage("res/rank.png", 520, 425, 30, 30);
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 130, 110, 300, 85);
		else {
			image.drawStringLeft(name, 132, 142, 40, shadow);
			image.drawStringLeft(name, 130, 140, 40, color);
		}
		
		final String sub = Stats.getSubTitle(uuid);
		
		image.drawStringLeft(sub, 132, 182, 36, shadow);
		image.drawStringLeft(sub, 130, 180, 36, color);
		
		final String discordId = GeneralUtils.getDiscordfromIGN(name);
		String discordNick = "N/A";
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);
			
			if (member.getEffectiveName().length() > 16) {
				discordNick = member.getEffectiveName().substring(0, 16)+"[...]" + "#" + member.getUser().getDiscriminator(); 
			} else {
				discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator(); 
			}
			discordNick = discordNick.replaceAll("@", "@ ");
		}
		
		if (!discordNick.equals("N/A")) {
			image.drawStringLeft(discordNick, 22, 252, 36, shadow);
			image.drawStringLeft(discordNick, 20, 250, 36, color);
		}
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		
		List<PracticeLeaderboardPlayer> lead = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"), true);
		final int qlb = GeneralUtils.getPLBPosToInt(uuid, "q", new ArrayList<PracticeLeaderboardPlayer>(lead));
		final int flb = GeneralUtils.getPLBPosToInt(uuid, "f", new ArrayList<PracticeLeaderboardPlayer>(lead));
		final int llb = GeneralUtils.getPLBPosToInt(uuid, "l", new ArrayList<PracticeLeaderboardPlayer>(lead));
		final int wlb = GeneralUtils.getPLBPosToInt(uuid, "wq", new ArrayList<PracticeLeaderboardPlayer>(lead));
		final int tlb = GeneralUtils.getPLBPosToInt(uuid, "t", new ArrayList<PracticeLeaderboardPlayer>(lead));
		final int olb = GeneralUtils.getPLBPosToInt(uuid, "o", new ArrayList<PracticeLeaderboardPlayer>(lead));
		
		
		final String qual = "Qualification: " + q + ((qlb != 10000) ? " (#"+ (qlb+1) + ")" : "");
		final String finals = "Finals: " + f + ((flb != 10000) ? " (#"+ (flb+1) + ")" : "");
		final String lobby = "Lobby: " + l + ((llb != 10000) ? " (#"+ (llb+1) + ")" : "");
		final String wide = "Wide Qual: " + wq + ((wlb != 10000) ? " (#"+ (wlb+1) + ")" : "");
		final String tot = "L/Q/F/WQ: " + (l+q+f+wq) + ((tlb != 10000) ? " (#"+ (tlb+1) + ")" : "");
		
		image.drawStringLeft(qual, 562, 152, 30, shadow);
		image.drawStringLeft(finals, 562, 212, 30, shadow);
		image.drawStringLeft(lobby, 562, 272, 30, shadow);
		image.drawStringLeft(wide, 562, 332, 30, shadow);
		image.drawStringLeft(tot, 562, 392, 30, shadow);
		
		image.drawStringLeft(qual, 560, 150, 30, color);
		image.drawStringLeft(finals, 560, 210, 30, color);
		image.drawStringLeft(lobby, 560, 270, 30, color);
		image.drawStringLeft(wide, 560, 330, 30, color);
		image.drawStringLeft(tot, 560, 390, 30, color);
	
		float rank = qlb + flb + llb + wlb + tlb;
		rank = (rank / 5) + 1;
		
		if ((int)(rank) == 10001) {
			image.drawStringLeft("Overall rank: N/A", 562, 452, 30, shadow);
			image.drawStringLeft("Overall rank: N/A", 560, 450, 30, color);
		} else {
			image.drawStringLeft("Overall rank: #"+rank + ((olb != 10000) ? " (#"+ (olb+1) + ")" : ""), 562, 452, 30, shadow);
			image.drawStringLeft("Overall rank: #"+rank + ((olb != 10000) ? " (#"+ (olb+1) + ")" : ""), 560, 450, 30, color);
		}
		
		image.save("pstats.png");
	}
}
