package com.example.data

import java.util.Calendar

data class EthiopianDate(
    val year: Int,
    val month: Int, // 1..13
    val day: Int,   // 1..30 (or 1..5/6 for Pagumen)
    val dayOfWeek: Int // 0=እሑድ, 1=ሰኞ, ..., 6=ቅዳሜ
) {
    val monthNameAmharic: String
        get() = EthiopianDateUtils.amharicMonths.getOrElse(month - 1) { "" }

    val dayNameAmharic: String
        get() = EthiopianDateUtils.amharicDays.getOrElse(dayOfWeek) { "" }

    fun toFormattedString(): String {
        return "$dayNameAmharic $monthNameAmharic $day/$year ዓ.ም"
    }

    fun toKeyString(): String {
        return "%04d-%02d-%02d".format(year, month, day)
    }

    fun toShortAmharic(): String {
        return "$monthNameAmharic $day, $year ዓ.ም"
    }
}

object EthiopianDateUtils {

    val amharicMonths = listOf(
        "መስከረም", "ጥቅምት", "ኅዳር", "ታኅሣሥ", "ጥር", "የካቲት",
        "መጋቢት", "ሚያዝያ", "ግንቦት", "ሰኔ", "ሐምሌ", "ነሐሴ", "ጳጉሜን"
    )

    val amharicDays = listOf(
        "እሑድ", "ሰኞ", "ማክሰኞ", "ረቡዕ", "ሐሙስ", "ዓርብ", "ቅዳሜ"
    )

    fun gregorianToJdn(year: Int, month: Int, day: Int): Long {
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        return day + (153 * m + 2) / 5 + 365L * y + y / 4 - y / 100 + y / 400 - 32045
    }

    fun jdnToEthiopian(jdn: Long): EthiopianDate {
        val r = (jdn - 1723856) % 1461
        val n = (r % 365) + 365 * (r / 1460)
        val ethYear = (4 * ((jdn - 1723856) / 1461) + (r / 365) - (r / 1460)).toInt()
        val ethMonth = ((n / 30) + 1).toInt()
        val ethDay = ((n % 30) + 1).toInt()
        val dayOfWeek = (((jdn + 1) % 7 + 7) % 7).toInt() // 0=Sunday
        return EthiopianDate(ethYear, ethMonth, ethDay, dayOfWeek)
    }

    fun ethiopianToJdn(ethYear: Int, ethMonth: Int, ethDay: Int): Long {
        return 1723856L + 365L * (ethYear - 1) + (ethYear / 4) + 30L * (ethMonth - 1) + ethDay - 1
    }

    fun getDaysInEthiopianMonth(year: Int, month: Int): Int {
        return if (month == 13) {
            if (year % 4 == 3) 6 else 5
        } else {
            30
        }
    }

    fun getFirstDayOfWeekForEthiopianMonth(year: Int, month: Int): Int {
        val jdn = ethiopianToJdn(year, month, 1)
        return (((jdn + 1) % 7 + 7) % 7).toInt() // 0 = Sunday (እሑድ)
    }

    fun toEthiopianDate(calendar: Calendar = Calendar.getInstance()): String {
        val gYear = calendar.get(Calendar.YEAR)
        val gMonth = calendar.get(Calendar.MONTH) + 1
        val gDay = calendar.get(Calendar.DAY_OF_MONTH)
        val jdn = gregorianToJdn(gYear, gMonth, gDay)
        return jdnToEthiopian(jdn).toFormattedString()
    }

    fun getTodayEthiopian(): EthiopianDate {
        val cal = Calendar.getInstance()
        val gYear = cal.get(Calendar.YEAR)
        val gMonth = cal.get(Calendar.MONTH) + 1
        val gDay = cal.get(Calendar.DAY_OF_MONTH)
        val jdn = gregorianToJdn(gYear, gMonth, gDay)
        return jdnToEthiopian(jdn)
    }

    fun getCurrentDateFormatted(): String {
        return getTodayEthiopian().toKeyString()
    }

    fun formatKeyToAmharic(key: String): String {
        return try {
            val parts = key.split("-")
            if (parts.size == 3) {
                val y = parts[0].toInt()
                val m = parts[1].toInt()
                val d = parts[2].toInt()
                val monthName = amharicMonths.getOrElse(m - 1) { "$m" }
                "$monthName $d, $y ዓ.ም"
            } else {
                key
            }
        } catch (e: Exception) {
            key
        }
    }
}
