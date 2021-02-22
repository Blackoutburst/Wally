package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandGetConfig extends CommandExecutable {

	public CommandGetConfig(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		MessageSender.sendFile(command, "config.json");
		return (true);
	}
}
