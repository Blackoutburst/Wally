package commands;


import core.*;
import net.dv8tion.jda.api.entities.Member;
import utils.*;

public class CommandRawStats extends CommandExecutable {

	public CommandRawStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String data;
		String uuid;
		
		if (command.getArgs().length == 0) {
			if (GeneralUtils.isLinkedDiscord(command.getSender().getId())) {
				data = Request.getPlayerStatsUUID(GeneralUtils.getUUIDfromDiscord(command.getSender().getId()));
				uuid = GeneralUtils.getUUIDfromDiscord(command.getSender().getId());
				if(!GeneralUtils.isValidJSON(data)) return (apiDead(this));
			} else {
				return (badUsage(this));
			}
		} else {
			data = Request.getPlayerStats(command.getArgs()[0]);
			if (data == null) return (unknownPlayer(this, command.getArgs()[0]));
			if(!GeneralUtils.isValidJSON(data)) return (apiDead(this));
			if (API.getPlayer(data) == null) return (neverJoined(this, command.getArgs()[0]));
			uuid = API.getUUID(data);
		}
		
		LeaderboardList.sort();

		final int lvl = (int) (1.0 + -8750.0 / 2500.0 + Math.sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (double)API.getLevelToInt(data)));

		final String discordId = GeneralUtils.getDiscordfromIGN(API.getName(data));
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

		final int wlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WINS);
		final int rlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.WALLS);
		final int qlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.Q);
		final int flb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.F);
		final int tlb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.QF);
		final int olb = GeneralUtils.getLBPosToIntFast(uuid, LeaderboardList.OVERALL);

		float rank = wlb + rlb + qlb + flb + tlb;
		rank = (rank / 5) + 1;

		final String wins = "Wins: " + API.getWins(data) + ((wlb != 10000) ? " (#"+ (wlb+1) + ")" : "");
		final String walls = "Walls: " + API.getWalls(data) + ((rlb != 10000) ? " (#"+ (rlb+1) + ")" : "");
		final String qualification = "Qualification: " + API.getQualification(data) + ((qlb != 10000) ? " (#"+ (qlb+1) + ")" : "");
		final String finals = "Finals: " + API.getFinals(data) + ((flb != 10000) ? " (#"+ (flb+1) + ")" : "");
		final String tot = "Q/F: " + API.getTotal(data) + ((tlb != 10000) ? " (#"+ (tlb+1) + ")" : "");
		String overall = "Overall rank: " + rank + ((olb != 10000) ? " (#" + (olb + 1) + ")" : "");

		if ((int)(rank) == 10001)
			overall = "Overall rank: N/A";

		String stats = "```yml\n" +
				"Name: "+API.getName(data)+"\n" +
				"Subtitle: "+Stats.getSubTitle(uuid)+"\n\n" +
				"Discord: "+discordNick+"\n" +
				"Network Level: "+lvl+"\n" +
				"Achievements Points: "+API.getAP(data)+"\n\n" +
				wins + "\n" +
				walls + "\n" +
				qualification + "\n" +
				finals + "\n" +
				tot + "\n" +
				overall + "\n" +
				"```";

		MessageSender.message(command, stats);

		String fileLocation = "leaderboard/" + uuid + "/data.json";
		String localData = GeneralUtils.readJsonToString(fileLocation);
		GeneralUtils.updateFile(data, localData, uuid, "leaderboard", false);

		if(!AltExcluder.isAnAlt(uuid, this.command) && API.getWinsToInt(data) >= 25) {
			GeneralUtils.addToLeaderBoard(uuid, data, command);
		}
		LeaderboardList.init();
		return (true);
	}
}
