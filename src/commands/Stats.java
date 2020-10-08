package commands;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import core.Lines;
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
		String qualification = "";
		String finals = "";
		String wins = "";
		String rounds = "";
		String total = "";
		
		if (msg.length < 2) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!stats player")).complete();
			return;
		}
		user = msg[1];
		output = Request.getPlayerInfo(user);
		if (output.equals("API LIMITATION")) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.api_error)).complete();
			return;
		}
		value = output.split("\n");
		for (int i = 0; i < value.length; i++) {
			if (value[i].contains("hitw_record_q")) {
				qualification = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
				qualification = qualification.substring(0, qualification.length()-1);
			}
			if (value[i].contains("hitw_record_f")) {
				finals = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
				finals = finals.substring(0, finals.length()-1);
			}
			if (value[i].contains("rounds_hole_in_the_wall")) {
				rounds = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
				rounds = rounds.substring(0, rounds.length()-1);
			}
			if (value[i].contains("displayname")) {
				user = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
				user = user.substring(0, user.length()-1);
			}
			if (value[i].contains("wins_hole_in_the_wall")) {
				wins = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
				wins = wins.substring(0, wins.length()-1);
			}
		}
		
		total = String.valueOf(Integer.valueOf(qualification) + Integer.valueOf(finals));
		qualification = formatter.format(Double.parseDouble(qualification));
		finals = formatter.format(Double.parseDouble(finals));
		wins = formatter.format(Double.parseDouble(wins));
		rounds = formatter.format(Double.parseDouble(rounds));
		total = formatter.format(Double.parseDouble(total));

		createCanvas(user, qualification, finals, wins, rounds, total);
		event.getChannel().sendFile(new File("stats.png")).complete();
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
	private static void createCanvas(String user, String qualification, String finals, String wins, String rounds, String total) {
		ProcessBuilder pb = new ProcessBuilder("node", "stats.js", user, qualification, finals, wins, rounds, total);
		
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
