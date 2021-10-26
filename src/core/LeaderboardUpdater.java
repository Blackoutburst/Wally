package core;

import java.io.File;

import utils.API;
import utils.GeneralUtils;

public class LeaderboardUpdater {
	
	public static boolean forced = false;
	public static Thread lbupdaterThread = null;
	
	public void start() {
		lbupdaterThread = new Thread(new Runnable(){
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
		
		for(String s: entries) {
			File playerFolder = new File(index.getPath(), s);
			String uuid = playerFolder.getName();
			String data = Request.getPlayerStatsUUID(playerFolder.getName());
			String localData = GeneralUtils.readJsonToString(playerFolder + "/data.json");
			
			if (data == null) continue;
			if (API.getPlayer(data) == null) continue;
			
			GeneralUtils.updateFile(data, localData, uuid, "leaderboard", false);
			delay(10000);
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
