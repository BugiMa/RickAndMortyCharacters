package com.bugima.rickandmortycharacters.util

import com.bugima.rickandmortycharacters.R

sealed class CustomException(
    open val originalException: Exception,
    open val title: String,
    open val description: String,
    open val iconResourceId: Int,
) : Exception() {

    class DatabaseException(originalException: Exception): CustomException(
        originalException = originalException,
        title = Const.DATABASE_ERROR_TITLE,
        description = Const.DATABASE_ERROR_DESC,
        iconResourceId = R.drawable.icon_database_error
    )

    class NetworkException(originalException: Exception): CustomException(
        originalException = originalException,
        title = Const.NETWORK_ERROR_TITLE,
        description = Const.NETWORK_ERROR_DESC,
        iconResourceId = R.drawable.icon_network_error
    )

    class ServerException(originalException: Exception): CustomException(
        originalException = originalException,
        title = Const.SERVER_ERROR_TITLE,
        description = Const.SERVER_ERROR_DESC,
        iconResourceId = R.drawable.icon_server_error
    )

    class UnknownException(originalException: Exception): CustomException(
        originalException = originalException,
        title = Const.UNKNOWN_ERROR_TITLE,
        description = Const.UNKNOWN_ERROR_DESC,
        iconResourceId = R.drawable.icon_error
    )
}
