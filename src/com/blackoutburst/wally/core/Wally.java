package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.events.EventListener;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.logger.Logger;
import com.blackoutburst.wally.main.Main;
import com.google.gson.Gson;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;


public class Wally {

    public final Set<WallyUser> users = new HashSet<>();

    public final Logger logger = Logger.getInstance();

    public static Guild hitwDiscord;

    private static Wally instance = null;

    /**
     * Get the Wally instance
     * @return the Wally instance
     */
    public static @Nonnull Wally getInstance() {
        if (instance == null) {
            try {
                instance = new Wally(Main.DISCORD_TOKEN);
            } catch (LoginException e) {
                e.printStackTrace();
                System.exit(84);
            }
        }
        return (instance);
    }

    /**
     * Load wally user list from database
     * @param jda The jda object used to retrieve guild and members
     */
    public void loadUser(@Nonnull JDA jda) {
        users.clear();

        final String rootFolderPath = "database/linked users/";
        final File rootFolder = new File(rootFolderPath);
        if (!rootFolder.exists()) {
            logger.log(LogLevel.WARN, "Database doesn't exist skipping user loading");
            return;
        }

        final String[] entries = rootFolder.list();
        if (entries == null) {
            logger.log(LogLevel.WARN, "Database doesn't exist skipping user loading");
            return;
        }

        final Gson gson = new Gson();

        for (final String s : entries) {
            final File userFolder = new File(rootFolder.getPath(), s);

            UserOption data;
            try {
                final FileReader reader = new FileReader(userFolder.getPath()+"/data.json");
                data = gson.fromJson(reader, UserOption.class);
                reader.close();
            } catch (Exception e) { data = null; }
            if (data == null) continue;

            final Guild guild = jda.getGuildById(data.getServerID());
            if (guild == null) continue;

            final Member member = guild.getMemberById(userFolder.getName());
            if (member == null) continue;

            users.add(new WallyUser(member,data));
        }
        logger.log(LogLevel.INFO, "Loaded "+users.size()+" users out of "+entries.length);
    }

    /**
     * Initialize JDA and log the bot on discord
     * @param token the discord bot token
     * @throws LoginException this will happen if the bot cannot log
     */
    private Wally(@Nonnull final String token) throws LoginException {
        final JDABuilder builder = JDABuilder.createDefault(token)
                .setCompression(Compression.NONE)
                .setAutoReconnect(true)
                .setActivity(Activity.competing("Hole in the Wall"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new EventListener())
                .enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.build();

        logger.log(LogLevel.INFO,"Wally initialized successfully");
    }
}
