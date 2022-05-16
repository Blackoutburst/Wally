package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.DisplayPreference;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class DatabaseUtils {

    public enum DataType {
        HYPIXEL("hypixel"),
        PRACTICE("practice");

        private final String name;

        DataType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Change someone display preference
     *
     * @param channel the message channel
     * @param discordID the user discord id
     * @param preference the new display preference
     */
    public static void updateUserDisplayPreference(@Nonnull MessageChannel channel, @Nonnull String discordID, @Nonnull DisplayPreference preference) {
        final String file = "database/linked users/" + discordID + "/data.json";
        final UserOption data = DatabaseUtils.getWallyUserData(discordID);
        if (data == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not linked or corrupted")).queue();
            return;
        }
        data.setDisplayPreference(preference);
        DatabaseUtils.generateWallyProfile(data, file);
    }

    /**
     * Get local data if api request fails
     * @param uuid the player uuid
     * @param type the data type
     * @param channel the message channel
     * @return the local data
     */
    public static String getBackupLocalData(@Nonnull String uuid, @Nonnull DataType type, @Nonnull MessageChannel channel) {
        String data = null;

        try {
            final WallyUser wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid);
            final Path filePath = Paths.get("database/linked users/" + wallyUser.getMember().getId() + "/accounts/" + uuid + "/" + type.getName() + ".json");

            data = new String(Files.readAllBytes(filePath));
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "local stats")).queue();
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            try {
                final Path filePath = Paths.get("database/leaderboards/" + type.getName()+ "/" + uuid + "/data.json");

                data = new String(Files.readAllBytes(filePath));
                channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "local stats")).queue();
            } catch (Exception e2) {
                Wally.getInstance().logger.logStackTrace(e2.getStackTrace());
            }
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
     * Update a player stats
     *
     * @param data the api data
     * @param type the stats type
     * @param discordID the user discord id
     * @param uuid the player uuid
     */
    public static void updateLinkedPlayerStats(@Nonnull String data, @Nonnull DataType type, @Nonnull String discordID, @Nonnull String uuid) {
        if (DatabaseUtils.isLinked(discordID)) {
            final String rootFolderPath = "database/linked users/" + discordID;
            final String playerFolderPath = rootFolderPath + "/accounts/" + uuid;
            DatabaseUtils.generateStats(data, playerFolderPath + "/" + type.getName() + ".json");
        }
    }

    /**
     * Update a player stats
     *
     * @param data the api data
     * @param type the stats type
     * @param uuid the player uuid
     */
    public static void updateLeaderboardStats(@Nonnull String data, @Nonnull DataType type, @Nonnull String uuid) {
        if (!FilesUtils.isExcluded(uuid)) {
            final String rootFolderPath = "database/leaderboards/" + type.getName();
            final String playerFolderPath = rootFolderPath + "/" + uuid;
            final File playerFolder = new File(playerFolderPath);

            if (!playerFolder.exists() && !playerFolder.mkdirs()) {
                Wally.getInstance().logger.log(LogLevel.WARN, "Player leaderboard folder creation failed");
            }
            DatabaseUtils.generateStats(data, playerFolderPath + "/data.json");
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
        return (getWallyUserFromUUID(uuid) != null);
    }

    /**
     * Check if a player is linked
     * @param uuid the user discord id
     * @return if the player is linked
     */
    public static WallyUser getWallyUserFromUUID(@Nonnull String uuid) {
        final Optional<WallyUser> wallyUser = Wally.getInstance().users.stream().filter(user -> {
            final File folder = new File("database/linked users/" + user.getMember().getId() + "/accounts/");
            final String[] entries = folder.list();
            if (entries == null) return false;
            return Arrays.asList(entries).contains(uuid);
        }).findFirst();

        return (wallyUser.orElse(null));
    }
}
