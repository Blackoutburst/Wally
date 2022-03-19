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

	public CommandCompare(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage(this));
		
		String player1;
		String player2;
		
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
		} else if (command.getArgs().length == 2) {
			player1 = Request.getPlayerStats(command.getArgs()[0]);
			if (player1 == null) return (unknownPlayer(this, command.getArgs()[0]));
			if (API.getPlayer(player1) == null) return (neverJoined(this, command.getArgs()[0]));
			if(!GeneralUtils.isValidJSON(player1)) return (apiDead(this));
			
			player2 = Request.getPlayerStats(command.getArgs()[1]);
			if (player2 == null) return (unknownPlayer(this, command.getArgs()[1]));
			if (API.getPlayer(player2) == null) return (neverJoined(this, command.getArgs()[1]));
			if(!GeneralUtils.isValidJSON(player2)) return (apiDead(this));
		} else {
			String[] players = new String[command.getArgs().length];
			
			for (int i = 0; i < command.getArgs().length; i++) {
				players[i] = Request.getPlayerStats(command.getArgs()[i]);
				if (players[i] == null) return (unknownPlayer(this, command.getArgs()[i]));
				if (API.getPlayer(players[i]) == null) return (neverJoined(this, command.getArgs()[i]));
				if(!GeneralUtils.isValidJSON(players[i])) return (apiDead(this));
			}

			for (String p : players) {
				String uuid = API.getUUID(p);
				if (uuid == null)
					return unknownPlayer(this, command.getArgs()[0]);

				if (uuid.equals("640459a7a5c8417bbb06f6e6d28614a1"))
					return gameOver(this);
			}

			Canvas image = new Canvas(1000, 200 + (180 * players.length));
			
			CompareCanvas.createMultiCompareImage(image, players);

			MessageSender.sendFile(command, "compare.png");
			
			return (true);
		}
		
		Canvas image = new Canvas(1000, 520);
		
		CompareCanvas.createCompareImage(image, player1, player2);

		MessageSender.sendFile(command, "compare.png");
		return (true);
	}
}
