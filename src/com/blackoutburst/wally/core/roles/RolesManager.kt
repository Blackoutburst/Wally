package com.blackoutburst.wally.core.roles

import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.entities.Member

object RolesManager {
    /**
     * Get the gender role associated with a specific reaction
     *
     * @param emote the emote code
     * @return the role name
     */
    fun getGenderRoleName( emote: String?): String {
        return when (emote) {
            "RE:U+1f1ed" -> "He/Him"
            "RE:U+1f1f8" -> "She/Her"
            else -> "They/Them"
        }
    }

    /**
     * Add a role to a member
     *
     * @param roleName the role name
     * @param member the member affected
     */
    fun addRole( roleName: String,  member: Member) {
        val guild = member.guild
        val role = guild.getRoleById(ConfigManager.loadValue("roles", roleName))
        val memberName = member.effectiveName

        if (role == null) {
            Wally.logger.log(
                LogLevel.WARN,
                "Couldn't apply the role [$roleName] to [$memberName]"
            )
            return
        }

        guild.addRoleToMember(member, role).queue()
        Wally.logger.log(LogLevel.INFO, "Role [$roleName] added to [$memberName]")
    }

    /**
     * Remove a role from a member
     *
     * @param roleName the role name
     * @param member the member affected
     */
    fun removeRole( roleName: String,  member: Member) {
        val guild = member.guild
        val role = guild.getRoleById(ConfigManager.loadValue("roles", roleName))
        val memberName = member.effectiveName

        if (role == null) {
            Wally.logger.log(
                LogLevel.WARN,
                "Couldn't remove the role [$roleName] from [$memberName]"
            )
            return
        }

        guild.removeRoleFromMember(member, role).queue()
        Wally.logger.log(
            LogLevel.INFO,
            "Role [$roleName] removed to [$memberName]"
        )
    }
}