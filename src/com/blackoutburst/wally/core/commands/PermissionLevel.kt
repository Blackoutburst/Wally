package com.blackoutburst.wally.core.commands

enum class PermissionLevel(val weight: Int) {
    MEMBER(0),
    STAFF(1),
    ADMIN(2);
}