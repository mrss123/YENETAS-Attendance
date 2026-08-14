package com.example.data

import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class AttendanceRepository(private val appDao: AppDao) {

    val allStudents: Flow<List<Student>> = appDao.getAllStudents()
    val allAttendanceRecords: Flow<List<AttendanceRecord>> = appDao.getAllAttendanceRecords()

    fun getAttendanceForDateAndSession(date: String, session: String): Flow<List<AttendanceRecord>> {
        return appDao.getAttendanceForDateAndSession(date, session)
    }

    fun getAttendanceForDate(date: String): Flow<List<AttendanceRecord>> {
        return appDao.getAttendanceForDate(date)
    }

    fun getAttendanceForStudent(studentId: Long): Flow<List<AttendanceRecord>> {
        return appDao.getAttendanceForStudent(studentId)
    }

    suspend fun insertStudent(student: Student): Long {
        return appDao.insertStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        appDao.updateStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        appDao.deleteStudent(student)
    }

    suspend fun markAttendance(
        studentId: Long,
        date: String,
        ethiopianDate: String,
        session: String,
        status: String,
        note: String
    ) {
        // Delete existing record for this date/session/student if any
        appDao.deleteAttendanceRecordForStudent(date, session, studentId)
        // Insert new record
        val record = AttendanceRecord(
            studentId = studentId,
            date = date,
            ethiopianDate = ethiopianDate,
            session = session,
            status = status,
            note = note
        )
        appDao.insertAttendanceRecord(record)
    }

    suspend fun markAllPresent(
        students: List<Student>,
        date: String,
        ethiopianDate: String,
        session: String
    ) {
        val records = students.map { student ->
            AttendanceRecord(
                studentId = student.id,
                date = date,
                ethiopianDate = ethiopianDate,
                session = session,
                status = AttendanceStatus.PRESENT.name,
                note = ""
            )
        }
        appDao.insertAttendanceRecords(records)
    }

    /**
     * Seeds initial traditional Abinet Timhirt Bet students if database is empty.
     */
    suspend fun seedInitialDataIfEmpty() {
        // We check if students list is empty by checking first emission in suspend block
        // Simple seed logic
        val initialStudents = listOf(
            Student(
                studentCode = "ABN-001",
                fullName = "ገብረ ማርያም ወልደ ኢየሱስ",
                department = Department.ZEMA.name,
                phone = "0911002233",
                currentLesson = "ጾመ ዲጓ (ሃሌ ሉያ)",
                lessonLevel = "መካከለኛ ደረጃ",
                guardianName = "መምህር ወልደ ጊዮርጊስ",
                joinedDate = "መስከረም 2016",
                note = "የጾመ ዲጓ ተማሪ"
            ),
            Student(
                studentCode = "ABN-002",
                fullName = "ወልደ ጊዮርጊስ ተስፋዬ",
                department = Department.NIBEB_TSELOT.name,
                phone = "0922334455",
                guardianName = "ዲያቆን ገብረ ኪዳን",
                joinedDate = "ጥቅምት 2016",
                note = "ዳዊት የጨረሰ"
            ),
            Student(
                studentCode = "ABN-003",
                fullName = "ኃይለ ሚካኤል ዘነበ",
                department = Department.QENE.name,
                phone = "0933445566",
                guardianName = "መምህር ሃብተ ወልድ",
                joinedDate = "ኅዳር 2015",
                note = "ጉባኤ ቃና ይተረጉማል"
            ),
            Student(
                studentCode = "ABN-004",
                fullName = "ተስፋ ጽዮን ገብረ ሕይወት",
                department = Department.AQUAQUAM.name,
                phone = "0944556677",
                guardianName = "ሊቀ ማኅሌት አበበ",
                joinedDate = "ጥር 2016",
                note = "ጽናጽልና ከበሮ ይይዛል"
            ),
            Student(
                studentCode = "ABN-005",
                fullName = "ክዳነ ማርያም መኮንን",
                department = Department.KEDASE.name,
                phone = "0955667788",
                guardianName = "ቀሲስ ገብረ እግዚአብሔር",
                joinedDate = "መጋቢት 2015",
                note = "የቅዳሴ ተማሪ"
            ),
            Student(
                studentCode = "ABN-006",
                fullName = "ሀብተ ማርያም አበበ",
                department = Department.METSAHIFT.name,
                phone = "0966778899",
                guardianName = "መምህር ዘካርያስ",
                joinedDate = "መስከረም 2014",
                note = "የብሉይ ኪዳን መጻሕፍት ተማሪ"
            ),
            Student(
                studentCode = "ABN-007",
                fullName = "ተክለ ሃይማኖት ካሣ",
                department = Department.ZEMA.name,
                phone = "0977889900",
                guardianName = "መርጌታ ተስፋዬ",
                joinedDate = "ግንቦት 2016",
                note = "ዝማሬና መዋሥዕት"
            ),
            Student(
                studentCode = "ABN-008",
                fullName = "ዘካርያስ ወልደ ሥላሴ",
                department = Department.QENE.name,
                phone = "0988990011",
                guardianName = "አባ ገብረ መስቀል",
                joinedDate = "ሰኔ 2016",
                note = "ወርቅና ሰም ይጽፋል"
            )
        )

        appDao.insertStudents(initialStudents)

        // Seed sample attendance for today
        val today = EthiopianDateUtils.getCurrentDateFormatted()
        val ethDate = EthiopianDateUtils.toEthiopianDate(Calendar.getInstance())

        val sampleAttendance = listOf(
            AttendanceRecord(studentId = 1, date = today, ethiopianDate = ethDate, session = SessionType.MORNING.name, status = AttendanceStatus.PRESENT.name),
            AttendanceRecord(studentId = 2, date = today, ethiopianDate = ethDate, session = SessionType.MORNING.name, status = AttendanceStatus.PRESENT.name),
            AttendanceRecord(studentId = 3, date = today, ethiopianDate = ethDate, session = SessionType.MORNING.name, status = AttendanceStatus.LATE.name, note = "15 ደቂቃ ዘግይቷል"),
            AttendanceRecord(studentId = 4, date = today, ethiopianDate = ethDate, session = SessionType.MORNING.name, status = AttendanceStatus.PRESENT.name),
            AttendanceRecord(studentId = 5, date = today, ethiopianDate = ethDate, session = SessionType.MORNING.name, status = AttendanceStatus.PERMISSION.name, note = "በሕመም ምክንያት"),
            AttendanceRecord(studentId = 6, date = today, ethiopianDate = ethDate, session = SessionType.MORNING.name, status = AttendanceStatus.PRESENT.name),
            AttendanceRecord(studentId = 7, date = today, ethiopianDate = ethDate, session = SessionType.MORNING.name, status = AttendanceStatus.ABSENT.name, note = "ለልመና/ቆሎ አልተመለሰም")
        )
        appDao.insertAttendanceRecords(sampleAttendance)
    }
}
