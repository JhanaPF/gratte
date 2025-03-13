package com.example.network.exception

import kotlin.reflect.KClass

class MissingDataException(inputType: KClass<*>, outputType: KClass<*>) :
    IllegalStateException("input $inputType output $outputType") {

    companion object {
        inline fun <reified T1 : Any, reified T2 : Any> from(): MissingDataException =
            MissingDataException(T1::class, T2::class)
    }
}
