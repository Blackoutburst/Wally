package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import comparators.PlayerComparatorF;
import comparators.PlayerComparatorQ;
import comparators.PlayerComparatorRounds;
import comparators.PlayerComparatorTotal;
import comparators.PlayerComparatorWins;
import core.Bot;
import core.Command;
import core.Request;
import core.RolesManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;

public class GeneralUtils {

	/**
	 * Update lifetime leaderboard roles
	 */
	public static void updateLifeTimeRoles() {
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(new File("linked player"));
		List<LeaderboardPlayer> lb = generatePlayerList(new File("leaderboard"));
	
		for (LeaderboardPlayer player : lead) {
			Member member = Bot.server.getMemberById(player.discord);
			if (member == null) continue;
			
			new RolesManager().cleanLifeTimeRole(member, lb, player);

			if (GeneralUtils.getLBPosToInt(player.name, 'w', lb) < 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime Wins");
			if (GeneralUtils.getLBPosToInt(player.name, 'q', lb) < 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime Q");
			if (GeneralUtils.getLBPosToInt(player.name, 'f', lb) < 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime F");
			if (GeneralUtils.getLBPosToInt(player.name, 'r', lb) < 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime Walls");
		}
	}
	
	/**
	 * Send a typing event
	 * @param event
	 */
	public static void startTyping(MessageChannel chan) {
		try {
			chan.sendTyping().complete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if member is staff
	 * @param member
	 * @return
	 */
	public static boolean isStaff(Member member) {
		Role staffRole = Bot.server.getRoleById(ConfigManager.getRoleId("Staff"));
		Role botDevRole = Bot.server.getRoleById(ConfigManager.getRoleId("Bot Repairman"));
		List<Role> memberRoles = member.getRoles();
		return (memberRoles.contains(staffRole) || memberRoles.contains(botDevRole) || member.hasPermission(Permission.ADMINISTRATOR));
	}

	/**
	 * Remove useless space in string
	 * @param str
	 * @return
	 */
	public static String removeDuplicateSpace(String str) {
		return str.replaceAll("\\s+", " ");
	}

	/**
	 * Check if the player is linked
	 * @param player
	 * @return
	 */
	public static boolean isLinkedIGN(String player) {
		String uuid = Request.getPlayerUUID(player);
		String playerFolder = "linked player/" + uuid;

		return new File(playerFolder).exists();
	}

	/**
	 * Check if the player is linked
	 * @param uuid
	 * @return
	 */
	public static boolean isLinkedUUID(String uuid) {
		String playerFolder = "linked player/" + uuid;

		return new File(playerFolder).exists();
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
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

			if (obj.getString("discordid").equals(discordid)) {
				return (true);
			}

		}
		return (false);
	}

	/**
	 * Check if JSON is valid
	 * @param player
	 * @return
	 */
	public static boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
			return (true);
		} catch (Exception e) {
			return (false);
		}
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
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

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
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

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
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

			if (obj.getString("name").equalsIgnoreCase(ign)) {
				return (obj.getString("discordid"));
			}
		}
		return (null);
	}

	/**
	 * Create an new JSON object from a json file
	 * @param file
	 * @return
	 */
	public static JSONObject readJson(String file) {
		return (new JSONObject(GeneralUtils.readJsonToString(file)));
	}

	/**
	 * Create an new string from a json file
	 * @param file
	 * @return
	 */
	public static String readJsonToString(String file) {
		try {
			if (new File(file).exists())
				return new String(Files.readAllBytes(Paths.get(file)));
		} catch (Exception e) {
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
			JSONObject obj = new JSONObject()
				.put("name", API.getName(data))
				.put("wins", API.getWins(data))
				.put("subtitle", "")
				.put("walls", API.getWalls(data))
				.put("qualification", API.getQualification(data))
				.put("finals", API.getFinals(data))
				.put("uuid", uuid);

			try {
				Files.write(Paths.get("leaderboard/" + uuid + "/data.json"), obj.toString(4).getBytes());
				MessageSender.messageJSON(command, "leaderboard add");
			} catch (Exception e) {
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
		Role hisRole = Bot.server.getRoleById(ConfigManager.getRoleId("He/Him"));
		Role herRole = Bot.server.getRoleById(ConfigManager.getRoleId("She/Her"));
		Role theirRole = Bot.server.getRoleById(ConfigManager.getRoleId("They/Them"));

		List<Role> memberRoles = Bot.server.getMemberById(discordid).getRoles();

		boolean his = memberRoles.contains(hisRole);
		boolean her = memberRoles.contains(herRole);
		boolean their = memberRoles.contains(theirRole);

		if (his && !her && !their) return ("his");
		if (her && !his && !their) return ("her");
		return ("their");
	}

	/**
	 * Check is the user in inside the discord server
	 * @param discordid
	 * @return
	 */
	public static boolean isInsideTheServer(String discordid) {
		Member member = Bot.server.getMemberById(discordid);

		return (member != null);
	}

	/**
	 * Check if the user in online
	 * @param discordid
	 * @return
	 */
	public static boolean isOnline(String discordid) {
		Member member = Bot.server.getMemberById(discordid);

		return (member != null && (member.getOnlineStatus() != OnlineStatus.OFFLINE && member.getOnlineStatus() != OnlineStatus.IDLE));
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
		if (localData == null) return;
		
		JSONObject obj = new JSONObject(localData)
			.put("wins", API.getWinsToInt(data))
			.put("walls", API.getWallsToInt(data))
			.put("qualification", API.getQualificationToInt(data))
			.put("finals", API.getFinalsToInt(data))
			.put("name", API.getName(data));

		try {
			Files.write(Paths.get(folder + "/" + uuid + "/data.json"), obj.toString(4).getBytes(), StandardOpenOption.WRITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sort leader board
	 * @param user
	 * @return
	 */
	public static List<LeaderboardPlayer> sortLB(List<LeaderboardPlayer> lead, char type) {
		switch (type) {
			case 'w' : Collections.sort(lead, new PlayerComparatorWins()); break;
			case 'r' : Collections.sort(lead, new PlayerComparatorRounds()); break;
			case 'q' : Collections.sort(lead, new PlayerComparatorQ()); break;
			case 'f' : Collections.sort(lead, new PlayerComparatorF()); break;
			case 't' : Collections.sort(lead, new PlayerComparatorTotal()); break;
			default : Collections.sort(lead, new PlayerComparatorWins()); break;
		}
		return (lead);
	}

	/**
	 * Get player position in the leader board
	 * @param user
	 * @return
	 */
	public static String getLBPos(String user, char type) {
		List<LeaderboardPlayer> lead = generatePlayerList(new File("leaderboard"));
		
		int pos = getLBPosToInt(user, type, lead) + 1;
		return (pos != 10001) ? " (#"+ pos + ")" : "";
	}

	/**
	 * Get player position in the leader board
	 * @param user
	 * @return
	 */
	public static int getLBPosToInt(String user, char type, List<LeaderboardPlayer> lead) {
		lead = sortLB(lead, type);

		if(user.length() > 31) {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).uuid.equals(user)) return i;
			}
			return (10000);
		} else {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).name.equals(user)) return i;
			}
			return (10000);
		}
	}

	/**
	 * Generate list of player from specified data folder
	 * @param index
	 * @return
	 */
	public static List<LeaderboardPlayer> generatePlayerList(File index) {
		List<LeaderboardPlayer> lead = new ArrayList<LeaderboardPlayer>();

		String[]entries = index.list();
		for(String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			String data = GeneralUtils.readJsonToString(playerFolder + "/data.json");

			lead.add(new LeaderboardPlayer(data));
		}
		return (lead);
	}
	
	/**
	 * Get player head
	 * @param uuid
	 */
	public static void getPlayerHead(String uuid) {
		try {
			URL url = new URL("https://crafatar.com/avatars/" + uuid + "?overlay");
			FileOutputStream fos = new FileOutputStream("res/head.png");
			
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			br.close();
			
			byte[] response = out.toByteArray();
			fos.write(response);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
