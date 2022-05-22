package com.blackoutburst.wally.core.request

import java.io.BufferedReader
import java.io.InputStreamReader
import com.blackoutburst.wally.core.Wally
import okio.IOException
import java.lang.StringBuilder
import java.net.URL

object ConnectionManager {

    fun readFromURL( urlString: String?): String? {
        return try {
            val url = URL(urlString)
            val connection = url.openConnection()
            val stream = connection.getInputStream()
            val reader = BufferedReader(InputStreamReader(stream))
            val builder = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) builder.append(line)
                .append(System.getProperty("line.separator"))

            stream.close()
            reader.close()
            builder.toString()
        } catch (e: IOException) {
            Wally.logger.logStackTrace(e.stackTrace)
            null
        }
    }
}