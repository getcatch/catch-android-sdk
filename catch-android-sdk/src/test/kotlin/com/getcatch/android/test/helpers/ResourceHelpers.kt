package com.getcatch.android.test.helpers

internal object ResourceHelpers {
    /**
     * Loads a resource file from the src/test/resources directory returns the
     * contents of the file as a string.
     *
     * @param resourcePath the path to the file relative to the src/test/resources directory
     */
    fun loadResource(resourcePath: String) =
        ClassLoader.getSystemResource(resourcePath).readText()
}
