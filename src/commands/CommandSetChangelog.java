package commands;

import java.io.File;
import java.util.Objects;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.entities.Message.Attachment;
import utils.MessageSender;

public class CommandSetChangelog extends CommandExecutable {

	public CommandSetChangelog(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getMessage().getAttachments().size() == 0) return (missingFile(this));
		
		Attachment file = command.getMessage().getAttachments().get(0);
		
		if (Objects.equals(file.getFileExtension(), "txt")) {
			file.downloadToFile(new File("changelog.txt"));
			MessageSender.messageJSON(command, "changelog update");
		} else {
			return (wrongFileFormat(this, ".txt"));
		}
		return (true);
	}
}
