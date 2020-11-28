package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import core.Lines;
import core.Reader;
import core.Request;
import core.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PBTester {
	
	/**
	 * Force specific user personnal best for debug purpose
	 * @param event
	 * @author Blackoutburst
	 */
	public static void force(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			String msg[] = event.getMessage().getContentDisplay().split(" ");
			String ign = null;
			String uuid = null;
			String type = null;
			
			if (msg.length < 3) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!forcepb [ING] [Q/F]")).complete();
				return;
			}
			ign = msg[1];
			type = msg[2];
			uuid = Request.getPlayerUUID(ign);
			
			if (uuid == null) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
				return;
			}
			if (!type.toLowerCase().equals("q") && !type.toLowerCase().equals("f")) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!forcepb [ING] [Q/F]")).complete();
				return;
			}
			
			File index = new File("linked player");
			String[]entries = index.list();
			for(String s: entries) {
				File f = new File(index.getPath(),s);
				if (s.equals(uuid)) {
					if (type.toUpperCase().contentEquals("Q")) {
						try {
							PrintWriter writer = new PrintWriter(f+"/Q");
							writer.write("0");
							writer.close();
						} catch (FileNotFoundException e) {}
						
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.force_pb).replace("%ign%", ign).replace("%type%", "qualifier")).complete();
						return;
					} else {
						try {
							PrintWriter writer = new PrintWriter(f+"/F");
							writer.write("0");
							writer.close();
						} catch (FileNotFoundException e) {}
						event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.force_pb).replace("%ign%", ign).replace("%type%", "finals")).complete();
						return;
					}
				}
			}
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.not_linked)).complete();
			
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
}
