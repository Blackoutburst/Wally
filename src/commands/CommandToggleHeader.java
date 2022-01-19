package commands;

import core.Command;
import core.CommandExecutable;
import org.json.JSONObject;
import utils.GeneralUtils;
import utils.MessageSender;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommandToggleHeader extends CommandExecutable {

	public CommandToggleHeader(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		try {
			toggleHeader(command.getSender().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (true);
	}
	
	private void toggleHeader(String discordid) throws Exception {
		File index = new File("linked player");
		String[]entries = index.list();

		if (entries == null) return;

		for (String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

			if (obj.getString("discordid").equals(discordid)) {
				File f = new File(playerFolder + "/hideHeader");
				if (f.exists()) {
					f.delete();
					MessageSender.message(command, "Your stats header as been enabled");
				} else {
					f.createNewFile();
					MessageSender.message(command, "Your stats header as been disabled");
				}
				return;
			}
		}
		MessageSender.messageJSONMention(command, "not linked");
	}
}
