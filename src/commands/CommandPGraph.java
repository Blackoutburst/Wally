package commands;


import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.Canvas;
import utils.GeneralUtils;
import utils.GraphCanvas;
import utils.MessageSender;

public class CommandPGraph extends CommandExecutable {

	public CommandPGraph(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String uuid = null;
		String data = null;
		String playerName = null;
		int games = 100;
		
		if (command.getArgs().length == 0 || (command.getArgs().length == 1 && command.getArgs()[0].length() <= 2) || (command.getArgs().length == 2 && command.getArgs()[0].length() <= 2)) {
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
		
		if (command.getArgs().length >= 2) {
			try {
				games = Integer.valueOf(command.getArgs()[1]);
			} catch(Exception e) {
				MessageSender.message(command, command.getArgs()[1]+" is not a valid amount of game");
			}
		}
		
		if (games <= 1) {
			games = 2;
			MessageSender.message(command, "At least two games are required for this to work");
		}
		
		if (games > 100) {
			games = 100;
			MessageSender.message(command, "Only the last 100 games are saved impossible to display more");
		}
		
		Canvas image = new Canvas(1000, 520);
		
		GraphCanvas.createPGraphImage(image, playerName, data, getType(), games);
		MessageSender.sendFile(command, "pgraph.png");

		return (true);
	}
	
	private String getType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1 || arg.length() == 2) {
				switch(arg.toLowerCase()) {
					case "q": return ("Qualification");
					case "f": return ("Finals");
					case "wq": return ("Wide Qualification");
					case "l": return ("Lobby");
				}
			}
		}
		return ("Qualification");
	}
}
