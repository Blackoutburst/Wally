package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.commands.PermissionLevel;
import net.dv8tion.jda.api.entities.Member;

import javax.annotation.Nonnull;

public class WallyUser {

    private final Member member;

    private PermissionLevel permissionLevel;

    public WallyUser(Member member, PermissionLevel permissionLevel) {
        this.member = member;
        this.permissionLevel = permissionLevel;
    }

    /**
     * Get the discord member associated with the WallyUser
     * 
     * @return the discord member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Get the user permission level (for commands)
     *
     * @return the permission level
     */
    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * Set the user permission level (for commands)
     *
     * @param permissionLevel the new permission level
     */
    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    /**
     * Get a new Wally user from a discord Member
     *
     * @param member the member supposed to be affiliated with a WallyUser
     * @return the WallyUser found or null if none
     */
    public static WallyUser getUser(@Nonnull Member member) {
        for (WallyUser u : Wally.getInstance().users) {
            if (u.getMember().getId().equals(member.getId()))
                return (u);
        }
        return (null);
    }
}
