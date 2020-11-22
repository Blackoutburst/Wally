package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Lines;
import core.Reader;
import core.Request;
import core.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Background {
	
	/**
	 * Take new user background file to update
	 * @param event
	 * @author Blackoutburst
	 */
	public static void set(MessageReceivedEvent event) {
		Attachment at;
		try {
		at = event.getMessage().getAttachments().get(0);
		} catch (Exception e) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.missing_background)).complete();
			return; 
		}
		
		String id = event.getAuthor().getId();
		
		File index = new File("linked player");
		String[]entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			try {
				if (Files.readAllLines(Paths.get(f+"/discord")).get(0).equals(id)) {
					if (at.getFileName().contains(".png") || at.getFileName().contains(".jpg") || at.getFileName().contains(".jpeg")) {
						at.downloadToFile(new File(f+"/background.png"));
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.background_update)).complete();
						return;
					} else {
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.wrong_background)).complete();
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.linked_only)).complete();
	}
	
	/**
	 * Remove user background
	 * @param event
	 * @author Blackoutburst
	 */
	public static void reset(MessageReceivedEvent event) {
		String id = event.getAuthor().getId();
		
		File index = new File("linked player");
		String[]entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			try {
				if (Files.readAllLines(Paths.get(f+"/discord")).get(0).equals(id)) {
					File bg = new File(f+"/background.png");
					if (bg.exists()) {
						bg.delete();
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.background_reset)).complete();
						return;
					} else {
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.stock_background)).complete();
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.linked_only)).complete();
	}
	
	/**
	 * Remove user background (admin)
	 * @param event
	 * @author Blackoutburst
	 */
	public static void remove(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			String[] msg = event.getMessage().getContentDisplay().split(" ");
			String id = "";
			
			if (msg.length < 2) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!removebackground player")).complete();
				return;
			}
			
			id = Request.getPlayerUUID(msg[1]);
			
			File index = new File("linked player");
			String[]entries = index.list();
			for(String s: entries) {
				File f = new File(index.getPath(),s);
				if (s.equals(id)) {
					File bg = new File(f+"/background.png");
					if (bg.exists()) {
						bg.delete();
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.background_remove).replace("%user%", msg[1])).complete();
						return;
					} else {
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.background_stock2).replace("%user%", msg[1])).complete();
						return;
					}
				}
			}
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.not_linked)).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
