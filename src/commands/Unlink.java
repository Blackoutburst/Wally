package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Lines;
import core.Reader;
import core.Request;
import core.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Unlink {
	
	/**
	 * Unlink a player discord and game account
	 * @param event
	 * @author Blackoutburst
	 */
	public static void unlink(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			String[] str = event.getMessage().getContentDisplay().split(" ");
			String ign = "";
			String uuid = "";
			String discord = "";
	
			if (str.length < 2) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!unlink IGN")).complete();
				return;
			}
			ign = str[1];
			
			uuid = Request.getPlayerUUID(ign);
			
			if (!new File("linked player/"+uuid).exists()) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.not_linked)).complete();
				return;
			}
			
			try {
				discord = Files.readAllLines(Paths.get("linked player/"+uuid+"/discord")).get(0);
				File index = new File ("linked player/"+uuid);
				String[]entries = index.list();
				for(String s: entries){
				    File currentFile = new File(index.getPath(),s);
				    currentFile.delete();
				}
				index.delete();
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unlink).replace("%ign%", ign).replace("%discord%", "<@"+discord+">")).complete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
