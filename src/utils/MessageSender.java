package utils;

import java.io.File;

import core.Bot;
import core.Command;
import core.RolesManager;
import net.dv8tion.jda.api.EmbedBuilder;

public class MessageSender {
	
	/**
	 * Send message with user mention in the command channel
	 * @param member
	 * @param channel
	 * @param message
	 */
	public static void messageJSONMention(Command command, String message) {
		command.getChannel().sendMessage(command.getSender().getAsMention() + ",\n" + ConfigManager.getMessage(message)).complete();
	}
	
	/**
	 * Send message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void messageJSON(Command command, String message) {
		command.getChannel().sendMessage(ConfigManager.getMessage(message)).complete();
	}
	
	/**
	 * Send message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void altAccount(Command command, String owner) {
		command.getChannel().sendMessage("This account is the alt account of `"+owner+"`").complete();
	}
	
	/**
	 * Send message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void boostedAccount(Command command, String owner) {
		command.getChannel().sendMessage("This account was boosted by `"+owner+"`").complete();
	}
	
	/**
	 * Send message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void susAccount(Command command) {
		command.getChannel().sendMessage("This account stat are off, it's most likely a boosted account or an alt account").complete();
	}
	
	/**
	 * Send message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void notOnLBAccount(Command command) {
		command.getChannel().sendMessage("For this reason this account will not appear on the leaderboard").complete();
	}
	
	/**
	 * Send custom message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void message(Command command, String message) {
		if (command == null) return;
		command.getChannel().sendMessage(message).complete();
	}
	
	/**
	 * Send custom message in the command channel and mention the user
	 * @param channel
	 * @param message
	 */
	public static void messageMention(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		command.getChannel().sendMessage(str+message).complete();
	}
	
	/**
	 * Send bad usage message
	 * @param channel
	 * @param message
	 */
	public static void badUsage(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("bad usage").replace("%command%", message);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send wrong file format message
	 * @param channel
	 * @param message
	 */
	public static void wrongFileFormat(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("wrong file type").replace("%format%", message);
		command.getChannel().sendMessage(str).complete();
	}

	/**
	 * Send wrong file size error message
	 * @param channel
	 * @param message
	 */
	public static void fileSizeOverLimit(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("large image").replace("%size%", message);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send unknown player message
	 * @param channel
	 * @param message
	 */
	public static void unknownPlayer(Command command, String player) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("unknown player").replace("%player%", player);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send never joined message
	 * @param channel
	 * @param message
	 */
	public static void neverJoined(Command command, String player) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("never joined").replace("%player%", player);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send unknown player message
	 * @param channel
	 * @param message
	 */
	public static void unknownMember(Command command, String id) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("unknown member").replace("%id%", id);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send file
	 * @param channel
	 * @param message
	 */
	public static void sendFile(Command command, String file) {
		command.getChannel().sendFile(new File(file)).complete();
	}
	
	/**
	 * Send embeded message
	 * @param channel
	 * @param message
	 */
	public static void sendEmbeded(Command command, EmbedBuilder embed) {
		command.getChannel().sendMessage(embed.build()).complete();
	}
	
	/**
	 * Display personal best message
	 * @param data
	 * @param discordid
	 * @param uuid
	 * @param type
	 */
	public static void pbMessage(String data, String discordid, String uuid, char type) {
		EmbedBuilder embed = new EmbedBuilder();
		String localData = GeneralUtils.readJsonToString("linked player/" + uuid + "/data.json");

		// Add a unique part to the request url so discord cannot cache it and the skin stays up to date.
		// By: EatMyVenom
		long time = System.currentTimeMillis();

		String url = "https://crafatar.com/renders/body/" + uuid + "?overlay&size=512&time=" + time;
		String avatarUrl = Bot.server.getMemberById(discordid).getUser().getAvatarUrl();
		String name = Bot.server.getMemberById(discordid).getEffectiveName();
		int oldQ = Stats.getQualificationToInt(localData);
		int newQ = API.getQualificationToInt(data);
		int oldF = Stats.getFinalsToInt(localData);
		int newF = API.getFinalsToInt(data);
		
		embed.setAuthor(name + " | " + API.getName(data), url, avatarUrl);
		embed.setThumbnail(url);
		embed.setFooter("Discord ID: " + discordid + "\nUUID: " + uuid);
		
		if (type == 'q') {
			if (newQ - oldQ <= 0) return;
			embed.setTitle(API.getName(data)+" Improved " + GeneralUtils.getGenderPrefix(discordid) + " **Qualifiers** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newQ));
			embed.addField("Old PB", "**" + oldQ + "**",true);
			embed.addField("New PB", "**" + newQ + "**",true);
			embed.addField("Increase","**" + (newQ - oldQ) + "**",true);
		} else {
			if (newF - oldF <= 0) return;
			embed.setTitle(API.getName(data) + " Improved " + GeneralUtils.getGenderPrefix(discordid) + " **Finals** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newF));
			embed.addField("Old PB", "**" + oldF + "**",true);
			embed.addField("New PB", "**" + newF + "**",true);
			embed.addField("Increase","**" + (newF - oldF) + "**",true);
		}
		new RolesManager().addClubRole(Bot.server, Bot.server.getMemberById(discordid), newQ, newF, null);
		Bot.server.getTextChannelById(ConfigManager.getString("trackerChannel")).sendMessage(embed.build()).complete();
	}
	
	/**
	 * Display personal best message
	 * @param data
	 * @param discordid
	 * @param uuid
	 * @param type
	 */
	public static void practicePbMessage(String data, String discordid, String uuid, String type) {
		EmbedBuilder embed = new EmbedBuilder();
		String localData = GeneralUtils.readJsonToString("pleaderboard/" + uuid + "/data.json");

		// Add a unique part to the request url so discord cannot cache it and the skin stays up to date.
		// By: EatMyVenom
		long time = System.currentTimeMillis();

		String url = "https://crafatar.com/renders/body/" + uuid + "?overlay&size=512&time=" + time;
		String avatarUrl = Bot.server.getMemberById(discordid).getUser().getAvatarUrl();
		String name = Bot.server.getMemberById(discordid).getEffectiveName();
		int oldQ = PracticeStats.getQualificationToInt(localData);
		int newQ = PracticeAPI.getQualificationToInt(data);
		int oldF = PracticeStats.getFinalsToInt(localData);
		int newF = PracticeAPI.getFinalsToInt(data);
		int oldL = PracticeStats.getLobbyToInt(localData);
		int newL = PracticeAPI.getLobbyToInt(data);
		int oldWQ = PracticeStats.getWideQualificationToInt(localData);
		int newWQ = PracticeAPI.getWideQualificationToInt(data);
		
		embed.setAuthor(name + " | " + PracticeAPI.getName(data), url, avatarUrl);
		embed.setThumbnail(url);
		embed.setFooter("Discord ID: " + discordid + "\nUUID: " + uuid);
		
		if (type == "q") {
			if (newQ - oldQ <= 0) return;
			embed.setTitle(PracticeAPI.getName(data)+" Improved " + GeneralUtils.getGenderPrefix(discordid) + " **Qualifiers** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newQ));
			embed.addField("Old PB", "**" + oldQ + "**",true);
			embed.addField("New PB", "**" + newQ + "**",true);
			embed.addField("Increase","**" + (newQ - oldQ) + "**",true);
		} else if (type == "f") {
			if (newF - oldF <= 0) return;
			embed.setTitle(PracticeAPI.getName(data) + " Improved " + GeneralUtils.getGenderPrefix(discordid) + " **Finals** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newF));
			embed.addField("Old PB", "**" + oldF + "**",true);
			embed.addField("New PB", "**" + newF + "**",true);
			embed.addField("Increase","**" + (newF - oldF) + "**",true);
		} else if (type == "l") {
			if (newL - oldL <= 0) return;
			embed.setTitle(PracticeAPI.getName(data) + " Improved " + GeneralUtils.getGenderPrefix(discordid) + " **Lobby** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newL));
			embed.addField("Old PB", "**" + oldL + "**",true);
			embed.addField("New PB", "**" + newL + "**",true);
			embed.addField("Increase","**" + (newL - oldL) + "**",true);
		}  else if (type == "wq") {
			if (newWQ - oldWQ <= 0) return;
			embed.setTitle(PracticeAPI.getName(data) + " Improved " + GeneralUtils.getGenderPrefix(discordid) + " **Wide Qualifiers** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newWQ));
			embed.addField("Old PB", "**" + oldWQ + "**",true);
			embed.addField("New PB", "**" + newWQ + "**",true);
			embed.addField("Increase","**" + (newWQ - oldWQ) + "**",true);
		}
		Bot.server.getTextChannelById(ConfigManager.getString("practiceTrackerChannel")).sendMessage(embed.build()).complete();
	}
}
