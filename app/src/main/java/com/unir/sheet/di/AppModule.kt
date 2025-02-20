package com.unir.sheet.di

import android.app.Application
import androidx.room.Room
import com.unir.sheet.data.local.database.CharacterDao
import com.unir.sheet.data.local.database.ItemDao
import com.unir.sheet.data.local.database.MyDatabase
import com.unir.sheet.data.local.repository.LocalCharacterRepository
import com.unir.sheet.data.local.repository.LocalSkillRepository
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.SkillRepository
import com.unir.sheet.util.Constants.MY_DATA_BASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  AppModule {




    @Provides
    @Singleton
    fun provideMyDataBase(app: Application): MyDatabase {
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            MY_DATA_BASE
        ).build()
    }



    @Provides
    @Singleton
    fun provideCharacterRepository(database: MyDatabase): CharacterRepository {
        return LocalCharacterRepository(
            database.characterDao(),
            database.getItemDao()
        )
    }

    @Provides
    @Singleton
    fun provideSkillRepository(database: MyDatabase): SkillRepository {
        return LocalSkillRepository(
            database.characterDao(),
        )
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: MyDatabase): CharacterDao {
        return database.characterDao()
    }
    @Provides
    @Singleton
    fun provideItemDao(database: MyDatabase): ItemDao {
        return database.getItemDao()
    }



}
