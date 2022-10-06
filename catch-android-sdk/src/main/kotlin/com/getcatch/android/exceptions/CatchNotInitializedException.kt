package com.getcatch.android.exceptions

public class CatchNotInitializedException :
    @Suppress("MaxLineLength")
    CatchException(message = "Catch SDK has not been initialized. Make sure to call Catch.initialize() and pass in your public key on app startup.")
