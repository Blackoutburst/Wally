package core;

import java.io.File;

import utils.API;
import utils.Utils;

public class LeaderboardUpdater {
	
	public static boolean forced = false;
	
	public LeaderboardUpdater() {
		Thread lbupdaterThread = new Thread(new Runnable(){
			public void run(){
				while (true) {
					checkUsers();
					delay(60000);
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
			String data = Request.getPlayerUUID(playerFolder.getName());
			String localData = Utils.readJsonToString(playerFolder + "/data.json");
			
			if (data == null) continue;
			if (API.getPlayer(data).equals(null)) continue;
			
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
