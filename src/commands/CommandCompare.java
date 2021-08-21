package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.Canvas;
import utils.CompareCanvas;
import utils.MessageSender;
import utils.GeneralUtils;

public class CommandCompare extends CommandExecutable {
	
	enum Type {
		WINS,
		WALLS,
		Q,
		F,
		T
	}
	
	public CommandCompare(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage(this));
		
		String player1 = null;
		String player2 = null;
		
		if (command.getArgs().length == 1) {
			if (GeneralUtils.isLinkedDiscord(command.getSender().getId())) {
				player1 = Request.getPlayerStatsUUID(GeneralUtils.getUUIDfromDiscord(command.getSender().getId()));
				
				player2 = Request.getPlayerStats(command.getArgs()[0]);
				if (player2 == null) return (unknownPlayer(this, command.getArgs()[0]));
				if (API.getPlayer(player2) == null) return (neverJoined(this, command.getArgs()[0]));
				if(!GeneralUtils.isValidJSON(player2)) return (apiDead(this));
			} else {
				return (badUsage(this));
			}
		} else {
			player1 = Request.getPlayerStats(command.getArgs()[0]);
			if (player1 == null) return (unknownPlayer(this, command.getArgs()[0]));
			if (API.getPlayer(player1) == null) return (neverJoined(this, command.getArgs()[0]));
			if(!GeneralUtils.isValidJSON(player1)) return (apiDead(this));
			
			player2 = Request.getPlayerStats(command.getArgs()[1]);
			if (player2 == null) return (unknownPlayer(this, command.getArgs()[1]));
			if (API.getPlayer(player2) == null) return (neverJoined(this, command.getArgs()[1]));
			if(!GeneralUtils.isValidJSON(player2)) return (apiDead(this));
		}
		
		Canvas image = new Canvas(1000, 520);
		
		CompareCanvas.createCompareImage(image, player1, player2);

		MessageSender.sendFile(command, "compare.png");
		return (true);
	}
}
