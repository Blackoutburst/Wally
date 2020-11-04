package commands;

import java.io.File;

import core.Lines;
import core.Reader;
import core.Request;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ap {
	
	/**
	 * Display help
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		String[] msg = event.getMessage().getContentDisplay().split(" ");
		String img = null;
		if (msg.length < 2) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!ap player")).complete();
			return;
		}
		img = Request.getPlanckeAP(msg[1]);
		if (img == null) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
			return;
		}
		event.getChannel().sendMessage(msg[1]+" AP:").complete();
		event.getChannel().sendFile(new File("ap.png")).complete();
	}
}
