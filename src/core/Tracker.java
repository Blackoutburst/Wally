package core;

import java.io.File;

import utils.*;

public class Tracker {
	
	public static boolean forced = true;
	public static Thread trackerThread = null;
	
	public void start() {
		trackerThread = new Thread(() -> {
			while (true) {
				try {
					checkUsers();
				} catch (Exception e) {
					e.printStackTrace();
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

		if (entries == null) return;

		for(String s: entries) {
			File playerFolder = new File(index.getPath(), s);
			String data;
			String fileLocation = playerFolder + "/data.json";

			File tmpFile = new File(fileLocation);
			if(!tmpFile.exists()) continue;

			String localData = GeneralUtils.readJsonToString(fileLocation);
			String discordid = Stats.getDiscordId(localData);

			String uuid = Stats.getUUID(localData);
			int oldQ;
			int newQ;
			int oldF;
			int newF;
			
			if(!GeneralUtils.isInsideTheServer(discordid)) {
				GeneralUtils.unlinkMember(uuid);
				continue;
			}
			if(!forced && !GeneralUtils.isOnline(discordid)) continue;

			data = Request.getPlayerStatsUUID(uuid);
			if (data == null) continue;
			if (API.getPlayer(data) == null) continue;

			oldQ = Stats.getQualificationToInt(localData);
			newQ = API.getQualificationToInt(data);
			oldF = Stats.getFinalsToInt(localData);
			newF = API.getFinalsToInt(data);

			if (newQ > oldQ) PBMessage.pbMessage(data, discordid, uuid, 'q');
			if (newF > oldF) PBMessage.pbMessage(data, discordid, uuid, 'f');
			
			GeneralUtils.updateFile(data, localData, uuid, "linked player", false);
			GeneralUtils.updateFile(data, localData, uuid, "leaderboard", false);
			GeneralUtils.updateLifeTimeRoles();
			LeaderboardList.init();

			delay();
		}
	}
	
	/**
	 * Stop the current thread for a defined amount of time
	 */
	private void delay() {
		try {
			Thread.sleep(450);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
