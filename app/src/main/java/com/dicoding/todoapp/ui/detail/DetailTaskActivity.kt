package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        //TODO 11 : Show detail task and implement delete action

        val tietTitle = findViewById<TextInputEditText>(R.id.detail_ed_title)
        val tietDecription = findViewById<TextInputEditText>(R.id.detail_ed_description)
        val tietDueDate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)
        val btnDeleteTask = findViewById<Button>(R.id.btn_delete_task)

        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]

        val taskId = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(taskId)

        val taskObserver = Observer<Task> {
            tietTitle.setText(it.title)
            tietDecription.setText(it.description)
            tietDueDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))
        }
        detailTaskViewModel.task.observe(this, taskObserver)

        btnDeleteTask.setOnClickListener {
            detailTaskViewModel.task.removeObserver(taskObserver)
            detailTaskViewModel.deleteTask()
            finish()
        }
    }
}