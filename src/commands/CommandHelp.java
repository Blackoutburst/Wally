package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandHelp extends CommandExecutable {

	public CommandHelp(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (senderIsStaff()) {
			MessageSender.messageJSON(command, "help admin");
		} else {
			MessageSender.messageJSON(command, "help");
		}
		return (true);
	}
}
