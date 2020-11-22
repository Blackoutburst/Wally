package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import core.Lines;
import core.Reader;
import core.Request;
import core.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Register {
	
	/**
	 * Display tournament player
	 * @param event
	 * @author Blackoutburst
	 */
	public static void display(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || Utils.isStaff(event.getMember())) {
			List<Member> members = event.getGuild().getMembers();
			
			String str = "```yml\nTournament player: Discord [IGN] Q/F T\n";
			for (Member m : members) {
				String id = m.getId();
				File index = new File("linked player");
				String[]entries = index.list();
				for(String s: entries) {
					String lid = "";
					String Q = "";
					String F = "";
					String T = "";
					File f = new File(index.getPath(),s);
					
					lid = readValue(f+"/discord");
					if (lid.equals(id)) {
						List<Role> roles = m.getRoles();
						for (Role r : roles) {
							if (r.getName().equals("Tournament Player")) {
								Q = readValue(f+"/Q");
								F = readValue(f+"/F");
								T = String.valueOf(Integer.valueOf(Q) + Integer.valueOf(F));
								str += m.getUser().getName()+": ["+Request.getPlayer(f.getName())+"] "+Q+"/"+F+" "+T+"\n";
							}
						}
					}
					
				}
			}
			str += "```";
			event.getChannel().sendMessage(str).complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
	
	/**
	 * Read file value
	 * @param file
	 * @return value read
	 * @author Blackoutburst
	 */
	private static String readValue(String file) {
		String str = "";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
