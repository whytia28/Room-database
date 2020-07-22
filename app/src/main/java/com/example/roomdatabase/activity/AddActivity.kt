package com.example.roomdatabase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Student
import com.example.roomdatabase.model.StudentDatabase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private var mDb: StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        mDb =
            StudentDatabase.getInstance(this)

        btn_save.setOnClickListener {
            val objectStudent = Student(
                null,
                et_name.text.toString(),
                et_email.text.toString()
            )
            GlobalScope.launch {
                val result = mDb?.studentDao()?.insert(objectStudent)
                runOnUiThread{
                    if (result != 0.toLong()) {
                        Toast.makeText(this@AddActivity, "Sukses menambah ${objectStudent.name}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AddActivity, "Gagal menambah ${objectStudent.name}", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }

            }

        }
    }
}