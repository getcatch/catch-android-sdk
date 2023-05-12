package com.getcatch.android.exceptions

/**
 * An error raised when a widget tries to render before the Catch Android SDK is initialized.
 *
 * To fix this error, simply make sure to call [Catch.initialize] from the `onStart` method of your
 * [Application](https://developer.android.com/reference/android/app/Application) class.
 */
public class CatchNotInitializedException : Exception(
    "Catch SDK has not been initialized. Make sure to call Catch.initialize() with your public key on app startup."
)
