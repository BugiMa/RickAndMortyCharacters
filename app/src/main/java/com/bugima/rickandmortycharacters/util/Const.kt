package com.bugima.rickandmortycharacters.util

object Const {
    // Database const
    const val DATABASE_NAME = "rick_and_morty_database"
    const val FAVORITE_CHARACTERS_TABLE = "favorite_characters"

    // Screens titles
    const val SCREEN_ALL = "Rick & Morty Characters"
    const val SCREEN_FAV = "Favorite Characters"

    // Initial pages for api call
    const val INITIAL_PAGE = 1
    const val INITIAL_NEXT_PAGE = 2

    // Info
    const val NO_FAVORITES_TITLE = "No Favorites"
    const val NO_FAVORITES_DESCRIPTION = "Looks like you did not add any favorites yet. Click button in the left corner to toggle to all characters list and add some from there."

    // Errors
    const val DATABASE_ERROR_TITLE = "Database Error"
    const val DATABASE_ERROR_DESC = "There seems to be an issue with the storage. Try again or clear app data."
    const val DATABASE_ERROR_LOG = "Database error occurred: %s. " +
            "SQL State: %s, " +
            "Error Code: %d. " +
            "Stack Trace: %s"

    const val NETWORK_ERROR_TITLE = "Network Error"
    const val NETWORK_ERROR_DESC = "We are having trouble connecting to the server. Please check your internet connection or try again later."
    const val NETWORK_ERROR_LOG = "Network error occurred: %s. " +
            "Cause: %s. " +
            "Please check your network connection or server status. " +
            "Stack Trace: %s"

    const val SERVER_ERROR_TITLE = "Server Error"
    const val SERVER_ERROR_DESC = "An issue occurred on the server side. Please try again later."
    const val SERVER_ERROR_LOG = "HTTP error occurred: " +
            "Code %d, " +
            "Message: %s, " +
            "URL: %s. " +
            "Error Body: %s. " +
            "Stack Trace: %s"

    const val UNKNOWN_ERROR_TITLE = "Unknown Error"
    const val UNKNOWN_ERROR_DESC = "Something unexpected happened, too bad..."
    const val UNKNOWN_ERROR_LOG = "An unexpected error occurred: %s. " +
            "Cause: %s. " +
            "Stack Trace: %s"

    // Animation
    const val SPIN_DEGREE = 1080f
    const val SPIN_DURATION = 3000
}
