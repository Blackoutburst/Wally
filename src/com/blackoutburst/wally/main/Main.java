package com.blackoutburst.wally.main;

import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.database.DatabaseManager;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public static final String DISCORD_TOKEN = readEnv(0);
	public static final String HYPIXEL_API = readEnv(1);
	public static final String PRACTICE_SERVER_IP = readEnv(2);
	public static final String PRACTICE_SERVER_PORT = readEnv(3);

	/**
	 * Read environment variable
	 * @param index the line index
	 * @return the variable value
	 */
	private static String readEnv(int index) {
		try {
			final List<String> lines = Files.readAllLines(Paths.get("config/.env"));

			return lines.get(index).split("=")[1].replace("\"", "");
		} catch (Exception e) {
			System.err.println("Invalid .env file");
			System.exit(84);
			return null;
		}
	}

	public static void main(String[] args) {
		ConfigManager.getInstance();
		DatabaseManager.getInstance();
		Wally.getInstance();
	}
}