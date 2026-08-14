package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_records")
data class AttendanceRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val date: String, // Format: YYYY-MM-DD
    val ethiopianDate: String, // Format: e.g. "ነሐሴ 7/2018"
    val session: String, // SessionType enum name
    val status: String, // AttendanceStatus enum name
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
