package commands;


import core.AltExcluder;
import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.Canvas;
import utils.MessageSender;
import utils.StatsCanvas;
import utils.GeneralUtils;

public class CommandStats extends CommandExecutable {

	public CommandStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String data = null;
		String uuid = null;
		
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
		
		Canvas image = new Canvas(1000, 400);
		
		StatsCanvas.createStatsImage(image, data, uuid);
		MessageSender.sendFile(command, "stats.png");
		
		String fileLocation = "leaderboard/" + uuid + "/data.json";
		String localData = GeneralUtils.readJsonToString(fileLocation);
		GeneralUtils.updateFile(data, localData, uuid, "leaderboard");

		if(!AltExcluder.isAnAlt(uuid, this.command) && API.getWinsToInt(data) >= 25) {
			GeneralUtils.addToLeaderBoard(uuid, data, command);
		}
		return (true);
	}
}
