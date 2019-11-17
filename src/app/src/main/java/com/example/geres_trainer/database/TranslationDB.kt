package com.example.geres_trainer.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

abstract class TranslationDB : RoomDatabase() {

    abstract val translationDBDao: TranslationDBDao


    companion object {

        @Volatile
        private var INSTANCE: TranslationDB? = null


        fun getInstance(context: Context): TranslationDB {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TranslationDB::class.java,
                        "translation_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}