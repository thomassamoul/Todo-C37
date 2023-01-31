package com.route.todo_c37_fri.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.todo_c37_fri.R
import com.route.todo_c37_fri.databinding.ActivityMainBinding
import com.route.todo_c37_fri.ui.home.addTask.AddTaskBottomSheet
import com.route.todo_c37_fri.ui.home.addTask.OnDismissListener
import com.route.todo_c37_fri.ui.home.list.TasksListFragment
import com.route.todo_c37_fri.ui.home.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding
    val tasksListFragment = TasksListFragment();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_tasks_list -> {
//                        viewBinding.screenTitle.text = "" ;R.string.title_settings
                        viewBinding.screenTitle.setText(R.string.title_tasks_list)
                        showFragment(tasksListFragment)
                    }
                    R.id.nav_settings -> {
                        viewBinding.screenTitle.setText(R.string.title_settings)
                        showFragment(SettingsFragment())
                    }
                }
                return@setOnItemSelectedListener true;
            }
        viewBinding.bottomNav.selectedItemId = R.id.nav_tasks_list
        viewBinding.addTask.setOnClickListener {
            showAddTaskBottomSheet();
        }
    }

    private fun showAddTaskBottomSheet() {
        val addTaskBottomSheet = AddTaskBottomSheet();
        addTaskBottomSheet.onDismissListener = OnDismissListener {
            tasksListFragment.loadTasks()

        }
        addTaskBottomSheet.show(supportFragmentManager, null);
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}