package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Compare {
	/**
	 * Get both user and set up value to create canvas
	 * @param event
	 */
	public static void display(MessageReceivedEvent event) {
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		String[] msg = event.getMessage().getContentDisplay().split(" ");
		String user = "";
		String user2 = "";
		String output = "";
		String output2 = "";
		String[] value;
		String[] value2;
		String qualification = "";
		String finals = "";
		String wins = "";
		String rounds = "";
		String total = "";
		String qualification2 = "";
		String finals2 = "";
		String wins2 = "";
		String rounds2 = "";
		String total2 = "";
		
		if (msg.length < 3) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!compare player player")).complete();
			return;
		}
		user = msg[1];
		output = getPlayerInfo(user);
		value = output.split(",");
		user2 = msg[2];
		output2 = getPlayerInfo(user2);
		value2 = output2.split(",");
		
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
		total = String.valueOf(Integer.valueOf(qualification) + Integer.valueOf(finals));
		qualification = formatter.format(Double.parseDouble(qualification));
		finals = formatter.format(Double.parseDouble(finals));
		wins = formatter.format(Double.parseDouble(wins));
		rounds = formatter.format(Double.parseDouble(rounds));
		total = formatter.format(Double.parseDouble(total));

		for (int i = 0; i < value2.length; i++) {
			if (value2[i].contains("hitw_record_q"))
				qualification2 = value2[i].replace(" ", "").split(":")[1];
			if (value2[i].contains("hitw_record_f"))
				finals2 = value2[i].replace(" ", "").split(":")[1];
			if (value2[i].contains("wins_hole_in_the_wall"))
				wins2 = value2[i].replace(" ", "").split(":")[1];
			if (value2[i].contains("rounds_hole_in_the_wall"))
				rounds2 = value2[i].replace(" ", "").split(":")[1];
			if (value2[i].contains("displayname"))
				user2 = value2[i].replace(" ", "").replace("\'", "").split(":")[1];
		}
		total2 = String.valueOf(Integer.valueOf(qualification2) + Integer.valueOf(finals2));
		qualification2 = formatter.format(Double.parseDouble(qualification2));
		finals2 = formatter.format(Double.parseDouble(finals2));
		wins2 = formatter.format(Double.parseDouble(wins2));
		rounds2 = formatter.format(Double.parseDouble(rounds2));
		total2 = formatter.format(Double.parseDouble(total2));
		
		createCanvas(user, qualification, finals, wins, rounds, total, user2, qualification2, finals2, wins2, rounds2, total2);
		event.getChannel().sendFile(new File("compare.png")).complete();
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
	 * Create canvas script
	 * @param user
	 * @param qualification
	 * @param finals
	 * @param wins
	 * @param rounds
	 * @param user2
	 * @param qualification2
	 * @param finals2
	 * @param wins2
	 * @param rounds2
	 */
	private static void createCanvas(String user, String qualification, String finals, String wins, String rounds, String total, 
	String user2, String qualification2, String finals2, String wins2, String rounds2, String total2) {
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		String q = String.valueOf(Integer.valueOf(qualification.replace(" ", "")) - Integer.valueOf(qualification2.replace(" ", "")));
		String f = String.valueOf(Integer.valueOf(finals.replace(" ", "")) - Integer.valueOf(finals2.replace(" ", "")));
		String w = String.valueOf(Integer.valueOf(wins.replace(" ", "")) - Integer.valueOf(wins2.replace(" ", "")));
		String r = String.valueOf(Integer.valueOf(rounds.replace(" ", "")) - Integer.valueOf(rounds2.replace(" ", "")));
		String t = String.valueOf(Integer.valueOf(total.replace(" ", "")) - Integer.valueOf(total2.replace(" ", "")));

		ProcessBuilder pb = new ProcessBuilder("node", "compare.js", user, qualification, finals, wins, rounds, total, 
		user2, qualification2, finals2, wins2, rounds2, total2, 
		formatter.format(Double.parseDouble(q)), formatter.format(Double.parseDouble(f)), 
		formatter.format(Double.parseDouble(w)), formatter.format(Double.parseDouble(r)), formatter.format(Double.parseDouble(t)),
		formatter.format(-Double.parseDouble(q)), formatter.format(-Double.parseDouble(f)), 
		formatter.format(-Double.parseDouble(w)), formatter.format(-Double.parseDouble(r)), formatter.format(-Double.parseDouble(t)));
		
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
