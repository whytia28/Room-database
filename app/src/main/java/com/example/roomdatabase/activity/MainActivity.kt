package com.example.roomdatabase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.*
import com.example.roomdatabase.adapter.StudentAdapter
import com.example.roomdatabase.model.StudentDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var mDb: StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDb =
            StudentDatabase.getInstance(this)

        rv_student.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        fetchData()
        fab_add.setOnClickListener {
            val addIntent = Intent(this, AddActivity::class.java)
            startActivity(addIntent)
        }

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {

        GlobalScope.launch {
            val listStudent = mDb?.studentDao()?.getAllStudent()
            runOnUiThread {
                listStudent?.let {
                    val adapter =
                        StudentAdapter(it)
                    rv_student.adapter = adapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StudentDatabase.destroyInstance()
    }
}