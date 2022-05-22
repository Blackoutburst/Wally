package com.blackoutburst.wally.core

import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.core.commands.DisplayPreference
import net.dv8tion.jda.api.entities.Member

class WallyUser(val member: Member, private val options: UserOption) {

    val permissionLevel: PermissionLevel
        get() = options.permissionLevel

    val displayPreference: DisplayPreference
        get() = options.displayPreference

    companion object {
        fun getUser(member: Member): WallyUser {
            for (u in Wally.users) {
                if (u.member.id == member.id) return u
            }
            return WallyUser(member, UserOption())
        }
    }
}