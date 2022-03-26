package com.blackoutburst.wally.core;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Wally {

    private static boolean isInitialized = false;

    public static void init(final String token) throws LoginException {
        if (isInitialized) return;

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

        isInitialized = true;
    }
}
