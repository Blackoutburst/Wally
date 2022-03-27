package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.events.EventListener;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.logger.Logger;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
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
                instance = new Wally(ConfigManager.getInstance().loadValue("settings", "discord token"));
            } catch (LoginException e) {
                e.printStackTrace();
                System.exit(84);
            }
        }
        return (instance);
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
