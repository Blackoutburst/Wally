package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Stats {
	/**
	 * Get user and set up value to create canvas
	 * @param event
	 */
	public static void display(MessageReceivedEvent event) {
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		String[] msg = event.getMessage().getContentDisplay().split(" ");
		String user = "";
		String output = "";
		String[] value;
		String qualification = "";
		String finals = "";
		String wins = "";
		String rounds = "";
		
		if (msg.length < 2) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!stats player")).complete();
			return;
		}
		user = msg[1];
		output = getPlayerInfo(user);
		value = output.split(",");
		
		for (int i = 0; i < value.length; i++) {
			if (value[i].contains("hitw_record_q"))
				qualification = value[i].replace(" ", "").split(":")[1];
			if (value[i].contains("hitw_record_f"))
				finals = value[i].replace(" ", "").split(":")[1];
			if (value[i].contains("wins_hole_in_the_wall"))
				wins = value[i].replace(" ", "").split(":")[1];
			if (value[i].contains("rounds_hole_in_the_wall"))
				rounds = value[i].replace(" ", "").split(":")[1];
			if (value[i].contains("displayname"))
				user = value[i].replace(" ", "").replace("\'", "").split(":")[1];
		}
		qualification = formatter.format(Double.parseDouble(qualification));
		finals = formatter.format(Double.parseDouble(finals));
		wins = formatter.format(Double.parseDouble(wins));
		rounds = formatter.format(Double.parseDouble(rounds));
		
		createCanvas(user, qualification, finals, wins, rounds);
		event.getChannel().sendFile(new File("stats.png")).complete();
	}
	
	/**
	 * Run JS file to get player information
	 * @param user
	 * @return
	 */
	private static String getPlayerInfo(String user) {
		ProcessBuilder pb = new ProcessBuilder("node", "player_request.js", user);
		
		try {
			Process p = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = reader.readLine()) != null) {
			   builder.append(line);
			   builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	/**
	 * Call canvas scripts
	 * @param user
	 * @param qualification
	 * @param finals
	 * @param wins
	 * @param rounds
	 */
	private static void createCanvas(String user, String qualification, String finals, String wins, String rounds) {
		ProcessBuilder pb = new ProcessBuilder("node", "stats.js", user, qualification, finals, wins, rounds);
		
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
