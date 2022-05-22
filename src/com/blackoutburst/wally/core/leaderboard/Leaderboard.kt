package com.blackoutburst.wally.core.leaderboard

import com.blackoutburst.wally.core.statsobject.StatsObject
import java.util.ArrayList
import java.util.Comparator

class Leaderboard(val name: String, val comparator: Comparator<StatsObject>, val type: Type) {

    enum class Type {
        WINS,
        WALLS,
        QUALIFICATION,
        FINALS,
        TOTAL,
        TOTAL_API,
        CHALLENGES,
        OVERALL,
        LOBBY,
        WIDE_QUALIFICATION,
        WIDE_FINALS
    }

    val players: MutableList<StatsObject> = ArrayList()
}