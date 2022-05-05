package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import net.dv8tion.jda.api.EmbedBuilder;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

public class EmbedUtils {

    /**
     * Generate the skin preview in embed
     *
     * @param uuid the player uuid
     * @param playerName the player name
     * @return the skin embed
     */
    public static EmbedBuilder generateSkin(@Nonnull String uuid, @Nonnull String playerName) {
        final long time = System.currentTimeMillis();

        return new EmbedBuilder()
                .setAuthor(playerName, "https://fr.namemc.com/profile/"+uuid, "https://visage.surgeplay.com/face/512/" + uuid + "?&time=" + time)
                .setTitle("NameMC", "https://fr.namemc.com/profile/"+uuid)
                .setColor(new Color(71, 165, 255))
                .setImage("https://visage.surgeplay.com/full/512/" + uuid + "?&time=" + time);
    }

    /**
     * Generate the changelog embed message
     * @return the changelog embed
     */
    public static EmbedBuilder generateChangelog() {
        try {
            final String wallyURL = "https://github.com/Blackoutburst/Wally";
            final String wallyPfp = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
            final BufferedReader reader = new BufferedReader(new FileReader("config/changelog.txt"));
            final StringBuilder changelog = new StringBuilder();
            final String title = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                changelog.append(line).append("\n");
            }
            reader.close();

            return (new EmbedBuilder()
                    .setAuthor(String.valueOf(LocalDate.now()), wallyURL, wallyPfp)
                    .setTitle(title, wallyURL)
                    .addField("Changes", changelog.toString(), false)
                    .addField("", "[GitHub](https://github.com/Blackoutburst/Wally)", true)
                    .addField("", "[Patreon](https://www.patreon.com/Blackoutburst)", true)
                    .setColor(new Color(71, 165, 255))
                    .setFooter("Made with love by Blackoutburst", "https://www.blackoutburst.com/img/pfp.jpg"));
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return (null);
    }
}
