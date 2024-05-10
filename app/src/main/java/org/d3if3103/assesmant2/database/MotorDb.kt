package org.d3if3103.assesmant2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3103.assesmant2.model.Motor

@Database(entities = [Motor::class], version = 1, exportSchema = false)
abstract class MotorDb:RoomDatabase() {

    abstract val dao: MotorDao

    companion object{
        @Volatile
        private var INSTANCE: MotorDb? = null

        fun getInstance(context: Context):MotorDb{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MotorDb::class.java,
                        "motor.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }

}