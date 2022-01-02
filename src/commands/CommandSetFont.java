package commands;

import java.io.File;
import java.util.Objects;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.entities.Message.Attachment;
import utils.GeneralUtils;
import utils.MessageSender;

public class CommandSetFont extends CommandExecutable {

	public CommandSetFont(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getMessage().getAttachments().size() == 0) return (missingFile(this));
		
		Attachment file = command.getMessage().getAttachments().get(0);

		if(file.getSize() > 8388608) return (fileSizeOverLimit(this, "8MiB"));
		
		if (Objects.equals(file.getFileExtension(), "ttf") || Objects.equals(file.getFileExtension(), "otf")) {
			updateFont(command.getSender().getId(), file);
		} else {
			return (wrongFileFormat(this, ".ttf/.otf"));
		}
		return (true);
	}
	
	private void updateFont(String discordid, Attachment file) {
		File index = new File("linked player");
		String[]entries = index.list();

		if (entries == null) return;

		for (String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(discordid)) {
				file.downloadToFile(new File(playerFolder + "/font.ttf"));
				MessageSender.message(command, "Font updated");
				return;
			}
		}
		MessageSender.messageJSONMention(command, "not linked");
	}
}
