package id.haeworks.haerulmuttaqin.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_table")
data class SourceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val source: String,
)


