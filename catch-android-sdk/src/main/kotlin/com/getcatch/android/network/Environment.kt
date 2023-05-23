package com.getcatch.android.network

/**
 * The Environment enum represents the two distinct backend environments that the Catch Android SDK
 * communicates with: sandbox and production.
 *
 * Both environments provide the same functionality, but sandbox is intended be used for development
 * and testing while production should be used in live applications. Purchases made in the
 * sandbox environment will not result in actual charges.
 *
 * Applications should also ensure that the environment they use in the Catch Android SDK lines up
 * with the environments being used for
 * [Catch's Transaction APIs](https://catch.readme.io/reference/transactions-integration-overview).
 * For example, to open a given checkout in live mode, the checkout must have been created using
 * Catch's Live Transaction API endpoint.
 *
 * For more details about testing in the sandbox environment,
 * see [the Catch Sandbox Testing docs](https://catch.readme.io/reference/sandbox-testing).
 */
public enum class Environment(internal val baseUrl: String) {
    /** The environment which should be used for development and testing. */
    SANDBOX("https://dev.app-sandbox.getcatch.com"),

    /** The environment which should be used for production releases. */
    PRODUCTION("https://dev.app.getcatch.com");
}
