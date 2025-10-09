package id.haeworks.haerulmuttaqin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {
    @Query("SELECT * FROM source_table WHERE id = 1 LIMIT 1")
    fun getFirst(): Flow<SourceEntity?>

    @Query("SELECT * FROM source_table")
    fun getAll(): Flow<List<SourceEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg entities: SourceEntity)

    @Insert
    suspend fun insert(entity: SourceEntity)
}


