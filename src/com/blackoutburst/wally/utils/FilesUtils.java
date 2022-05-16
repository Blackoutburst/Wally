package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class FilesUtils {

    /**
     * Check if a player is excluded from leaderboard
     * @param uuid the player uuid
     * @return if the player is excluded
     */
    public static boolean isExcluded(@Nonnull String uuid) {
        try {
        final File file = new File("config/excluded.txt");

        return Files.lines(file.toPath()).anyMatch(line -> line.contains(uuid));
        } catch (IOException e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return false;
    }

    /**
     * Remove the leaderboard exclusion by removing the player uuid from the exclude file
     *
     * @param uuid the player uuid that must be removed from the file
     */
    public static void removeExclusion(@Nonnull String uuid) {
        try {
            final File file = new File("config/excluded.txt");
            final List<String> out = Files.lines(file.toPath())
                    .filter(line -> !line.contains(uuid))
                    .collect(Collectors.toList());

            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
    }

    /**
     * Exclude certain account from the leaderboard by writing their
     * uuid into a special file
     *
     * Automatically create the file if it doesn't exist
     * @param uuid the player uuid that must be added to the file
     */
    public static void excludeFromLb(@Nonnull String uuid) {
        try {
            Files.write(Paths.get("config/excluded.txt"), (uuid+"\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
    }

}
