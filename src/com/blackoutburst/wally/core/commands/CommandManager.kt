package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.util.UUID
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.HashSet
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object CommandManager {

    /**
     * Get the command prefix
     * We could use a static variable but the goal is to
     * have the configuration refreshed automatically
     *
     * @return the command prefix
     */
    var prefix: String = ConfigManager.loadValue("settings", "command prefix")

    val commands: MutableSet<Command> = HashSet()

    /**
     * Register Wally commands
     */
    init {
        register(PermissionLevel.MEMBER, CommandPing(), "ping")
        register(PermissionLevel.STAFF, CommandSay(), "say")
        register(PermissionLevel.STAFF, CommandLink(), "link")
        register(PermissionLevel.STAFF, CommandUnlink(), "unlink")
        register(PermissionLevel.ADMIN, CommandSetLogLevel(), "setLogLevel")
        register(PermissionLevel.ADMIN, CommandReloadUser(), "reloadUser", "reloadUsers")
        register(PermissionLevel.ADMIN, CommandSetPermissionLevel(), "setPermissionLevel", "setPerms", "setPermission")
        register(PermissionLevel.ADMIN, CommandGetConfig(), "getConfig", "getConfiguration")
        register(PermissionLevel.ADMIN, CommandSetConfig(), "setConfig", "setConfiguration")
        register(PermissionLevel.MEMBER, CommandPoll(), "poll")
        register(PermissionLevel.ADMIN, CommandSetTracker(), "setTracker")
        register(PermissionLevel.ADMIN, CommandShowTracker(), "showTracker")
        register(PermissionLevel.ADMIN, CommandSetPracticeTracker(), "setPracticeTracker")
        register(PermissionLevel.ADMIN, CommandShowPracticeTracker(), "showPracticeTracker")
        register(PermissionLevel.ADMIN, CommandSetAnswerLifetime(), "setAnswerLifetime", "setAnswerLife")
        register(PermissionLevel.ADMIN, CommandGetLog(), "getLog", "getLogs")
        register(PermissionLevel.ADMIN, CommandPreviewChangelog(), "preChangelog", "previewChangelog")
        register(PermissionLevel.ADMIN, CommandChangelog(), "changelog")
        register(PermissionLevel.ADMIN, CommandGetChangelog(), "getChangelog")
        register(PermissionLevel.ADMIN, CommandSetChangelog(), "setChangelog")
        register(PermissionLevel.STAFF, CommandAddAlt(), "addAlt")
        register(PermissionLevel.STAFF, CommandRemoveAlt(), "removeAlt")
        register(PermissionLevel.STAFF, CommandListAccount(), "listAccount", "listAcc", "listAccounts", "listAccs")
        register(PermissionLevel.MEMBER, CommandSkin(), "skin")
        register(PermissionLevel.STAFF, CommandExcludeFromLeaderboard(),"excludeFromLeaderboard", "excludeFromLb", "excludeLb", "elb")
        register(PermissionLevel.STAFF, CommandAddToLeaderboard(), "addToLeaderboard", "addToLb", "addLb", "alb")
        register(PermissionLevel.STAFF, CommandExcludeList(), "excludeList", "eList")
        register(PermissionLevel.ADMIN, CommandLogLevel(), "logLevel", "showLogLevel", "displayLogLevel")
        register(PermissionLevel.MEMBER, CommandLeaderboard(),"leaderboard", "leaderboards", "lead", "leads", "lb", "lbs")
        register(PermissionLevel.ADMIN, CommandSetPrefix(), "setPrefix", "setCommandPrefix")
        register(PermissionLevel.MEMBER, CommandStats(), "stats", "stat", "s")
        register(PermissionLevel.MEMBER, CommandPracticeStats(),"practiceStats", "practiceStat", "ps", "pStats", "pStat")
        register(PermissionLevel.MEMBER, CommandSetDisplayPreference(), "setDisplayPreference", "sdp")
        Wally.logger.log(LogLevel.INFO, "Command registered!")
    }

    /**
     * Register new commands
     *
     * @param level command execution permission level
     * @param executable command executable
     * @param labels command label / alias
     */
    private fun register( level: PermissionLevel, executable: CommandExecutable, vararg labels: String) {
        commands.add(Command(level, executable, labels))
        Wally.logger.log(LogLevel.INFO, "Command [" + labels[0] + "] is registered")
    }

    private fun checkForCloseCommand(commandSender: WallyUser, commandName: String, message: Message) {
        var lowestWeight = Int.MAX_VALUE
        var closest: String? = null
        val inputWeight = commandName.chars().sum()
        
        for (c in commands) {
            for (l in c.labels) {
                val tempWeight = l.chars().sum()
                if (abs(inputWeight - tempWeight) < lowestWeight) {
                    lowestWeight = abs(inputWeight - tempWeight)
                    closest = l
                }
            }
        }
        
        if (lowestWeight > 20) closest = null
        if (closest == null) {
            message.reply(ConfigManager.loadValue("replies", "unknown command")
                .replace("%command_name%", commandName) + '\n' +
                    ConfigManager.loadValue("replies", "run help").replace("%help%", prefix + "help")
            ).setActionRow(
                Button.primary(commandSender.member.id + ":" + message.id, "Yes run help"),
                Button.danger(commandSender.member.id + ":" + UUID.randomUUID(), "No"))
            .queue { r: Message ->
                r.delete().queueAfter(ConfigManager.loadValue("settings", "answer lifetime").toLong(), TimeUnit.SECONDS)
            }
            return
        }
        message.reply(ConfigManager.loadValue("replies", "unknown command")
            .replace("%command_name%", commandName) + '\n' +
                ConfigManager.loadValue("replies", "close command").replace("%command_name%", closest)
        ).setActionRow(
            Button.primary(commandSender.member.id + ":" + message.id, "Yes run $closest"),
            Button.danger(commandSender.member.id + ":" + UUID.randomUUID(), "No"))
        .queue { r: Message ->
            r.delete().queueAfter(
                ConfigManager.loadValue("settings", "answer lifetime").toLong(),
                TimeUnit.SECONDS
            )
        }
    }

    /**
     * Execute a command
     *
     * This is art, but you do not have to touch this function at all
     * If you find yourself in the need to edit this then you probably did something wrong
     */
    fun runCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild?, channel: MessageChannel, message: Message) {
        channel.sendTyping().queue()
        for (command in commands) {
            for (label in command.labels) {
                if (commandName.equals(label, ignoreCase = true)) {
                    if (commandSender.permissionLevel.weight >= command.permissionLevel.weight) {
                        Wally.logger.log(LogLevel.INFO,commandSender.member.effectiveName + " used [" + label + "]")
                        command.executable.onCommand(commandSender, commandName, args, guild!!, channel, message)
                    } else {
                        Wally.logger.log(LogLevel.INFO,commandSender.member.effectiveName + " miss permission to use [" + label + "] U:" + commandSender.permissionLevel.weight + "/C:" + command.permissionLevel.weight)
                        message.reply(ConfigManager.loadValue("replies", "missing permission")).queue()
                    }
                    return
                }
            }
        }
        checkForCloseCommand(commandSender, commandName, message)
    }
}