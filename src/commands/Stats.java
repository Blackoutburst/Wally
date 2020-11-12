package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparator.PlayerComparatorF;
import comparator.PlayerComparatorQ;
import comparator.PlayerComparatorRounds;
import comparator.PlayerComparatorTotal;
import comparator.PlayerComparatorWins;
import core.Lines;
import core.Player;
import core.Reader;
import core.Request;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Stats {
	/**
	 * Get user and set up value to create canvas
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		String[] msg = event.getMessage().getContentDisplay().split(" ");
		String user = "";
		String output = "";
		String[] value;
		String qualification = "0";
		String finals = "0";
		String wins = "0";
		String rounds = "0";
		String total = "0";
		String uuid = "";
		boolean linked = false;
		
		if (msg.length < 2) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!stats player")).complete();
			return;
		}
		user = msg[1];
		
		
		if (Request.getPlayerUUID(user) == null) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
			return;
		}
		
		output = Request.getPlayerInfo(user);
		if (output.equals("API LIMITATION")) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.api_error)).complete();
			return;
		}
		
		if (user.toLowerCase().equals("wally")) {
			getWallyStats(user, event, formatter);
			return;
		}
		
		value = output.split("\n");
		for (int i = 0; i < value.length; i++) {
			if (value[i].contains("hitw_record_q")) {
				qualification = value[i].replaceAll("[^0-9]", "");
			}
			if (value[i].contains("hitw_record_f")) {
				finals = value[i].replaceAll("[^0-9]", "");
			}
			if (value[i].contains("rounds_hole_in_the_wall")) {
				rounds = value[i].replaceAll("[^0-9]", "");
			}
			if (value[i].contains("displayname")) {
				user = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
			}
			if (value[i].contains("wins_hole_in_the_wall")) {
				wins = value[i].replaceAll("[^0-9]", "");
			}
		}
		
		uuid = Request.getPlayerUUID(user);
		File index = new File("linked player");
		String[]entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			if (f.getName().equals(uuid)) {
				linked = true;
			}
		}
		
		
		total = String.valueOf(Integer.valueOf(qualification) + Integer.valueOf(finals));
		qualification = formatter.format(Double.parseDouble(qualification));
		finals = formatter.format(Double.parseDouble(finals));
		wins = formatter.format(Double.parseDouble(wins));
		rounds = formatter.format(Double.parseDouble(rounds));
		total = formatter.format(Double.parseDouble(total));

		createCanvas(user, qualification, finals, wins, rounds, total, linked, uuid);
		event.getChannel().sendFile(new File("stats.png")).complete();
	}
	
	
	private static void getWallyStats(String user, MessageReceivedEvent event, DecimalFormat formatter) {
		List<Player> player = new ArrayList<Player>();
		String qualification = "0";
		String finals = "0";
		String wins = "0";
		String rounds = "0";
		String total = "0";
		String uuid = "";
		boolean linked = false;
		
		uuid = Request.getPlayerUUID(user);
		File index = new File("linked player");
		String[]entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			if (f.getName().equals(uuid)) {
				linked = true;
			}
		}
		
		index = new File("leaderboard");
		
		entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			
			wins = readValue(f+"/W");
			rounds = readValue(f+"/R");
			qualification = readValue(f+"/Q");
			finals = readValue(f+"/F");
			total = qualification + finals;
			player.add(new Player(Integer.valueOf(wins), Integer.valueOf(rounds), Integer.valueOf(qualification), Integer.valueOf(finals), Integer.valueOf(total), "", ""));
		}
		
		Collections.sort(player, new PlayerComparatorWins());Collections.reverse(player);
		wins = String.valueOf(player.get(0).W+1);
		Collections.sort(player, new PlayerComparatorRounds());Collections.reverse(player);
		rounds = String.valueOf(player.get(0).R+1);
		Collections.sort(player, new PlayerComparatorQ());Collections.reverse(player);
		qualification = String.valueOf(player.get(0).Q+1);
		Collections.sort(player, new PlayerComparatorF());Collections.reverse(player);
		finals = String.valueOf(player.get(0).F+1);
		Collections.sort(player, new PlayerComparatorTotal());Collections.reverse(player);
		total = String.valueOf(player.get(0).total+1);
		
		total = String.valueOf(Integer.valueOf(qualification) + Integer.valueOf(finals));
		qualification = formatter.format(Double.parseDouble(qualification));
		finals = formatter.format(Double.parseDouble(finals));
		wins = formatter.format(Double.parseDouble(wins));
		rounds = formatter.format(Double.parseDouble(rounds));
		total = formatter.format(Double.parseDouble(total));
		
		createCanvas(user, qualification, finals, wins, rounds, total, linked, uuid);
		event.getChannel().sendFile(new File("stats.png")).complete();
	}
	
	private static String readValue(String file) {
		String str = "0";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (Exception e) {
			System.err.println("Corrupted: "+file);
		}
		return str;
	}
	
	/**
	 * Call canvas scripts (JS)
	 * @param user
	 * @param qualification
	 * @param finals
	 * @param wins
	 * @param rounds
	 * @see stats.js
	 * @author Blackoutburst
	 */
	private static void createCanvas(String user, String qualification, String finals, String wins, String rounds, String total, boolean linked, String uuid) {
		ProcessBuilder pb = new ProcessBuilder("node", "stats.js", user, qualification, finals, wins, rounds, total, String.valueOf(linked), uuid);
		
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
