package com.example.roomdatabase.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "student")
@Parcelize
data class Student(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "email")var email: String
) : Parcelable