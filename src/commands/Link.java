package commands;

import java.io.File;
import java.io.PrintWriter;

import core.Lines;
import core.Reader;
import core.Request;
import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Link {
	
	/**
	 * Link player discord account and in game account
	 * @param event
	 * @author Blackoutburst
	 */
	public static void link(MessageReceivedEvent event) {
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().getId().equals(Main.bypassID)) {
			if (!new File("tracker").exists()) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.tracker_undefined)).complete();
				return;
			}
			
			String[] str = event.getMessage().getContentDisplay().split(" ");
			String discord = "";
			String ign = "";
			String uuid = "";
			
			if (str.length < 3) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!link discordID IGN")).complete();
				return;
			}
			discord = str[1];
			ign = str[2];
			uuid = Request.getPlayerUUID(ign);
			
			if (uuid.equals("null")) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.unknow_player)).complete();
				return;
			}
			
			File f = new File("linked player/"+uuid);
			
			if (f.exists()) {
				event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.linked)).complete();
				return;
			}
			try {
				String[] value;
				String qualification = "";
				String finals = "";
				String output = Request.getPlayerInfo(ign);
				
				value = output.split(",");
				for (int i = 0; i < value.length; i++) {
					if (value[i].contains("hitw_record_q"))
						qualification = value[i].replace(" ", "").split(":")[1];
					if (value[i].contains("hitw_record_f"))
						finals = value[i].replace(" ", "").split(":")[1];
				}
				f.mkdirs();
				PrintWriter writer = new PrintWriter("linked player/"+uuid+"/discord");
				writer.write(discord);
				writer.close();
				writer = new PrintWriter("linked player/"+uuid+"/Q");
				writer.write(qualification);
				writer.close();
				writer = new PrintWriter("linked player/"+uuid+"/F");
				writer.write(finals);
				writer.close();
			
				setRole(Integer.valueOf(qualification), Integer.valueOf(finals), event);
				event.getChannel().sendMessage(Reader.read(Lines.link).replace("%discord%", "<@"+discord+">").replace("%ign%", ign).replace("%q%", qualification).replace("%f%", finals)).complete();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.misssing_perms)).complete();
		}
	}
	
	private static void setRole(int qualification, int finals, MessageReceivedEvent event) {
		if (qualification > 350 || finals > 350) {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("350+ Club", false).get(0)).complete();
		} else if (qualification > 300 || finals > 300) {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("300+ Club", false).get(0)).complete();
		} else if (qualification > 250 || finals > 250) {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("250+ Club", false).get(0)).complete();
		} else if (qualification > 200 || finals > 200) {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("200+ Club", false).get(0)).complete();
		} else if (qualification > 150 || finals > 150) {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("150+ Club", false).get(0)).complete();
		} else if (qualification > 100 || finals > 100) {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("100+ Club", false).get(0)).complete();
		} else if (qualification > 50 || finals > 50) {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("50+ Club", false).get(0)).complete();
		}
	}
}
