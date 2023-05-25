package com.getcatch.sample.utils

enum class CheckoutDemoOption {
    DIRECT_OPEN,
    VIRTUAL_CARD_OPEN,
    VIRTUAL_CARD_CREATE;

    val label: String
        get() = when (this) {
            DIRECT_OPEN -> "Direct"
            VIRTUAL_CARD_CREATE -> "Virtual Card - Create and Open"
            VIRTUAL_CARD_OPEN -> "Virtual Card - Open"
        }

    val idLabel: String
        get() = when (this) {
            DIRECT_OPEN, VIRTUAL_CARD_OPEN -> "Checkout ID"
            VIRTUAL_CARD_CREATE -> "Order ID"
        }

    val buttonLabel: String
        get() = when (this) {
            DIRECT_OPEN -> "Open Checkout"
            VIRTUAL_CARD_CREATE -> "Create and Open Virtual Card Checkout"
            VIRTUAL_CARD_OPEN -> "Open Virtual Card Checkout"
        }
}
