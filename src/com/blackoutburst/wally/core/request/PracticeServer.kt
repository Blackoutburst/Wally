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

    fun getPlayerStats( uuid: String, silent :Boolean = false): String? {
        return try {
            if (!silent)
                Wally.logger.log(LogLevel.INFO, "New Practice server request sent for [$uuid]")
            val res = ConnectionManager.readFromURL("http://178.32.125.169:30100/user?uuid=$uuid")
            if (res?.get(0) != '{')
                throw Exception("Invalid json")
            res
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            null
        }
    }
}