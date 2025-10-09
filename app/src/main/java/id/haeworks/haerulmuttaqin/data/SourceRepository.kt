package id.haeworks.haerulmuttaqin.data

import id.haeworks.haerulmuttaqin.db.SourceDao
import id.haeworks.haerulmuttaqin.db.SourceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceRepository @Inject constructor(
    private val sourceDao: SourceDao,
) {
    fun getFirst(): Flow<SourceEntity?> = sourceDao.getFirst()
    fun getAll(): Flow<List<SourceEntity?>> = sourceDao.getAll()
    suspend fun add(sourceEntity: SourceEntity) = sourceDao.insert(sourceEntity)
}


