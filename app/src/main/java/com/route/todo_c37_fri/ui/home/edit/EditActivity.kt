package com.route.todo_c37_fri.ui.home.edit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.route.todo_c37_fri.database.MyDataBase
import com.route.todo_c37_fri.database.model.Task
import com.route.todo_c37_fri.databinding.ActivityEditBinding
import com.route.todo_c37_fri.ui.Constant.Companion.TASK
import com.route.todo_c37_fri.ui.home.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityEditBinding

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task = ((intent.getSerializableExtra(TASK) as? Task)!!)

        showData(task)

        binding.submit.setOnClickListener {
            updateTodo()
        }

    }

    private fun isDateValid(): Boolean {
        var isValid = true

        if (binding.titleContainer.editText?.text.toString().isBlank()) {
            binding.titleContainer.error = "Please Enter Title"
            isValid = false
        } else binding.titleContainer.error = null

        if (binding.descContainer.editText?.text.toString().isBlank()) {
            binding.descContainer.error = "Please Enter Your Description"
            isValid = false
        } else binding.descContainer.error = null

        if (binding.date.text.isNullOrBlank()) {
            binding.dateContainer.error = "Please Select Date"
            isValid = false
        } else binding.dateContainer.error = null

        return isValid

    }

    private fun updateTodo() {
        if (isDateValid()) {
            task.title = binding.titleContainer.editText?.text.toString()
            task.description = binding.descContainer.editText?.text.toString()
//            task.date = binding.dateContainer.editText!!.text.toString().toLong()


            MyDataBase.getDataBase(this).tasksDao().updateTask(task)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun showData(task: Task) {
        binding.titleContainer.editText?.setText(task.title)
        val date = convertLongToTime(task.date)
        binding.date.text = date
        binding.descContainer.editText?.setText(task.description)
    }

    private fun convertLongToTime(date: Long?): String {
        val date = Date(date!!)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }


}