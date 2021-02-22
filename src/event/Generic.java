package event;

import core.Bot;
import net.dv8tion.jda.api.events.GenericEvent;
import utils.Config;

public class Generic {
	
	/**
	 * Execute the event
	 * @param event
	 */
	public void run(GenericEvent event) {
		Bot.server = event.getJDA().getGuildById(Config.getString("serverID"));
	}
}
