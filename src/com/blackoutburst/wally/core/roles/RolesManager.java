package com.blackoutburst.wally.core.roles;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nonnull;

public class RolesManager {

    /**
     * Get the gender role associated with a specific reaction
     *
     * @param emote the emote code
     * @return the role name
     */
    public static @Nonnull String getGenderRoleName(@Nonnull String emote) {
        switch(emote) {
            case "RE:U+1f1ed" : return ("He/Him");
            case "RE:U+1f1f8" : return ("She/Her");
            default: return ("They/Them");
        }
    }

    /**
     * Add a role to a member
     *
     * @param roleName the role name
     * @param member the member affected
     */
    public static void addRole(@Nonnull String roleName, @Nonnull Member member) {
        final Guild guild = member.getGuild();
        final Role role = guild.getRoleById(ConfigManager.getInstance().loadValue("roles", roleName));

        if (role == null) {
            Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't apply the role ["+roleName+"] to ["+member.getEffectiveName()+"]");
            return;
        }

        guild.addRoleToMember(member, role).complete();
        Wally.getInstance().logger.log(LogLevel.INFO, "Role ["+roleName+"] added to ["+member.getEffectiveName()+"]");
    }

    /**
     * Remove a role from a member
     *
     * @param roleName the role name
     * @param member the member affected
     */
    public static void removeRole(@Nonnull String roleName, @Nonnull Member member) {
        final Guild guild = member.getGuild();
        final Role role = guild.getRoleById(ConfigManager.getInstance().loadValue("roles", roleName));

        if (role == null) {
            Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't remove the role ["+roleName+"] from ["+member.getEffectiveName()+"]");
            return;
        }

        guild.removeRoleFromMember(member, role).complete();
        Wally.getInstance().logger.log(LogLevel.INFO, "Role ["+roleName+"] removed to ["+member.getEffectiveName()+"]");
    }
}
