package com.example.geres_trainer.database.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Configuration::class], version = 1, exportSchema = false)
abstract class ConfigurationDB : RoomDatabase() {

    abstract val configurationDAO : ConfigurationDAO

    companion object {

        @Volatile
        private var INSTANCE: ConfigurationDB? = null

        fun getInstance(context: Context): ConfigurationDB {

            synchronized(this){
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ConfigurationDB::class.java,
                        "configuration_database"
                    )
                        .createFromAsset("initConfigDB.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }


}