package core;

import java.io.File;

import utils.GeneralUtils;
import utils.MessageSender;
import utils.PracticeAPI;
import utils.PracticeStats;
import utils.Stats;

public class PTracker {
	
	public static boolean forced = false;
	public static Thread ptrackerThread = null;
	
	public void start() {
		ptrackerThread = new Thread(new Runnable(){
			public void run(){
				while (true) {
					try {
						checkUsers();
					} catch (Exception e) {
						e.printStackTrace();
					};
				}
			}
		});
		ptrackerThread.setDaemon(true);
		ptrackerThread.setName("Practice Tracker");
		ptrackerThread.start();
	}
	
	/**
	 * Check every user register and update them
	 */
	private void checkUsers() {
		File index = new File("pleaderboard");
		String[] entries = index.list();
		
		for(String s: entries) {
			File playerFolder = new File(index.getPath(), s);
			String uuid = playerFolder.getName();
			String data = Request.getPracticeServerScore(uuid);
			String localData = GeneralUtils.readJsonToString(playerFolder + "/data.json");
			
			if (data.contains("Unknown player")) continue;
			if (!GeneralUtils.isLinkedUUID(uuid)) continue;
			
			
			String discordId = Stats.getDiscordId(GeneralUtils.readJsonToString("linked player/" + uuid + "/data.json"));
			if(!forced && !GeneralUtils.isOnline(discordId)) continue;
			
			int oldQ = PracticeStats.getQualificationToInt(localData);
			int newQ = PracticeAPI.getQualificationToInt(data);
			int oldF = PracticeStats.getFinalsToInt(localData);
			int newF = PracticeAPI.getFinalsToInt(data);
			int oldL = PracticeStats.getLobbyToInt(localData);
			int newL = PracticeAPI.getLobbyToInt(data);
			int oldWQ = PracticeStats.getWideQualificationToInt(localData);
			int newWQ = PracticeAPI.getWideQualificationToInt(data);
			
			if (newQ > oldQ) MessageSender.practicePbMessage(data, discordId, uuid, "q");
			if (newF > oldF) MessageSender.practicePbMessage(data, discordId, uuid, "f");
			if (newL > oldL) MessageSender.practicePbMessage(data, discordId, uuid, "l");
			if (newWQ > oldWQ) MessageSender.practicePbMessage(data, discordId, uuid, "wq");
			
			GeneralUtils.updateFile(data, localData, uuid, "pleaderboard", true);
			delay(500);
		}
	}
	
	/**
	 * Stop the current thread for a defined amount of time
	 * @param ms
	 */
	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
