package com.example.lopning.cache

import androidx.room.Database

@Database(
    entities = [Run::class],
    version = 1
)
abstract class RunDatabase {


    abstract fun getDao() : RunDao


}