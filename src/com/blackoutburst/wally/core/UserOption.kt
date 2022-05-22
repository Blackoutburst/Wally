package com.blackoutburst.wally.core

import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.core.commands.DisplayPreference

class UserOption {

    var permissionLevel: PermissionLevel
    var displayPreference: DisplayPreference
    var mainAccountUUID: String
    var fontColor: String
    var serverID: String

    constructor() {
        permissionLevel = PermissionLevel.MEMBER
        displayPreference = DisplayPreference.CANVAS
        mainAccountUUID = ""
        fontColor = "#FFFFFF"
        serverID = ""
    }

    constructor(permissionLevel: PermissionLevel, displayPreference: DisplayPreference, mainAccountUUID: String, serverID: String) {
        this.permissionLevel = permissionLevel
        this.displayPreference = displayPreference
        this.mainAccountUUID = mainAccountUUID
        fontColor = "#FFFFFF"
        this.serverID = serverID
    }
}