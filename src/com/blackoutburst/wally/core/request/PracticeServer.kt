package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.main.Main.Companion.PRACTICE_SERVER_IP
import com.blackoutburst.wally.main.Main.Companion.PRACTICE_SERVER_PORT
import java.net.Socket
import java.io.InputStreamReader
import java.io.BufferedReader
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import java.io.OutputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets

object PracticeServer {

    /**
     * Get a player score from the Practice server
     *
     * @param uuid the player uuid
     * @return the player stats as string (JSON)
     */
    fun getPlayerStats( uuid: String): String? {
        return try {
            val socket = Socket(PRACTICE_SERVER_IP, PRACTICE_SERVER_PORT.toInt())
            val inputStream = InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            val reader = BufferedReader(inputStream)
            val outputStream: OutputStream = socket.getOutputStream()
            outputStream.write("$uuid\n".toByteArray(StandardCharsets.UTF_8))

            val dataReceived = reader.readLine()
            inputStream.close()
            outputStream.close()
            reader.close()
            Wally.logger.log(LogLevel.INFO, "Practice server request sent for [$uuid]")
            dataReceived
        } catch (e: Exception) {
            e.printStackTrace()
            Wally.logger.logStackTrace(e.stackTrace)
            null
        }
    }
}