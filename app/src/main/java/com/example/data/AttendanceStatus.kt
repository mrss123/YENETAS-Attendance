package com.example.data

import androidx.compose.ui.graphics.Color

enum class AttendanceStatus(
    val nameAmharic: String,
    val nameEnglish: String,
    val colorHex: Long,
    val iconName: String
) {
    PRESENT("ተገኝቷል", "Present", 0xFF2E7D32, "CheckCircle"),
    ABSENT("ቀርቷል", "Absent", 0xFFC62828, "Cancel"),
    LATE("ዘግይቷል", "Late", 0xFFEF6C00, "Schedule"),
    PERMISSION("በፈቃድ", "Permission", 0xFF1565C0, "AssignmentTurnedIn");

    fun getColor(): Color = Color(colorHex)

    companion object {
        fun fromString(value: String): AttendanceStatus {
            return entries.firstOrNull { it.name == value || it.nameAmharic == value } ?: PRESENT
        }
    }
}
