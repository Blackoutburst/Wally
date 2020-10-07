package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SetTracker {
	
	/**
	 * Set pb tracker channel
	 * @param event
	 */
	public static void set(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			try {
				PrintWriter writer = new PrintWriter("tracker");
				writer.write(event.getChannel().getId());
				writer.close();
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.tracker_set).replace("%channel%", "<#"+event.getChannel().getId()+">")).complete();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
	
	/**
	 * Show pb tracker current channel
	 * @param event
	 */
	public static void show(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			if (!new File("tracker").exists()) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.tracker_unset)).complete();
			} else {
				try {
					event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.tracker).replace("%channel%", "<#"+Files.readAllLines(Paths.get("tracker")).get(0)+">")).complete();
				} catch (IOException e) {
					e.printStackTrace();
					event.getChannel().sendMessage(e.toString());
				}
			}
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
