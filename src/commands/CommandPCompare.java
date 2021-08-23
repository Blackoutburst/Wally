package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.Canvas;
import utils.CompareCanvas;
import utils.MessageSender;
import utils.GeneralUtils;

public class CommandPCompare extends CommandExecutable {
	
	enum Type {
		WINS,
		WALLS,
		Q,
		F,
		T
	}
	
	public CommandPCompare(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage(this));
		
		String player1 = null;
		String player2 = null;
		String player1Uuid = null;
		String player2Uuid = null;
		String player1Name = null;
		String player2Name = null;
		
		if (command.getArgs().length == 1) {
			if (GeneralUtils.isLinkedDiscord(command.getSender().getId())) {
				player1Uuid = GeneralUtils.getUUIDfromDiscord(command.getSender().getId());
				player1Name = GeneralUtils.getIGNfromDiscord(command.getSender().getId());
				
				player2Uuid = Request.getPlayerUUID(command.getArgs()[0]);
				player2Name = command.getArgs()[0];
			} else {
				return (badUsage(this));
			}
		} else {
			player1Uuid = Request.getPlayerUUID(command.getArgs()[0]);
			player1Name = command.getArgs()[0];
			
			player2Uuid = Request.getPlayerUUID(command.getArgs()[1]);
			player2Name = command.getArgs()[1];
		}
		
		player1 = Request.getPracticeServerScore(player1Uuid);
		if (player1.contains("Unknown player")) {
			return unknownPlayer(this, player1Name);
		}
		
		player2 = Request.getPracticeServerScore(player2Uuid);
		if (player2.contains("Unknown player")) {
			return unknownPlayer(this, player2Name);
		}
		
		Canvas image = new Canvas(1000, 520);
		
		CompareCanvas.createPCompareImage(image, player1Uuid, player2Uuid, player1, player2, player1Name, player2Name);

		MessageSender.sendFile(command, "pcompare.png");
		return (true);
	}
}
