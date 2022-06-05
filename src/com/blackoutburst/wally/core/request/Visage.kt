package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.utils.DatabaseUtils
import java.io.*
import java.net.URL
import java.net.URLConnection


object Visage {

    fun getPlayerHead(uuid :String) {
        try {
            val url = URL("https://visage.surgeplay.com/face/128/$uuid")
            val con: URLConnection = url.openConnection()
            val `is`: InputStream = con.getInputStream()
            val br = BufferedReader(InputStreamReader(`is`))
            val `in`: InputStream = BufferedInputStream(url.openStream())
            val out = ByteArrayOutputStream()
            val buf = ByteArray(1024)
            var n: Int

            while (-1 != `in`.read(buf).also { n = it }) {
                out.write(buf, 0, n)
            }

            out.close()
            `in`.close()
            br.close()

            val response = out.toByteArray()

            if (DatabaseUtils.isLinkedUUID(uuid)) {
                val user = DatabaseUtils.getWallyUserFromUUID(uuid)
                val fos = FileOutputStream("database/linked users/${user!!.member.id}/accounts/${uuid}/head.png")
                fos.write(response)
                fos.close()
            }

            if (File("database/leaderboards/hypixel/${uuid}").exists()) {
                val fos = FileOutputStream("database/leaderboards/hypixel/${uuid}/head.png")
                fos.write(response)
                fos.close()
            }

            if (File("database/leaderboards/practice/${uuid}").exists()) {
                val fos = FileOutputStream("database/leaderboards/practice/${uuid}/head.png")
                fos.write(response)
                fos.close()
            }
            Wally.logger.log(LogLevel.INFO, "Visage request sent for [$uuid]")
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
        }
    }
}