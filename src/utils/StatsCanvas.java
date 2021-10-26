package utils;

import java.io.File;
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
		int f = API.getFinalsToInt(data);
		int q = API.getQualificationToInt(data);
		int score = q > f ? q : f;
		
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
		
		if (API.getUUID(data).equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
		else if (API.getUUID(data).equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImage("res/venom.png", 130, 110, 300, 85);
		else {
			image.drawStringLeft(API.getName(data), 132, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft(API.getName(data), 130, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		if (API.getUUID(data).equals("640459a7a5c8417bbb06f6e6d28614a1"))
			image.drawImage("res/heart.png", 220, 156, 32, 28);
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 132, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, GeneralUtils.getFontColor(uuid));
		
		int lvl = (int) (1.0 + -8750.0 / 2500.0 + Math.sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (double)API.getLevelToInt(data)));
		
		String discordId = GeneralUtils.getDiscordfromIGN(API.getName(data));
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
			image.drawStringLeft(discordNick, 22, 252, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft("Hypixel level: " + lvl, 22, 312, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft("Achievement points: " + API.getAP(data), 22, 372, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			
			image.drawStringLeft(discordNick, 20, 250, 36, GeneralUtils.getFontColor(uuid));
			image.drawStringLeft("Hypixel level: " + lvl, 20, 310, 36, GeneralUtils.getFontColor(uuid));
			image.drawStringLeft("Achievement points: " + API.getAP(data), 20, 370, 36, GeneralUtils.getFontColor(uuid));
		} else {
			image.drawStringLeft("Hypixel level: " + lvl, 22, 252, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft("Achievement points: " + API.getAP(data), 22, 312, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			
			image.drawStringLeft("Hypixel level: " + lvl, 20, 250, 36, GeneralUtils.getFontColor(uuid));
			image.drawStringLeft("Achievement points: " + API.getAP(data), 20, 310, 36, GeneralUtils.getFontColor(uuid));
		}
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		image.drawStringLeft("Wins: " + API.getWins(data) + GeneralUtils.getLBPos(API.getUUID(data), 'w'), 562, 152, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + GeneralUtils.getLBPos(API.getUUID(data), 'r'), 562, 212, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Qualification score: " + API.getQualification(data) + GeneralUtils.getLBPos(API.getUUID(data), 'q'), 562, 272, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Finals score: " + API.getFinals(data) + GeneralUtils.getLBPos(API.getUUID(data), 'f'), 562, 332, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Q/F score: " + API.getTotal(data) + GeneralUtils.getLBPos(API.getUUID(data), 't'), 562, 392, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		
		image.drawStringLeft("Wins: " + API.getWins(data) + GeneralUtils.getLBPos(API.getUUID(data), 'w'), 560, 150, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + GeneralUtils.getLBPos(API.getUUID(data), 'r'), 560, 210, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("Qualification score: " + API.getQualification(data) + GeneralUtils.getLBPos(API.getUUID(data), 'q'), 560, 270, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("Finals score: " + API.getFinals(data) + GeneralUtils.getLBPos(API.getUUID(data), 'f'), 560, 330, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("Q/F score: " + API.getTotal(data) + GeneralUtils.getLBPos(API.getUUID(data), 't'), 560, 390, 30, GeneralUtils.getFontColor(uuid));
		
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(new File("leaderboard"));
		float rank = GeneralUtils.getLBPosToInt(API.getUUID(data), 'w', lead);
		rank += GeneralUtils.getLBPosToInt(API.getUUID(data), 'r', lead);
		rank += GeneralUtils.getLBPosToInt(API.getUUID(data), 'q', lead);
		rank += GeneralUtils.getLBPosToInt(API.getUUID(data), 'f', lead);
		rank += GeneralUtils.getLBPosToInt(API.getUUID(data), 't', lead);
		rank = (rank / 5) + 1;
		
		image.drawStringLeft("Overall rank: #"+rank, 562, 452, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Overall rank: #"+rank, 560, 450, 30, GeneralUtils.getFontColor(uuid));
		
		image.save("stats.png");
	}
	
	/**
	 * Create practice stats image
	 * @param image
	 * @param data
	 */
	public static void createPStatsImage(Canvas image, String uuid, String name, String data) {
		int q = PracticeAPI.getQualificationToInt(data);
		int f = PracticeAPI.getFinalsToInt(data);
		int wq = PracticeAPI.getWideQualificationToInt(data);
		int l = PracticeAPI.getLobbyToInt(data);
		int score = 0;
		
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
			image.drawStringLeft(name, 132, 142, 40, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft(name, 130, 140, 40, GeneralUtils.getFontColor(uuid));
		}
		
		if (uuid.equals("640459a7a5c8417bbb06f6e6d28614a1"))
			image.drawImage("res/heart.png", 220, 156, 32, 28);
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 132, 182, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, GeneralUtils.getFontColor(uuid));
		
		String discordId = GeneralUtils.getDiscordfromIGN(name);
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
			image.drawStringLeft(discordNick, 22, 252, 36, GeneralUtils.getFontColor(uuid).darker().darker().darker());
			image.drawStringLeft(discordNick, 20, 250, 36, GeneralUtils.getFontColor(uuid));
		}
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		image.drawStringLeft("Qualification score: " + q + GeneralUtils.getPLBPos(uuid, "q"), 562, 152, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Finals score: " + f + GeneralUtils.getPLBPos(uuid, "f"), 562, 212, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Lobby score: " + l + GeneralUtils.getPLBPos(uuid, "l"), 562, 272, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Wide Qual score: " + wq + GeneralUtils.getPLBPos(uuid, "wq"), 562, 332, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("L/Q/F score: " + (l+q+f) + GeneralUtils.getPLBPos(uuid, "t"), 562, 392, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		
		image.drawStringLeft("Qualification score: " + q + GeneralUtils.getPLBPos(uuid, "q"), 560, 150, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("Finals score: " + f + GeneralUtils.getPLBPos(uuid, "f"), 560, 210, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("Lobby score: " + l + GeneralUtils.getPLBPos(uuid, "l"), 560, 270, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("Wide Qual score: " + wq + GeneralUtils.getPLBPos(uuid, "wq"), 560, 330, 30, GeneralUtils.getFontColor(uuid));
		image.drawStringLeft("L/Q/F score: " + (l+q+f) + GeneralUtils.getPLBPos(uuid, "t"), 560, 390, 30, GeneralUtils.getFontColor(uuid));
	
		List<PracticeLeaderboardPlayer> lead = GeneralUtils.generatePracticePlayerList(new File("pleaderboard"));
		float rank = GeneralUtils.getPLBPosToInt(uuid, "q", lead);
		rank += GeneralUtils.getPLBPosToInt(uuid, "f", lead);
		rank += GeneralUtils.getPLBPosToInt(uuid, "l", lead);
		rank += GeneralUtils.getPLBPosToInt(uuid, "wq", lead);
		rank += GeneralUtils.getPLBPosToInt(uuid, "t", lead);
		rank = (rank / 5) + 1;
		
		image.drawStringLeft("Overall rank: #"+rank, 562, 452, 30, GeneralUtils.getFontColor(uuid).darker().darker().darker());
		image.drawStringLeft("Overall rank: #"+rank, 560, 450, 30, GeneralUtils.getFontColor(uuid));
		
		
		image.save("pstats.png");
	}
}
