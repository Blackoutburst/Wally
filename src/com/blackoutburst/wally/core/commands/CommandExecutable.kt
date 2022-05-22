package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

/**
 * This is the parent class for every command
 * It contains the function automatically called
 * by the manager to perform command action
 */
abstract class CommandExecutable {

    abstract fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message)
}