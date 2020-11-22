package commands;

import core.Lines;
import core.Reader;
import core.Utils;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ForceTracker {
	
	/**
	 * Force tracker to check every server member
	 * @param event
	 * @author Blackoutburst
	 */
	public static void toggle(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			Main.forceTracker = (Main.forceTracker) ? false : true;
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Main.forceTracker).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
