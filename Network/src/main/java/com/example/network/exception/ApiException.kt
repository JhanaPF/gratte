package com.example.network.exception

import androidx.annotation.IntDef
import kotlinx.serialization.json.JsonElement

class ApiException(
    val status: Int,
    val errorCode: Int,
    val error: String,
    val errorBody: JsonElement? = null,
    val str: String? = null,
    val link: String? = null,
) : Exception() {

    companion object {
        const val SOCKET_ERROR = -5
        const val SOCKET_TIMEOUT_ERROR = -4
        const val SSL_ERROR = -3
        const val UNKNOWN_HOST_ERROR = -2
        const val CONNECT_ERROR = -1
        const val UNKNOWN_ERROR = 0
        const val SDC_VERSION_INVALID_ERROR = -1000
        const val INVALID_REQUEST_ERROR = 1001
        const val FIRST_NAME_BIRTH_DATE_REQUIRED_ERROR = 1062
        const val ACCESS_DENIED_ERROR = 1005
        const val INVALID_GRANT_ERROR = 1008
        const val INVALID_TOKEN_ERROR = 1010
        const val EXPIRED_TOKEN_ERROR = 1011
        const val USER_BANNED_ERROR = 1018
        const val BLACK_LISTED_PHONE_NUMBER = 2003
        const val WRONG_CODE_ERROR = 1054
        const val COUNTRY_NOT_ENABLED_ERROR = 1055
        const val WRONG_CODE_THROTTLED_ERROR = 1056
        const val INVALID_SMS_PROVIDER_REQUEST_ERROR = 1057
        const val BAD_FORMAT_PARAMETER_ERROR = 4004
        const val PHONE_NUMBER_INVALID = 500500
        const val USER_TOO_YOUNG_ERROR = 1022
        const val EMAIL_ALREADY_VERIFIED = 19001
        const val USER_INVALID_EMAIL_ERROR = 20001
        const val CONVERSATION_DISABLED_ERROR_CODE = 50006
        const val NO_FACE_DETECTED_ERROR_CODE = 2604
        const val ALBUM_NEVER_VALIDATED_ERROR_CODE = 2605
        const val FACE_DETECTION_UNAVAILABLE = 2606
        const val VERIFICATION_WILL_BE_LOST_ERROR_CODE = 2607
        const val NO_MORE_CREDIT_ERROR = 4700
        const val NO_MORE_RENEWABLE_LIKES = 4701
        const val ALREADY_DELIVERED = 9001
        const val RECEIPT_NOT_VALID = 9003

        // CrushTime
        const val NO_BOARD_FOUND_ERROR = 18001
        const val MAX_DAILY_CRUSHES_REACHED_ERROR = 18002
        const val ALL_BOARD_PLAYED_FOR_TODAY_ERROR = 18003
        const val BOARD_ALREADY_WON_ERROR = 18004
        const val CARD_ALREADY_PICKED_ERROR = 18005
        const val PICK_AND_BOARD_MISMATCH_ERROR = 18006
        const val UNABLE_TO_PICK_CARD_ERROR = 18007

        const val ERROR_CODE_FIRST_NAME_MAX_LENGTH_REACHED = 1050
        const val ERROR_CODE_FIRST_NAME_REGEX = 1051

        const val ERROR_CODE_BIRTH_DATE_UNDERAGE = 1052
        const val ERROR_CODE_INVALID_BIRTH_DATE = 400400

        // Boost
        const val ERROR_CODE_BOOST_ALREADY_ON_GOING = 6000
        const val ERROR_CODE_BOOST_NO_LAST_POSITION_FOUND = 6002

        // TODO: should be deleted
        fun getStatus(exception: Throwable?): Int =
            (exception as? ApiException)?.status ?: UNKNOWN_ERROR
    }

    constructor(status: Int, @ApiErrorCode errorCode: Int, message: String) : this(
        status = status,
        errorCode = errorCode,
        error = message,
    )

    override fun toString(): String =
        "status = $status errorCode = $errorCode error = $error"

    @IntDef(
        UNKNOWN_ERROR,
        SDC_VERSION_INVALID_ERROR,
        INVALID_REQUEST_ERROR,
        INVALID_GRANT_ERROR,
        INVALID_TOKEN_ERROR,
        EXPIRED_TOKEN_ERROR,
        USER_BANNED_ERROR,
        USER_TOO_YOUNG_ERROR,
        CONVERSATION_DISABLED_ERROR_CODE,
        NO_FACE_DETECTED_ERROR_CODE,
        ALBUM_NEVER_VALIDATED_ERROR_CODE,
        FACE_DETECTION_UNAVAILABLE,
        VERIFICATION_WILL_BE_LOST_ERROR_CODE,
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ApiErrorCode
}
