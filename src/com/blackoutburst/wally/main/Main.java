package com.blackoutburst.wally.main;

import com.blackoutburst.wally.core.Wally;

import javax.security.auth.login.LoginException;

public class Main {
	
	public static void main(String[] args) throws LoginException {
		if (args.length == 0) {
			System.err.println("You must set the bot Token as first program argument");
			return;
		}

		Wally.init(args[0]);
	}
}