package commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import core.Lines;
import core.Reader;
import core.Request;
import core.Tracker;
import core.Utils;
import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
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
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BAD_USAGE).replace("%command%", "!forcepb [ING]")).complete();
				return;
			}
			ign = msg[1];
			uuid = Request.getPlayerUUID(ign);
			
			if (uuid == null) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.UNKNOW_PLAYER)).complete();
				return;
			}
			
			File lbfile = new File("leaderboard");
			File index = new File("linked player");
			String channelID = Utils.readValue("tracker");
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
					
					discord = Utils.readValue(f+"/discord");
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
					
					currentQualification = Utils.readValue(f+"/Q");
					currentFinals = Utils.readValue(f+"/F");
					
					onHighscore(currentQualification, qualification, currentFinals, finals, role_level, channelID, discord, f, user, lbf, uuid);
					
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
					event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.FORCE_PB).replace("%ign%", ign)).complete();
					return;
				}
			}
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.NOT_LINKED)).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_PERMS)).complete();
		}
	}
	
	/**
	 * Perform action on new user highscore
	 * @param currentQualification
	 * @param qualification
	 * @param currentFinals
	 * @param finals
	 * @param channelID
	 * @param discord
	 * @param f
	 * @param user
	 * @author Blackoutburst
	 */
	private static void onHighscore(String currentQualification, String qualification, String currentFinals, String finals, int role_level,
			String channelID, String discord, File f, String user, File lbf, String uuid) {
		/**
		 * @author Heartbreaker
		 * @author Fuby
		 */
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
		int qual_int = Integer.parseInt(qualification);
		int cur_qual_int = Integer.parseInt(currentQualification);
		int final_int = Integer.parseInt(finals);
		int cur_final_int = Integer.parseInt(currentFinals);
		EmbedBuilder embed = new EmbedBuilder();
		int x = 3;
		if (x > 0) {
			String url = "https://www.mc-heads.net/body/" + uuid;
			String avatarUrl = Tracker.server.getMemberById(discord).getUser().getAvatarUrl();
			String name = Tracker.server.getMemberById(discord).getEffectiveName();
			embed.setAuthor(name + " | " + user,url,avatarUrl);
			embed.setThumbnail(url);
			embed.setFooter("Discord ID: " + discord + "\nUUID: " + uuid);
		}
		if (x > 1) {
			int score = cur_qual_int;
			int pb = qual_int;
			embed.setTitle(user+" Improved "+gender+" **Qualifiers** Personal Best!");
			embed.setColor(getRoleColor(pb));
			pb_embed("Q", f, lbf, score, pb, channelID, embed);
			setRole(discord, qual_int, role_level);
		}
		if ((x % 2)==1) {
			int score = cur_final_int;
			int pb = final_int;
			embed.setTitle(user+" Improved "+gender+" **Finals** Personal Best!");
			embed.setColor(getRoleColor(pb));
			pb_embed("F",f, lbf, score, pb, channelID, embed);
			setRole(discord, final_int, role_level);
		}
	}
	private static void pb_embed(String pb_type, File f, File lbf, int score, int pb, String channelID, EmbedBuilder embed) {
		TextChannel channel = Tracker.server.getTextChannelById(channelID);
		embed.addField("Old PB", "**" + score + "**",true);
		embed.addField("New PB", "**" + pb + "**",true);
		embed.addField("Increase","**" + (pb - score) + "**",true);
		writeHighScore(pb, f, pb_type);
		writeHighScore(pb, lbf, pb_type);
		channel.sendMessage(embed.build()).complete();
		embed.clearFields();
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
	 * Return role color
	 * @param discord
	 * @return role level
	 * @author Blackoutburst
	 */
	private static Color getRoleColor(int score) {
		Color color = Tracker.server.getRolesByName("Members", false).get(0).getColor();
		
		if (score >= 50) {color = Tracker.server.getRolesByName("50+ Club", false).get(0).getColor();}
		if (score >= 100) {color = Tracker.server.getRolesByName("100+ Club", false).get(0).getColor();}
		if (score >= 150) {color = Tracker.server.getRolesByName("150+ Club", false).get(0).getColor();}
		if (score >= 200) {color = Tracker.server.getRolesByName("200+ Club", false).get(0).getColor();}
		if (score >= 250) {color = Tracker.server.getRolesByName("250+ Club", false).get(0).getColor();}
		if (score >= 300) {color = Tracker.server.getRolesByName("300+ Club", false).get(0).getColor();}
		if (score >= 350) {color = Tracker.server.getRolesByName("350+ Club", false).get(0).getColor();}
		if (score >= 400) {color = Tracker.server.getRolesByName("400+ Club", false).get(0).getColor();}
		return color;
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
