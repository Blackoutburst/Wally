package commands;

import java.io.File;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.entities.Message.Attachment;
import utils.ConfigManager;
import utils.MessageSender;

public class CommandSetConfig extends CommandExecutable {

	public CommandSetConfig(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getMessage().getAttachments().size() == 0) return (missingFile(this));
		
		Attachment file = command.getMessage().getAttachments().get(0);
		
		if (file.getFileExtension().equals("json")) {
			file.downloadToFile(new File("config.json"));
			try {
				new ConfigManager().init("config.json");
				MessageSender.messageJSON(command, "config update");
			} catch (Exception e) {
				MessageSender.messageJSONMention(command, "config error");
				e.printStackTrace();
			}
		} else {
			return (wrongFileFormat(this, ".json"));
		}
		return (true);
	}
}
