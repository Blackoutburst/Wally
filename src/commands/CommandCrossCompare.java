package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.Canvas;
import utils.CompareCanvas;
import utils.GeneralUtils;
import utils.MessageSender;

public class CommandCrossCompare extends CommandExecutable {

	public CommandCrossCompare(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage(this));

		String player1Hypixel;
		String player2Hypixel;
		String player1Practice;
		String player2Practice;
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
		
		player1Practice = Request.getPracticeServerScore(player1Uuid);
		if (player1Practice != null && player1Practice.contains("Unknown player")) {
			return unknownPlayer(this, player1Name);
		}

		player2Practice = Request.getPracticeServerScore(player2Uuid);
		if (player2Practice != null && player2Practice.contains("Unknown player")) {
			return unknownPlayer(this, player2Name);
		}

		player1Hypixel = Request.getPlayerStatsUUID(player1Uuid);
		if (player1Hypixel != null && player1Hypixel.contains("Unknown player")) {
			return unknownPlayer(this, player1Name);
		}

		player2Hypixel = Request.getPlayerStatsUUID(player2Uuid);
		if (player2Hypixel != null && player2Hypixel.contains("Unknown player")) {
			return unknownPlayer(this, player2Name);
		}

		Canvas image = new Canvas(1000, 640);
		
		CompareCanvas.createCrossCompareImage(image, player1Uuid, player2Uuid, player1Hypixel, player2Hypixel, player1Practice, player2Practice, player2Name);

		MessageSender.sendFile(command, "ccompare.png");
		return (true);
	}
}
