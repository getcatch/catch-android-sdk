package com.getcatch.android.exceptions

public class CatchNotInitializedException : Exception(
    "Catch SDK has not been initialized. Make sure to call Catch.initialize() with your public key on app startup."
)
