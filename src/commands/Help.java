package commands;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help {
	
	/**
	 * Display help
	 * @param event
	 */
	public static void display(MessageReceivedEvent event) {
		System.out.println(event.getChannel());
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			event.getChannel().sendMessage(Reader.read(Lines.help_admin)).complete();
		} else {
			event.getChannel().sendMessage(Reader.read(Lines.help)).complete();
		}
	}
}
