package com.blackoutburst.wally.core.statsobject

import com.blackoutburst.wally.core.leaderboard.Leaderboard

interface StatsObject {

    val name: String
    val uuid: String
    val finals: Int
    val qualification: Int
    val total: Int
    val overall: Float
    val overallPosition: Int
    fun getDataFromType(type: Leaderboard.Type): Int
    val scores: IntArray
}