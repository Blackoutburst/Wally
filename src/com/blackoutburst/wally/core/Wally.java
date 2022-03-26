package com.blackoutburst.wally.core;

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


public class Wally {

    public static final Logger LOG = Logger.getInstance();

    public static Guild hitwDiscord;

    private static boolean isInitialized = false;

    public static void init(@Nonnull final String token) throws LoginException {
        if (isInitialized) return;
        isInitialized = true;

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

        LOG.log(LogLevel.INFO,"Wally initialized successfully");
    }
}
