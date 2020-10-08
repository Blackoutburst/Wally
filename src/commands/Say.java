package commands;


import core.Lines;
import core.Reader;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Say {
	
	/**
	 * Talk through Wally
	 * @param event
	 * @author Blackoutburst
	 */
	public static void talk(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().getId().equals(Main.bypassID)) {
			event.getChannel().sendMessage(event.getMessage().getContentDisplay().replace("!say ", "")).complete();
			event.getMessage().delete().complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
