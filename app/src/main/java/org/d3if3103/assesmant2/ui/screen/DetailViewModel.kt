package org.d3if3103.assesmant2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3103.assesmant2.database.MotorDao
import org.d3if3103.assesmant2.model.Motor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel (private val dao: MotorDao): ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(nama:String, stok:String, merek: String){
        val motor = Motor (
            nama = nama,
            tanggal = formatter.format(Date()),
            stok = stok,
            merek = merek
        )
        viewModelScope.launch (Dispatchers.IO){
            dao.insert(motor)
        }
    }

    suspend fun getmotor(id:Long): Motor?{
        return dao.getMotorById(id)
    }

    fun  update(id: Long, nama: String,stok: String,merek: String){
        val motor = Motor(
            id = id,
            nama = nama,
            tanggal = formatter.format(Date()),
            stok = stok,
            merek = merek
        )
        viewModelScope.launch (Dispatchers.IO){
            dao.update(motor)
        }

    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteById(id)
        }
    }

}