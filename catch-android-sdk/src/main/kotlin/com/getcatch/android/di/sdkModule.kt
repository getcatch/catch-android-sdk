package com.getcatch.android.di

import com.getcatch.android.cache.CacheManager
import com.getcatch.android.cache.CacheManagerImpl
import com.getcatch.android.clients.KtorClient
import com.getcatch.android.clients.merchants.MerchantsSvcClient
import com.getcatch.android.clients.merchants.MerchantsSvcClientImpl
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.repository.MerchantRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val sdkModule = module {
    single {
        KtorClient
    }
    single<CacheManager> {
        CacheManagerImpl(context = androidContext())
    }
    single<MerchantsSvcClient> {
        MerchantsSvcClientImpl(httpClient = get())
    }
    single<MerchantRepository> {
        MerchantRepositoryImpl(merchantsSvcClient = get(), cache = get())
    }
}
