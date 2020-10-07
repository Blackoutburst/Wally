package main;

import javax.security.auth.login.LoginException;

import core.Bot;

public class Main {
	// Constant definition
	private static final String TOKEN = "X";
	private static final String ACTIVITY = "!help";
	
	/**
	 *  Main
	 * @param args
	 */
	public static void main(String[] args) {
		Bot bot = new Bot();
		
		try {
			bot.login(TOKEN, ACTIVITY);
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
}
