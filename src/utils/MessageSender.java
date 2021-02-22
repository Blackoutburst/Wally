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
		command.getEvent().getChannel().sendMessage(command.getSender().getAsMention() + ",\n" + Config.getMessage(message)).complete();
	}
	
	/**
	 * Send message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void messageJSON(Command command, String message) {
		command.getEvent().getChannel().sendMessage(Config.getMessage(message)).complete();
	}
	
	/**
	 * Send custom message in the command channel
	 * @param channel
	 * @param message
	 */
	public static void message(Command command, String message) {
		command.getEvent().getChannel().sendMessage(message).complete();
	}
	
	/**
	 * Send custom message in the command channel and mention the user
	 * @param channel
	 * @param message
	 */
	public static void messageMention(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		command.getEvent().getChannel().sendMessage(str+message).complete();
	}
	
	/**
	 * Send bad usage message
	 * @param channel
	 * @param message
	 */
	public static void badUsage(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += Config.getMessage("bad usage").replace("%command%", message);
		command.getEvent().getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send wrong file format message
	 * @param channel
	 * @param message
	 */
	public static void wrongFileFormat(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += Config.getMessage("wrong file type").replace("%format%", message);
		command.getEvent().getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send unknown player message
	 * @param channel
	 * @param message
	 */
	public static void unknownPlayer(Command command, String player) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += Config.getMessage("unknown player").replace("%player%", player);
		command.getEvent().getChannel().sendMessage(str).complete();
	}
	
	
	/**
	 * Send unknown player message
	 * @param channel
	 * @param message
	 */
	public static void unknownMember(Command command, String id) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += Config.getMessage("unknown member").replace("%id%", id);
		command.getEvent().getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send file
	 * @param channel
	 * @param message
	 */
	public static void sendFile(Command command, String file) {
		command.getEvent().getChannel().sendFile(new File(file)).complete();
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
		String localData = Utils.readJsonToString("linked player/" + uuid + "/data.json");
		String url = "https://www.mc-heads.net/body/" + uuid;
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
			embed.setTitle(API.getName(data)+" Improved " + Utils.getGenderPrefix(discordid) + " **Qualifiers** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newQ));
			embed.addField("Old PB", "**" + oldQ + "**",true);
			embed.addField("New PB", "**" + newQ + "**",true);
			embed.addField("Increase","**" + (newQ - oldQ) + "**",true);
		} else {
			embed.setTitle(API.getName(data) + " Improved " + Utils.getGenderPrefix(discordid) + " **Finals** Personal Best!");
			embed.setColor(RolesManager.getRoleColor(newF));
			embed.addField("Old PB", "**" + oldF + "**",true);
			embed.addField("New PB", "**" + newF + "**",true);
			embed.addField("Increase","**" + (newF - oldF) + "**",true);
		}
		new RolesManager().addClubRole(Bot.server, Bot.server.getMemberById(discordid), newQ, newF);
		Bot.server.getTextChannelById(Config.getString("trackerChannel")).sendMessage(embed.build()).complete();
	}
}
