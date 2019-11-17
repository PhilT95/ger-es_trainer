package com.example.geres_trainer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "word_translation_table")
data class Translation(

    @PrimaryKey(autoGenerate = true)
    var translationID : Long = 0L,

    @ColumnInfo(name = "word_ger")
    var wordGer : String = "",

    @ColumnInfo(name = "word_es")
    var wordES : String = "",

    @ColumnInfo(name = "info")
    var info : String = ""

)