package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandGetChangelog extends CommandExecutable {

	public CommandGetChangelog(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		MessageSender.sendFile(command, "changelog.txt");
		return (true);
	}
}
