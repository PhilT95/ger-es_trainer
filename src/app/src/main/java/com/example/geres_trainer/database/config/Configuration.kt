package com.example.geres_trainer.database.config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "configuration_table")
data class Configuration(

    @PrimaryKey(autoGenerate = true)
    var configID : Long = 0L,

    @ColumnInfo(name = "game_length")
    var gameLength : Int = 5
)