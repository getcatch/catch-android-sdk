package com.getcatch.android.di

import com.getcatch.android.cache.CacheManager
import com.getcatch.android.cache.CacheManagerImpl
import com.getcatch.android.clients.KtorClient
import com.getcatch.android.clients.merchant.MerchantSvcClient
import com.getcatch.android.clients.merchant.MerchantSvcClientImpl
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
    single<MerchantSvcClient> {
        MerchantSvcClientImpl(httpClient = get())
    }
    single<MerchantRepository> {
        MerchantRepositoryImpl(merchantSvcClient = get(), cache = get())
    }
}
