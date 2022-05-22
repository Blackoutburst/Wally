package com.blackoutburst.wally.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import java.util.ArrayList

object APIUtils {

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
        } catch (e: JsonSyntaxException) {
            defaultValue
        }
    }
}