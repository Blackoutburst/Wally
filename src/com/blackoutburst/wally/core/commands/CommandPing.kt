package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

/**
 * A very basic ping command
 */
class CommandPing : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val time = System.currentTimeMillis()

        channel.sendMessage("Pong !").queue { ping: Message -> ping.editMessage((System.currentTimeMillis() - time).toString() + "ms").queue() }
    }
}


