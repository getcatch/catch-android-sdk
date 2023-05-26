package com.getcatch.android.network

/**
 * The Environment enum represents the two distinct backend environments that the Catch Android SDK
 * communicates with: `SANDBOX` and `LIVE`.
 *
 * Both environments provide the same functionality, but `SANDBOX` is intended be used for
 * development and testing while `LIVE` should be used in production applications. Purchases made in
 * the `SANDBOX` environment will not result in actual charges.
 *
 * Applications should also ensure that the environment they use in the Catch Android SDK lines up
 * with the environments being used for
 * [Catch's Transaction APIs](https://catch.readme.io/reference/transactions-integration-overview).
 * For example, to open a given checkout in `LIVE`, the checkout must have been created using
 * Catch's `LIVE` transaction API endpoint.
 *
 * For more details about testing in the `SANDBOX` environment,
 * see [the Catch Sandbox Testing docs](https://catch.readme.io/reference/sandbox-testing).
 */
public enum class Environment(internal val baseUrl: String) {
    /** The environment which should be used for development and testing. */
    SANDBOX("https://dev.app-sandbox.getcatch.com"),

    /** The environment which should be used for production releases. */
    LIVE("https://dev.app.getcatch.com");
}
