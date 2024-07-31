package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.data.local.UserDao
import com.example.movieapp.data.local.UserDatabase
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.data.repo.MoviesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun providesBaseURL(): String {
        return "https://api.themoviedb.org/3/movie/"
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideUserDatabase(@ApplicationContext appContext: Context): UserDatabase {
        return UserDatabase.getDatabase(appContext)
    }

    @Provides
    fun providesUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

    @Provides
    fun providesMoviesRepo(apiService: ApiService, userDao: UserDao): MoviesRepo =
        MoviesRepo(apiService, userDao)

    @Provides
    fun providesRetrofitInstance(baseURL: String, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
