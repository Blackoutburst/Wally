package commands;

import java.io.File;

import core.Lines;
import core.Reader;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Config {
	
	/**
	 * Send configuration file
	 * @param event
	 * @author Blackoutburst
	 */
	public static void get(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().getId().equals(Main.bypassID)) {
			event.getChannel().sendFile(new File("msg_file.yml")).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
	
	/**
	 * Take new configuration file to update
	 * @param event
	 * @author Blackoutburst
	 */
	public static void update(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().getId().equals(Main.bypassID)) {
			Attachment f = event.getMessage().getAttachments().get(0);
			
			f.downloadToFile(new File("msg_file.yml"));
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.config_update)).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
