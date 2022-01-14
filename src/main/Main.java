package main;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import core.Bot;
import utils.ConfigManager;

public class Main {

	private static final String TOKEN = "ODA2MDg2MjQwNDI3OTY2NDg0.YBkUUA.wtnzbqYPpwQyDd7xXaYyX7WSycs";
	private static final String ACTIVITY = "Hole in the Wall";

	public static final String BOT_ID = "806086240427966484";
	public static final String API = "5687a8ae-cb08-4fa3-ba4f-77b9944916cb";
	public static final String PREFIX = "!";

	public static final String PRACTICE_API_IP = "178.32.125.169";
	public static final int PRACTICE_API_PORT = 30100;
	
	public static void main(String[] args) throws LoginException, IOException {
		new ConfigManager().init("config.json");
		new Bot(TOKEN, ACTIVITY);
	}

}
