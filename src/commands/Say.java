package commands;


import core.Lines;
import core.Reader;
import core.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Say {
	
	/**
	 * Talk through Wally
	 * @param event
	 * @author Blackoutburst
	 */
	public static void talk(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			event.getChannel().sendMessage(event.getMessage().getContentDisplay().replace("!say ", "")).complete();
			event.getMessage().delete().complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_PERMS)).complete();
		}
	}
}
