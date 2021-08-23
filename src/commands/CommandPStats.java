package commands;


import core.AltExcluder;
import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.Canvas;
import utils.GeneralUtils;
import utils.MessageSender;
import utils.StatsCanvas;

public class CommandPStats extends CommandExecutable {

	public CommandPStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String uuid = null;
		String data = null;
		String playerName = null;
		
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
		if (data.contains("Unknown player")) {
			return unknownPlayer(this, playerName);
		}
		
		Canvas image = new Canvas(1000, 400);
		
		StatsCanvas.createPStatsImage(image, uuid, playerName, data.replace("\n", ""));
		MessageSender.sendFile(command, "pstats.png");
		AltExcluder.isAnAlt(uuid, this.command);
		return (true);
	}
}
