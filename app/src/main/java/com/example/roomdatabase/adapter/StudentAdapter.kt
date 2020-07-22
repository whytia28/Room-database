package com.example.roomdatabase.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Student
import com.example.roomdatabase.model.StudentDatabase
import com.example.roomdatabase.activity.EditActivity
import com.example.roomdatabase.activity.MainActivity
import kotlinx.android.synthetic.main.item_student.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StudentAdapter(val listStudent: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listStudent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_id.text = listStudent[position].id.toString()
        holder.itemView.tv_nama.text = listStudent[position].name
        holder.itemView.tv_email.text = listStudent[position].email
        holder.itemView.iv_edit.setOnClickListener {
            val intentEditActivity = Intent(it.context, EditActivity::class.java)
            intentEditActivity.putExtra("student", listStudent[position])

            it.context.startActivity(intentEditActivity)
        }

        holder.itemView.iv_delete.setOnClickListener {
            val builder = AlertDialog.Builder(it.context).setPositiveButton("Ya") { p0, p1 ->
                val mDb =
                    StudentDatabase.getInstance(
                        holder.itemView.context
                    )

                GlobalScope.launch {
                    val result = mDb?.studentDao()?.deleteStudent(listStudent[position])
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (result != 0) {
                            Toast.makeText(
                                it.context,
                                "Berhasil menghapus data ${listStudent[position].name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                it.context,
                                "Gagal menghapus data ${listStudent[position].name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    (holder.itemView.context as MainActivity).fetchData()
                }
            }.setNegativeButton(
                "Tidak"
            ) { p0, p1 ->
                p0.dismiss()
            }
                .setMessage("Apakah anda mau menghapus data ${listStudent[position].name}")
                .setTitle("Konfirmasi hapus")
                .show()
            builder.create()
        }
    }


    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}