package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandSay extends CommandExecutable {

	public CommandSay(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		String fullString = String.join(" ", command.getArgs());
		
		MessageSender.message(command, fullString);
		if (command.getMessage() != null)
			command.getMessage().delete().complete();
		return (true);
	}
}
