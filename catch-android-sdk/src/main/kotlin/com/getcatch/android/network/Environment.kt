package com.getcatch.android.network

public enum class Environment(internal val host: String) {
    SANDBOX("staging.app-sandbox.getcatch.com"),
    PRODUCTION("staging.app.getcatch.com");
}
