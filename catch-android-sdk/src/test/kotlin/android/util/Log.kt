@file:JvmName("Log")

package android.util

public fun e(tag: String, msg: String, t: Throwable?): Int {
    if (t != null) {
        println("ERROR: $tag: $msg (${t.javaClass.simpleName})")
    } else {
        println("ERROR: $tag: $msg")
    }
    return 0
}

public fun e(tag: String, msg: String): Int {
    println("ERROR: $tag: $msg")
    return 0
}

public fun w(tag: String, msg: String): Int {
    println("WARN: $tag: $msg")
    return 0
}

public fun i(tag: String, msg: String): Int {
    println("INFO: $tag: $msg")
    return 0
}

public fun d(tag: String, msg: String): Int {
    println("DEBUG: $tag: $msg")
    return 0
}
