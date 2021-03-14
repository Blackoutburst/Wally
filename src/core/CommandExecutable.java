package core;

import utils.MessageSender;
import utils.Utils;

public abstract class CommandExecutable implements CommandError {
	
	protected Command command;
	protected String errorMessage;
	protected boolean staffOnly;
	
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
		return (Utils.isStaff(command.getSender()));
	}
}
