package com.blackoutburst.wally.core.roles

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.statsobject.HypixelStats
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role

object RolesManager {

    fun getGenderRoleName(emote: String?): String {
        return when (emote) {
            "UnicodeEmoji(U+1f1ed)" -> "He/Him"
            "UnicodeEmoji(U+1f1f8)" -> "She/Her"
            else -> "They/Them"
        }
    }

    fun addRole(roleName: String,  member: Member) {
        val guild = member.guild
        val role = guild.getRoleById(ConfigManager.loadValue("roles", roleName))
        val memberName = member.effectiveName
        val roles = Wally.hitwDiscord!!.roles
        val hasRole = roles.stream().filter { r: Role -> r.name == roleName }.findFirst()

        if (role == null) {
            Wally.logger.log(
                LogLevel.WARN,
                "Couldn't apply the role [$roleName] to [$memberName]"
            )
            return
        }

        if (member.roles.contains(hasRole.get())) {
            return;
        }

        guild.addRoleToMember(member, role).queue()
        Wally.logger.log(LogLevel.INFO, "Role [$roleName] added to [$memberName]")
    }

    fun removeRole(roleName: String,  member: Member) {
        val guild = member.guild
        val role = guild.getRoleById(ConfigManager.loadValue("roles", roleName))
        val memberName = member.effectiveName
        val roles = Wally.hitwDiscord!!.roles
        val hasRole = roles.stream().filter { r: Role -> r.name == roleName }.findFirst()

        if (!member.roles.contains(hasRole.get())) {
            return;
        }

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

    fun addClubRole(score: Int, member: Member) {
        if (Wally.hitwDiscord == null) return

        val lowRound = (if (score > 500) 500 else score + 50) / 50 * 50 - 50
        if (lowRound < 50) return

        val roles = Wally.hitwDiscord!!.roles
        val role = roles.stream().filter { r: Role -> r.name == "$lowRound+ Club" }.findFirst()

        if (!member.roles.contains(role.get())) {
            for (role in member.roles) {
                if (role.name.contains("+ Club"))
                    removeRole(role.name, member)
            }
            addRole("$lowRound+ Club", member)
        }
    }

    fun addLifeTimeRoles(member: Member, stats: HypixelStats) {
        if (stats.winsPosition <= 10)
            addRole("Top 10 Lifetime Wins", member)
        if (stats.wallsPosition <= 10)
            addRole("Top 10 Lifetime Walls", member)
        if (stats.qualificationPosition <= 10)
            addRole("Top 10 Lifetime Q", member)
        if (stats.finalsPosition <= 10)
            addRole("Top 10 Lifetime F", member)
    }

    fun cleanLifeTimeRoles(member: Member, stats: HypixelStats) {
        for (role in member.roles) {
            if (role.name == "Top 10 Lifetime Wins" && stats.winsPosition > 10)
                removeRole(role.name, member)
            if (role.name == "Top 10 Lifetime Walls" && stats.wallsPosition > 10)
                removeRole(role.name, member)
            if (role.name == "Top 10 Lifetime Q" && stats.qualificationPosition > 10)
                removeRole(role.name, member)
            if (role.name == "Top 10 Lifetime F" && stats.finalsPosition > 10)
                removeRole(role.name, member)
        }
    }
}