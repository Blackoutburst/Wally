package main;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import core.Bot;
import utils.ConfigManager;

public class Main {

	private static final String TOKEN = "X";
	private static final String ACTIVITY = "Hole in the Wall";

	public static final String BOT_ID = "X";
	public static final String API = "X";
	public static final String PREFIX = "!";

	public static final String PRACTICE_API_IP = "X";
	public static final int PRACTICE_API_PORT = 30100;
	
	public static void main(String[] args) throws LoginException, IOException {
		new ConfigManager().init("config.json");
		new Bot(TOKEN, ACTIVITY);
	}

}
