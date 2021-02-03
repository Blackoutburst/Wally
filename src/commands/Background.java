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
	 * Get the image file and search if the user is linked
	 * if the user is linked then we update his background image
	 * with the uploaded file
	 * @param event
	 * @author Blackoutburst
	 */
	public static void set(MessageReceivedEvent event) {
		Attachment at;
		String id = event.getAuthor().getId();
		File index = new File("linked player");
		String[]entries = index.list();
		
		try {
			at = event.getMessage().getAttachments().get(0);
		} catch (Exception e) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_BACKGROUND)).complete();
			return; 
		}
		
		for (String s: entries) {
			File f = new File(index.getPath(),s);
			
			try {
				if (Files.readAllLines(Paths.get(f+"/discord")).get(0).equals(id)) {
					if (at.getFileName().contains(".png") || at.getFileName().contains(".jpg") || at.getFileName().contains(".jpeg")) {
						at.downloadToFile(new File(f+"/background.png"));
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BACKGROUND_UPDATE)).complete();
						return;
					} else {
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.WRONG_BACKGROUND)).complete();
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.LINKED_OLNY)).complete();
	}
	
	/**
	 * if the user is linked we remove is custom background
	 * @param event
	 * @author Blackoutburst
	 */
	public static void reset(MessageReceivedEvent event) {
		String id = event.getAuthor().getId();
		File index = new File("linked player");
		String[]entries = index.list();
		
		for (String s: entries) {
			File f = new File(index.getPath(),s);
			
			try {
				if (Files.readAllLines(Paths.get(f+"/discord")).get(0).equals(id)) {
					File bg = new File(f+"/background.png");
					if (bg.exists()) {
						bg.delete();
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BACKGROUND_RESET)).complete();
						return;
					} else {
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.STOCK_BACKGROUND)).complete();
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.LINKED_OLNY)).complete();
	}
	
	/**
	 * Admin command, remove a specific user background
	 * @param event
	 * @author Blackoutburst
	 */
	public static void remove(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			String[] msg = event.getMessage().getContentDisplay().split(" ");
			String id = "";
			File index = new File("linked player");
			String[]entries = index.list();
			
			if (msg.length < 2) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BAD_USAGE).replace("%command%", "!removebackground player")).complete();
				return;
			}
			
			id = Request.getPlayerUUID(msg[1]);
			
			for (String s: entries) {
				File f = new File(index.getPath(),s);
				
				if (s.equals(id)) {
					File bg = new File(f+"/background.png");
					if (bg.exists()) {
						bg.delete();
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BACKGROUND_REMOVE).replace("%user%", msg[1])).complete();
						return;
					} else {
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BACKGROUND_STOCK2).replace("%user%", msg[1])).complete();
						return;
					}
				}
			}
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.NOT_LINKED)).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_PERMS)).complete();
		}
	}
}
