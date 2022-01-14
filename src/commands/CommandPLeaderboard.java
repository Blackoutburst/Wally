package commands;

import core.Command;
import core.CommandExecutable;
import utils.*;

import java.util.List;
import java.util.Locale;

public class CommandPLeaderboard extends CommandExecutable {

	public CommandPLeaderboard(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		String type = getType();
		int page = getPage();

		List<PracticeLeaderboardPlayer> lead = null;

		switch(type.toLowerCase(Locale.ROOT)) {
			case "q": lead = LeaderboardList.PQ; break;
			case "f": lead = LeaderboardList.PF; break;
			case "l": lead = LeaderboardList.L; break;
			case "wq": lead = LeaderboardList.WQ; break;
			case "wf": lead = LeaderboardList.WF; break;
			case "t": lead = LeaderboardList.LQF; break;
			case "o": lead = LeaderboardList.POVERALL; break;
		}

		PracticeLeaderboard lb = new PracticeLeaderboard(lead, type);

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
	 */
	private int getPage() {
		int page = 1;

		for (String arg : command.getArgs()) {
			try {
				page = Integer.parseInt(arg);
				break;
			} catch(Exception ignored) {}
		}
		if (page <= 0) page = 1;
		page--;
		return (page);
	}
}
