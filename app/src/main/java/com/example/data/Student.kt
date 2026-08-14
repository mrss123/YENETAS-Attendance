package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentCode: String,
    val fullName: String,
    val department: String, // Department enum name
    val phone: String = "",
    val currentLesson: String = "",
    val lessonLevel: String = "",
    val guardianName: String = "",
    val joinedDate: String = "",
    val isActive: Boolean = true,
    val note: String = ""
)
