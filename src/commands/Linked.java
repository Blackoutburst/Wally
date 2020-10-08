package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Lines;
import core.Reader;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Linked {
	
	/**
	 * Send list of every user linked
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().getId().equals(Main.bypassID)) {
			event.getChannel().sendMessage(Reader.read(Lines.linked_list)).complete();
			String str = "";
			
			File index = new File("linked player");
			String[]entries = index.list();
			for(String s: entries){
				File f = new File(index.getPath(),s);
				try {
					str += f.getName()+": "+Files.readAllLines(Paths.get(f+"/discord")).get(0)+"\n";
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			event.getChannel().sendMessage("```yml\nUUID: discordID\n"+str+"```").complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
