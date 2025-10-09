package id.haeworks.haerulmuttaqin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [SourceEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sourceDao(): SourceDao

    companion object {
        fun build(
            context: Context,
        ): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app-db",
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val instance = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "app-db",
                        ).build()
                        instance.sourceDao().insertAll(
                            SourceEntity(id = 1, source = "FASTRATA BUANA"),
                            SourceEntity(id = 2, source = "KAPAL API"),
                            SourceEntity(id = 3, source = "EXCELSO ROBUSTA"),
                            SourceEntity(id = 4, source = "GOFAST VANILA"),
                        )
                        instance.close()
                    }
                }
            }).build()
        }
    }
}


