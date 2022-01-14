package utils;

import core.Bot;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class StatsCanvas {

	/**
	 * Create stats image
	 */
	public static void createStatsImage(Canvas image, String data, String uuid) {
		final int f = API.getFinalsToInt(data);
		final int q = API.getQualificationToInt(data);
		final int score = Math.max(q, f);
		
		final String ap = API.getAP(data);
		final String name = API.getName(data);
		final Color color = GeneralUtils.getFontColor(uuid);
		final Color shadow = GeneralUtils.getFontColor(uuid).darker().darker().darker();
		
		if (GeneralUtils.hasCustomBackground(uuid)) {
			image.drawImage(GeneralUtils.getCustomBackground(uuid), 0, 80, 1000, 440);
		} else {
			image.drawImageFromMemory(LoadedRessources.BACKGROUND_LARGE, 0, 0, 1000, 520);
		}
		image.drawImageFromMemory(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImageFromMemory(LoadedRessources.WINS, 520, 125, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WALLS, 520, 185, 30, 30);
		image.drawImageFromMemory(LoadedRessources.Q, 520, 245, 30, 30);
		image.drawImageFromMemory(LoadedRessources.F, 520, 305, 30, 30);
		image.drawImageFromMemory(LoadedRessources.QF, 520, 365, 30, 30);
		image.drawImageFromMemory(LoadedRessources.RANK, 520, 425, 30, 30);
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImageFromMemory(LoadedRessources.BLACKOUT, 130, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImageFromMemory(LoadedRessources.VENOM, 130, 110, 300, 85);
		else {
			image.drawStringLeft(name, 132, 142, 40, shadow);
			image.drawStringLeft(name, 130, 140, 40, color);
		}
		
		final String sub = Stats.getSubTitle(uuid);
		
		image.drawStringLeft(sub, 132, 182, 36, shadow);
		image.drawStringLeft(sub, 130, 180, 36, color);
		
		final int lvl = (int) (1.0 + -8750.0 / 2500.0 + Math.sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (double)API.getLevelToInt(data)));
		
		final String discordId = GeneralUtils.getDiscordfromIGN(name);
		String discordNick = "N/A";
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);

			if (member != null) {
				if (member.getEffectiveName().length() > 16) {
					discordNick = member.getEffectiveName().substring(0, 16)+"[...]" + "#" + member.getUser().getDiscriminator(); 
				} else {
					discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator();
				}
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
		
		final int wlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WINS);
		final int rlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WALLS);
		final int qlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.Q);
		final int flb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.F);
		final int tlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.QF);
		final int olb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.OVERALL);
		
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
			final String overall = "Overall rank: #" + rank + ((olb != 10000) ? " (#" + (olb + 1) + ")" : "");
			image.drawStringLeft(overall, 562, 452, 30, shadow);
			image.drawStringLeft(overall, 560, 450, 30, color);
		}
		
		image.save("stats.png");
	}

	/**
	 * Create practice stats image
	 */
	public static void createPStatsImage(Canvas image, String uuid, String name, String data) {
		final int q = PracticeAPI.getQualificationToInt(data);
		final int f = PracticeAPI.getFinalsToInt(data);
		final int wq = PracticeAPI.getWideQualificationToInt(data);
		final int wf = PracticeAPI.getWideFinalsToInt(data);
		final int l = PracticeAPI.getLobbyToInt(data);
		int score = 0;
		final Color color = GeneralUtils.getFontColor(uuid);
		final Color shadow = GeneralUtils.getFontColor(uuid).darker().darker().darker();
		
		if (q >= f && q >= wq && q >= l && q >= wf) score = q;
		if (f >= q && f >= wq && f >= l && f >= wf) score = f;
		if (wq >= f && wq >= q && wq >= l && l >= wf) score = wq;
		if (l >= f && l >= q && l >= wq && l >= wf) score = l;
		if (wf >= f && wf >= q && wf >= wq && wf >= l) score = wf;
		
		if (GeneralUtils.hasCustomBackground(uuid)) {
			image.drawImage(GeneralUtils.getCustomBackground(uuid), 0, 80, 1000, 440);
		} else {
			image.drawImage(GeneralUtils.getCustomBackground(uuid), 0, 0, 1000, 520);
		}
		image.drawImageFromMemory(Canvas.getHeader(score), 0, 0, 1000, 400);
		image.drawImageFromMemory(LoadedRessources.PHEADER, 0, 0, 1000, 400);
		image.loadFont(GeneralUtils.getCustomFont(uuid));
		
		image.drawImageFromMemory(LoadedRessources.Q, 520, 115, 30, 30);
		image.drawImageFromMemory(LoadedRessources.F, 520, 175, 30, 30);
		image.drawImageFromMemory(LoadedRessources.L, 520, 235, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WQ, 520, 295, 30, 30);
		image.drawImageFromMemory(LoadedRessources.WF, 520, 355, 30, 30);
		image.drawImageFromMemory(LoadedRessources.QF, 520, 415, 30, 30);
		image.drawImageFromMemory(LoadedRessources.RANK, 520, 475, 30, 30);
		
		if (uuid.equals("b8ef1c7615e04b958d474ca133561f5a"))
			image.drawImageFromMemory(LoadedRessources.BLACKOUT, 130, 110, 320, 80);
		else if (uuid.equals("92a5199614ac4bd181d1f3c951fb719f"))
			image.drawImageFromMemory(LoadedRessources.VENOM, 130, 110, 300, 85);
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

			if (member != null) {
				if (member.getEffectiveName().length() > 16) {
					discordNick = member.getEffectiveName().substring(0, 16)+"[...]" + "#" + member.getUser().getDiscriminator();
				} else {
					discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator();
				}
			}
			discordNick = discordNick.replaceAll("@", "@ ");
		}
		
		if (!discordNick.equals("N/A")) {
			image.drawStringLeft(discordNick, 22, 252, 36, shadow);
			image.drawStringLeft(discordNick, 20, 250, 36, color);
		}
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		
		final int qlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.PQ);
		final int flb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.PF);
		final int llb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.L);
		final int wlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.WQ);
		final int wflb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.WF);
		final int tlb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.LQF);
		final int olb = GeneralUtils.getPLBPosToIntFast(uuid, LeaderboardList.POVERALL);

		final String qual = "Qualification: " + q + ((qlb != 10000) ? " (#"+ (qlb+1) + ")" : "");
		final String finals = "Finals: " + f + ((flb != 10000) ? " (#"+ (flb+1) + ")" : "");
		final String lobby = "Lobby: " + l + ((llb != 10000) ? " (#"+ (llb+1) + ")" : "");
		final String wide = "Wide Q: " + wq + ((wlb != 10000) ? " (#"+ (wlb+1) + ")" : "");
		final String wideF = "Wide F: " + wf + ((wflb != 10000) ? " (#"+ (wflb+1) + ")" : "");
		final String tot = "Total: " + (l+q+f+wq+wf) + ((tlb != 10000) ? " (#"+ (tlb+1) + ")" : "");
		
		image.drawStringLeft(qual, 562, 142, 30, shadow);
		image.drawStringLeft(finals, 562, 202, 30, shadow);
		image.drawStringLeft(lobby, 562, 262, 30, shadow);
		image.drawStringLeft(wide, 562, 322, 30, shadow);
		image.drawStringLeft(wideF, 562, 382, 30, shadow);
		image.drawStringLeft(tot, 562, 442, 30, shadow);
		
		image.drawStringLeft(qual, 560, 140, 30, color);
		image.drawStringLeft(finals, 560, 200, 30, color);
		image.drawStringLeft(lobby, 560, 260, 30, color);
		image.drawStringLeft(wide, 560, 320, 30, color);
		image.drawStringLeft(wideF, 560, 380, 30, color);
		image.drawStringLeft(tot, 560, 440, 30, color);
	
		float rank = qlb + flb + llb + wlb + tlb;
		rank = (rank / 5) + 1;
		
		if ((int)(rank) == 10001) {
			image.drawStringLeft("Overall rank: N/A", 562, 452, 30, shadow);
			image.drawStringLeft("Overall rank: N/A", 560, 450, 30, color);
		} else {
			final String overall = "Overall rank: #" + rank + ((olb != 10000) ? " (#" + (olb + 1) + ")" : "");
			image.drawStringLeft(overall, 562, 502, 30, shadow);
			image.drawStringLeft(overall, 560, 500, 30, color);
		}
		
		image.save("pstats.png");
	}
}
