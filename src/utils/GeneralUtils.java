package utils;

import comparators.*;
import core.Bot;
import core.Command;
import core.RolesManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneralUtils {

	public static boolean hasCustomBackground(String uuid) {
		String playerFolder = "linked player/" + uuid;

		return(new File(playerFolder + "/background.png").exists());
	}

	public static String getCustomFont(String uuid) {
		String playerFolder = "linked player/" + uuid;

		if(new File(playerFolder + "/font.ttf").exists()) {
			return (playerFolder + "/font.ttf");
		}

		return ("res/font.ttf");
	}

	public static boolean hasHeader(String uuid) {
		return (!new File("linked player/" + uuid + "/hideHeader").exists());
	}
	
	public static String getCustomBackground(String uuid) {
		String playerFolder = "linked player/" + uuid;

		if(new File(playerFolder + "/background.png").exists()) {
			return (playerFolder + "/background.png");
		}

		return ("res/backgroundLarge.png");
	}
	
	public static Color getFontColor(String uuid) {
		String playerFolder = "linked player/" + uuid;

		if(new File(playerFolder + "/fontColor").exists()) {
			try {
				String hex = Files.readAllLines(Paths.get(playerFolder + "/fontColor")).get(0);
				return (Color.decode(hex));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (Color.white);
	}
	
	/**
	 * Update lifetime leaderboard roles
	 */
	public static void updateLifeTimeRoles() {
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(new File("linked player"), false);
		List<LeaderboardPlayer> lb = generatePlayerList(new File("leaderboard"), false);
	
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
	 */
	public static boolean isStaff(Member member) {
		Role staffRole = Bot.server.getRoleById(ConfigManager.getRoleId("Staff"));
		Role botDevRole = Bot.server.getRoleById(ConfigManager.getRoleId("Bot Repairman"));
		List<Role> memberRoles = member.getRoles();
		return (memberRoles.contains(staffRole) || memberRoles.contains(botDevRole) || member.hasPermission(Permission.ADMINISTRATOR));
	}

	/**
	 * Remove useless space in string
	 */
	public static String removeDuplicateSpace(String str) {
		return str.replaceAll("\\s+", " ");
	}

	/**
	 * Check if the player is linked
	 */
	public static boolean isLinkedUUID(String uuid) {
		return new File("linked player/" + uuid).exists();
	}
	
	/**
	 * Check if the player is linked
	 */
	public static boolean isLinkedDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();

		if (entries == null) return (false);

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
	 */
	public static String getUUIDfromDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();

		if (entries == null) return (null);

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
	 */
	public static String getIGNfromDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();

		if (entries == null) return (null);

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
	 */
	public static String getDiscordfromIGN(String ign) {
		File index = new File("linked player");
		String[] entries  = index.list();

		if (entries == null) return (null);

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
	 * Create a new JSON object from a json file
	 */
	public static JSONObject readJson(String file) {
		return (new JSONObject(Objects.requireNonNull(GeneralUtils.readJsonToString(file))));
	}

	/**
	 * Create a new string from a json file
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
	 * Add a player to the practice leader board
	 */
	public static void addToPracticeLeaderBoard(String uuid, String data, Command command) {
		if (!new File("pleaderboard/" + uuid).exists()) {
			new File("pleaderboard/" + uuid).mkdir();
			JSONObject obj = new JSONObject()
				.put("name", PracticeAPI.getName(data))
				.put("qualification", PracticeAPI.getQualificationToInt(data))
				.put("finals", PracticeAPI.getFinalsToInt(data))
				.put("wide_qualification", PracticeAPI.getWideQualificationToInt(data))
				.put("wide_finals", PracticeAPI.getWideFinalsToInt(data))
				.put("lobby", PracticeAPI.getLobbyToInt(data))
				.put("uuid", uuid);

			try {
				Files.write(Paths.get("pleaderboard/" + uuid + "/data.json"), obj.toString(4).getBytes());
				MessageSender.messageJSON(command, "leaderboard add");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get gender prefix from roles
	 */
	public static String getGenderPrefix(String discordid) {
		Role hisRole = Bot.server.getRoleById(ConfigManager.getRoleId("He/Him"));
		Role herRole = Bot.server.getRoleById(ConfigManager.getRoleId("She/Her"));
		Role theirRole = Bot.server.getRoleById(ConfigManager.getRoleId("They/Them"));

		List<Role> memberRoles = Objects.requireNonNull(Bot.server.getMemberById(discordid)).getRoles();

		final boolean his = memberRoles.contains(hisRole);
		final boolean her = memberRoles.contains(herRole);
		final boolean their = memberRoles.contains(theirRole);

		if (his && !her && !their) return ("his");
		if (her && !his && !their) return ("her");
		return ("their");
	}

	/**
	 * Check is the user in inside the discord server
	 */
	public static boolean isInsideTheServer(String discordid) {
		Member member = Bot.server.getMemberById(discordid);

		return (member != null);
	}

	/**
	 * Check if the user in online
	 */
	public static boolean isOnline(String discordid) {
		Member member = Bot.server.getMemberById(discordid);

		return (member != null && (member.getOnlineStatus() != OnlineStatus.OFFLINE && member.getOnlineStatus() != OnlineStatus.IDLE));
	}

	/**
	 * Remove a member from the database
	 */
	public static void unlinkMember(String uuid) {
		File index = new File("linked player/" + uuid);
		String[] entries = index.list();

		if (entries == null) return;

		for(String s : entries) {
			File file = new File(index.getPath(), s);

			file.delete();
		}
		index.delete();
	}

	/**
	 * Update player data file
	 */
	public static void updateFile(String data, String localData, String uuid, String folder, boolean practiceServer) {
		if (localData == null) return;
		
		JSONObject obj = new JSONObject(localData);
		if (practiceServer) {
			obj.put("qualification", PracticeAPI.getQualificationToInt(data));
			obj.put("finals", PracticeAPI.getFinalsToInt(data));
			obj.put("wide_qualification", PracticeAPI.getWideQualificationToInt(data));
			obj.put("wide_finals", PracticeAPI.getWideFinalsToInt(data));
			obj.put("lobby", PracticeAPI.getLobbyToInt(data));
			obj.put("name", PracticeAPI.getName(data));
		} else {
			obj.put("wins", API.getWinsToInt(data));
			obj.put("walls", API.getWallsToInt(data));
			obj.put("qualification", API.getQualificationToInt(data));
			obj.put("finals", API.getFinalsToInt(data));
			obj.put("name", API.getName(data));
		}
		try {
			Files.write(Paths.get(folder + "/" + uuid + "/data.json"), obj.toString(4).getBytes(), StandardOpenOption.WRITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sort leader board
	 */
	public static void sortLB(List<LeaderboardPlayer> lead, char type) {
		switch (type) {
			case 'r' : lead.sort(new PlayerComparatorRounds()); break;
			case 'q' : lead.sort(new PlayerComparatorQ()); break;
			case 'f' : lead.sort(new PlayerComparatorF()); break;
			case 't' : lead.sort(new PlayerComparatorTotal()); break;
			case 'o' : lead.sort(new PlayerComparatorOverall()); break;
			default : lead.sort(new PlayerComparatorWins()); break;
		}
	}
	
	/**
	 * Sort leader board
	 */
	public static void sortPLB(List<PracticeLeaderboardPlayer> lead, String type) {
		switch (type) {
			case "f" : lead.sort(new PlayerComparatorPF()); break;
			case "wq" : lead.sort(new PlayerComparatorWQ()); break;
			case "wf" : lead.sort(new PlayerComparatorWF()); break;
			case "l" : lead.sort(new PlayerComparatorL()); break;
			case "t" : lead.sort(new PlayerComparatorLQF()); break;
			case "o" : lead.sort(new PlayerComparatorPOverall()); break;
			default : lead.sort(new PlayerComparatorPQ()); break;
		}
	}

	/**
	 * Get player position in the leader board
	 */
	public static int getLBPosToInt(String user, char type, List<LeaderboardPlayer> lead) {
		sortLB(lead, type);

		if(user.length() > 31) {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).uuid.equals(user)) return i;
			}
		} else {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).name.equals(user)) return i;
			}
		}
		return (10000);
	}
	
	/**
	 * Get player position in the leader board
	 */
	public static int getLBPosToIntFast(String user, List<LeaderboardPlayer> lead) {
		if(user.length() > 31) {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).uuid.equals(user)) return i;
			}
		} else {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).name.equals(user)) return i;
			}
		}
		return (10000);
	}

	/**
	 * Get player position in the leader board
	 */
	public static int getPLBPosToIntFast(String user, List<PracticeLeaderboardPlayer> lead) {
		if(user.length() > 31) {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).uuid.equals(user)) return i;
			}
		} else {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).name.equals(user)) return i;
			}
		}
		return (10000);
	}

	/**
	 * Get player position in the leader board
	 */
	public static int getPLBPosToInt(String user, String type, List<PracticeLeaderboardPlayer> lead) {
		sortPLB(lead, type);

		if(user.length() > 31) {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).uuid.equals(user)) return i;
			}
		} else {
			for(int i = 0; i < lead.size(); i++) {
				if(lead.get(i).name.equals(user)) return i;
			}
		}
		return (10000);
	}

	/**
	 * Generate list of player from specified data folder
	 */
	public static List<LeaderboardPlayer> generatePlayerList(File index, boolean overall) {
		List<LeaderboardPlayer> lead = new ArrayList<>();
		String[]entries = index.list();

		if (entries == null) return (lead);

		for(String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			String data = GeneralUtils.readJsonToString(playerFolder + "/data.json");

			lead.add(new LeaderboardPlayer(data, false));
		}
		
		if (overall) {
			for (LeaderboardPlayer p : lead) {
				float rank = GeneralUtils.getLBPosToInt(p.uuid, 'w', new ArrayList<>(lead));
				rank += GeneralUtils.getLBPosToInt(p.uuid, 'r', new ArrayList<>(lead));
				rank += GeneralUtils.getLBPosToInt(p.uuid, 'q', new ArrayList<>(lead));
				rank += GeneralUtils.getLBPosToInt(p.uuid, 'f', new ArrayList<>(lead));
				rank += GeneralUtils.getLBPosToInt(p.uuid, 't', new ArrayList<>(lead));
				rank = (rank / 5) + 1;
				p.overall = rank;
			}
		}
		
		return (lead);
	}
	
	/**
	 * Generate list of player from specified data folder
	 */
	public static List<PracticeLeaderboardPlayer> generatePracticePlayerList(File index, boolean overall) {
		List<PracticeLeaderboardPlayer> lead = new ArrayList<>();
		String[]entries = index.list();

		if (entries == null) return (lead);

		for(String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			String data = GeneralUtils.readJsonToString(playerFolder + "/data.json");

			lead.add(new PracticeLeaderboardPlayer(data));
		}
		
		if (overall) {
			for (PracticeLeaderboardPlayer p : lead) {
				float rank = GeneralUtils.getPLBPosToInt(p.uuid, "q", new ArrayList<>(lead));
				rank += GeneralUtils.getPLBPosToInt(p.uuid, "f", new ArrayList<>(lead));
				rank += GeneralUtils.getPLBPosToInt(p.uuid, "l", new ArrayList<>(lead));
				rank += GeneralUtils.getPLBPosToInt(p.uuid, "wq", new ArrayList<>(lead));
				rank += GeneralUtils.getPLBPosToInt(p.uuid, "wf", new ArrayList<>(lead));
				rank += GeneralUtils.getPLBPosToInt(p.uuid, "t", new ArrayList<>(lead));
				rank = (rank / 6) + 1;
				p.overall = rank;
			}
		}
		
		return (lead);
	}
	
	/**
	 * Get player head
	 */
	public static void getPlayerHead(String uuid) {
		try {
			URL url = new URL("https://visage.surgeplay.com/face/128/" + uuid);
			FileOutputStream fos = new FileOutputStream("res/head.png");
			
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n;
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
