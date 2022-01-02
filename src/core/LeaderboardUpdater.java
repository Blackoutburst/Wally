package core;

import java.io.File;

import utils.API;
import utils.GeneralUtils;

public class LeaderboardUpdater {
	
	public static Thread lbupdaterThread = null;
	
	public void start() {
		lbupdaterThread = new Thread(() -> {
			while (true) {
				try {
					checkUsers();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		lbupdaterThread.setDaemon(true);
		lbupdaterThread.setName("Leaderboard updater");
		lbupdaterThread.start();
	}
	
	/**
	 * Check every user register and update them
	 */
	private void checkUsers() {
		File index = new File("leaderboard");
		String[] entries = index.list();

		if (entries == null) return;

		for(String s: entries) {
			File playerFolder = new File(index.getPath(), s);
			String uuid = playerFolder.getName();
			String data = Request.getPlayerStatsUUID(playerFolder.getName());
			String localData = GeneralUtils.readJsonToString(playerFolder + "/data.json");
			
			if (data == null) continue;
			if (API.getPlayer(data) == null) continue;
			
			GeneralUtils.updateFile(data, localData, uuid, "leaderboard", false);
			delay();
		}
	}
	
	/**
	 * Stop the current thread for a defined amount of time
	 */
	private void delay() {
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
