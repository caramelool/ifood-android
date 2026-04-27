package com.lc.ifood.di

import com.lc.ifood.BuildConfig
import com.lc.ifood.data.remote.MealReminderApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    /**
     * OkHttp client with full request/response body logging.
     *
     * [HttpLoggingInterceptor.Level.BODY] logs headers and body for every call, which aids
     * debugging during development. In a production build this should be gated behind
     * [BuildConfig.DEBUG] to avoid logging sensitive data.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    /**
     * Retrofit instance pointed at [BuildConfig.BASE_URL].
     *
     * Uses [MoshiConverterFactory] to deserialize JSON responses into Kotlin data classes
     * without requiring `@JsonClass(generateAdapter = true)` on every model.
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideMealReminderApiService(retrofit: Retrofit): MealReminderApiService =
        retrofit.create(MealReminderApiService::class.java)
}
