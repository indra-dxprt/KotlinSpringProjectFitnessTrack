package com.fitness.fitnesstracker.util

enum class RoleEnum(val description: String) {
    Admin("Admin"), User("User"), Super_Admin("Super Admin");

    companion object {
        // Reverse-lookup map for getting a day from an abbreviation
        private val lookup: MutableMap<String, RoleEnum> = HashMap()
        operator fun get(description: String): RoleEnum? {
            return lookup[description]
        }
    }
}