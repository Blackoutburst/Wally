package com.blackoutburst.wally.main;

import com.blackoutburst.wally.core.Logger;
import com.blackoutburst.wally.core.Wally;

import javax.security.auth.login.LoginException;

public class Main {

	public static void main(String[] args) throws LoginException {
		if (args.length == 0) {
			Logger.error("You must set the bot Token as first program argument");
			return;
		}

		Wally.init(args[0]);
	}
}