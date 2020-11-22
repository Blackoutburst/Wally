package commands;

import java.io.File;

import core.Lines;
import core.Reader;
import core.Utils;
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
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
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
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			try {
				Attachment f = event.getMessage().getAttachments().get(0);
				if (f.getFileName().contains(".yml")) {
					f.downloadToFile(new File("msg_file.yml"));
				} else {
					event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.wrong_file)).complete();
					return;
				}
			} catch (Exception e) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.missing_file)).complete();
				return;
			}
			
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.config_update)).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
