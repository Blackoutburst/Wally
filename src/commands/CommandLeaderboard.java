package commands;

import java.io.File;
import java.util.List;

import core.Command;
import core.CommandExecutable;
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.GeneralUtils;
import utils.Leaderboard;
import utils.LeaderboardCanvas;

public class CommandLeaderboard extends CommandExecutable {

	public CommandLeaderboard(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		char type = getType();
		int page = getPage();
		boolean discord = isDiscord();
		String fileName = discord ? "linked player" : "leaderboard";
		File index = new File(fileName);
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(index);
		Leaderboard lb = new Leaderboard(lead, type);
		lb.sort();
		
		int pass = 0;
		for (int i = (10 * page); i < (10 * page) + 10; i++) {
			if (i > lb.getPlayers().size() && pass == 0) return(largeValue(this));
			pass++;
		}

		Canvas image = new Canvas(1000, 770);

		LeaderboardCanvas.createLeaderboardImage(image, page, lb);
		MessageSender.sendFile(command, "lead.png");
		return (true);
	}

	/**
	 * Check is the leader board is discord only
	 * @return
	 */
	private boolean isDiscord() {
		for (String arg : command.getArgs()) {
			if (arg.equalsIgnoreCase("discord")) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Get leader board type
	 * @return
	 */
	private char getType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1) {
				return (arg.toLowerCase().charAt(0));
			}
		}
		return ('w');
	}

	/**
	 * Get leader board page
	 * @return
	 */
	private int getPage() {
		int page = 1;

		for (String arg : command.getArgs()) {
			try {
				page = Integer.parseInt(arg);
				break;
			} catch(Exception e) {}
		}
		if (page <= 0) page = 1;
		page--;
		return (page);
	}
}
