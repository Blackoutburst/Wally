package core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import main.Main;

public class Request {
	/**
	 * Run JS file to get player information
	 * @param user
	 * @return player information
	 * @see player_request.js
	 * @author Blackoutburst
	 */
	public static String getPlayerInfo(String user) {
		String uuid = getPlayerUUID(user);
		
		try {
			URL url = new URL("https://api.hypixel.net/player?uuid="+uuid+"&key="+Main.API);
		    URLConnection con = url.openConnection();
		    InputStream is =con.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = br.readLine()) != null) {
			   builder.append(line);
			   builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();
		} catch (Exception e) {
			System.err.println("API limit reached");
			return "API LIMITATION";
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
		try {
			URL url = new URL("https://api.hypixel.net/player?uuid="+uuid+"&key="+Main.API);
		    URLConnection con = url.openConnection();
		    InputStream is =con.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = br.readLine()) != null) {
			   builder.append(line);
			   builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();
		} catch (Exception e) {
			System.err.println("API limit reached");
			return "API LIMITATION";
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
	
	/**
	 * Get player Hypixel AP
	 * @param user name
	 * @return ap
	 * @author Blackoutburst
	 */
	public static String getPlanckeAP(String name) {
		try {
			URL url = new URL("https://gen.plancke.io/achievementPoints/"+name+".png");
		    URLConnection con = url.openConnection();
		    InputStream is =con.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    if (br.readLine().contains("ensure")) {
		    	return (null);
		    } else {
		    	InputStream in = new BufferedInputStream(url.openStream());
		    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		    	byte[] buf = new byte[1024];
		    	int n = 0;
		    	while (-1!=(n=in.read(buf)))
		    	{
		    	   out.write(buf, 0, n);
		    	}
		    	out.close();
		    	in.close();
		    	byte[] response = out.toByteArray();
		    	FileOutputStream fos = new FileOutputStream("ap.png");
		    	fos.write(response);
		    	fos.close();
		    	return ("ok");
		    }
		} catch (Exception e) {
			return (null);
		}
	}
	
	
	/**
	 * Get player Hypixel Level
	 * @param user name
	 * @return level
	 * @author Blackoutburst
	 */
	public static String getPlanckeLevel(String name) {
		try {
			URL url = new URL("https://gen.plancke.io/exp/"+name+".png");
		    URLConnection con = url.openConnection();
		    InputStream is =con.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    if (br.readLine().contains("ensure")) {
		    	return (null);
		    } else {
		    	InputStream in = new BufferedInputStream(url.openStream());
		    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		    	byte[] buf = new byte[1024];
		    	int n = 0;
		    	while (-1!=(n=in.read(buf)))
		    	{
		    	   out.write(buf, 0, n);
		    	}
		    	out.close();
		    	in.close();
		    	byte[] response = out.toByteArray();
		    	FileOutputStream fos = new FileOutputStream("level.png");
		    	fos.write(response);
		    	fos.close();
		    	return ("ok");
		    }
		} catch (Exception e) {
			return (null);
		}
	}
}
