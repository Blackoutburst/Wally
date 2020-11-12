package commands;

import core.Lines;
import core.Reader;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ForceTracker {
	
	/**
	 * Toggle information
	 * @param event
	 * @author Blackoutburst
	 */
	public static void toggle(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().getId().equals(Main.bypassID)) {
			Main.forceTracker = (Main.forceTracker) ? false : true;
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Main.forceTracker).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
