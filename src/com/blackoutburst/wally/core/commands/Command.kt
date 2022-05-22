package com.blackoutburst.wally.core.commands

class Command(val permissionLevel: PermissionLevel, val executable: CommandExecutable, val labels: Array<out String>)