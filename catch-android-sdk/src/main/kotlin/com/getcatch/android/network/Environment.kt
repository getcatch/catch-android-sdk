package com.getcatch.android.network

public enum class Environment(internal val baseUrl: String) {
    SANDBOX("https://dev.app-sandbox.getcatch.com"),
    PRODUCTION("https://dev.app.getcatch.com");
}
