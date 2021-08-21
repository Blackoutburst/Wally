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
	public static void altAccountOwner(Command command, String owner) {
		command.getChannel().sendMessage("This account is the alt account of `"+owner+"`").complete();
	}
	
	/**
	 * Send message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void altAccount(Command command) {
		command.getChannel().sendMessage("This account is an alt account, the owner is `unknown` for now").complete();
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
		String url = "https://crafatar.com/renders/body/" + uuid + "?overlay&size=512";
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
}
