package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import utils.API;
import utils.MessageSender;

public class AltExcluder {
	
	/**
	 * Check is this account is an alt using their uuid
	 */
	public static boolean isAnAlt(String uuid, Command command) {
		List<String> altUuid = readList();

		boolean alt = false;
		
		for (String str : altUuid) {
			String[] split = str.split(",");
			
			if (uuid.equals(split[0])) {
				switch (split[2]) {
					case "s":
						MessageSender.susAccount(command);
						break;
					case "a": {
						String data = Request.getPlayerStatsUUID(split[1]);
						MessageSender.altAccount(command, API.getName(data));
						break;
					}
					case "b": {
						String data = Request.getPlayerStatsUUID(split[1]);
						MessageSender.boostedAccount(command, API.getName(data));
						break;
					}
				}
				alt = true;
			}
		}
		
		if (alt)
			MessageSender.notOnLBAccount(command);
		
		return(alt);
	}
	
	/**
	 * Read alt account list
	 */
	private static List<String> readList() {
		List<String> uuids = new ArrayList<>();
		
		try {
			FileReader in = new FileReader("./alts.txt");
			BufferedReader br = new BufferedReader(in);
			String line;
			
			while ((line = br.readLine()) != null) {
				uuids.add(line);
			}
			br.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return(uuids);
	}
}
