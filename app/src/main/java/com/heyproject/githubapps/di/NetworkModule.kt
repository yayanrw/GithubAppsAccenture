package com.heyproject.githubapps.di

import com.heyproject.githubapps.BuildConfig
import com.heyproject.githubapps.data.datasource.remote.api.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 07:06
Github : https://github.com/yayanrw
 **/

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val certificatePinner =
        CertificatePinner.Builder().add(HOST_NAME, BuildConfig.SERTIFICATE_KEY_1)
            .add(HOST_NAME, BuildConfig.SERTIFICATE_KEY_2).build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner).build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): GithubService {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        return retrofit.create(GithubService::class.java)
    }

    companion object {
        const val HOST_NAME = "api.github.com"
        const val BASE_URL = "https://api.github.com/"
    }
}