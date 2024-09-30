package com.bugima.rickandmortycharacters.util

import retrofit2.HttpException
import java.io.IOException
import java.sql.SQLException
import java.util.Locale

fun Exception.toCustomError(): CustomException {
    return when (this) {
        is IOException -> CustomException.NetworkException(this)
        is HttpException -> CustomException.ServerException(this)
        is SQLException -> CustomException.DatabaseException(this)
        else -> CustomException.UnknownException(this)
    }
}

fun Exception.createLogMessage(): String {
    return when (this) {
        is IOException -> String.format(
            Locale.ROOT,
            Const.NETWORK_ERROR_LOG,
            localizedMessage,
            cause,
            stackTraceToString()
        )
        is HttpException -> {
            String.format(
                Locale.ROOT,
                Const.SERVER_ERROR_LOG,
                code(),
                message(),
                response()?.raw()?.request?.url,
                response()?.errorBody()?.string(),
                stackTraceToString()
            )
        }
        is SQLException -> {
            String.format(
                Locale.ROOT,
                Const.DATABASE_ERROR_LOG,
                localizedMessage,
                sqlState,
                errorCode,
                stackTraceToString()
            )
        }

        else -> {
            String.format(
                Locale.ROOT,
                Const.UNKNOWN_ERROR_LOG,
                localizedMessage,
                cause,
                stackTraceToString()
            )
        }
    }
}
