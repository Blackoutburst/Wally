package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class APIUtils {

    /**
     * Return the API value of the specified data<br>
     *
     * Usage <br><br>
     *
     * <code>
     * String value = getValuesFromAPI(apiData, "player.stats.Arcade.hitw_record_q")
     * </code>
     *
     * @param apiData the apiData as JSON string
     * @param path the value path in the JSON
     * @param defaultValue is a value used if the object returned is null
     * @return the value if found
     */
    public static @Nonnull String getValuesFromAPI(@Nonnull String apiData, @Nonnull String path, @Nonnull String defaultValue) {
        try {
            final Gson gson = new Gson();
            final String[] splitPath = path.split("\\.");
            final JsonObject rootObject = gson.fromJson(apiData, JsonObject.class);
            final List<JsonObject> objects = new ArrayList<>();
            objects.add(rootObject);

            for (int i = 0; i < splitPath.length - 1; i++)
                objects.add(objects.get(i).getAsJsonObject(splitPath[i]));

            final JsonElement element = objects.get(objects.size() - 1).get(splitPath[splitPath.length - 1]);
            if (element == null)
                return (defaultValue);

            final String result = element.getAsString();

            return (result == null ? defaultValue : result);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return (defaultValue);
        }
    }
}
