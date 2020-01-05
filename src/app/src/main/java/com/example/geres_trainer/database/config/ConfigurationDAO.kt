package com.example.geres_trainer.database.config

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update


@Dao
interface ConfigurationDAO {


    @Update
    fun update(configuration: Configuration)

    @Query("SELECT * FROM configuration_table LIMIT 1")
    fun getConfiguration(): LiveData<Configuration>

}