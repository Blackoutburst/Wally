package core;

import java.io.File;

import utils.GeneralUtils;

public class PLeaderboardUpdater {
	
	public static boolean forced = false;
	public static Thread plbupdaterThread = null;
	
	public void start() {
		plbupdaterThread = new Thread(new Runnable(){
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
		plbupdaterThread.setDaemon(true);
		plbupdaterThread.setName("Practice Leaderboard updater");
		plbupdaterThread.start();
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
			
			GeneralUtils.updateFile(data, localData, uuid, "pleaderboard", true);
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
