package core;

import utils.MessageSender;
import utils.Utils;

public abstract class CommandExecutable {
	
	protected Command command;
	protected boolean staffOnly;
	protected String errorMessage;
	
	public CommandExecutable(Command command, boolean staffOnly, String errorMessage) {
		this.command = command;
		this.staffOnly = staffOnly;
		this.errorMessage = errorMessage;
	}
	
	protected abstract boolean execute();
	
	/**
	 * Check user permission to use the command
	 */
	public void run() {
		if (isStaffOnly()) {
			if (senderIsStaff()) {
				execute();
			} else {
				MessageSender.messageJSONMention(command, "missing permission");
				return;
			}
		} else {
			execute();
		}
	}
	
	/**
	 * Return if the command is staff only
	 * @return
	 */
	protected boolean isStaffOnly() {
		return staffOnly;
	}
	
	/**
	 * Check is the command sender is staff
	 * @return
	 */
	protected boolean senderIsStaff() {
		if (Utils.isStaff(command.getSender())) {
			return (true);
		}
		return (false);
	}
	
	/**
	 * Custom error for bad usage
	 * @return
	 */
	protected boolean badUsage() {
		MessageSender.badUsage(command, errorMessage);
		return (false);
	}
	
	/**
	 * Custom error for unlink error
	 * @return
	 */
	protected boolean unlinkError() {
		MessageSender.messageJSONMention(command, "unlink error");
		return (false);
	}

	/**
	 * Custom error for unknown player
	 * @param playerName
	 * @return
	 */
	protected boolean unknownPlayer(String playerName) {
		MessageSender.unknownPlayer(command, playerName);
		return (false);
	}
	
	/**
	 * Custom error for missing file
	 * @return
	 */
	protected boolean missingFile() {
		MessageSender.messageJSONMention(command, "missing file");
		return (false);
	}
	
	/**
	 * Custom error for not in database
	 * @return
	 */
	protected boolean notInDatabase() {
		MessageSender.messageJSONMention(command, "not in database");
		return (false);
	}
	
	/**
	 * Custom error for wrong file format
	 * @param format
	 * @return
	 */
	protected boolean wrongFileFormat(String format) {
		MessageSender.wrongFileFormat(command, format);
		return (false);
	}
	
	/**
	 * Custom error for unknown member
	 * @param format
	 * @return
	 */
	protected boolean unknownMember(String id) {
		MessageSender.unknownMember(command, id);
		return (false);
	}
}
