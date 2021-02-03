package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import core.Request;
import core.Lines;
import core.Reader;
import core.Utils;
import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static core.Tracker.server;


public class PBTester {

	private static EmbedBuilder embed = new EmbedBuilder();
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
					
					onHighscore(currentQualification, qualification, currentFinals, finals, channelID, discord, f, user, lbf, uuid);
					
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
	 * @param channelID
	 * @param discord
	 * @param f
	 * @param user
	 * @author Blackoutburst
	 */
	private static void onHighscore(String currentQualification, String qualification, String currentFinals, String finals, String channelID, String discord, File f, String user, File lbf, String uuid) {
		/**
		 * @author Heartbreaker
		 * @author Fuby
		 */
		String gender = "their";
		if (Utils.isHe(server.getMemberById(discord))) {
			gender = "his";
		}
		if (Utils.isShe(server.getMemberById(discord))) {
			gender = "her";
		}
		if (Utils.isBoth(server.getMemberById(discord))) {
			gender = "their";
		}
		int qual_int = Integer.parseInt(qualification);
		int cur_qual_int = Integer.parseInt(currentQualification);
		int final_int = Integer.parseInt(finals);
		int cur_final_int = Integer.parseInt(currentFinals);
		int x = 3;
		if (x > 0) {
			String url = "https://www.mc-heads.net/body/" + uuid;
			String avatarUrl = server.getMemberById(discord).getUser().getAvatarUrl();
			String name = server.getMemberById(discord).getEffectiveName();
			embed.setAuthor(name + " | " + user,url,avatarUrl);
			embed.setThumbnail(url);
			embed.setFooter("Discord ID: " + discord + "\nUUID: " + uuid);
		}
		if (x > 1) {
			int score = cur_qual_int;
			int pb = qual_int;
			embed.setTitle(user+" Improved "+gender+" **Qualifiers** Personal Best!");
			pb_embed("Q", f, lbf, score, pb, channelID);
		}
		if ((x % 2)==1) {
			int score = cur_final_int;
			int pb = final_int;
			embed.setTitle(user+" Improved "+gender+" **Finals** Personal Best!");
			pb_embed("F",f, lbf, score, pb, channelID);
		}
	}
	private static void pb_embed(String pb_type, File f, File lbf, int score, int pb, String channelID) {
		net.dv8tion.jda.api.entities.TextChannel channel = server.getTextChannelById(channelID);
		embed.addField("Old PB", "**" + score + "**",true);
		embed.addField("New PB", "**" + pb + "**",true);
		embed.addField("Increase","**" + (pb - score) + "**",true);
		writeHighScore(pb, f, pb_type);
		writeHighScore(pb, lbf, pb_type);
		channel.sendMessage(embed.build()).queue();
		embed.clearFields();
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
			roles = server.getMemberById(discord).getRoles();
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
		List<Role> roles = server.getMemberById(discord).getRoles();
		
		for (Role r : roles) {
			if (r.getName().contains("Club")) {
				server.removeRoleFromMember(server.getMemberById(discord), r).complete();
			}
		}	
		server.addRoleToMember(server.getMemberById(discord), server.getRolesByName(roleName, false).get(0)).complete();
	}
}
