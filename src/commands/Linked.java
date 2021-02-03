package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Lines;
import core.Reader;
import core.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Linked {
	
	/**
	 * Send list of every user linked
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			event.getChannel().sendMessage(Reader.read(Lines.LINKED_LIST)).complete();
			String str = "UUID DISCORD_ID\n";
			
			File index = new File("linked player");
			String[]entries = index.list();
			for(String s: entries){
				File f = new File(index.getPath(),s);
				try {
					str += f.getName()+" "+Files.readAllLines(Paths.get(f+"/discord")).get(0)+"\n";
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			PrintWriter writer;
			try {
				writer = new PrintWriter("linked list.txt");
				writer.write(str);
				writer.close();
				event.getChannel().sendFile(new File("linked list.txt")).complete();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.MISSING_PERMS)).complete();
		}
	}
}
