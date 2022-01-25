package core;

import utils.API;
import utils.MessageSender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Patreon {
	
	/**
	 * Check is this account is an alt using their uuid
	 */
	public static boolean isPatreon(String uuid) {
		List<String> patreonUuid = readList();

		boolean patreon = false;
		
		for (String str : patreonUuid) {
			String[] split = str.split(",");
			
			if (uuid.equals(split[0])) {
				patreon = true;
			}
		}
		return(patreon);
	}

	public static int getPatreonLevel(String uuid) {
		List<String> patreonUuid = readList();

		int level = 0;

		for (String str : patreonUuid) {
			String[] split = str.split(",");

			if (uuid.equals(split[0])) {
				level = Integer.parseInt(split[1]);
			}
		}
		return(level);
	}
	
	private static List<String> readList() {
		List<String> uuids = new ArrayList<>();
		
		try {
			FileReader in = new FileReader("./patreon.txt");
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
