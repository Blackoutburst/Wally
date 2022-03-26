package com.blackoutburst.wally.main;

import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.Wally;

import javax.security.auth.login.LoginException;

public class Main {

	public static void main(String[] args) throws LoginException {
		ConfigManager.init();

		Wally.init(ConfigManager.loadValue("settings", "discord token"));
	}
}