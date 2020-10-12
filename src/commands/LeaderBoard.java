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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LeaderBoard {

	/**
	 * Display discord user leaderboard
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		List<Player> player = new ArrayList<Player>();
		String[] msg = event.getMessage().getContentDisplay().split(" ");
		int page = 1;
		String type = "W";
		String name = "";
		String discord = "";
		int wins = 0;
		int rounds = 0;
		int qualification = 0;
		int finals = 0;
		int total = 0;
		
		if (msg.length < 2) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!lead W/R/Q/F/T (page)")).complete();
			return;
		}
		type = msg[1];
		type = type.toLowerCase();
		
		if (!type.equals("w") && !type.equals("r") && !type.equals("q") && !type.equals("f") && !type.equals("t")) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!lead W/R/Q/F/T (page)")).complete();
		}
		
		if (msg.length > 2) {
			try {
				page = Integer.valueOf(msg[2]);
			} catch (Exception e) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!lead W/R/Q/F/T (page)")).complete();
			}
		}
		page--;
		
		File index = new File("linked player");
		String[]entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			try {
				Member m = event.getGuild().getMemberById(readValue(f+"/discord"));
				discord = m.getNickname();
				if (discord == null) {
					discord = m.getUser().getName();
				}
			} catch(Exception e) {
				continue;
			}
			name = readValue(f+"/name");
			wins = Integer.valueOf(readValue(f+"/W"));
			rounds = Integer.valueOf(readValue(f+"/R"));
			qualification = Integer.valueOf(readValue(f+"/Q"));
			finals = Integer.valueOf(readValue(f+"/F"));
			total = qualification + finals;
			player.add(new Player(wins, rounds, qualification, finals, total, name, discord));
		}
		switch (type) {
			case "w":Collections.sort(player, new PlayerComparatorWins());Collections.reverse(player);break;
			case "r":Collections.sort(player, new PlayerComparatorRounds());Collections.reverse(player);break;
			case "q":Collections.sort(player, new PlayerComparatorQ());Collections.reverse(player);break;
			case "f":Collections.sort(player, new PlayerComparatorF());Collections.reverse(player);break;
			case "t":Collections.sort(player, new PlayerComparatorTotal());Collections.reverse(player);break;
			default:Collections.sort(player, new PlayerComparatorWins());Collections.reverse(player);break;
		}
		
		
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		String str = "```xml\n";
		for (int i = (10 * page); i < (10 * page) + 10; i++) {
			if (i >= player.size()) {
				break;
			}
			Player p = player.get(i);
			
			switch (type) {
				case "w":str += "<#"+(i+1)+" "+p.name+" = Wins: "+formatter.format(Double.parseDouble(String.valueOf(p.W)))+">\n";break;
				case "r":str += "<#"+(i+1)+" "+p.name+" = Walls: "+formatter.format(Double.parseDouble(String.valueOf(p.R)))+">\n";break;
				case "q":str += "<#"+(i+1)+" "+p.name+" = Qualifications: "+formatter.format(Double.parseDouble(String.valueOf(p.Q)))+">\n";break;
				case "f":str += "<#"+(i+1)+" "+p.name+" = Final: "+formatter.format(Double.parseDouble(String.valueOf(p.F)))+">\n";break;
				case "t":str += "<#"+(i+1)+" "+p.name+" = Q/F_Total: "+formatter.format(Double.parseDouble(String.valueOf(p.total)))+">\n";break;
				default:str += "<#"+(i+1)+" "+p.name+" = Wins: "+formatter.format(Double.parseDouble(String.valueOf(p.W)))+">\n";break;
			}
		}
		str += "```";
		event.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Read file value
	 * @param file
	 * @return value read
	 * @author Blackoutburst
	 */
	private static String readValue(String file) {
		String str = "";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
