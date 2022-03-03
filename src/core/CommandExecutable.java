package core;

import com.sun.deploy.config.Config;
import utils.ConfigManager;
import utils.MessageSender;
import utils.GeneralUtils;

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
		GeneralUtils.startTyping(this.command.channel);
		if (isStaffOnly()) {
			if (!this.command.getGuild().getId().equals(ConfigManager.getString("serverID")) &&
				!this.command.getGuild().getId().equals(ConfigManager.getString("devServerID"))) {
				MessageSender.message(this.command, "This action in not possible inside this server!");
				return;
			}
			if (senderIsStaff()) {
				execute();
			} else {
				MessageSender.messageJSONMention(command, "missing permission");
			}
		} else {
			execute();
		}
	}
	
	/**
	 * Return if the command is staff only
	 */
	protected boolean isStaffOnly() {
		return staffOnly;
	}
	
	/**
	 * Check is the command sender is staff
	 */
	protected boolean senderIsStaff() {
		return (GeneralUtils.isStaff(command.getSender()));
	}
}
