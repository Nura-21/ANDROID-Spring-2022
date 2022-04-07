package com.example.todo.database

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.InsertUpdate

@Database(
    entities = [Todo::class, Categ::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){

    //DAO
    abstract fun todoDao():TodoDAO

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "Todo.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as AppDatabase
        }
    }
}