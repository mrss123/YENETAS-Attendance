package com.example.data

enum class SessionType(
    val nameAmharic: String,
    val nameEnglish: String,
    val timeDescription: String
) {
    MORNING("የጠዋት ክፍለ ጊዜ", "Morning Session", "12:00 - 6:00 (6:00 AM - 12:00 PM)"),
    AFTERNOON("የከሰአት ክፍለ ጊዜ", "Afternoon Session", "7:00 - 11:00 (1:00 PM - 5:00 PM)"),
    NIGHT("የምሽት/የቡድን", "Night/Group Session", "12:00 - 3:00 (6:00 PM - 9:00 PM)");

    companion object {
        fun fromString(value: String): SessionType {
            return entries.firstOrNull { it.name == value || it.nameAmharic == value } ?: MORNING
        }
    }
}
