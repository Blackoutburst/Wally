package com.blackoutburst.wally.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel


object APIUtils {

    fun getMessageFromHistory(messageChannel: MessageChannel, messageID: String, historyCount: Int): Message? {
        val history = messageChannel.history.retrievePast(historyCount).complete()
        val message = history.stream().filter { msg: Message? -> msg!!.id == messageID }
            .findFirst()
        return message.orElse(null)
    }

    fun getValuesFromAPI(apiData: String, path: String, defaultValue: String): String {
        return try {
            val gson = Gson()
            val splitPath = path.split(".").toTypedArray()
            val rootObject = gson.fromJson(apiData, JsonObject::class.java)
            val objects: MutableList<JsonObject> = ArrayList()
            objects.add(rootObject)

            for (i in 0 until splitPath.size - 1) objects.add(objects[i].getAsJsonObject(splitPath[i]))

            val element = objects[objects.size - 1][splitPath[splitPath.size - 1]] ?: return defaultValue
            val result = try {element.asString} catch (ignored : Exception) {element.toString()}

            result ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }
}