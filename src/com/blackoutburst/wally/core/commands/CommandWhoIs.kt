package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.utils.APIUtils
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils
import com.blackoutburst.wally.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.sqrt

class CommandWhoIs : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        var uuid: String? = if (args.isNotEmpty()) {
            Mojang.getPlayerUUID(args[0])
        } else {
            message.reply(ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "whois [Minecraft name]")).queue()
            return
        }

        if (uuid == null) {
            Wally.logger.log(LogLevel.WARN, "Player uuid is null")
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        val user = DatabaseUtils.getWallyUserFromUUID(uuid)
        val time = System.currentTimeMillis()
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"

        var name : String? = null
        if (DatabaseUtils.isLinkedUUID(uuid)) {
           val mainUuid = DatabaseUtils.getWallyUserFromUUID(uuid)?.mainAccountUuid

            if (mainUuid != null) {
                uuid = mainUuid
                name = Mojang.getPlayerName(mainUuid)
            }
        } else {
            name = Mojang.getPlayerName(uuid)
        }
        if (name == null) {
            name = "unknown"
        }

        val embed = EmbedBuilder()
            .setAuthor("Who is $name ?", wallyURL, iconURL)
            .setTitle(name)
            .setThumbnail(if (DatabaseUtils.isLinkedUUID(uuid)) user!!.member.effectiveAvatarUrl else "https://visage.surgeplay.com/face/512/$uuid?&time=$time")

        if (user != null) {
            val background = File("database/linked users/${user.member.id}/resources/background.png")
            if (user.hasBackground()) {
                embed.setImage("attachment://background.png")
            }
            val stats: MutableList<HypixelStats> = ArrayList()
            val uuids = File("database/linked users/${user.member.id}/accounts").list()

            uuids.forEach { u ->
                val filePath = Paths.get("database/linked users/${user.member.id}/accounts/$u/hypixel.json")
                try {
                    val data = String(Files.readAllBytes(filePath))
                    stats.add(HypixelStats(data))
                } catch (e: Exception) {
                    Wally.logger.logStackTrace(e)
                }
            }

            var wins = 0
            stats.forEach { s -> wins += s.wins }
            var walls = 0
            stats.forEach { s -> walls += s.walls }
            val qualification = Utils.getHighestScore(stats.map { s -> s.qualification }.toIntArray())
            val finals = Utils.getHighestScore(stats.map { s -> s.finals }.toIntArray())
            val total = qualification + finals
            val totalAPI = Utils.getHighestScore(stats.map { s -> s.totalAPI }.toIntArray())
            var challenges = 0
            stats.forEach { s -> challenges += s.challenges }

            val combinedStats = HypixelStats(user.mainAccountUuid, "${user.member.effectiveName}#${user.member.user.discriminator}", wins, walls, qualification, finals, total, totalAPI, challenges)
            embed.setColor(Utils.getColorFromScore(Utils.getHighestScore(combinedStats.scores)))
            embed.addField("Discord", "`${user.member.effectiveName}#${user.member.user.discriminator}`", true)

            val folder = File("database/linked users/${user.member.id}/accounts/")
            val entries = folder.list()
            val data = DatabaseUtils.getWallyUserData(user.member.id)

            if (data == null) {
                MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("account list error"))
                return
            }

            val mainUUID = data.mainAccountUUID
            val builder = StringBuilder()

            builder.append("```md").append(System.lineSeparator())
                .append("# ").append(Mojang.getPlayerName(mainUUID)).append(System.lineSeparator())

            for (s in entries!!) {
                if (s == mainUUID) continue
                builder.append("- ").append(Mojang.getPlayerName(s)).append(System.lineSeparator())
            }

            builder.append("```").append(System.lineSeparator())
            embed.addField("Minecraft Accounts", builder.toString(), false)

            var hypixelData = Hypixel.getPlayerStats(uuid)
            if (hypixelData == null) {
                hypixelData = DatabaseUtils.getBackupLocalData(uuid, DatabaseUtils.DataType.HYPIXEL, channel)
            }

            if (hypixelData != null) {
                val hypixelLevel = Wally.decimalFormat.format(1.0 + -8750.0 / 2500.0 + sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (APIUtils.getValuesFromAPI(hypixelData, "player.networkExp", "0").toBigDecimal().toInt())))
                val ap = Wally.numberFormat.format(APIUtils.getValuesFromAPI(hypixelData, "player.achievementPoints", "0").toInt())

                embed.addField("Hypixel level", "`$hypixelLevel`",true)
                embed.addField("Achievement points", "`$ap`",true)
                embed.addBlankField(true)
            }

            embed.addField("", "[Hypixel](https://hypixel.net/player/$uuid)", true)
            embed.addField("", "[Plancke](https://plancke.io/hypixel/player/stats/$uuid)", true)
            embed.addField("", "[NameMC](https://namemc.com/profile/$uuid)", true)

            embed.setFooter(uuid)

            channel.sendMessageEmbeds(embed.build()).addFile(background,"background.png").queue()
        } else {
            var data = Hypixel.getPlayerStats(uuid)
            if (data == null) {
                data = DatabaseUtils.getBackupLocalData(uuid, DatabaseUtils.DataType.HYPIXEL, channel)
            }
            if (data != null) {
                val stats = HypixelStats(data)
                embed.setColor(Utils.getColorFromScore(Utils.getHighestScore(stats.scores)))

                embed.addField("Discord", "`not linked`", true)
                embed.addBlankField(true)
                embed.addBlankField(true)

                val hypixelLevel = Wally.decimalFormat.format(1.0 + -8750.0 / 2500.0 + sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (APIUtils.getValuesFromAPI(data, "player.networkExp", "0").toBigDecimal().toInt())))
                val ap = Wally.numberFormat.format(APIUtils.getValuesFromAPI(data, "player.achievementPoints", "0").toInt())

                embed.addField("Hypixel level", "`$hypixelLevel`",true)
                embed.addField("Achievement points", "`$ap`",true)
                embed.addBlankField(true)
            }

            embed.addField("", "[Hypixel](https://hypixel.net/player/$uuid)", true)
            embed.addField("", "[Plancke](https://plancke.io/hypixel/player/stats/$uuid)", true)
            embed.addField("", "[NameMC](https://namemc.com/profile/$uuid)", true)
            embed.setFooter(uuid)

            channel.sendMessageEmbeds(embed.build()).queue()
        }

    }
}


