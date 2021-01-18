package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import core.Lines;
import core.Reader;
import core.Request;
import core.Tracker;
import core.Utils;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PBTester {
	
	/**
	 * Force specific user personnal best for debug purpose
	 * @param event
	 * @author Blackoutburst
	 */
	public static void force(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			String msg[] = event.getMessage().getContentDisplay().split(" ");
			String ign = null;
			String uuid = null;
			
			if (msg.length < 2) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!forcepb [ING]")).complete();
				return;
			}
			ign = msg[1];
			uuid = Request.getPlayerUUID(ign);
			
			if (uuid == null) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
				return;
			}
			
			File lbfile = new File("leaderboard");
			File index = new File("linked player");
			String channelID = readValue("tracker");
			String[]entries = index.list();
			for(String s: entries) {
				File f = new File(index.getPath(),s);
				if (s.equals(uuid)) {
					File lbf = new File(lbfile.getPath(),s);
					int role_level = 0;
					String user = "";
					String discord = "";
					String qualification = "0";
					String finals = "0";
					String wins = "0";
					String rounds = "0";
					String currentQualification = "0";
					String currentFinals = "0";
					String output = "";
					String[] value;
					
					discord = readValue(f+"/discord");
					role_level = getRoleLevel(discord);
					if (role_level == -1)
						continue;
					
					output = Request.getPlayerInfoUUID(f.getName());
					if (output.equals("API LIMITATION")) {
						System.out.println("Tracker aborted due to api limiation");
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
					
					currentQualification = readValue(f+"/Q");
					currentFinals = readValue(f+"/F");
					
					onHighscore(currentQualification, qualification, currentFinals, finals, role_level, channelID, discord, f, user, lbf);
					
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
						if (Main.trackerInformation) {System.out.println("Successfully updater user data");}
						writer = new PrintWriter(lbf+"/W");
						writer.write(String.valueOf(wins));
						writer.close();
						writer = new PrintWriter(lbf+"/R");
						writer.write(String.valueOf(rounds));
						writer.close();
						writer = new PrintWriter(lbf+"/name");
						writer.write(String.valueOf(user));
						writer.close();
						if (Main.trackerInformation) {System.out.println("Successfully updater leaderboard data");}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.force_pb).replace("%ign%", ign)).complete();
					return;
				}
			}
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.not_linked)).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
	
	/**
	 * Perform action on new user highscore
	 * @param currentQualification
	 * @param qualification
	 * @param currentFinals
	 * @param finals
	 * @param role_level
	 * @param channelID
	 * @param discord
	 * @param f
	 * @param user
	 * @author Blackoutburst
	 */
	private static void onHighscore(String currentQualification, String qualification, String currentFinals, String finals,
			int role_level, String channelID, String discord, File f, String user, File lbf) {
		
		String gender = "their";
		
		if (Utils.isHe(Tracker.server.getMemberById(discord))) {
			gender = "his";
		}
		if (Utils.isShe(Tracker.server.getMemberById(discord))) {
			gender = "her";
		}
		if (Utils.isBoth(Tracker.server.getMemberById(discord))) {
			gender = "their";
		}
			
		if (Integer.valueOf(currentQualification) < Integer.valueOf(qualification)) {
			Tracker.server.getTextChannelById(channelID).sendMessage(Reader.read(Lines.qualifiers_score).replace("%player%", user.replace("_", "\\_")).replace("%gender%", gender).replace("%score%", qualification).replace("%up%", String.valueOf(Integer.valueOf(qualification) - Integer.valueOf(currentQualification)))).complete();
			writeHighScore(Integer.valueOf(qualification), f, "Q");
			writeHighScore(Integer.valueOf(qualification), lbf, "Q");
			setRole(discord, Integer.valueOf(qualification), role_level);
		}
		if (Integer.valueOf(currentFinals) < Integer.valueOf(finals)) {
			Tracker.server.getTextChannelById(channelID).sendMessage(Reader.read(Lines.finals_score).replace("%player%", user.replace("_", "\\_")).replace("%gender%", gender).replace("%score%", finals).replace("%up%", String.valueOf(Integer.valueOf(finals) - Integer.valueOf(currentFinals)))).complete();
			writeHighScore(Integer.valueOf(finals), f, "F");
			writeHighScore(Integer.valueOf(finals), lbf, "F");
			setRole(discord, Integer.valueOf(finals), role_level);
		}
	}
	
	/**
	 * Read file value
	 * @param file
	 * @return value read
	 * @author Blackoutburst
	 */
	private static String readValue(String file) {
		String str = "";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (IOException e) {
			if (!file.equals("tracker")) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	 * Write new highscore
	 * @param score
	 * @param f
	 * @param type
	 * @author Blackoutburst
	 */
	private static void writeHighScore(int score, File f, String type) {
		try {
			PrintWriter writer = new PrintWriter(f+"/"+type);
			writer.write(String.valueOf(score));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return user role level
	 * @param discord
	 * @return role level
	 * @author Blackoutburst
	 */
	private static int getRoleLevel(String discord) {
		int role_level = 0;
		List<Role> roles = null;
		try {
			roles = Tracker.server.getMemberById(discord).getRoles();
		} catch(Exception e) {
			return -1;
		}
		
		
		for (Role r : roles) {
			if (r.getName().equals("50+ Club") && role_level <= 0) {role_level = 1;}
			if (r.getName().equals("100+ Club") && role_level <= 1) {role_level = 2;}
			if (r.getName().equals("150+ Club") && role_level <= 2) {role_level = 3;}
			if (r.getName().equals("200+ Club") && role_level <= 3) {role_level = 4;}
			if (r.getName().equals("250+ Club") && role_level <= 4) {role_level = 5;}
			if (r.getName().equals("300+ Club") && role_level <= 5) {role_level = 6;}
			if (r.getName().equals("350+ Club") && role_level <= 6) {role_level = 7;}
			if (r.getName().equals("400+ Club") && role_level <= 7) {role_level = 8;}
		}
		return role_level;
	}
	
	/**
	 * Set user new role and remove all lower one
	 * @param discord
	 * @param newScore
	 * @param role_level
	 * @author Blackoutburst
	 * @author Fuby
	 * @author eatmyvenom
	 */
	private static void setRole(String discord, int newScore, int role_level) {
		if (Integer.valueOf(newScore) > 400 && role_level < 8) {
			manageUserRole(discord, "400+ Club");
		} else if (Integer.valueOf(newScore) > 350 && role_level < 7) {
			manageUserRole(discord, "350+ Club");
		} else if (Integer.valueOf(newScore) >= 300 && role_level < 6) {
			manageUserRole(discord, "300+ Club");
		} else if (Integer.valueOf(newScore) >= 250 && role_level < 5) {
			manageUserRole(discord, "250+ Club");
		} else if (Integer.valueOf(newScore) >= 200 && role_level < 4) {
			manageUserRole(discord, "200+ Club");
		} else if (Integer.valueOf(newScore) >= 150 && role_level < 3) {
			manageUserRole(discord, "150+ Club");
		} else if (Integer.valueOf(newScore) >= 100 && role_level < 2) {
			manageUserRole(discord, "100+ Club");
		} else if (Integer.valueOf(newScore) >= 50 && role_level < 1) {
			manageUserRole(discord, "50+ Club");
		}
	}
	
	/**
	 * Remove every user role (point related)
	 * @param discord
	 * @param roleName
	 * @author Blackoutburst
	 */
	private static void manageUserRole(String discord, String roleName) {
		List<Role> roles = Tracker.server.getMemberById(discord).getRoles();
		
		for (Role r : roles) {
			if (r.getName().contains("Club")) {
				Tracker.server.removeRoleFromMember(Tracker.server.getMemberById(discord), r).complete();
			}
		}	
		Tracker.server.addRoleToMember(Tracker.server.getMemberById(discord), Tracker.server.getRolesByName(roleName, false).get(0)).complete();
	}
}
