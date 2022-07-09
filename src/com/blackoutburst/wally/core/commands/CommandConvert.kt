package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt


class CommandConvert : CommandExecutable() {

    private enum class Type {
        Q, F
    }

    private fun displayFinalValue(value: Double, channel: MessageChannel) {
        var a: Double
        var b: Double
        var RF: Double
        var F = value
        var Q = 0
        var searching = true
        if (F < 0 || F > 550) {
            channel.sendMessage(ConfigManager.loadMessage("f range")).queue()
            return
        }
        if (F == 0.0) {
            channel.sendMessage(ConfigManager.loadMessage("f zero")).queue()
            return
        }
        while (searching) {
            Q = 0
            while (Q < 401) {
                a = 0.00285258
                b = 0.382482
                if (Q < 100) {
                    a = -0.0209171241809
                    b = 2.75945240632
                }
                RF = (Q - 100) * (a * Q + b) + 100
                if (Math.round(RF) == Math.round(F)) {
                    searching = false
                    break
                }
                Q++
            }
            F--
        }
        channel.sendMessage(ConfigManager.loadMessage("f convert")
            .replace("%F%", value.roundToInt().toString())
            .replace("%Q%", Q.toFloat().roundToInt().toString())).queue()
    }

    private fun displayQualificationValue(value: Double, channel: MessageChannel) {
        val F: Double
        if (value < 0 || value > 400) {
            channel.sendMessage(ConfigManager.loadMessage("q range")).queue()
            return
        }
        if (value < 45) {
            channel.sendMessage(ConfigManager.loadMessage("q range").replace("%Q%", value.roundToInt().toString())).queue()
            return
        }
        var a = 0.00285258
        var b = 0.382482
        if (value < 100) {
            a = -0.0209171241809
            b = 2.75945240632
        }
        F = (value - 100) * (a * value + b) + 100

        channel.sendMessage(ConfigManager.loadMessage("q convert")
            .replace("%Q%", value.roundToInt().toString())
            .replace("%F%", F.roundToInt().toString())).queue()
    }

    private fun getTypeArgIndex(args: Array<String>): Int {
        for ((index, arg) in args.withIndex()) {
            if (arg.isEmpty()) return -1
            if (arg.lowercase(Locale.getDefault())[0] == 'q' || arg.lowercase(Locale.getDefault())[0] == 'f') {
                return index
            }
        }
        return -1
    }

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size < 2) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", CommandManager.prefix + "convert [sccore] [Q/F]")).queue()
            return
        }
        val type: Type
        val typeArg = getTypeArgIndex(args)
        if (typeArg == -1) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "convert [sccore] [Q/F]")).queue()
            return
        }

        type = when (args[typeArg].toLowerCase()) {
            "q" -> Type.Q
            "f" -> Type.F
            else -> {
                message.reply(
                    ConfigManager.loadMessage( "invalid command usage")
                        .replace("%usage%", CommandManager.prefix + "convert [sccore] [Q/F]")).queue()
                return
            }
        }
        val value: Double = try {
            args[abs(typeArg - 1)].toDouble()
        } catch (e: Exception) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "convert [sccore] [Q/F]")).queue()
            return
        }
        when (type) {
            Type.Q -> displayQualificationValue(value, channel)
            Type.F -> displayFinalValue(value, channel)
        }
    }

}


