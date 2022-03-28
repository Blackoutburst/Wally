package com.blackoutburst.wally.main;

import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.database.DatabaseManager;

public class Main {

	public static void main(String[] args) {
		ConfigManager.getInstance();
		DatabaseManager.getInstance();
		Wally.getInstance();
	}
}