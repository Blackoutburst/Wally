package commands;

import java.io.File;
import java.util.List;

import core.Command;
import core.CommandExecutable;
import utils.Canvas;
import utils.GeneralUtils;
import utils.LeaderboardCanvas;
import utils.MessageSender;
import utils.PracticeLeaderboard;
import utils.PracticeLeaderboardPlayer;

public class CommandPLeaderboard extends CommandExecutable {

	public CommandPLeaderboard(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		String type = getType();
		int page = getPage();
		String fileName = "pleaderboard";
		File index = new File(fileName);
		List<PracticeLeaderboardPlayer> lead = GeneralUtils.generatePracticePlayerList(index, true);
		PracticeLeaderboard lb = new PracticeLeaderboard(lead, type);
		lb.sort();
		
		int pass = 0;
		for (int i = (10 * page); i < (10 * page) + 10; i++) {
			if (i > lb.getPlayers().size() && pass == 0) return(largeValue(this));
			pass++;
		}

		Canvas image = new Canvas(1000, 770);

		LeaderboardCanvas.createPLeaderboardImage(image, page, lb);
		MessageSender.sendFile(command, "plead.png");
		return (true);
	}

	/**
	 * Get leader board type
	 * @return
	 */
	private String getType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1 || arg.length() == 2) {
				return (arg.toLowerCase());
			}
		}
		return ("Q");
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
