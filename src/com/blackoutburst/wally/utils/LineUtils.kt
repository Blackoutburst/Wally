package com.blackoutburst.wally.utils

import com.blackoutburst.wally.utils.Utils.formatLine
import com.blackoutburst.wally.utils.Utils.formatScoreCompare
import com.blackoutburst.wally.utils.Utils.formatLineReverse

object LineUtils {

    fun comparisonLine(label :String, player1Score :Number, player1Position :Int, player2Score :Number, player2Position :Int) :String {
        return String.format("%-20s %-16s %-20s %16s\n",
            "$label:",
            formatLine(player1Score, player1Position),
            formatScoreCompare(player1Score, player2Score),
            formatLineReverse(player2Score, player2Position))
    }
}