package com.blackoutburst.wally.core

import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.sun.org.apache.xpath.internal.operations.Bool
import net.dv8tion.jda.api.entities.Member
import java.io.File

class WallyUser(val member: Member, private val options: UserOption) {

    val permissionLevel: PermissionLevel
        get() = options.permissionLevel

    val displayPreference: DisplayPreference
        get() = options.displayPreference

    val fontColor: String
        get() = options.fontColor

    val subtitle: String
        get() = options.subtitle

    val patreonTier: Int
        get() = options.patreonTier

    val isDeveloper: Boolean
        get() = options.isDeveloper

    fun hasBackground() :Boolean {
        return File("database/linked users/${member.id}/resources/background.png").exists()
    }

    fun hasFont() :Boolean {
        return File("database/linked users/${member.id}/resources/font.ttf").exists()
    }

    companion object {
        fun getUser(member: Member): WallyUser {
            for (u in Wally.users) {
                if (u.member.id == member.id) return u
            }
            return WallyUser(member, UserOption())
        }
    }
}