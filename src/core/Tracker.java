package core;

import java.io.File;

import utils.API;
import utils.MessageSender;
import utils.Stats;
import utils.Utils;

public class Tracker {
	
	public static boolean forced = false;
	
	public Tracker() {
		Thread trackerThread = new Thread(new Runnable(){
			public void run(){
				while (true) {
					checkUsers();
					delay(750);
				}
			}
		});
		trackerThread.setDaemon(true);
		trackerThread.setName("Tracker");
		trackerThread.start();
	}
	
	/**
	 * Check every linked user for new personal best
	 */
	private void checkUsers() {
		File index = new File("linked player");
		String[] entries = index.list();
		
		for(String s: entries) {
			File playerFolder = new File(index.getPath(), s);
			String data = "";
			String localData = Utils.readJsonToString(playerFolder + "/data.json");
			String discordid = Stats.getDiscordId(localData);
			
			
			String uuid = Stats.getUUID(localData);
			int oldQ = 0;
			int newQ = 0;
			int oldF = 0;
			int newF = 0;
			
			if(!Utils.isInsideTheServer(discordid)) Utils.unlinkMember(uuid);
			if(!Utils.isOnline(discordid) && !forced) continue;
			
			data = Request.getPlayerStatsUUID(uuid);
			oldQ = Stats.getQualificationToInt(localData);
			newQ = API.getQualificationToInt(data);
			oldF = Stats.getFinalsToInt(localData);
			newF = API.getFinalsToInt(data);
			
			if (newQ > oldQ) MessageSender.pbMessage(data, discordid, uuid, 'q');
			if (newF > oldF) MessageSender.pbMessage(data, discordid, uuid, 'f');
			Utils.updateFile(data, localData, uuid, "linked player");
			Utils.updateFile(data, localData, uuid, "leaderboard");
		}
	}
	
	/**
	 * Stop the current thread for a defined amount of time
	 * @param ms
	 */
	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
