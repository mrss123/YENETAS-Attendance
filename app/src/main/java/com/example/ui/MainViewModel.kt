package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.AttendanceRecord
import com.example.data.AttendanceRepository
import com.example.data.AttendanceStatus
import com.example.data.Department
import com.example.data.EthiopianDateUtils
import com.example.data.SessionType
import com.example.data.Student
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AttendanceRepository

    val selectedDate = MutableStateFlow(EthiopianDateUtils.getCurrentDateFormatted())
    val selectedSession = MutableStateFlow(SessionType.MORNING)
    val selectedDepartment = MutableStateFlow<Department?>(null)
    val searchQuery = MutableStateFlow("")
    val currentTab = MutableStateFlow(0)

    val isAddStudentDialogOpen = MutableStateFlow(false)
    val editingStudent = MutableStateFlow<Student?>(null)
    val viewingStudentDetail = MutableStateFlow<Student?>(null)

    init {
        val dao = AppDatabase.getDatabase(application).appDao()
        repository = AttendanceRepository(dao)

        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    val allStudents: StateFlow<List<Student>> = repository.allStudents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filteredStudents: StateFlow<List<Student>> = combine(
        allStudents,
        selectedDepartment,
        searchQuery
    ) { students, dept, query ->
        students.filter { student ->
            val matchesDept = dept == null || student.department == dept.name
            val matchesSearch = query.isBlank() ||
                    student.fullName.contains(query, ignoreCase = true) ||
                    student.studentCode.contains(query, ignoreCase = true) ||
                    student.guardianName.contains(query, ignoreCase = true)
            matchesDept && matchesSearch
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val attendanceForCurrentSession: StateFlow<List<AttendanceRecord>> = combine(
        selectedDate,
        selectedSession
    ) { date, session ->
        Pair(date, session)
    }.flatMapLatest { (date, session) ->
        repository.getAttendanceForDateAndSession(date, session.name)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allAttendanceRecords: StateFlow<List<AttendanceRecord>> = repository.allAttendanceRecords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onDateSelected(date: String) {
        selectedDate.value = date
    }

    fun onSessionSelected(session: SessionType) {
        selectedSession.value = session
    }

    fun onDepartmentFilterSelected(dept: Department?) {
        selectedDepartment.value = dept
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun onTabSelected(index: Int) {
        currentTab.value = index
    }

    fun markAttendance(studentId: Long, status: AttendanceStatus, note: String = "") {
        viewModelScope.launch {
            val date = selectedDate.value
            val session = selectedSession.value.name
            val ethDate = EthiopianDateUtils.toEthiopianDate(Calendar.getInstance())
            repository.markAttendance(
                studentId = studentId,
                date = date,
                ethiopianDate = ethDate,
                session = session,
                status = status.name,
                note = note
            )
        }
    }

    fun markAllPresent() {
        viewModelScope.launch {
            val students = filteredStudents.value
            val date = selectedDate.value
            val session = selectedSession.value.name
            val ethDate = EthiopianDateUtils.toEthiopianDate(Calendar.getInstance())
            repository.markAllPresent(
                students = students,
                date = date,
                ethiopianDate = ethDate,
                session = session
            )
        }
    }

    fun addStudent(
        fullName: String,
        code: String,
        department: Department,
        phone: String,
        currentLesson: String = "",
        lessonLevel: String = "",
        guardianName: String = "",
        note: String = ""
    ) {
        viewModelScope.launch {
            val studentCode = code.ifBlank { "ABN-${(allStudents.value.size + 1).toString().padStart(3, '0')}" }
            val newStudent = Student(
                studentCode = studentCode,
                fullName = fullName,
                department = department.name,
                phone = phone,
                currentLesson = currentLesson,
                lessonLevel = lessonLevel,
                guardianName = guardianName,
                joinedDate = EthiopianDateUtils.toEthiopianDate(Calendar.getInstance()),
                note = note
            )
            repository.insertStudent(newStudent)
            isAddStudentDialogOpen.value = false
        }
    }

    fun updateStudentLesson(student: Student, newLesson: String, newLevel: String) {
        viewModelScope.launch {
            val updated = student.copy(
                currentLesson = newLesson,
                lessonLevel = newLevel
            )
            repository.updateStudent(updated)
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            repository.updateStudent(student)
            editingStudent.value = null
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            repository.deleteStudent(student)
            if (viewingStudentDetail.value?.id == student.id) {
                viewingStudentDetail.value = null
            }
        }
    }
}
