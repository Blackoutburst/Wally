package core;

import java.awt.Color;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import utils.Config;

public class RolesManager {
	
	/**
	 * Set member tournament player role
	 * @param action
	 * @param guild
	 * @param member
	 */
	public void setTournamentPlayer(RoleAction action, Guild guild, Member member) {
		switch (action) {
			case ADD: guild.addRoleToMember(member, guild.getRoleById(Config.getRoleId("Tournament Player"))).complete(); break;
			case REMOVE: guild.removeRoleFromMember(member, guild.getRoleById(Config.getRoleId("Tournament Player"))).complete(); break;
		}
	}
	
	/**
	 * Set member gender role
	 * @param action
	 * @param guild
	 * @param member
	 * @param roleName
	 */
	public void setGender(RoleAction action, Guild guild, Member member, String roleName) {
		switch (action) {
			case ADD: guild.addRoleToMember(member, guild.getRoleById(Config.getRoleId(roleName))).complete(); break;
			case REMOVE: guild.removeRoleFromMember(member, guild.getRoleById(Config.getRoleId(roleName))).complete(); break;
		}
	}
	
	/**
	 * Set club roles from score
	 */
	
	public void addClubRole(Guild guild, Member member, int qualification, int finals) {
		int best = (finals > qualification) ? finals : qualification;
		String roleName = "";
		
		if (best < 50) return;
		if (best >= 50) roleName = "50+ Club";
		if (best >= 100) roleName = "100+ Club";
		if (best >= 150) roleName = "150+ Club";
		if (best >= 200) roleName = "200+ Club";
		if (best >= 250) roleName = "250+ Club";
		if (best >= 300) roleName = "300+ Club";
		if (best >= 350) roleName = "350+ Club";
		if (best >= 400) roleName = "400+ Club";
		
		cleanClubRole(guild, member);
		guild.addRoleToMember(member, guild.getRoleById(Config.getRoleId(roleName))).complete();
	}
	
	/**
	 * Remove every roles club
	 * @param guild
	 * @param member
	 */
	private void cleanClubRole(Guild guild, Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().contains("Club")) {
				guild.removeRoleFromMember(member, r).complete();
			}
		}
	}
	
	/**
	 * Set club roles from score
	 */
	
	public void addLifeTimeRole(Member member, String roleName) {
		Bot.server.addRoleToMember(member, Bot.server.getRoleById(Config.getRoleId(roleName))).complete();
	}
	
	/**
	 * Remove every roles club
	 * @param guild
	 * @param member
	 */
	public void cleanLifeTimeRole(Member member) {
		for (Role r : member.getRoles()) {
			if (r.getName().contains("LifeTime")) {
				Bot.server.removeRoleFromMember(member, r).complete();
			}
		}
	}
	
	/**
	 * Return role color
	 * @param discord
	 * @return
	 */
	public static Color getRoleColor(int score) {
		Color color = Bot.server.getRolesByName("Members", false).get(0).getColor();
		
		if (score >= 50) {color = Bot.server.getRoleById(Config.getRoleId("50+ Club")).getColor();}
		if (score >= 100) {color = Bot.server.getRoleById(Config.getRoleId("100+ Club")).getColor();}
		if (score >= 150) {color = Bot.server.getRoleById(Config.getRoleId("150+ Club")).getColor();}
		if (score >= 200) {color = Bot.server.getRoleById(Config.getRoleId("200+ Club")).getColor();}
		if (score >= 250) {color = Bot.server.getRoleById(Config.getRoleId("250+ Club")).getColor();}
		if (score >= 300) {color = Bot.server.getRoleById(Config.getRoleId("300+ Club")).getColor();}
		if (score >= 350) {color = Bot.server.getRoleById(Config.getRoleId("350+ Club")).getColor();}
		if (score >= 400) {color = Bot.server.getRoleById(Config.getRoleId("400+ Club")).getColor();}
		return color;
	}
}
