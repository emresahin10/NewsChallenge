package com.denbase.newschallenge.di

import android.content.Context
import androidx.room.Room
import com.denbase.newschallenge.api.NewsApi
import com.denbase.newschallenge.data.db.ArticleDao
import com.denbase.newschallenge.data.db.ArticleDataBase
import com.denbase.newschallenge.repositories.MainNewsRepository
import com.denbase.newschallenge.repositories.NewsRepository
import com.denbase.newschallenge.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) :
        ArticleDataBase = Room.databaseBuilder(appContext, ArticleDataBase::class.java, "article_db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideArticleDao(articleDB: ArticleDataBase) = articleDB.getArticleDao()

    @Singleton
    @Provides
    fun provideRepos(
        articleDao: ArticleDao, newsApi: NewsApi): NewsRepository = MainNewsRepository(newsApi, articleDao)



}