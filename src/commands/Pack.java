package commands;

import java.util.List;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Pack {
	
	/**
	 * Display texture pack link
	 * @param event
	 */
	public static void display(MessageReceivedEvent event) {
		List<GuildChannel> chan = event.getGuild().getChannels();
		GuildChannel pack = null;
		
		for (GuildChannel c : chan) {
			if (c.getName().equals("resource-pack")) {
				pack = c;
				break;
			}
		}
		
		
		event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.pack).replace("%link%", "<#"+pack.getId()+">")).complete();
	}
}
