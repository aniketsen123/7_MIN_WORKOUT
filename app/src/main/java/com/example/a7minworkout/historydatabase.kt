package com.example.a7minworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [historyentity::class], version = 1)
abstract  class historydatabase:RoomDatabase() {
    abstract fun historyDoa(): historydao

    companion object {
        //when instances value is update  then all threads are updated
        @Volatile
        private var INSTANCE: historydatabase? = null

        fun getinstance(context: Context): historydatabase {
            //2 thread try to create the wsame object
            synchronized(this) {
                var instances = INSTANCE
                if (instances == null) {
                    instances = Room.databaseBuilder(
                        context.applicationContext,
                        historydatabase::class.java,
                        "notes_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instances
                }
                return instances
            }
        }
    }
}