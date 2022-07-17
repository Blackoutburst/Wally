package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.google.gson.Gson
import com.google.gson.JsonObject

object Mojang {

    fun getPlayerUUID(playerName: String): String? {
        return try {
            val result = ConnectionManager.readFromURL("https://api.mojang.com/users/profiles/minecraft/$playerName")
            val gson = Gson()
            val json = gson.fromJson(result, JsonObject::class.java)

            Wally.logger.log(LogLevel.INFO, "New Mojang request sent for [$playerName]")
            json["id"].asString
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            null
        }
    }

    fun getPlayerName(uuid: String): String? {
        return try {
            val result = ConnectionManager.readFromURL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid")
            val gson = Gson()
            val json = gson.fromJson(result, JsonObject::class.java)

            Wally.logger.log(LogLevel.INFO, "New Mojang request sent for [$uuid]")
            json["name"].asString
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            null
        }
    }
}