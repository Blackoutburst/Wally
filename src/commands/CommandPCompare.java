package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.*;

public class CommandPCompare extends CommandExecutable {

	public CommandPCompare(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage(this));
		
		String player1;
		String player2;
		String player1Uuid;
		String player2Uuid;
		String player1Name;
		String player2Name;
		
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

		if (player1Uuid == null)
			return unknownPlayer(this, command.getArgs()[0]);

		if (player1Uuid.equals("640459a7a5c8417bbb06f6e6d28614a1"))
			return gameOver(this);

		if (player2Uuid == null)
			return unknownPlayer(this, command.getArgs()[0]);

		if (player2Uuid.equals("640459a7a5c8417bbb06f6e6d28614a1"))
			return gameOver(this);
		
		player1 = Request.getPracticeServerScore(player1Uuid);
		if (player1 != null && player1.contains("Unknown player")) {
			return unknownPlayer(this, player1Name);
		}
		
		player2 = Request.getPracticeServerScore(player2Uuid);
		if (player2 != null && player2.contains("Unknown player")) {
			return unknownPlayer(this, player2Name);
		}

		player1Name = PracticeStats.getName(player1);
		player2Name = PracticeStats.getName(player2);
		
		Canvas image = new Canvas(1000, 640);
		
		CompareCanvas.createPCompareImage(image, player1Uuid, player2Uuid, player1, player2, player1Name, player2Name);

		MessageSender.sendFile(command, "pcompare.png");
		return (true);
	}
}
