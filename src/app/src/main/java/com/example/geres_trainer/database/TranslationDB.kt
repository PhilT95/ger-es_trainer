package com.example.geres_trainer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.geres_trainer.R
import com.example.geres_trainer.util.ioThread
import com.example.geres_trainer.util.splitTranslation


@Database(entities = [Translation::class], version = 2, exportSchema = false)
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
                        .createFromAsset("initDB.db")
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                // Initializes Database with default data set in the IOThread
                                ioThread {
                                    //dbInit(context)
                                }

                            }

                        })
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }


        /*
        Prepoluates the database when created through a callback
         */
        private fun dbInit(context: Context) {
            val data = context.resources.getStringArray(R.array.translations)


            for (value in data) {
                val translationData =
                    splitTranslation(value)

                var translation = Translation()
                translation.wordGer = translationData.get(0)
                translation.wordES = translationData.get(1)
                translation.info = translationData.get(2)

                getInstance(context).translationDBDao.insert(translation)
            }
        }

    }




}