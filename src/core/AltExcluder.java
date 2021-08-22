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
	 * @param uuid
	 * @return
	 */
	public static boolean isAnAlt(String uuid, Command command) {
		List<String> altUuid = readList();

		boolean alt = false;
		
		for (String str : altUuid) {
			String[] split = str.split(",");
			
			if (uuid.equals(split[0])) {
				if (split[2].equals("s")) {
					MessageSender.susAccount(command);
				} else if (split[2].equals("a")) {
					String data = Request.getPlayerStatsUUID(split[1]);
					MessageSender.altAccount(command, API.getName(data));
				} else if (split[2].equals("b")) {
					String data = Request.getPlayerStatsUUID(split[1]);
					MessageSender.boostedAccount(command, API.getName(data));
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
	 * @return
	 */
	private static List<String> readList() {
		List<String> uuids = new ArrayList<String>();
		
		try {
			FileReader in = new FileReader("./alts.txt");
			BufferedReader br = new BufferedReader(in);
			String line = null;
			
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
