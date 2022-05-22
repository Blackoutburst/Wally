package com.blackoutburst.wally.core.logger

enum class LogLevel(val weight: Int) {
    INFO(0),
    WARN(1),
    ERROR(2),
    NONE(3);
}