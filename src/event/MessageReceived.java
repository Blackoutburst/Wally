package event;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import commands.CommandManager;
import core.Command;
import core.DisplayPBOnMention;
import core.DisplayPracticePBOnMention;
import main.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.ConfigManager;
import utils.GeneralUtils;

public class MessageReceived {
	
	/**
	 * Execute the event
	 */
	public void run(MessageReceivedEvent event) {
		if (event.getMember() == null) return;
		if (event.getMember().getUser().isBot() && event.getMessage().getContentDisplay().equals("⠀")) event.getMessage().delete().complete();
		if (event.isFromType(ChannelType.PRIVATE)) return;
		if (event.getMember().getUser().isBot()) return;
		if (event.getMessage().getContentRaw().length() == 0) return;
		if (wallyMention(event)) return;
		if (!event.getMessage().getContentRaw().startsWith(Main.PREFIX)) return;

		String message = event.getMessage().getContentRaw();
		Member sender = event.getMember();
		String name = getCommandName(message);
		String[] args = getArgs(message);
		
		CommandManager.run(new Command(sender, name, args, event.getChannel(), event.getMessage(), event.getGuild()));
	}
	
	/**
	 * Check if Wally is mentioned inside the tracker channel
	 */
	private boolean wallyMention(MessageReceivedEvent event) {
		if (event.getMember() == null) return false;
		if (event.getMember().getId().equals("696055080629043201")) return false;

		if (event.getChannel().getId().equals(ConfigManager.getString("trackerChannel"))) {
			List<Member> members = event.getMessage().getMentionedMembers();
			for (Member m : members) {
				if (m.getId().equals(Main.BOT_ID)) {
					GeneralUtils.startTyping(event.getChannel());
					new DisplayPBOnMention(event.getMember(), event);
					return (true);
				}
			}
		} else if (event.getChannel().getId().equals(ConfigManager.getString("practiceTrackerChannel"))) {
			List<Member> members = event.getMessage().getMentionedMembers();
			for (Member m : members) {
				if (m.getId().equals(Main.BOT_ID)) {
					GeneralUtils.startTyping(event.getChannel());
					new DisplayPracticePBOnMention(event.getMember(), event);
					return (true);
				}
			}
		}
		return (false);
	}
	
	/**
	 * Get command name
	 */
	private String getCommandName(String message) {
		String str = GeneralUtils.removeDuplicateSpace(message);
		String[] strarr = str.split(" ");

		return (strarr[0].substring(Main.PREFIX.length()).toLowerCase());
	}

	/**
	 * Get commands arguments
	 */
	private String[] getArgs(String message) {
		String str = GeneralUtils.removeDuplicateSpace(message);
		String[] strarr = str.split(" ");
		
		return (Arrays.copyOfRange(strarr, 1, strarr.length));
	}
}
