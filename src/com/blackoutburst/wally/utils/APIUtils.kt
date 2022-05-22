package com.blackoutburst.wally.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.Exception
import java.util.ArrayList

object APIUtils {

    /**
     * Return the API value of the specified data<br></br>
     *
     * Usage:
     *
     * val value = getValuesFromAPI(apiData, "player.stats.Arcade.hitw_record_q")
     *
     * @param apiData the apiData as JSON string
     * @param path the value path in the JSON
     * @param defaultValue is a value used if the object returned is null
     * @return the value if found
     */
    fun getValuesFromAPI(apiData: String, path: String, defaultValue: String): String {
        return try {
            val gson = Gson()
            val splitPath = path.split(".").toTypedArray()
            val rootObject = gson.fromJson(apiData, JsonObject::class.java)
            val objects: MutableList<JsonObject> = ArrayList()
            objects.add(rootObject)

            for (i in 0 until splitPath.size - 1) objects.add(objects[i].getAsJsonObject(splitPath[i]))

            val element = objects[objects.size - 1][splitPath[splitPath.size - 1]] ?: return defaultValue
            val result = element.asString

            result ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }
}