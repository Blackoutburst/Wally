package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseUtils {

    /**
     * Get local data if api request fails
     * @param uuid the player uuid
     * @param type the data type
     * @param channel the message channel
     * @return the local data
     */
    public static String getBackupLocalData(@Nonnull String uuid, @Nonnull String type, @Nonnull MessageChannel channel) {
        String data = null;

        final WallyUser wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid);
        final Path filePath = Paths.get("database/linked users/" + wallyUser.getMember().getId() + "/accounts/" + uuid + "/"+type+".json");
        try {
            data = new String(Files.readAllBytes(filePath));
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "local stats")).queue();
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }

        return data;
    }

    /**
     * Save API data to file
     *
     * @param data the player data data
     * @param file the file used to save them
     * @return if the operation worked
     */
    public static boolean generateStats(@Nonnull String data, @Nonnull String file) {
        try {
            final Writer writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return false;
        }
    }

    /**
     * Write wally user data into a JSON file
     * @param wallyProfile the user data
     * @param file the JSON file
     * @return if the operation worked
     */
    public static boolean generateWallyProfile(@Nonnull UserOption wallyProfile, @Nonnull String file) {
        try {
            final Gson gson = new Gson();

            final Writer writer = new FileWriter(file);

            gson.toJson(wallyProfile, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return false;
        }
    }

    /**
     * Return wally user data using a discord ID
     * @param discordID the user discord ID
     * @return the Wally user if found
     */
    public static UserOption getWallyUserData(@Nonnull String discordID) {
        final Gson gson = new Gson();

        try {
            final FileReader reader = new FileReader("database/linked users/" + discordID + "/data.json");
            final UserOption data = gson.fromJson(reader, UserOption.class);

            reader.close();

            return (data);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return (null);
        }
    }

    /**
     * Check if a player is linked
     * @param discordID the user discord id
     * @return if the player is linked
     */
    public static boolean isLinked(@Nonnull String discordID) {
        final String rootFolderPath = "database/linked users/" + discordID;
        return (new File(rootFolderPath).exists());
    }

    /**
     * Check if a player is linked
     * @param uuid the user discord id
     * @return if the player is linked
     */
    public static boolean isLinkedUUID(@Nonnull String uuid) {
        for (WallyUser w : Wally.getInstance().users) {
            final File folder = new File("database/linked users/" + w.getMember().getId() + "/accounts/");
            final String[] entries = folder.list();

            for (String e : entries) {
                if (e.equals(uuid)) {
                    return (true);
                }
            }
        }
        return false;
    }

    /**
     * Check if a player is linked
     * @param uuid the user discord id
     * @return if the player is linked
     */
    public static WallyUser getWallyUserFromUUID(@Nonnull String uuid) {
        for (WallyUser w : Wally.getInstance().users) {
            final File folder = new File("database/linked users/" + w.getMember().getId() + "/accounts/");
            final String[] entries = folder.list();

            for (String e : entries) {
                if (e.equals(uuid)) {
                    return (w);
                }
            }
        }
        return null;
    }
}
