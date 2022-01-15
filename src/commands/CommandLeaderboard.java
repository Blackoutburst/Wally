package commands;

import java.util.List;

import core.Command;
import core.CommandExecutable;
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.Leaderboard;
import utils.LeaderboardCanvas;
import utils.LeaderboardList;

public class CommandLeaderboard extends CommandExecutable {

	public CommandLeaderboard(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		char type = getType();
		int page = getPage();
		
		List<LeaderboardPlayer> lead = null;
		
		switch(type) {
			case 'w': lead = LeaderboardList.WINS; break;
			case 'r': lead = LeaderboardList.WALLS; break;
			case 'q': lead = LeaderboardList.Q; break;
			case 'f': lead = LeaderboardList.F; break;
			case 't': lead = LeaderboardList.QF; break;
			case 'o': lead = LeaderboardList.OVERALL; break;
			default:
				MessageSender.message(command,"This leaderboard doesn't exist, try one of these [W, R, Q, F, T, O]");
			return false;
		}
		
		Leaderboard lb = new Leaderboard(lead, type);
		
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
	 * Get leader board type
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
