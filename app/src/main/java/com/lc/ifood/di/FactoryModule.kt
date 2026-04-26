package com.lc.ifood.di

import com.lc.ifood.data.mapper.MealMapperImpl
import com.lc.ifood.domain.mapper.MealMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FactoryModule {

    @Binds
    @Singleton
    abstract fun bindMealFactory(impl: MealMapperImpl): MealMapper
}
