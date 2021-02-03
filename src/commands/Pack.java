package commands;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Pack {
	
	/**
	 * Send link to #ressource-pack channel
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.PACK).replace("%link%", "<#739642373415633017>")).complete();
	}
}
