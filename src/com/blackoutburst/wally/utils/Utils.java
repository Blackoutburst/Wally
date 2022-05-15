package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.List;
import java.util.Optional;

public class Utils {

    /**
     * Input as many scores as you want and it will return the highest one
     * @param scores the scores
     * @return the highest score
     */
    public static int getHighestScore(int... scores) {
        int highest = 0;

        for (int score : scores)
            if (score > highest)
                highest = score;

        return highest;
    }

    /**
     * Get a club role color based on score
     * The input number is rounded to the lower 50 div
     * @param score the player score
     * @return the role color
     */
    public static @Nonnull Color getColorFromScore(int score) {
        final int lowRound = (score + 49) / 50 * 50;
        final List<Role> roles = Wally.hitwDiscord.getRoles();
        final Optional<Role> role = roles.stream().filter(r -> r.getName().equals(lowRound + "+ Club")).findFirst();

        return (role.isPresent() && role.get().getColor() != null ? role.get().getColor() : Color.GRAY);
    }

}
