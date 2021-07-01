package com.practice.data.utils

import android.util.Log
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

fun logError(
    coroutineContext: CoroutineContext? = null,
    throwable: Throwable,
    from: KClass<*>? = null,
    line: Int = 0,
    ) {
    var fromPrettyPrint = ""
    if (from != null) {
        fromPrettyPrint = """
            Exception
            //////////////////////////////////////////////
            .fromClass(${from.simpleName}.kt:$line)
            //////////////////////////////////////////////
        """.trimIndent()
    }
    var stackString = "$fromPrettyPrint \n ${throwable.localizedMessage}\n${coroutineContext?:""}\n"

    throwable.stackTrace.forEach { element ->
        stackString += "$element\n"
    }
    Log.e("logStackTrace", stackString)
}