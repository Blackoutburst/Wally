package core;

import utils.MessageSender;

public interface CommandError {
	
	/**
	 * Custom error for bad usage
	 * @return
	 */
	default boolean badUsage(CommandExecutable cmd) {
		MessageSender.badUsage(cmd.command, cmd.errorMessage);
		return (false);
	}
	
	/**
	 * Custom error for unlink error
	 * @return
	 */
	default boolean unlinkError(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "unlink error");
		return (false);
	}

	/**
	 * Custom error for unknown player
	 * @param playerName
	 * @return
	 */
	default boolean unknownPlayer(CommandExecutable cmd, String playerName) {
		MessageSender.unknownPlayer(cmd.command, playerName);
		return (false);
	}
	
	/**
	 * Custom error for never joined
	 * @param playerName
	 * @return
	 */
	default boolean neverJoined(CommandExecutable cmd, String playerName) {
		MessageSender.neverJoined(cmd.command, playerName);
		return (false);
	}
	
	/**
	 * Custom error for missing file
	 * @return
	 */
	default boolean missingFile(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "missing file");
		return (false);
	}
	
	/**
	 * Custom error for not in database
	 * @return
	 */
	default boolean notInDatabase(CommandExecutable cmd) {
		MessageSender.messageJSONMention(cmd.command, "not in database");
		return (false);
	}
	
	/**
	 * Custom error for wrong file format
	 * @param format
	 * @return
	 */
	default boolean wrongFileFormat(CommandExecutable cmd, String format) {
		MessageSender.wrongFileFormat(cmd.command, format);
		return (false);
	}
	
	/**
	 * Custom error for unknown member
	 * @param format
	 * @return
	 */
	default boolean unknownMember(CommandExecutable cmd, String id) {
		MessageSender.unknownMember(cmd.command, id);
		return (false);
	}

}
