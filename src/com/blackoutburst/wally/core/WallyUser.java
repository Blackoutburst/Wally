package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.commands.DisplayPreference;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import net.dv8tion.jda.api.entities.Member;

import javax.annotation.Nonnull;

public class WallyUser {

    private final Member member;

    private final UserOption options;

    public WallyUser(Member member, UserOption options) {
        this.member = member;
        this.options = options;
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
        return options.getPermissionLevel();
    }

    /**
     * Get the user display preference (for commands)
     *
     * @return the display preference
     */
    public DisplayPreference getDisplayPreference() {
        return options.getDisplayPreference();
    }

    /**
     * Get user options
     *
     * @return user options
     */
    public UserOption getOptions() {
        return options;
    }

    /**
     * Get a new Wally user from a discord Member
     * If there is no user a default user will get returned instead
     *
     * @param member the member supposed to be affiliated with a WallyUser
     * @return the WallyUser found or null if none
     */
    public static @Nonnull WallyUser getUser(@Nonnull Member member) {
        for (WallyUser u : Wally.getInstance().users) {
            if (u.getMember().getId().equals(member.getId()))
                return (u);
        }
        
        return (new WallyUser(member, new UserOption()));
    }
}
