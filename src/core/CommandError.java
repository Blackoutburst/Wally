package core;

import utils.API;
import utils.MessageSender;

public interface CommandError {
	
	/**
	 * Custom error for bad usage
	 */
	default boolean badUsage(CommandExecutable cmd) {
		MessageSender.badUsage(cmd.command, cmd.errorMessage);

		return (false);
	}
	
	/**
	 * Custom error for bad usage
	 */
	default boolean outOfBounds(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "unlink error");
		return (false);
	}
	
	/**
	 * Custom error for unlink error
	 */
	default boolean unlinkError(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "unlink error");
		return (false);
	}

	/**
	 * Custom error for unknown player
	 */
	default boolean unknownPlayer(CommandExecutable cmd, String playerName) {
		MessageSender.unknownPlayer(cmd.command, playerName);
		return (false);
	}
	
	/**
	 * Custom error for never joined
	 */
	default boolean neverJoined(CommandExecutable cmd, String playerName) {
		MessageSender.neverJoined(cmd.command, playerName);
		return (false);
	}
	
	/**
	 * Custom error for missing file
	 */
	default boolean missingFile(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "missing file");
		return (false);
	}
	
	/**
	 * Custom error for not in database
	 */
	default boolean notInDatabase(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "not in database");
		return (false);
	}
	
	/**
	 * Custom error for wrong file format
	 */
	default boolean wrongFileFormat(CommandExecutable cmd, String format) {
		MessageSender.wrongFileFormat(cmd.command, format);
		return (false);
	}

	/**
	 * Custom error for too large attachments
	 */
	default boolean fileSizeOverLimit(CommandExecutable cmd, String format) {
		MessageSender.fileSizeOverLimit(cmd.command, format);
		return (false);
	}
	
	/**
	 * Custom error for unknown member
	 */
	default boolean unknownMember(CommandExecutable cmd, String id) {
		MessageSender.unknownMember(cmd.command, id);
		return (false);
	}
	
	/**
	 * Custom error for unlink error
	 */
	default boolean largeValue(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "large value");
		return (false);
	}

	/**
	 * Custom error for API error
	 */
	default boolean apiDead(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "api down");
		return (false);
	}

}
