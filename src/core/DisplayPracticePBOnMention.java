package core;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.ConfigManager;
import utils.MessageSender;
import utils.PracticeAPI;
import utils.PracticeStats;
import utils.GeneralUtils;

public class DisplayPracticePBOnMention {
	
	/**
	 * Get user data from mention inside tracker channel
	 */
	public DisplayPracticePBOnMention(Member sender, MessageReceivedEvent event) {
		String data;
		String localData;
		
		if (GeneralUtils.isLinkedDiscord(sender.getId())) {
			data = Request.getPracticeServerScore(GeneralUtils.getUUIDfromDiscord(sender.getId()));
			localData = GeneralUtils.readJsonToString("pleaderboard/" + GeneralUtils.getUUIDfromDiscord(sender.getId()) + "/data.json");
		} else {
			event.getChannel().sendMessage(sender.getAsMention() + ",\n" + ConfigManager.getMessage("not linked")).complete();
			return;
		}
		execute(data, localData, sender, event);
	}
	
	
	/**
	 * Check for pb and display them
	 */
	private void execute(String data, String localData, Member sender, MessageReceivedEvent event) {
		int oldQ = PracticeStats.getQualificationToInt(localData);
		int newQ = PracticeAPI.getQualificationToInt(data);
		int oldF = PracticeStats.getFinalsToInt(localData);
		int newF = PracticeAPI.getFinalsToInt(data);
		int oldL = PracticeStats.getLobbyToInt(localData);
		int newL = PracticeAPI.getLobbyToInt(data);
		int oldWQ = PracticeStats.getWideQualificationToInt(localData);
		int newWQ = PracticeAPI.getWideQualificationToInt(data);
		
		if (oldQ >= newQ && oldF >= newF && oldL >= newL && oldWQ >= newWQ) {
			event.getChannel().sendMessage(sender.getAsMention() + ",\n" + ConfigManager.getMessage("not pb")).complete();
			return;
		}
		
		String uuid = GeneralUtils.getUUIDfromDiscord(sender.getId());
		
		if (newQ > oldQ) MessageSender.practicePbMessage(data, sender.getId(), uuid, "q");
		if (newF > oldF) MessageSender.practicePbMessage(data, sender.getId(), uuid, "f");
		if (newL > oldL) MessageSender.practicePbMessage(data, sender.getId(), uuid, "l");
		if (newWQ > oldWQ) MessageSender.practicePbMessage(data, sender.getId(), uuid, "wq");
		
		GeneralUtils.updateFile(data, localData, uuid, "pleaderboard", true);
	}
}
