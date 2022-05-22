package com.blackoutburst.wally.core

import com.blackoutburst.wally.main.Main.Companion.DISCORD_TOKEN
import net.dv8tion.jda.api.JDA
import java.util.HashSet
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import com.blackoutburst.wally.core.logger.LogLevel
import com.google.gson.Gson
import java.io.FileReader
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.requests.GatewayIntent
import com.blackoutburst.wally.core.events.EventListener
import com.blackoutburst.wally.core.logger.Logger
import com.google.gson.JsonSyntaxException
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.utils.Compression
import java.io.File

object Wally {

    var jda: JDA? = null
    val users: MutableSet<WallyUser> = HashSet()
    val logger = Logger
    var isReady = false
    val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH) as DecimalFormat
    val decimalFormat = DecimalFormat("#.##")
    var hitwDiscord: Guild? = null

    fun loadUser() {
        users.clear()

        val rootFolderPath = "database/linked users/"
        val rootFolder = File(rootFolderPath)
        if (!rootFolder.exists()) {
            logger.log(LogLevel.WARN, "Database doesn't exist skipping user loading")
            return
        }

        val entries = rootFolder.list()
        if (entries == null) {
            logger.log(LogLevel.WARN, "Database doesn't exist skipping user loading")
            return
        }

        val gson = Gson()
        for (s in entries) {
            val userFolder = File(rootFolder.path, s)
            var data: UserOption?
            try {
                val reader = FileReader(userFolder.path + "/data.json")
                data = gson.fromJson(reader, UserOption::class.java)
                reader.close()
            } catch (e: JsonSyntaxException) {
                data = null
            }
            if (data == null) continue
            val guild = jda!!.getGuildById(data.serverID) ?: continue
            val member = guild.getMemberById(userFolder.name) ?: continue
            users.add(WallyUser(member, data))
        }
        logger.log(LogLevel.INFO, "Loaded " + users.size + " users out of " + entries.size)
    }

    init {
        val builder = JDABuilder.createDefault(DISCORD_TOKEN)
            .setCompression(Compression.NONE)
            .setAutoReconnect(true)
            .setActivity(Activity.competing("Hole in the Wall"))
            .setChunkingFilter(ChunkingFilter.ALL)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .enableIntents(GatewayIntent.GUILD_MEMBERS)
            .addEventListeners(EventListener())
            .enableIntents(GatewayIntent.GUILD_PRESENCES)
        builder.build()
        logger.log(LogLevel.INFO, "Wally initialized successfully")
    }
}