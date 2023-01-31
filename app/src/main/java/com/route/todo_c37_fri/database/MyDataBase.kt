package com.route.todo_c37_fri.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todo_c37_fri.database.dao.TasksDao
import com.route.todo_c37_fri.database.model.Task

@Database(entities = [Task::class],
    version = 1,
    exportSchema = false )
abstract class MyDataBase :RoomDatabase(){
    abstract fun tasksDao():TasksDao

    companion object{
        private val dataBaseName = "RouteTasksDataBase";
        private var myDataBase:MyDataBase? = null
        fun getDataBase(context:Context):MyDataBase{
            if(myDataBase==null){
                // initialize
                myDataBase = Room.databaseBuilder(
                    context,
                    MyDataBase::class.java,
                    dataBaseName
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return myDataBase!!;
        }

    }
}