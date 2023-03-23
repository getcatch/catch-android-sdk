package com.getcatch.android.test.mocks

import com.getcatch.android.cache.CacheManager
import com.getcatch.android.models.Merchant

internal class MockCacheManager(override var merchant: Merchant? = null) : CacheManager
