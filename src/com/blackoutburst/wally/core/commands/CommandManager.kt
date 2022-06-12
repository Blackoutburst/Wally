package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object CommandManager {

    var prefix: String = ConfigManager.loadValue("settings", "command prefix")
    val commands: MutableSet<Command> = HashSet()

    init {
        register(PermissionLevel.MEMBER, CommandPing(), "ping")
        register(PermissionLevel.STAFF, CommandSay(), "say")
        register(PermissionLevel.STAFF, CommandLink(), "link")
        register(PermissionLevel.STAFF, CommandUnlink(), "unlink")
        register(PermissionLevel.ADMIN, CommandSetLogLevel(), "setLogLevel")
        register(PermissionLevel.ADMIN, CommandReloadUser(), "reloadUser", "reloadUsers")
        register(PermissionLevel.ADMIN, CommandSetPermissionLevel(), "setPermissionLevel", "setPerms", "setPermission", "spl")
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
        register(PermissionLevel.STAFF, CommandRemoveAlt(), "removeAlt", "rmAlt")
        register(PermissionLevel.STAFF, CommandListAccount(), "listAccount", "listAcc", "listAccounts", "listAccs")
        register(PermissionLevel.MEMBER, CommandSkin(), "skin")
        register(PermissionLevel.STAFF, CommandExcludeFromLeaderboard(),"excludeFromLeaderboard", "excludeFromLb", "excludeLb", "elb")
        register(PermissionLevel.STAFF, CommandAddToLeaderboard(), "addToLeaderboard", "addToLb", "addLb", "alb")
        register(PermissionLevel.STAFF, CommandExcludeList(), "excludeList", "eList")
        register(PermissionLevel.ADMIN, CommandLogLevel(), "logLevel", "showLogLevel", "displayLogLevel")
        register(PermissionLevel.MEMBER, CommandLeaderboard(),"leaderboard", "leaderboards", "lead", "leads", "lb", "lbs")
        register(PermissionLevel.ADMIN, CommandSetPrefix(), "setPrefix", "setCommandPrefix", "scp")
        register(PermissionLevel.MEMBER, CommandStats(), "stats", "stat", "s")
        register(PermissionLevel.MEMBER, CommandPracticeStats(),"practiceStats", "practiceStat", "ps", "pStats", "pStat")
        register(PermissionLevel.MEMBER, CommandSetDisplayPreference(), "setDisplayPreference", "sdp")
        register(PermissionLevel.MEMBER, CommandCompare(), "compare", "c")
        register(PermissionLevel.MEMBER, CommandPracticeCompare(), "pCompare", "pc", "practiceCompare", "practiceC")
        register(PermissionLevel.STAFF, CommandSetMainAccount(), "setMain", "setMainAccount", "setMainAcc")
        register(PermissionLevel.MEMBER, CommandSetFontColor(), "setFontColor", "sfc")
        register(PermissionLevel.MEMBER, CommandResetFontColor(), "resetFontColor", "rfc")
        register(PermissionLevel.STAFF, CommandDeleteFontColor(), "deleteFontColor", "dfc")
        register(PermissionLevel.ADMIN, CommandSortLeaderboard(), "sortLeaderboard", "sortLead", "sortLb", "slb")
        register(PermissionLevel.MEMBER, CommandSetFont(), "setFont", "sf")
        register(PermissionLevel.MEMBER, CommandResetFont(), "resetFont", "rf")
        register(PermissionLevel.STAFF, CommandDeleteFont(), "deleteFont", "df")
        register(PermissionLevel.MEMBER, CommandSetBackground(), "setBackground", "setBg", "sbg")
        register(PermissionLevel.MEMBER, CommandResetBackground(), "resetBackground", "resetBg", "rbg")
        register(PermissionLevel.STAFF, CommandDeleteBackground(), "deleteBackground", "deleteBg", "dbg")
        Wally.logger.log(LogLevel.INFO, "Command registered!")
    }

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
            message.reply(ConfigManager.loadMessage( "unknown command")
                .replace("%command_name%", commandName) + '\n' +
                    ConfigManager.loadMessage( "run help").replace("%help%", prefix + "help")
            ).setActionRow(
                Button.primary(commandSender.member.id + ":" + message.id, "Yes run help"),
                Button.danger(commandSender.member.id + ":" + UUID.randomUUID(), "No"))
            .queue { r: Message ->
                r.delete().queueAfter(ConfigManager.loadValue("settings", "answer lifetime").toLong(), TimeUnit.SECONDS)
            }
            return
        }
        message.reply(ConfigManager.loadMessage( "unknown command")
            .replace("%command_name%", commandName) + '\n' +
                ConfigManager.loadMessage( "close command").replace("%command_name%", closest)
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

    fun runCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        channel.sendTyping().queue()

        val userName = commandSender.member.effectiveName
        val userPermission = commandSender.permissionLevel.weight
        val command = commands.find { c ->
           c.labels.any{ it.equals(commandName, ignoreCase = true) }
        }

        command?.let {
            val commandPermission = command.permissionLevel.weight
            if (commandSender.permissionLevel.weight >= command.permissionLevel.weight) {
                Wally.logger.log(LogLevel.INFO,  "$userName used [$commandName]")
                command.executable.onCommand(commandSender, commandName, args, guild, channel, message)
            } else {
                Wally.logger.log(LogLevel.INFO,"$userName miss permission to use [$commandName] U:$userPermission/C:$commandPermission")
                message.reply(ConfigManager.loadMessage("missing permission")).queue()
            }
            return
        }

        message.reply(ConfigManager.loadMessage( "unknown command")
            .replace("%command_name%", commandName) + '\n' +
                ConfigManager.loadMessage( "run help").replace("%help%", prefix + "help")
        ).setActionRow(
            Button.primary(commandSender.member.id + ":" + message.id, "Yes run help"),
            Button.danger(commandSender.member.id + ":" + UUID.randomUUID(), "No"))
            .queue { r: Message ->
                r.delete().queueAfter(ConfigManager.loadValue("settings", "answer lifetime").toLong(), TimeUnit.SECONDS)
            }
        //checkForCloseCommand(commandSender, commandName, message) Temps disabled until it works properly
    }
}