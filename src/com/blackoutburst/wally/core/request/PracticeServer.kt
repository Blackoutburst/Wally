package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.main.Main
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.StandardCharsets

object PracticeServer {

    fun enableWhitelist() : String? {
        return try {
            Wally.logger.log(LogLevel.INFO, "Enabling server whitelist")
            ConnectionManager.readFromURL("http://178.32.125.169:30100/whitelistenable?token=${Main.PRACTICE_TOKEN}")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun disableWhitelist() : String? {
        return try {
            Wally.logger.log(LogLevel.INFO, "Disabling server whitelist")
            ConnectionManager.readFromURL("http://178.32.125.169:30100/whitelistdisable?token=${Main.PRACTICE_TOKEN}")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun whitelistAdd( name: String) : String? {
        return try {
            Wally.logger.log(LogLevel.INFO, "Adding [$name] to the whitelist")
            ConnectionManager.readFromURL("http://178.32.125.169:30100/whitelistadd?token=${Main.PRACTICE_TOKEN}&name=$name")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun whitelistRemove( name: String) : String? {
        return try {
            Wally.logger.log(LogLevel.INFO, "Adding [$name] to the whitelist")
            ConnectionManager.readFromURL("http://178.32.125.169:30100/whitelistremove?token=${Main.PRACTICE_TOKEN}&name=$name")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getPlayerStats( uuid: String, silent :Boolean = false): String? {
        return try {
            if (!silent)
                Wally.logger.log(LogLevel.INFO, "New Practice server request sent for [$uuid]")
            val res = ConnectionManager.readFromURL("http://178.32.125.169:30100/user?uuid=$uuid")
            if (res?.get(0) != '{')
                throw Exception("Invalid json")
            res
        } catch (e: Exception) {
            null
        }
    }

    fun getPlayerCredits( uuid: String, silent :Boolean = false): String? {
        return try {
            if (!silent)
                Wally.logger.log(LogLevel.INFO, "New Practice server request sent for [$uuid]")
            val res = ConnectionManager.readFromURL("http://beta.hitw.community:30150/credits?uuid=$uuid")
            if (res?.get(0) != '{')
                throw Exception("Invalid json")
            res
        } catch (e: Exception) {
            null
        }
    }
}