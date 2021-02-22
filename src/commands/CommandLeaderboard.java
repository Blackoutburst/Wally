package commands;

import java.awt.Color;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import comparators.PlayerComparatorF;
import comparators.PlayerComparatorQ;
import comparators.PlayerComparatorRounds;
import comparators.PlayerComparatorTotal;
import comparators.PlayerComparatorWins;
import core.Command;
import core.CommandExecutable;
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.Stats;
import utils.Utils;

public class CommandLeaderboard extends CommandExecutable {

	enum Type {
		W,
		R,
		Q,
		F,
		T
	}
	
	public CommandLeaderboard(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		Type type = getType();
		int page = getPage();
		boolean discord = isDiscord();
		String fileName = discord ? "linked player" : "leaderboard";
		File index = new File(fileName);
		Canvas image = new Canvas(600, 400);
		List<LeaderboardPlayer> lead = generatePlayerList(index);
		
		lead = sort(type, lead);
		generateCanvas(image, type, page, lead);
		MessageSender.sendFile(command, "lead.png");
		return (true);
	}
	
	/**
	 * Generate leader board canvas
	 * @param image
	 * @param type
	 * @param page
	 * @param lead
	 */
	private void generateCanvas(Canvas image, Type type, int page, List<LeaderboardPlayer> lead) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int y = 0;
		
		page--;
		image.drawBackground();
		image.drawStringCenter(getCanvasName(type), 300, 40, 32, Color.white);

		for (int i = (10 * page); i < (10 * page) + 10; i++) {
			if (i > lead.size()) break;
			LeaderboardPlayer player = lead.get(i);
			String str = "";
			switch (type) {
				case W: str = "#" + (i+1) + " "+player.name + " - " + formatter.format(player.wins); break;
				case R: str = "#" + (i+1) + " "+player.name + " - " + formatter.format(player.walls); break;
				case Q: str = "#" + (i+1) + " "+player.name + " - " + formatter.format(player.qualification); break;
				case F: str = "#" + (i+1) + " "+player.name + " - " + formatter.format(player.finals); break;
				case T: str = "#" + (i+1) + " "+player.name + " - " + formatter.format(player.total); break;
			}
			image.drawStringCenter(str, 300, 75+(35*y), 26, Color.white);
			y++;
		}
		image.save("lead.png");
	}
	
	/**
	 * Get canvas name from leader board type
	 * @param type
	 * @return
	 */
	private String getCanvasName(Type type) {
		switch (type) {
			case W: return ("Wins Leaderboard");
			case R: return ("Walls cleared Leaderboard");
			case Q: return ("Qualification Leaderboard");
			case F: return ("Finals Leaderboard");
			case T: return ("Q/F Total Leaderboard");
			default: return ("Wins Leaderboard");
		}
	}
	
	/**
	 * Sort leader board
	 * @param type
	 * @param lead
	 * @return
	 */
	private List<LeaderboardPlayer> sort(Type type, List<LeaderboardPlayer> lead) {
		switch (type) {
			case W: Collections.sort(lead, new PlayerComparatorWins());break;
			case R: Collections.sort(lead, new PlayerComparatorRounds());break;
			case Q: Collections.sort(lead, new PlayerComparatorQ());break;
			case F: Collections.sort(lead, new PlayerComparatorF());break;
			case T: Collections.sort(lead, new PlayerComparatorTotal());break;
		}
		return (lead);
	}
	
	/**
	 * Generate list of player from specified data folder
	 * @param index
	 * @return
	 */
	private List<LeaderboardPlayer> generatePlayerList(File index) {
		List<LeaderboardPlayer> lead = new ArrayList<LeaderboardPlayer>();
		String name = "";
		int wins = 0;
		int rounds = 0;
		int qualification = 0;
		int finals = 0;
		int total = 0;
		
		String[]entries = index.list();
		for(String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			String data = Utils.readJsonToString(playerFolder+"/data.json");
			
			name = Stats.getName(data);
			wins = Stats.getWinsToInt(data);
			rounds = Stats.getWallsToInt(data);
			qualification = Stats.getQualificationToInt(data);
			finals = Stats.getFinalsToInt(data);
			total = Stats.getTotalToInt(data);
			lead.add(new LeaderboardPlayer(wins, rounds, qualification, finals, total, name));
		}
		return (lead);
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
	private Type getType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1) {
				switch(arg.toLowerCase().charAt(0)) {
					case 'w' : return (Type.W);
					case 'r' : return (Type.R);
					case 'q' : return (Type.Q);
					case 'f' : return (Type.F);
					case 't' : return (Type.T);
				}
			}
		}
		return (Type.W);
	}
	
	/**
	 * Get leader board page
	 * @return
	 */
	private int getPage() {
		int page = 1;
		
		for (String arg : command.getArgs()) {
			try {
				page = Integer.valueOf(arg);
				break;
			} catch(Exception e) {}
		}
		if (page <= 0) page = 1;
		return (page);
	}
}
