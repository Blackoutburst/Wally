package core;


import javax.security.auth.login.LoginException;

import commands.Help;
import commands.Stats;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Bot extends ListenerAdapter {
	// Bot instance
	private JDABuilder bot;
	
	/**
	 * Log the bot and set the activity
	 * @param token
	 * @param activity
	 * @throws LoginException
	 */
	public void login(String token, String activity) throws LoginException {
		bot = JDABuilder.createDefault(token);
		bot.setActivity(Activity.playing(activity));
		bot.addEventListeners(new Bot());
		bot.build();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.isFromType(ChannelType.PRIVATE)) {
			if (event.getMessage().getContentDisplay().startsWith("!help")) {
				Help.display(event);
			}
			if (event.getMessage().getContentDisplay().startsWith("!stats")) {
				Stats.display(event);
			}
		}
    }
	
}
