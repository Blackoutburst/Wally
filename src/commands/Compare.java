package commands;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import core.Lines;
import core.Reader;
import core.Request;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Compare {
	/**
	 * Get both user and set up value to create canvas
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		String[] msg = event.getMessage().getContentDisplay().split(" ");
		String user = "";
		String user2 = "";
		String output = "";
		String output2 = "";
		String[] value;
		String[] value2;
		int qualification = 0;
		int finals = 0;
		int wins = 0;
		int rounds = 0;
		int total = 0;
		int qualification2 = 0;
		int finals2 = 0;
		int wins2 = 0;
		int rounds2 = 0;
		int total2 = 0;
		
		if (msg.length < 3) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!compare player player")).complete();
			return;
		}
		user = msg[1];
		
		if (Request.getPlayerUUID(user) == null) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.compare_not_found).replace("%player%", user)).complete();
			return;
		}
		
		output = Request.getPlayerInfo(user);
		if (output.equals("API LIMITATION")) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.api_error)).complete();
			return;
		}
		value = output.split("\n");
		user2 = msg[2];
		
		if (Request.getPlayerUUID(user2) == null) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.compare_not_found).replace("%player%", user2)).complete();
			return;
		}
		
		output2 = Request.getPlayerInfo(user2);
		if (output2.equals("API LIMITATION")) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.api_error)).complete();
			return;
		}
		
		for (int i = 0; i < value.length; i++) {
			if (value[i].contains("hitw_record_q")) {
				qualification = Integer.valueOf(value[i].replaceAll("[^0-9]", ""));
			}
			if (value[i].contains("hitw_record_f")) {
				finals = Integer.valueOf(value[i].replaceAll("[^0-9]", ""));
			}
			if (value[i].contains("rounds_hole_in_the_wall")) {
				rounds = Integer.valueOf(value[i].replaceAll("[^0-9]", ""));
			}
			if (value[i].contains("displayname")) {
				user = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
			}
			if (value[i].contains("wins_hole_in_the_wall")) {
				wins = Integer.valueOf(value[i].replaceAll("[^0-9]", ""));
			}
		}
		total = qualification + finals;

		value2 = output2.split("\n");
		for (int i = 0; i < value2.length; i++) {
			if (value2[i].contains("hitw_record_q")) {
				qualification2 = Integer.valueOf(value2[i].replaceAll("[^0-9]", ""));
			}
			if (value2[i].contains("hitw_record_f")) {
				finals2 = Integer.valueOf(value2[i].replaceAll("[^0-9]", ""));
			}
			if (value2[i].contains("rounds_hole_in_the_wall")) {
				rounds2 = Integer.valueOf(value2[i].replaceAll("[^0-9]", ""));
			}
			if (value2[i].contains("displayname")) {
				user2 = value2[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
			}
			if (value2[i].contains("wins_hole_in_the_wall")) {
				wins2 = Integer.valueOf(value2[i].replaceAll("[^0-9]", ""));
			}
		}
		total2 = qualification2 + finals2;
		
		createCanvas(user, qualification, finals, wins, rounds, total, user2, qualification2, finals2, wins2, rounds2, total2);
		event.getChannel().sendFile(new File("compare.png")).complete();
	}
	
	/**
	 * Create canvas script (JS)
	 * @param user
	 * @param qualification
	 * @param finals
	 * @param wins
	 * @param rounds
	 * @param total
	 * @param user2
	 * @param qualification2
	 * @param finals2
	 * @param wins2
	 * @param rounds2
	 * @param total2
	 * @see compare.js
	 * @author Blackoutburst
	 */
	private static void createCanvas(String user, int qualification, int finals, int wins, int rounds, int total, 
	String user2, int qualification2, int finals2, int wins2, int rounds2, int total2) {
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		int q = qualification - qualification2;
		int f = finals - finals2;
		int w = wins - wins2;
		int r = rounds - rounds2;
		int t = total - total2;

		int mq = -q;
		int mf = -f;
		int mw = -w;
		int mr = -r;
		int mt = -t;
		
		ProcessBuilder pb = new ProcessBuilder("node", "compare.js", 
			user, 
			formatter.format(Double.parseDouble(String.valueOf(qualification))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(finals))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(wins))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(rounds))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(total))).replace(" ", " "), 
			user2, 
			formatter.format(Double.parseDouble(String.valueOf(qualification2))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(finals2))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(wins2))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(rounds2))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(total2))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(q))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(f))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(w))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(r))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(t))).replace(" ", " "),
			formatter.format(Double.parseDouble(String.valueOf(mq))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(mf))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(mw))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(mr))).replace(" ", " "), 
			formatter.format(Double.parseDouble(String.valueOf(mt))).replace(" ", " "));
		
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
