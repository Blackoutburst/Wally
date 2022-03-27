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

    public Member getMember() {
        return member;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel level) {
        this.permissionLevel = permissionLevel;
    }

    public static WallyUser getUser(@Nonnull Member member) {
        for (WallyUser u : Wally.getInstance().users) {
            if (u.getMember().getId().equals(member.getId()))
                return (u);
        }
        return (null);
    }
}
