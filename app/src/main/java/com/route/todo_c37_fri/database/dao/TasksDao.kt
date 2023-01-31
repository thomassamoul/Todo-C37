package com.route.todo_c37_fri.database.dao

import androidx.room.*
import com.route.todo_c37_fri.database.model.Task

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from tasks")
    fun getAllTasks(): List<Task>

    @Query("select * from tasks where date=:selectedDate")
    fun getTasksByDate(selectedDate: Long): List<Task>
}