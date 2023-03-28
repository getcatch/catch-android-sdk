package com.getcatch.android.cache

import com.getcatch.android.models.Merchant

internal interface CacheManager {
    var merchant: Merchant?
    var deviceToken: String?
}
