package com.example.roomdatabase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Student
import com.example.roomdatabase.model.StudentDatabase
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditActivity : AppCompatActivity() {

    private var mDb: StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        mDb =
            StudentDatabase.getInstance(this)

        val objectStudent = intent.getParcelableExtra<Student>("student")

        supportActionBar?.title = "Ubah data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        et_name.setText(objectStudent?.name)
        et_email.setText(objectStudent?.email)

        btn_save.setOnClickListener {
            objectStudent?.name = et_name.text.toString()
            objectStudent?.email = et_email.text.toString()
            GlobalScope.launch {
               val result = mDb?.studentDao()?.updateStudent(objectStudent)
                runOnUiThread{
                    if (result != 0) {
                        Toast.makeText(this@EditActivity, "Sukses mengubah ${objectStudent?.name}", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(this@EditActivity, "Gagal mengubah ${objectStudent?.name}", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }

            }
        }
    }
}