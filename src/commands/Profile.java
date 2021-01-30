package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Lines;
import core.Reader;
import core.Request;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Profile {
	/**
	 * Get user and set up value to create canvas
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		String[] msg = event.getMessage().getContentDisplay().split(" ");
		String level = null;
		String ap = null;
		String uuid = "";
		String name = "";
		boolean linked = false;
		
		if (msg.length >= 2) {
			uuid = Request.getPlayerUUID(msg[1]);
		} else {
			String id = event.getAuthor().getId();
			File index = new File("linked player");
			String[]entries = index.list();
			
			for (String s: entries) {
				File f = new File(index.getPath(),s);
				try {
					if (Files.readAllLines(Paths.get(f+"/discord")).get(0).equals(id)) {
						uuid = f.getName();
						name = readValue(f+"/name");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (uuid.equals("")) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!profile player")).complete();
				return;
			}
		}
		if (uuid == null) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
			return;
		}
		
		level = Request.getPlanckeLevel(uuid);
		if (level == null) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
			return;
		}
		ap = Request.getPlanckeAP(uuid);
		if (ap == null) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
			return;
		}
		
		
		File index = new File("linked player");
		String[]entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			if (f.getName().equals(uuid)) {
				linked = true;
			}
		}
		
		if (msg.length >= 2) {
			createCanvas(msg[1], linked, uuid);
		} else {
			createCanvas(name, linked, uuid);
		}
		event.getChannel().sendFile(new File("profile.png")).complete();
	}
	
	/**
	 * Read file value
	 * @param file
	 * @return file value
	 * @author Blackoutburst
	 */
	private static String readValue(String file) {
		String str = "0";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (Exception e) {
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
	private static void createCanvas(String user, boolean linked, String uuid) {
		ProcessBuilder pb = new ProcessBuilder("node", "profile.js", user, String.valueOf(linked), uuid);
		
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
