package com.blackoutburst.wally.core.config

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.FileReader
import java.io.File
import kotlin.system.exitProcess

object ConfigManager {

    val configurationFile: File

    init {
        val configFile = File("config/config.json")
        if (!configFile.exists()) {
            Wally.logger.log(LogLevel.ERROR, "The configuration file [config/config.json] is missing!")
            exitProcess(0)
        }
        configurationFile = configFile
    }

    fun loadValue(key: String, value: String): String {
        val fileReader = FileReader(configurationFile)
        val gson = Gson()
        val json = gson.fromJson(fileReader, JsonObject::class.java)

        fileReader.close()
        return json.getAsJsonObject(key)[value].asString
    }

    fun loadMessage(value: String): String {
        val fileReader = FileReader(configurationFile)
        val gson = Gson()
        val json = gson.fromJson(fileReader, JsonObject::class.java)

        fileReader.close()
        return json.getAsJsonObject("replies")[value].asString
    }
}