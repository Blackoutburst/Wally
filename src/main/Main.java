package main;

import java.io.File;

import javax.security.auth.login.LoginException;

import core.Bot;

public class Main {
	private static final String TOKEN = "NzYzMTQzMTExNzE4ODYyODQ5.X3zaaA.K7ZkFAW5wRLZJbaPBiHhjbm3TbE";
	private static final String ACTIVITY = "!help";
	public static final String bypassID = "X";
	public static final String serverID = "X";
	
	/**
	 *  Main
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
	
	private static void setup() {
		File f = new File("linked player");
		
		if (!f.exists()) {
			f.mkdir();
		}
	}
}
