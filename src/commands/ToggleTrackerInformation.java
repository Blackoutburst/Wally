package commands;

import core.Lines;
import core.Reader;
import core.Utils;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ToggleTrackerInformation {
	
	/**
	 * Toggle information
	 * @param event
	 * @author Blackoutburst
	 */
	public static void toggle(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			Main.trackerInformation = (Main.trackerInformation) ? false : true;
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Main.trackerInformation).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_PERMS)).complete();
		}
	}
}
