package commands;

import core.Command;
import core.CommandExecutable;

public class CommandPing extends CommandExecutable {

	public CommandPing(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		long time = System.currentTimeMillis();
		
		command.getEvent().getChannel().sendMessage("...").queue(response -> {
			response.editMessageFormat("Ping: **%d**ms", System.currentTimeMillis() - time).queue();
		});
		return (true);
	}
}
