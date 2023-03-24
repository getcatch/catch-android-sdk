package com.getcatch.android.test.exceptions

import com.getcatch.android.test.mocks.MockRequest

internal class RequestNotMockedException(message: String): Exception(message) {
    companion object {
        /**
         * Creates an instance of the exception with a helpful error message for debugging.
         *
         * An example message created with this function would look like this:
         * ```
         * Received request that did not match any mock:
         * Received: GET https://google.com
         * Expected requests:
         *   - GET https://getcatch.com
         * ```
         */
        fun create(receivedRequest: MockRequest, expectedRequests: Collection<MockRequest>): RequestNotMockedException {
            val messageStringBuilder = StringBuilder()
            messageStringBuilder.appendLine("Received request that did not match any mock:")
            messageStringBuilder.appendLine("Received: $receivedRequest")
            messageStringBuilder.appendLine("Expected requests:")
            for (mockReq in expectedRequests) {
                messageStringBuilder.appendLine("  - $mockReq")
            }
            return RequestNotMockedException(messageStringBuilder.toString())
        }
    }
}
