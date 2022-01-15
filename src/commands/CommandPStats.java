package commands;


import core.AltExcluder;
import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.*;

public class CommandPStats extends CommandExecutable {

	public CommandPStats(Command command, boolean admin, String errorMessage) {
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
		
		data = Request.getPracticeServerScore(uuid);
		if (data != null && data.contains("Unknown player")) {
			return unknownPlayer(this, playerName);
		}

		LeaderboardList.pSort();
		Canvas image = new Canvas(1000, 520);
		
		
		StatsCanvas.createPStatsImage(image, uuid, playerName, data);
		MessageSender.sendFile(command, "pstats.png");

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
