package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Request {
	/**
	 * Run JS file to get player information
	 * @param user
	 * @return player information
	 * @see player_request.js
	 * @author Blackoutburst
	 */
	public static String getPlayerInfo(String user) {
		Bot.request++;
		if (Bot.request >= 119) {
			System.err.println("API limit reached");
			return "API LIMITATION";
		}
		ProcessBuilder pb = new ProcessBuilder("node", "player_request.js", user);
		
		try {
			Process p = pb.start();
			pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			pb.redirectError(ProcessBuilder.Redirect.INHERIT);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = reader.readLine()) != null) {
			   builder.append(line);
			   builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	/**
	 * Run JS file to get player information
	 * @param user
	 * @return player information
	 * @see player_requestuuid.js
	 * @author Blackoutburst
	 */
	public static String getPlayerInfoUUID(String uuid) {
		Bot.request++;
		if (Bot.request >= 119) {
			System.err.println("API limit reached");
			return "API LIMITATION";
		}
		ProcessBuilder pb = new ProcessBuilder("node", "player_requestuuid.js", uuid);
		
		try {
			Process p = pb.start();
			pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			pb.redirectError(ProcessBuilder.Redirect.INHERIT);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = reader.readLine()) != null) {
			   builder.append(line);
			   builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	/**
	 * Use username to get user uuid
	 * @param user
	 * @return user uuid
	 * @author Blackoutburst
	 */
	public static String getPlayerUUID(String user) {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/"+user);
		    URLConnection con = url.openConnection();
		    InputStream is =con.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String uuid = br.readLine().split("\"")[7];
			return (uuid);
		} catch (Exception e) {
			return (null);
		}
	}
	
	/**
	 * Use uuid to get user name
	 * @param user uuid
	 * @return user
	 * @author Blackoutburst
	 */
	public static String getPlayer(String uuid) {
		try {
			URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/"+uuid);
		    URLConnection con = url.openConnection();
		    InputStream is =con.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    br.readLine();
		    br.readLine();
		    String ign = br.readLine().split("\"")[3];
			return (ign);
		} catch (Exception e) {
			return (null);
		}
	}
}
