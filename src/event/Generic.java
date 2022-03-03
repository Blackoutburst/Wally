package event;

import core.Bot;
import net.dv8tion.jda.api.events.GenericEvent;
import utils.ConfigManager;

public class Generic {
	
	/**
	 * Execute the event
	 */
	public void run(GenericEvent event) {
		Bot.server = event.getJDA().getGuildById(ConfigManager.getString("serverID"));
		Bot.devServer = event.getJDA().getGuildById(ConfigManager.getString("devServerID"));
	}
}
