package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.commands.PermissionLevel;

import javax.annotation.Nonnull;

public class UserOption {

    private PermissionLevel permissionLevel;

    private String mainAccountUUID;
    private String fontColor;
    private String serverID;

    public UserOption() {
        this.permissionLevel = PermissionLevel.MEMBER;
        this.mainAccountUUID = "";
        this.fontColor = "#FFFFFF";
        this.serverID = "";
    }

    public UserOption(@Nonnull PermissionLevel permissionLevel, @Nonnull String mainAccountUUID, @Nonnull String serverID) {
        this.permissionLevel = permissionLevel;
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

    public String getMainAccountUUID() {
        return mainAccountUUID;
    }

    public void setMainAccountUUID(String mainAccountUUID) {
        this.mainAccountUUID = mainAccountUUID;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }
}
