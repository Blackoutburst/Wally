package commands;

import java.util.List;

import core.Lines;
import core.Reader;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GetRole {
	
	/**
	 * Display server roles
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().getId().equals(Main.bypassID)) {
			List<Role> roles = event.getGuild().getRoles();
			
			for (Role r : roles) {
				System.out.println(r.getName());
			}
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", done.").complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
