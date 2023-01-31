package com.route.todo_c37_fri.ui.home.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todo_c37_fri.base.BaseFragment
import com.route.todo_c37_fri.database.MyDataBase
import com.route.todo_c37_fri.database.model.Task
import com.route.todo_c37_fri.databinding.FragmentListBinding
import com.route.todo_c37_fri.ui.Constant.Companion.TASK
import com.route.todo_c37_fri.ui.home.edit.EditActivity
import java.util.*

class TasksListFragment : BaseFragment() {
    lateinit var viewBinding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentListBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }


    lateinit var tasksAdapter: TasksRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksAdapter = TasksRecyclerAdapter(null)
        viewBinding.tasksRecycler.adapter = tasksAdapter
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                currentDate.set(Calendar.DAY_OF_MONTH, date.day)
                currentDate.set(Calendar.MONTH, date.month - 1)
                currentDate.set(Calendar.YEAR, date.year)
                loadTasks();
            }
        }


        viewBinding.calendarView.selectedDate = CalendarDay.today()

        tasksAdapter.onItemClicked = object : OnItemClicked {
            override fun onItemClick(task: Task) {
                showMessage("what do you want to do?",
                    "Update",
                    { _, i -> updateTodo(task) },
                    "Make Done",
                    { _, i -> makeTodoDone(task) }
                )
            }
        }

        tasksAdapter.onItemDeleteCLicked = object : OnItemDeleteCLicked {
            override fun onItemDeleteClick(pos: Int, task: Task) {

                deleteTask(task)
            }

        }

        // loadTasks()
    }

    private fun deleteTask(task: Task) {
        showMessage("Are you want to delete this task",
            posActionTitle = "Yes",
            posAction = { dialog, _ ->
                dialog.dismiss()
                MyDataBase.getDataBase(requireContext()).tasksDao().deleteTask(task)
                refreshRecyclerView()
            },
            negActionTitle = "Cancel",
            negAction = { dialog, _ ->
                dialog.dismiss()
            }
        )
    }

    private fun makeTodoDone(task: Task) {
        task.isDone = true
        MyDataBase.getDataBase(requireContext()).tasksDao().updateTask(task)
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        tasksAdapter.changeData(MyDataBase.getDataBase(requireContext()).tasksDao().getAllTasks())
        tasksAdapter.notifyDataSetChanged()
    }

    private fun updateTodo(task: Task) {
        var intent = Intent(requireContext(), EditActivity::class.java)
        intent.putExtra(TASK, task)
        startActivity(intent)
    }

    val currentDate = Calendar.getInstance()

    init {
        currentDate.set(Calendar.HOUR, 0)
        currentDate.set(Calendar.MINUTE, 0)
        currentDate.set(Calendar.SECOND, 0)
        currentDate.set(Calendar.MILLISECOND, 0)
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }


    fun loadTasks() {
        if (!isResumed) {
            return
        }
        val tasks = MyDataBase.getDataBase(requireActivity()).tasksDao()
            .getTasksByDate(currentDate.timeInMillis);
        tasksAdapter.changeData(tasks)

    }
}