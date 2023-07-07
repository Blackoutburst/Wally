package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.sun.management.UnixOperatingSystemMXBean
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.lang.management.ManagementFactory
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class CommandPing : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val time = System.currentTimeMillis()
        val rt = Runtime.getRuntime()
        val memTotal = rt.totalMemory()
        val memMax = rt.maxMemory()
        val memLeft = rt.freeMemory()
        val nf = NumberFormat.getNumberInstance(Locale.ENGLISH)
        val formatter = nf as DecimalFormat
        val memUsed = memTotal - memLeft
        val mbTotal = formatter.format(memTotal / 1048576)
        val mbMax = formatter.format(memMax / 1048576)
        val mbUsed = formatter.format(memUsed / 1048576)
        val mxBean = ManagementFactory.getRuntimeMXBean()
        val uptime = formatter.format(mxBean.uptime / 60000)
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        val filesOpen = (ManagementFactory.getOperatingSystemMXBean() as UnixOperatingSystemMXBean).openFileDescriptorCount

        var threads = "**Threads:**\n"
        threads += "```diff\n"
        for (thread in Wally.threadSet!!) {
            threads += if (thread.isAlive) { "+ " } else { "- " } + thread.name + "\n"
        }

        threads += "\n```"

        val tmp: MessageEmbed = EmbedBuilder()
            .setAuthor("Wally status", wallyURL, iconURL)
            .setColor(Color(71, 165, 255))
            .setDescription("Pong!").build()
        val embed: MessageEmbed = EmbedBuilder()
            .setAuthor("Wally status", wallyURL, iconURL)
            .setColor(Color(71, 165, 255))
            .setDescription(threads)

            .addField("Used memory", "`$mbUsed`MiB", true)
            .addField("Total memory", "`$mbTotal`MiB", true)
            .addField("Max memory", "`$mbMax`MiB", true)

            .addField("Ping", "`" + (System.currentTimeMillis() - time) + "`ms", true)
            .addField("Uptime", "`$uptime` minutes", true)
            .addField("Files", "`$filesOpen`", true)

            .addField("Version", "`2.0.8`", true)

            .addField("", "[GitHub](https://github.com/Blackoutburst/Wally)", true)
            .setFooter("Made with ❤️ by Blackoutburst", "https://www.blackoutburst.com/img/pfp.jpg")
            .build()

        channel.sendMessageEmbeds(tmp).queue { response ->
            response.editMessageEmbeds(embed).queue()
        }
    }
}


