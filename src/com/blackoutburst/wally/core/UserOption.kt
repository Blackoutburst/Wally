package com.blackoutburst.wally.core

import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.commands.PermissionLevel

class UserOption {

    var permissionLevel: PermissionLevel
    var displayPreference: DisplayPreference
    var mainAccountUUID: String
    var fontColor: String
    var fontSize: Float
    var serverID: String
    var subtitle: String
    var patreonTier: Int
    var isDeveloper: Boolean
    var useHeader: Boolean

    constructor() {
        permissionLevel = PermissionLevel.MEMBER
        displayPreference = DisplayPreference.CANVAS
        mainAccountUUID = ""
        fontColor = "#FFFFFF"
        serverID = ""
        subtitle = ""
        patreonTier = 0
        fontSize = 0f
        isDeveloper = false
        useHeader = true
    }

    constructor(permissionLevel: PermissionLevel, displayPreference: DisplayPreference, mainAccountUUID: String, serverID: String) {
        this.permissionLevel = permissionLevel
        this.displayPreference = displayPreference
        this.mainAccountUUID = mainAccountUUID
        fontColor = "#FFFFFF"
        this.serverID = serverID
        subtitle = ""
        patreonTier = 0
        fontSize = 0f
        isDeveloper = false
        useHeader = true
    }
}