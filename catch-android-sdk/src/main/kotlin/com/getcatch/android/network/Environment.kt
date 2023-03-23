package com.getcatch.android.network

public enum class Environment(internal val host: String) {
    SANDBOX("dev.app-sandbox.getcatch.com"),
    PRODUCTION("dev.app.getcatch.com");
}
