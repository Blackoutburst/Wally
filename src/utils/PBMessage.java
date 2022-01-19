package utils;

import core.Bot;
import core.RolesManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Objects;

public class PBMessage {

    private static void react(boolean weTakeThose, EmbedBuilder embed) {
        if (weTakeThose) {
            Objects.requireNonNull(Bot.server.getTextChannelById(ConfigManager.getString("trackerChannel"))).sendMessage(embed.build()).queue(response ->
                response.addReaction("weTakeThose:839985386012147783").queue());
        } else {
            Objects.requireNonNull(Bot.server.getTextChannelById(ConfigManager.getString("trackerChannel"))).sendMessage(embed.build()).complete();
        }
    }

    private static void fillEmbed(EmbedBuilder embed, int newScore, int oldScore, String name, String discordid, String gameType) {
        if (oldScore > 0) {
            embed.setTitle(name+" Improved " + GeneralUtils.getGenderPrefix(discordid) + " **"+gameType+"** Personal Best!");
            embed.setColor(RolesManager.getRoleColor(newScore));
            embed.addField("Old PB", "**" + oldScore + "**",true);
            embed.addField("New PB", "**" + newScore + "**",true);
            embed.addField("Increase","**" + (newScore - oldScore) + "**",true);
        } else {
            embed.setTitle(name+" completed their first game of **"+gameType+"**!");
            embed.setColor(RolesManager.getRoleColor(newScore));
            embed.addField("They got", "**" + newScore + "** points!",true);
        }
    }

    public static void pbMessage(String data, String discordid, String uuid, char type) {
        final EmbedBuilder embed = new EmbedBuilder();
        final String localData = GeneralUtils.readJsonToString("linked player/" + uuid + "/data.json");

        // Add a unique part to the request url so discord cannot cache it and the skin stays up to date.
        // By: EatMyVenom
        final long time = System.currentTimeMillis();
        final String url = "https://crafatar.com/renders/body/" + uuid + "?overlay&size=512&time=" + time;
        final String avatarUrl = Objects.requireNonNull(Bot.server.getMemberById(discordid)).getUser().getAvatarUrl();
        final String name = Objects.requireNonNull(Bot.server.getMemberById(discordid)).getEffectiveName();
        final String playerName = API.getName(data);
        final int oldQ = Stats.getQualificationToInt(localData);
        final int newQ = API.getQualificationToInt(data);
        final int oldF = Stats.getFinalsToInt(localData);
        final int newF = API.getFinalsToInt(data);

        boolean weTakeThose = false;

        embed.setAuthor(name + " | " + API.getName(data), url, avatarUrl);
        embed.setThumbnail(url);
        embed.setFooter("Discord ID: " + discordid + "\nUUID: " + uuid);

        switch(type) {
            case 'q':
                if (newQ - oldQ <= 0) return;
                if (newQ - oldQ == 1) weTakeThose = true;
                fillEmbed(embed, newQ, oldQ, playerName, discordid, "Qualifiers");
            break;
            case 'f':
                if (newF - oldF <= 0) return;
                if (newF - oldF == 1) weTakeThose = true;
                fillEmbed(embed, oldF, oldF, playerName, discordid, "Finals");
            break;
            default: break;
        }

        new RolesManager().addClubRole(Bot.server, Bot.server.getMemberById(discordid), newQ, newF, null);
        react(weTakeThose, embed);
    }

    public static void practicePbMessage(String data, String discordid, String uuid, String type) {
        final EmbedBuilder embed = new EmbedBuilder();
        final String localData = GeneralUtils.readJsonToString("pleaderboard/" + uuid + "/data.json");

        // Add a unique part to the request url so discord cannot cache it and the skin stays up to date.
        // By: EatMyVenom
        final long time = System.currentTimeMillis();
        final String url = "https://crafatar.com/renders/body/" + uuid + "?overlay&size=512&time=" + time;
        final String avatarUrl = Objects.requireNonNull(Bot.server.getMemberById(discordid)).getUser().getAvatarUrl();
        final String name = Objects.requireNonNull(Bot.server.getMemberById(discordid)).getEffectiveName();
        final String playerName = API.getName(data);
        final int oldQ = PracticeStats.getQualificationToInt(localData);
        final int newQ = PracticeAPI.getQualificationToInt(data);
        final int oldF = PracticeStats.getFinalsToInt(localData);
        final int newF = PracticeAPI.getFinalsToInt(data);
        final int oldL = PracticeStats.getLobbyToInt(localData);
        final int newL = PracticeAPI.getLobbyToInt(data);
        final int oldWQ = PracticeStats.getWideQualificationToInt(localData);
        final int newWQ = PracticeAPI.getWideQualificationToInt(data);
        final int oldWF = PracticeStats.getWideFinalsToInt(localData);
        final int newWF = PracticeAPI.getWideFinalsToInt(data);

        boolean weTakeThose = false;

        embed.setAuthor(name + " | " + PracticeAPI.getName(data), url, avatarUrl);
        embed.setThumbnail(url);
        embed.setFooter("Discord ID: " + discordid + "\nUUID: " + uuid);

        switch(type) {
            case "q":
                if (newQ - oldQ <= 0) return;
                if (newQ - oldQ == 1) weTakeThose = true;
                fillEmbed(embed, newQ, oldQ, playerName, discordid, "Qualifiers");
            break;
            case "f":
                if (newF - oldF <= 0) return;
                if (newF - oldF == 1) weTakeThose = true;
                fillEmbed(embed, newF, oldF, playerName, discordid, "Finals");
            break;
            case "l":
                if (newL - oldL <= 0) return;
                if (newL - oldL == 1) weTakeThose = true;
                fillEmbed(embed, newL, oldL, playerName, discordid, "Lobby");
            break;
            case "wq":
                if (newWQ - oldWQ <= 0) return;
                if (newWQ - oldWQ == 1) weTakeThose = true;
                fillEmbed(embed, newWQ, oldWQ, playerName, discordid, "Wide Qualifiers");
            break;
            case "wf":
                if (newWF - oldWF <= 0) return;
                if (newWF - oldWF == 1) weTakeThose = true;
                fillEmbed(embed, newWF, oldWF, playerName, discordid, "Wide Finals");
            break;
        }

        react(weTakeThose, embed);
    }
}
