package core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import main.Main;
import utils.Stats;

public class Request {
	
	/**
	 * Get player stats from Hypixel API (slower)
	 * @param user
	 * @return
	 */
	public static String getPlayerStats(String user) {
		try {
			String uuid = getPlayerUUID(user);
			URL url = new URL("https://api.hypixel.net/player?uuid=" + uuid + "&key=" + Main.API);
			
			return (getStats(url));
		} catch (MalformedURLException e) {
			return (null);
		}
	}
	
	/**
	 * Get player stats from Hypixel API using UUID (faster)
	 * @param user
	 * @return
	 */
	public static String getPlayerStatsUUID(String uuid) {
			try {
				URL url = new URL("https://api.hypixel.net/player?uuid=" + uuid + "&key=" + Main.API);
				
				return (getStats(url));
			} catch (MalformedURLException e) {
				return (null);
			}
	}
	
	/**
	 * Get player stats from Hypixel API
	 * @param url
	 * @return
	 */
	private static String getStats(URL url) {
		try {
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder builder = new StringBuilder();
			String line = null;
			
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
	 * Get player UUID from his user name
	 * @param user
	 * @return
	 */
	public static String getPlayerUUID(String player) {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder builder = new StringBuilder();
			String line = null;
			
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
	
	/**
	 * Get user achievement point bar from plancke generator
	 * @param name
	 * @return
	 */
	public static String getAPBar(String name) {
		try {
			URL url = new URL("https://gen.plancke.io/achievementPoints/" + name + ".png");
			FileOutputStream fos = new FileOutputStream("ap.png");
			
			byte[] response = getPicture(url);
			fos.write(response);
			fos.close();
			return ("ok");
		} catch (Exception e) {
			return (null);
		}
	}
	
	/**
	 * Get user level bar from plancke generator
	 * @param name
	 * @return
	 */
	public static String getLevelBar(String name) {
		try {
			URL url = new URL("https://gen.plancke.io/exp/" + name + ".png");
			FileOutputStream fos = new FileOutputStream("level.png");
			
			byte[] response = getPicture(url);
			fos.write(response);
			fos.close();
			return ("ok");
		} catch (Exception e) {
			return (null);
		}
	}
	
	/**
	 * Get picture from plancke generator
	 * @param url
	 * @return
	 */
	private static byte[] getPicture(URL url) {
		try {
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			if (br.readLine().contains("ensure"))return (null);
				
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
			return (out.toByteArray());
		} catch (Exception e) {
			return (null);
		}
	}
}
