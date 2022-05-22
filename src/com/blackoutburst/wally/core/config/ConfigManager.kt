package com.blackoutburst.wally.core.config

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.FileReader
import java.io.File
import kotlin.system.exitProcess

object ConfigManager {
    @JvmField
    val configurationFile: File

    /**
     * Create a new configuration manager
     * Try to load the config.json file if missing
     * an error message will be displayed and the program will abort
     */
    init {
        val configFile = File("config/config.json")
        if (!configFile.exists()) {
            Wally.logger.log(LogLevel.ERROR, "The configuration file [config/config.json] is missing!")
            exitProcess(0)
        }
        configurationFile = configFile
    }

    /**
     * Load a value from the config.json
     *
     * @param key the key name
     * @param value the object name
     * @return the object value as String
     */
    fun loadValue(key: String, value: String): String {
        val gson = Gson()
        val json = gson.fromJson(FileReader(configurationFile), JsonObject::class.java)

        return json.getAsJsonObject(key)[value].asString
    }

    /**
     * Load a value from the config.json
     *
     * @param key the key name
     * @param value the object name
     * @return the object value as String
     */
    fun loadMessage(value: String): String {
        val gson = Gson()
        val json = gson.fromJson(FileReader(configurationFile), JsonObject::class.java)

        return json.getAsJsonObject("replies")[value].asString
    }
}