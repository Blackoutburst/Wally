package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandPack extends CommandExecutable {

	public CommandPack(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		MessageSender.messageJSON(command, "pack");
		return (true);
	}
}
