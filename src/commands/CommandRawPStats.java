package commands;


import core.*;
import net.dv8tion.jda.api.entities.Member;
import utils.*;

public class CommandRawPStats extends CommandExecutable {

	public CommandRawPStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String uuid;
		String data;
		String playerName;
		
		if (command.getArgs().length == 0) {
			if (GeneralUtils.isLinkedDiscord(command.getSender().getId())) {
				uuid = GeneralUtils.getUUIDfromDiscord(command.getSender().getId());
				playerName = GeneralUtils.getIGNfromDiscord(command.getSender().getId());
			} else {
				return (badUsage(this));
			}
		} else {
			uuid = Request.getPlayerUUID(command.getArgs()[0]);
			playerName = command.getArgs()[0];
		}

		if (uuid == null) {
			return unknownPlayer(this, playerName);
		}

		if (uuid.equals("640459a7a5c8417bbb06f6e6d28614a1"))
			return gameOver(this);

		data = Request.getPracticeServerScore(uuid);
		if (data != null && data.contains("Unknown player")) {
			return unknownPlayer(this, playerName);
		}

		LeaderboardList.pSort();

		final String discordId = GeneralUtils.getDiscordfromIGN(PracticeAPI.getName(data));
		String discordNick = "N/A";
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);

			if (member != null) {
				discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator();
			}
			discordNick = discordNick.replaceAll("@", "@ ");
		}

		final int q = PracticeAPI.getQualificationToInt(data);
		final int f = PracticeAPI.getFinalsToInt(data);
		final int wq = PracticeAPI.getWideQualificationToInt(data);
		final int l = PracticeAPI.getLobbyToInt(data);
		final int wf = PracticeAPI.getWideFinalsToInt(data);

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
		final String wide = "Wide Qualification: " + wq + ((wlb != 10000) ? " (#"+ (wlb+1) + ")" : "");
		final String wideF = "Wide Finals: " + wq + ((wflb != 10000) ? " (#"+ (wflb+1) + ")" : "");
		final String tot = "Total: " + (l+q+f+wq+wf) + ((tlb != 10000) ? " (#"+ (tlb+1) + ")" : "");

		float rank = qlb + flb + llb + wlb + tlb;
		rank = (rank / 5) + 1;

		String overall = "Overall rank: " + rank + ((olb != 10000) ? " (#" + (olb + 1) + ")" : "");

		if ((int)(rank) == 10001)
			overall = "Overall rank: N/A";

		String stats = "```yml\n" +
				"Name: "+PracticeAPI.getName(data)+"\n" +
				"Subtitle: "+Stats.getSubTitle(uuid)+"\n\n" +
				"Discord: "+discordNick+"\n\n" +
				qual + "\n" +
				finals + "\n" +
				lobby + "\n" +
				wide + "\n" +
				wideF + "\n" +
				tot + "\n" +
				overall + "\n" +
				"```";

		MessageSender.message(command, stats);

		String fileLocation = "pleaderboard/" + uuid + "/data.json";
		String localData = GeneralUtils.readJsonToString(fileLocation);
		GeneralUtils.updateFile(data, localData, uuid, "pleaderboard", true);

		if(!AltExcluder.isAnAlt(uuid, this.command)) {
			GeneralUtils.addToPracticeLeaderBoard(uuid, data, command);
		}
		LeaderboardList.pInit();
		return (true);
	}
}
