package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping {
	
	/**
	 * Display ping
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		long time = System.currentTimeMillis();
		event.getChannel().sendMessage("...").queue(response -> {
			response.editMessageFormat("Ping: **%d**ms", System.currentTimeMillis() - time).queue();
        });
	}
}
