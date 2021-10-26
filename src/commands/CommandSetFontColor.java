package commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import utils.GeneralUtils;
import utils.MessageSender;

public class CommandSetFontColor extends CommandExecutable {

	public CommandSetFontColor(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		try {
			updateColor(command.getSender().getId(), command.getArgs()[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (true);
	}
	
	private void updateColor(String discordid, String color) throws Exception {
		File index = new File("linked player");
		String[]entries = index.list();
		if (!color.startsWith("#")) {
			color = "#"+color;
		}
		
		for (String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(discordid)) {
				Files.write(Paths.get(playerFolder + "/fontColor"), color.getBytes());
				MessageSender.message(command, "Font color updated\n(note if the color is invalid the font will be white)");
				return;
			}
		}
		MessageSender.messageJSONMention(command, "not linked");
	}
}
