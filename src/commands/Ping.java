package commands;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping {
	
	/**
	 * Display ping
	 * @param event
	 */
	public static void display(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			long time = System.currentTimeMillis();
			event.getChannel().sendMessage("...").queue(response -> {
				response.editMessageFormat("Ping: **%d**ms", System.currentTimeMillis() - time).queue();
            });
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
