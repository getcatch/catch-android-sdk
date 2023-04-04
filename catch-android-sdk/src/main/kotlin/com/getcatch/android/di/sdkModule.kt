package com.getcatch.android.di

import com.getcatch.android.cache.CacheManager
import com.getcatch.android.cache.CacheManagerImpl
import com.getcatch.android.network.clients.KtorClient
import com.getcatch.android.network.clients.merchants.MerchantsSvcClient
import com.getcatch.android.network.clients.merchants.MerchantsSvcClientImpl
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import com.getcatch.android.network.clients.transactions.TransactionsSvcClientImpl
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.repository.MerchantRepositoryImpl
import com.getcatch.android.repository.UserRepository
import com.getcatch.android.repository.UserRepositoryImpl
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
        MerchantsSvcClientImpl(httpClient = get(), environment = get())
    }
    single<MerchantRepository> {
        MerchantRepositoryImpl(merchantsSvcClient = get(), cache = get(), publicKey = get())
    }
    single<TransactionsSvcClient> {
        TransactionsSvcClientImpl(httpClient = get(), environment = get())
    }
    single<UserRepository> {
        UserRepositoryImpl(transactionsSvcClient = get(), cache = get())
    }
}
