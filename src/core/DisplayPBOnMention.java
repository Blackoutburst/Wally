package core;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.API;
import utils.ConfigManager;
import utils.MessageSender;
import utils.Stats;
import utils.GeneralUtils;

public class DisplayPBOnMention {
	
	/**
	 * Get user data from mention inside tracker channel
	 * @param sender
	 * @param event
	 */
	public DisplayPBOnMention(Member sender, MessageReceivedEvent event) {
		String data = null;
		String localData = null;
		
		if (GeneralUtils.isLinkedDiscord(sender.getId())) {
			data = Request.getPlayerStatsUUID(GeneralUtils.getUUIDfromDiscord(sender.getId()));
			localData = GeneralUtils.readJsonToString("linked player/" + GeneralUtils.getUUIDfromDiscord(sender.getId()) + "/data.json");
		} else {
			event.getChannel().sendMessage(sender.getAsMention() + ",\n" + ConfigManager.getMessage("not linked")).complete();
			return;
		}
		execute(data, localData, sender, event);
	}
	
	/**
	 * Check for pb and display them
	 * @param data
	 * @param localData
	 * @param sender
	 * @param event
	 */
	private void execute(String data, String localData, Member sender, MessageReceivedEvent event) {
		int oldQ = Stats.getQualificationToInt(localData);
		int oldF = Stats.getFinalsToInt(localData);
		int newQ = API.getQualificationToInt(data);
		int newF = API.getFinalsToInt(data);
		
		if (oldQ >= newQ && oldF >= newF) {
			event.getChannel().sendMessage(sender.getAsMention() + ",\n" + ConfigManager.getMessage("not pb")).complete();
			return;
		}
		
		if (newQ > oldQ) MessageSender.pbMessage(data, sender.getId(), GeneralUtils.getUUIDfromDiscord(sender.getId()), 'q');
		if (newF > oldF) MessageSender.pbMessage(data, sender.getId(), GeneralUtils.getUUIDfromDiscord(sender.getId()), 'f');
		
		GeneralUtils.updateFile(data, localData, GeneralUtils.getUUIDfromDiscord(sender.getId()), "linked player", false);
		GeneralUtils.updateFile(data, localData, GeneralUtils.getUUIDfromDiscord(sender.getId()), "leaderboard", false);
	}
}
