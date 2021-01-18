package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import main.Main;

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
				}
			}
		});
		leadThread.setDaemon(true);
		leadThread.setName("Leaderboard");
		leadThread.start();
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
