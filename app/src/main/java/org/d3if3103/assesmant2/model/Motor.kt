package org.d3if3103.assesmant2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motor")
data class Motor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val tanggal: String,
    val stok: String,
    val merek: String

)