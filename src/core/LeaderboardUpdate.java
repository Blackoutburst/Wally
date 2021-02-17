package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparator.PlayerComparatorF;
import comparator.PlayerComparatorQ;
import comparator.PlayerComparatorWins;
import main.Main;
import net.dv8tion.jda.api.entities.Role;

public class LeaderboardUpdate {

	/**
	 * Track user pb displays them in chat and change user role
	 * @author Blackoutburst
	 */
	public void start() throws IOException {
		Thread leadThread = new Thread(new Runnable(){
			public void run(){
				while (true) {
					String output = "";
					String[] value;
					File index = new File("leaderboard");
					String[]entries = index.list();
					for(String s: entries) {
						String user = "";
						String qualification = "0";
						String finals = "0";
						String wins = "0";
						String rounds = "0";
						File f = new File(index.getPath(),s);
						
						if (Main.leaderboardInformation) {System.out.print(Request.getPlayer(f.getName())+" ");}
						output = Request.getPlayerInfoUUID(f.getName());
						if (output.equals("API LIMITATION")) {
							System.out.println("Lead aborted due to api limiation");
							break;
						}
						value = output.split(",");
						for (int i = 0; i < value.length; i++) {
							if (value[i].contains("hitw_record_q")) {
								qualification = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("hitw_record_f")) {
								finals = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("wins_hole_in_the_wall")) {
								wins = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("rounds_hole_in_the_wall")) {
								rounds = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("displayname")) {
								user = value[i].replace(" ", "").replace("\'", "").replace(",", "").replace("\"", "").split(":")[1];
							}
						}
						
						try {
							PrintWriter writer = new PrintWriter(f+"/W");
							writer.write(String.valueOf(wins));
							writer.close();
							writer = new PrintWriter(f+"/R");
							writer.write(String.valueOf(rounds));
							writer.close();
							writer = new PrintWriter(f+"/name");
							writer.write(String.valueOf(user));
							writer.close();
							writer = new PrintWriter(f+"/Q");
							writer.write(String.valueOf(qualification));
							writer.close();
							writer = new PrintWriter(f+"/F");
							writer.write(String.valueOf(finals));
							writer.close();
							if (Main.leaderboardInformation) {System.out.println("Successfully updater user data");}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						delay(60000);
					}
					getLead();
				}
			}
		});
		leadThread.setDaemon(true);
		leadThread.setName("Leaderboard");
		leadThread.start();
	}
	
	/**
	 * Get leaderbaord
	 * @author Blackoutburst
	 */
	private static void getLead() {
		List<Player> player = new ArrayList<Player>();
		File index = new File("leaderboard");
		String name = "";
		String discord = null;
		int wins = 0;
		int rounds = 0;
		int qualification = 0;
		int finals = 0;
		int total = 0;
		
		
		String[]entries = index.list();
		for(String s: entries) {
			File f = new File(index.getPath(),s);
			
			try {discord = Utils.readValue(f+"/discord");}catch(Exception e) {}
			name = Utils.readValue(f+"/name");
			wins = Integer.valueOf(Utils.readValue(f+"/W"));
			rounds = Integer.valueOf(Utils.readValue(f+"/R"));
			qualification = Integer.valueOf(Utils.readValue(f+"/Q"));
			finals = Integer.valueOf(Utils.readValue(f+"/F"));
			total = qualification + finals;
			player.add(new Player(wins, rounds, qualification, finals, total, name, discord));
		}
		
		for (Player p : player) {
			if (p.discord.equals("0")) {
				continue;
			}
			cleanRoles(p.discord);
		}
		
		Collections.sort(player, new PlayerComparatorWins());Collections.reverse(player);
		for(int i = 0; i < 10; i++) {if (player.get(i).discord.equals("0")) {continue;} manageUserRole(player.get(i).discord, "Top 10 Lifetime Wins");};
		Collections.sort(player, new PlayerComparatorQ());Collections.reverse(player);
		for(int i = 0; i < 10; i++) {if (player.get(i).discord.equals("0")) {continue;} manageUserRole(player.get(i).discord, "Top 10 Lifetime Q");};
		Collections.sort(player, new PlayerComparatorF());Collections.reverse(player);
		for(int i = 0; i < 10; i++) {if (player.get(i).discord.equals("0")) {continue;} manageUserRole(player.get(i).discord, "Top 10 Lifetime F");};
		
	}
	
	/**
	 * Remove leader board roles
	 * @param discord
	 * @author Blackoutburst
	 */
	private static void cleanRoles(String discord) {
		List<Role> roles = Tracker.server.getMemberById(discord).getRoles();
		for (Role r : roles) {
			if (r.getName().contains("Top 10 Lifetime")) {
				Tracker.server.removeRoleFromMember(Tracker.server.getMemberById(discord), r).complete();
			}
		}	
	}
	
	/**
	 * Add user lifetime role
	 * @param discord
	 * @param roleName
	 * @author Blackoutburst
	 * @return 
	 */
	private static void manageUserRole(String discord, String roleName) {
		Tracker.server.addRoleToMember(Tracker.server.getMemberById(discord), Tracker.server.getRolesByName(roleName, false).get(0)).complete();
	}
	
	/**
	 * Add delay to leaderboard updater
	 * @param ms
	 * @author Blackoutburst
	 */
	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
