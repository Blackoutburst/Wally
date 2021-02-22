package commands;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparators.PlayerComparatorF;
import comparators.PlayerComparatorQ;
import comparators.PlayerComparatorRounds;
import comparators.PlayerComparatorTotal;
import comparators.PlayerComparatorWins;
import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.Stats;
import utils.Utils;

public class CommandStats extends CommandExecutable {

	public CommandStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		String data = null;
		
		String uuid = null;
		
		if (command.getArgs().length == 0) {
			if (Utils.isLinkedDiscord(command.getSender().getId())) {
				data = Request.getPlayerStatsUUID(Utils.getUUIDfromDiscord(command.getSender().getId()));
				uuid = Utils.getUUIDfromDiscord(command.getSender().getId());
			} else {
				return (badUsage());
			}
		} else {
			data = Request.getPlayerStats(command.getArgs()[0]);
			if (data == null) return (unknownPlayer(command.getArgs()[0]));
			if (API.getPlayer(data).equals(null)) return (unknownPlayer(command.getArgs()[0]));
			uuid = API.getUUID(data);
		}
		
		Canvas image = new Canvas(600, 400);
		
		if (command.getArgs().length == 0) {
			image.drawCustomBackground(Utils.getCustomBackground(Utils.getIGNfromDiscord(command.getSender().getId())), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(Utils.getIGNfromDiscord(command.getSender().getId())), 300, 70, 26, Color.white);
		} else {
			image.drawCustomBackground(Utils.getCustomBackground(command.getArgs()[0]), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(command.getArgs()[0]), 300, 70, 26, Color.white);
		}
		
		createImage(image, data);
		MessageSender.sendFile(command, "stats.png");
		Utils.addToLeaderBoard(uuid, data, command);
		return (true);
	}
	
	/**
	 * Create stats image
	 * @param image
	 * @param data
	 */
	private void createImage(Canvas image, String data) {
		image.drawImage("res/win.png", 100, 105, 24, 24);
		image.drawImage("res/wall.png", 100, 155, 24, 24);
		image.drawImage("res/q.png", 100, 205, 24, 24);
		image.drawImage("res/f.png", 100, 255, 24, 24);
		image.drawImage("res/total.png", 100, 305, 24, 24);
		
		image.drawStringCenter(API.getName(data), 300, 40, 32, Color.white);
		
		image.drawStringLeft("Wins: " + API.getWins(data) + getLBPos(API.getName(data), 'w'), 150, 125, 24, Color.white);
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + getLBPos(API.getName(data), 'r'), 150, 175, 24, Color.white);
		image.drawStringLeft("Best qualification score: " + API.getQualification(data) + getLBPos(API.getName(data), 'q'), 150, 225, 24, Color.white);
		image.drawStringLeft("Best final score: " + API.getFinals(data) + getLBPos(API.getName(data), 'f'), 150, 275, 24, Color.white);
		image.drawStringLeft("Q/F total: " + API.getTotal(data) + getLBPos(API.getName(data), 't'), 150, 325, 24, Color.white);
		image.save("stats.png");
	}
	
	/**
	 * Get player position in the leader board
	 * @param user
	 * @return
	 */
	private String getLBPos(String user, char type) {
		List<LeaderboardPlayer> lead = generatePlayerList(new File("leaderboard"));
		
		switch (type) {
			case 'w': Collections.sort(lead, new PlayerComparatorWins());break;
			case 'r': Collections.sort(lead, new PlayerComparatorRounds());break;
			case 'q': Collections.sort(lead, new PlayerComparatorQ());break;
			case 'f': Collections.sort(lead, new PlayerComparatorF());break;
			case 't': Collections.sort(lead, new PlayerComparatorTotal());break;
		}
		int i = 0;
		for (LeaderboardPlayer p : lead) {
			i++; 
			if (p.name.equals(user)) {
				return (" (#"+String.valueOf(i)+")");
			}
		}
		return ("");
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
			String data = Utils.readJsonToString(playerFolder + "/data.json");
			
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
}
