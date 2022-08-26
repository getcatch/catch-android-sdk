package com.getcatch.android

public object Catch {
    private var publicKey: String? = null
    public fun greet(): String {
        if (publicKey == null) {
            return "Not yet initialized."
        }
        return "Hello from Catch!"
    }

    public fun initialize(publicKey: String) {
        this.publicKey = publicKey
    }
}
