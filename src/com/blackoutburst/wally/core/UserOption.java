package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.commands.DisplayPreference;
import com.blackoutburst.wally.core.commands.PermissionLevel;

import javax.annotation.Nonnull;

/**
 * This class is used to serialize data using GSON
 */
public class UserOption {

    private PermissionLevel permissionLevel;

    private DisplayPreference displayPreference;

    private String mainAccountUUID;
    private String fontColor;
    private String serverID;

    public UserOption() {
        this.permissionLevel = PermissionLevel.MEMBER;
        this.displayPreference = DisplayPreference.CANVAS;
        this.mainAccountUUID = "";
        this.fontColor = "#FFFFFF";
        this.serverID = "";
    }

    public UserOption(@Nonnull PermissionLevel permissionLevel, @Nonnull DisplayPreference displayPreference, @Nonnull String mainAccountUUID, @Nonnull String serverID) {
        this.permissionLevel = permissionLevel;
        this.displayPreference = displayPreference;
        this.mainAccountUUID = mainAccountUUID;
        this.fontColor = "#FFFFFF";
        this.serverID = serverID;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public DisplayPreference getDisplayPreference() {
        return displayPreference;
    }

    public void setDisplayPreference(DisplayPreference displayPreference) {
        this.displayPreference = displayPreference;
    }

    public @Nonnull String getMainAccountUUID() {
        return mainAccountUUID;
    }

    public void setMainAccountUUID(String mainAccountUUID) {
        this.mainAccountUUID = mainAccountUUID;
    }

    public @Nonnull String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public @Nonnull String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }
}
