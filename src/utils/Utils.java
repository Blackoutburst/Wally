package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

import core.Bot;
import core.Command;
import core.Request;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class Utils {
	
	/**
	 * Check if member is staff
	 * @param member
	 * @return
	 */
	public static boolean isStaff(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getId().equals(Config.getRoleId("Staff"))) {
				return (true);
			}
		}
		if (member.hasPermission(Permission.ADMINISTRATOR)) {
			return (true);
		}
		return (false);
	}
	
	/**
	 * Remove useless space in string
	 * @param str
	 * @return
	 */
	public static String removeDuplicateSpace(String str) {
		while(str.contains("  ")) {
			str = str.replace("  ", " ");
		}
		return (str);
	}
	
	/**
	 * Check if the player is linked
	 * @param player
	 * @return
	 */
	public static boolean isLinkedIGN(String player) {
		String uuid = Request.getPlayerUUID(player);
		File index = new File("linked player");
		String[] entries  = index.list();
		
		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			
			if (playerFolder.getName().equals(uuid)) {
				return (true);
			}
		}
		return (false);
	}
	
	/**
	 * Check if the player is linked
	 * @param player
	 * @return
	 */
	public static boolean isLinkedDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();
		
		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = Utils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(discordid)) {
				return (true);
			}
			
		}
		return (false);
	}
	
	/**
	 * Get player uuid from is discord id
	 * @param player
	 * @return
	 */
	public static String getUUIDfromDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();
		
		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = Utils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(discordid)) {
				return (obj.getString("uuid"));
			}
		}
		return (null);
	}
	
	/**
	 * Get user IGN from his discord account
	 * @param player
	 * @return
	 */
	public static String getIGNfromDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();
		
		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = Utils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(discordid)) {
				return (obj.getString("name"));
			}
		}
		return (null);
	}
	
	/**
	 * Get user discord id from is IGN
	 * @param player
	 * @return
	 */
	public static String getDiscordfromIGN(String ign) {
		File index = new File("linked player");
		String[] entries  = index.list();
		
		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = Utils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("name").equalsIgnoreCase(ign)) {
				return (obj.getString("discordid"));
			}
		}
		return (null);
	}
	
	/**
	 * Get player background path
	 * @param player
	 * @return
	 */
	public static String getCustomBackground(String player) {
		String uuid = Request.getPlayerUUID(player);
		File index = new File("linked player");
		String[] entries  = index.list();
		
		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			
			if (playerFolder.getName().equals(uuid)) {
				if (new File(playerFolder + "/background.png").exists()) {
					return (playerFolder + "/background.png");
				}
			}
		}
		return ("res/background.png");
	}
	
	/**
	 * Create an new JSON object from a json file
	 * @param file
	 * @return
	 */
	public static JSONObject readJson(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String json = "";
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				json += line;
			}
			reader.close();
			return (new JSONObject(json));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (null);
	}
	
	/**
	 * Create an new string from a json file
	 * @param file
	 * @return
	 */
	public static String readJsonToString(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String json = "";
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				json += line;
			}
			reader.close();
			return (json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (null);
	}
	
	/**
	 * Add a player to the leader board
	 * @param uuid
	 * @param data
	 */
	public static void addToLeaderBoard(String uuid, String data, Command command) {
		if (!new File("leaderboard/" + uuid).exists()) {
			new File("leaderboard/" + uuid).mkdir();
			JSONObject obj = new JSONObject();
			
			obj.put("name", API.getName(data));
			obj.put("subtitle", "");
			obj.put("wins", API.getWins(data));
			obj.put("walls", API.getWalls(data));
			obj.put("qualification", API.getQualification(data));
			obj.put("finals", API.getFinals(data));
			obj.put("uuid", uuid);
			
			try {
				PrintWriter writer = new PrintWriter("leaderboard/" + uuid + "/data.json");
				writer.write(obj.toString(4));
				writer.close();		
				MessageSender.messageJSON(command, "leaderboard add");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get gender prefix from roles
	 * @param discordid
	 * @return
	 */
	public static String getGenderPrefix(String discordid) {
		boolean his = false;
		boolean her = false;
		boolean their = false;
		
		for (Role r : Bot.server.getMemberById(discordid).getRoles()) {
			if (r.getId().equals(Config.getRoleId("She/Her"))) her = true;
			if (r.getId().equals(Config.getRoleId("He/Him"))) his = true;
			if (r.getId().equals(Config.getRoleId("They/Them"))) their = true;
		}
		
		if (their) return ("their");
		if (his && her) return ("their");
		if (his) return ("his");
		if (her) return ("her");
		return ("their");
	}
	
	/**
	 * Check is the user in inside the discord server
	 * @param discordid
	 * @return
	 */
	public static boolean isInsideTheServer(String discordid) {
		Member member = Bot.server.getMemberById(discordid);
		
		if (member == null) return (false);
		return (true);
	}
	
	/**
	 * Check if the user in online
	 * @param discordid
	 * @return
	 */
	public static boolean isOnline(String discordid) {
		Member member = Bot.server.getMemberById(discordid);
		
		if (member.getOnlineStatus() == OnlineStatus.OFFLINE || member.getOnlineStatus() == OnlineStatus.IDLE) return (false);
		return (true);
	}
	
	/**
	 * Remove a member from the database
	 * @param uuid
	 */
	public static void unlinkMember(String uuid) {
		File index = new File("linked player/" + uuid);
		String[] entries = index.list();
		
		for(String s : entries) {
			File file = new File(index.getPath(), s);
			
			file.delete();
		}
		index.delete();
	}
	
	/**
	 * Update player data file
	 * @param data
	 * @param uuid
	 * @param folder
	 */
	public static void updateFile(String data, String localData, String uuid, String folder) {
		JSONObject obj = new JSONObject(localData);
				
		obj.put("wins", API.getWinsToInt(data));
		obj.put("walls", API.getWallsToInt(data));
		obj.put("qualification", API.getQualificationToInt(data));
		obj.put("finals", API.getFinalsToInt(data));
		obj.put("name", API.getName(data));

		try {
			PrintWriter writer = new PrintWriter(folder + "/" + uuid + "/data.json");
			writer.write(obj.toString(4));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
