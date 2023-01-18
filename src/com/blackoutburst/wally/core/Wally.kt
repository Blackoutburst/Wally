package com.blackoutburst.wally.core

import com.blackoutburst.wally.core.events.EventListener
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.logger.Logger
import com.blackoutburst.wally.main.Main
import com.google.gson.Gson
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.Compression
import net.dv8tion.jda.api.utils.MemberCachePolicy
import java.io.File
import java.io.FileReader
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object Wally {

    var jda: JDA? = null
    val users: MutableList<WallyUser> = ArrayList()
    val logger = Logger
    var isReady = false
    val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH) as DecimalFormat
    val decimalFormat = NumberFormat.getNumberInstance(Locale.ENGLISH) as DecimalFormat
    var hitwDiscord: Guild? = null
    var devServer: Guild? = null
    var threadSet: Set<Thread>? = null

    fun setReady() {
        isReady = true
        jda!!.presence.setStatus(OnlineStatus.ONLINE)
    }

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
            } catch (e: Exception) {
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
        val builder = JDABuilder.createDefault(Main.DISCORD_TOKEN)
            .setCompression(Compression.NONE)
            .setAutoReconnect(true)
            .setStatus(OnlineStatus.IDLE)
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