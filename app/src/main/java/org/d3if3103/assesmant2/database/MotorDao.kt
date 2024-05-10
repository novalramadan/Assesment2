package org.d3if3103.assesmant2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3103.assesmant2.model.Motor


@Dao
interface MotorDao {
    @Insert
    suspend fun insert(motor: Motor)

    @Update
    suspend fun update(motor: Motor)

//    @Query("SELECT * FROM motor ORDER By kelas,stok ASC")
    @Query("SELECT * FROM motor")
    fun getMotor(): Flow<List<Motor>>

    @Query("SELECT * FROM motor WHERE id = :id")
    suspend fun getMotorById(id: Long): Motor?

    @Query("DELETE FROM motor WHERE id = :id")
    suspend fun deleteById(id:Long)




}