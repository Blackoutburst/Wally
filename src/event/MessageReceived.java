package event;

import java.util.Arrays;

import commands.CommandManager;
import core.Command;
import main.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.Utils;

public class MessageReceived {
	
	/**
	 * Execute the event
	 * @param event
	 */
	public void run(MessageReceivedEvent event) {
		if (event.isFromType(ChannelType.PRIVATE)) return;
		if (event.getMember().getUser().isBot()) return;
		if (event.getMessage().getContentRaw().length() == 0) return;
		if (!event.getMessage().getContentRaw().startsWith(Main.PREFIX)) return;
		
		String message = event.getMessage().getContentRaw();
		Member sender = event.getMember();
		String name = getCommandName(message);
		String[] args = getArgs(message);
		
		new CommandManager(new Command(sender, name, args, event));
	}
	
	/**
	 * Get command name
	 * @param message
	 * @return
	 */
	private String getCommandName(String message) {
		String str = Utils.removeDuplicateSpace(message);
		String[] strarr = str.split(" ");

		return (strarr[0].substring(Main.PREFIX.length()).toLowerCase());
	}
	
	/**
	 * Get commands arguments
	 * @param message
	 * @return
	 */
	private String[] getArgs(String message) {
		String str = Utils.removeDuplicateSpace(message);
		String[] strarr = str.split(" ");
		
		return (Arrays.copyOfRange(strarr, 1, strarr.length));
	}
}
