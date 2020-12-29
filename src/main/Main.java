package main;

import java.io.File;
import javax.security.auth.login.LoginException;

import core.Bot;

public class Main {
	private static final String TOKEN = "";
	private static final String ACTIVITY = "";
	public static final String serverID = "";
	public static boolean trackerInformation = false;
	public static boolean leaderboardInformation = false;
	public static boolean forceTracker  = false;
	
	/**
	 * Main
	 * @param args
	 * @author Blackoutburst
	 */
	public static void main(String[] args) {
		Bot bot = new Bot();

		setup();
		
		try {
			bot.login(TOKEN, ACTIVITY);
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate useful folder on start
	 * @author Blackoutburst
	 */
	private static void setup() {
		File f = new File("linked player");
		
		if (!f.exists())
			f.mkdir();
		f = new File("leaderboard");
		if (!f.exists())
			f.mkdir();
	}
}
