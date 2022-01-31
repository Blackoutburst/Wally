package core;

import main.Main;
import utils.Stats;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Request {
	
	/**
	 * Get a player stats from the practice server API
	 */
	public static String getPracticeServerScore(String uuid) {
		Socket socket;
		try {
			socket = new Socket(Main.PRACTICE_API_IP, Main.PRACTICE_API_PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			OutputStream out = socket.getOutputStream();
			uuid += '\n';
			out.write(uuid.getBytes(StandardCharsets.UTF_8));
			String data = in.readLine();
			socket.close();
			in.close();
			out.close();
			return (data);
		} catch (Exception e) {
			return (null);
		}
	}
	
	/**
	 * Get player stats from Hypixel API (slower)
	 */
	public static String getPlayerStats(String user) {
		try {
			String uuid = getPlayerUUID(user);
			URL url = new URL("https://api.hypixel.net/player?uuid=" + uuid + "&key=" + Main.API);
			
			return (getStats(url));
		} catch (Exception e) {
			return (null);
		}
	}
	
	/**
	 * Get player stats from Hypixel API using UUID (faster)
	 */
	public static String getPlayerStatsUUID(String uuid) {
			try {
				URL url = new URL("https://api.hypixel.net/player?uuid=" + uuid + "&key=" + Main.API);
				
				return (getStats(url));
			} catch (Exception e) {
				return (null);
			}
	}
	
	/**
	 * Get player stats from Hypixel API
	 */
	private static String getStats(URL url) {
		try {
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder builder = new StringBuilder();
			String line;
			
			while ( (line = br.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			is.close();
			br.close();
			return (builder.toString());
		} catch (Exception e) {
			return (null);
		}
	}
	
	/**
	 * Get player UUID from his username
	 */
	public static String getPlayerUUID(String player) {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder builder = new StringBuilder();
			String line;
			
			while ( (line = br.readLine()) != null) {
					builder.append(line);
					builder.append(System.getProperty("line.separator"));
				}
			is.close();
			br.close();
			return (Stats.getID(builder.toString()));
		} catch (Exception e) {
			return (null);
		}
	}
}
