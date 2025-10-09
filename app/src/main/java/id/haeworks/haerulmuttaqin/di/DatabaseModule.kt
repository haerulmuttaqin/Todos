package id.haeworks.haerulmuttaqin.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.haeworks.haerulmuttaqin.db.AppDatabase
import id.haeworks.haerulmuttaqin.db.SourceDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = AppDatabase.build(context)

    @Provides
    @Singleton
    fun provideSourceDao(db: AppDatabase): SourceDao = db.sourceDao()
}


