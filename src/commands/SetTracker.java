package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Lines;
import core.Reader;
import core.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SetTracker {
	
	/**
	 * Set pb tracker channel
	 * @param event
	 * @author Blackoutburst
	 */
	public static void set(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			try {
				PrintWriter writer = new PrintWriter("tracker");
				writer.write(event.getChannel().getId());
				writer.close();
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.TRACKER_SET).replace("%channel%", "<#"+event.getChannel().getId()+">")).complete();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_PERMS)).complete();
		}
	}
	
	/**
	 * Show pb tracker current channel
	 * @param event
	 * @author Blackoutburst
	 */
	public static void show(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			if (!new File("tracker").exists()) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.TRACKER_UNSET)).complete();
			} else {
				try {
					event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.TRACKER).replace("%channel%", "<#"+Files.readAllLines(Paths.get("tracker")).get(0)+">")).complete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_PERMS)).complete();
		}
	}
}
