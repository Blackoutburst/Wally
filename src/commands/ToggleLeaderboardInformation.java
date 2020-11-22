package commands;

import core.Lines;
import core.Reader;
import core.Utils;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ToggleLeaderboardInformation {
	
	/**
	 * Toggle information
	 * @param event
	 * @author Blackoutburst
	 */
	public static void toggle(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			Main.leaderboardInformation = (Main.leaderboardInformation) ? false : true;
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Main.leaderboardInformation).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
