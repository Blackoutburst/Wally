package utils;

import java.io.File;
import java.util.Objects;

import core.Bot;
import core.Command;
import core.RolesManager;
import net.dv8tion.jda.api.EmbedBuilder;

public class MessageSender {
	
	/**
	 * Send message with user mention in the command channel
	 */
	public static void messageJSONMention(Command command, String message) {
		command.getChannel().sendMessage(command.getSender().getAsMention() + ",\n" + ConfigManager.getMessage(message)).complete();
	}
	
	/**
	 * Send message in the command channel
	 */
	public static void messageJSON(Command command, String message) {
		command.getChannel().sendMessage(ConfigManager.getMessage(message)).complete();
	}
	
	/**
	 * Send message in the command channel
	 */
	public static void altAccount(Command command, String owner) {
		command.getChannel().sendMessage("This account is the alt account of `"+owner+"`").complete();
	}
	
	/**
	 * Send message in the command channel
	 */
	public static void boostedAccount(Command command, String owner) {
		command.getChannel().sendMessage("This account was boosted by `"+owner+"`").complete();
	}
	
	/**
	 * Send message in the command channel
	 */
	public static void susAccount(Command command) {
		command.getChannel().sendMessage("This account stat are off, it's most likely a boosted account or an alt account").complete();
	}
	
	/**
	 * Send message in the command channel
	 */
	public static void notOnLBAccount(Command command) {
		command.getChannel().sendMessage("For this reason this account will not appear on the leaderboard").complete();
	}
	
	/**
	 * Send custom message in the command channel
	 */
	public static void message(Command command, String message) {
		if (command == null) return;
		command.getChannel().sendMessage(message).complete();
	}
	
	/**
	 * Send custom message in the command channel and mention the user
	 */
	public static void messageMention(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		command.getChannel().sendMessage(str+message).complete();
	}
	
	/**
	 * Send bad usage message
	 */
	public static void badUsage(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("bad usage").replace("%command%", message);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send wrong file format message
	 */
	public static void wrongFileFormat(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("wrong file type").replace("%format%", message);
		command.getChannel().sendMessage(str).complete();
	}

	/**
	 * Send wrong file size error message
	 */
	public static void fileSizeOverLimit(Command command, String message) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("large image").replace("%size%", message);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send unknown player message
	 */
	public static void unknownPlayer(Command command, String player) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("unknown player").replace("%player%", player);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send never joined message
	 */
	public static void neverJoined(Command command, String player) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("never joined").replace("%player%", player);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send unknown player message
	 */
	public static void unknownMember(Command command, String id) {
		String str = command.getSender().getAsMention() + ",\n";
		
		str += ConfigManager.getMessage("unknown member").replace("%id%", id);
		command.getChannel().sendMessage(str).complete();
	}
	
	/**
	 * Send file
	 */
	public static void sendFile(Command command, String file) {
		command.getChannel().sendFile(new File(file)).complete();
	}
	
	/**
	 * Send embeded message
	 */
	public static void sendEmbeded(Command command, EmbedBuilder embed) {
		command.getChannel().sendMessage(embed.build()).complete();
	}
}
