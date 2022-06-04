package com.blackoutburst.wally.utils

import com.blackoutburst.wally.utils.Utils.formatLine
import com.blackoutburst.wally.utils.Utils.formatScoreCompare
import com.blackoutburst.wally.utils.Utils.formatLineReverse

object LineUtils {

    fun comparisonLine(label :String, player1Score :Number, player1Position :Int, player2Score :Number, player2Position :Int) :String {
        return "$label: " +
                "${formatLine(player1Score, player1Position)} \t " +
                "${formatScoreCompare(player1Score, player1Position, player2Score, player2Position)} \t " +
                "${formatLineReverse(player2Score, player2Position)} :$label${System.lineSeparator()}"
    }
}