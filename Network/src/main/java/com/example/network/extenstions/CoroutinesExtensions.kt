package com.example.network.extenstions

import com.example.network.exception.MissingDataException
import com.example.network.model.ResponseApiModel

inline fun <reified T1 : Any, reified T2 : Any> ResponseApiModel<T1>.dataOrError(crossinline transform: (T1) -> T2): T2 {
    val data = this.data
    return if (data == null) {
        throw MissingDataException.from<T1, T2>()
    } else {
        transform(data)
    }
}

suspend inline fun <reified T1 : Any, reified T2 : Any> ResponseApiModel<T1>.suspendDataOrError(
    crossinline transform: suspend (T1) -> T2,
): T2 {
    val data = this.data
    return if (data == null) {
        throw MissingDataException.from<T1, T2>()
    } else {
        transform(data)
    }
}
