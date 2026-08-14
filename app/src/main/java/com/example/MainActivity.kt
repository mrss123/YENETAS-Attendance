package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainViewModel
import com.example.ui.components.AbinetTopBar
import com.example.ui.components.AddStudentDialog
import com.example.ui.screens.AbinetInfoScreen
import com.example.ui.screens.AddStudentScreen
import com.example.ui.screens.LearningProgressScreen
import com.example.ui.screens.ReportsScreen
import com.example.ui.screens.StudentListScreen
import com.example.ui.screens.TakeAttendanceScreen
import com.example.ui.theme.AbinetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AbinetTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen(
    viewModel: MainViewModel = viewModel()
) {
    val selectedSession by viewModel.selectedSession.collectAsStateWithLifecycle()
    val selectedDepartment by viewModel.selectedDepartment.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()

    val filteredStudents by viewModel.filteredStudents.collectAsStateWithLifecycle()
    val allStudents by viewModel.allStudents.collectAsStateWithLifecycle()
    val attendanceRecords by viewModel.attendanceForCurrentSession.collectAsStateWithLifecycle()
    val allAttendanceRecords by viewModel.allAttendanceRecords.collectAsStateWithLifecycle()

    val isAddStudentDialogOpen by viewModel.isAddStudentDialogOpen.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AbinetTopBar(
                selectedSession = selectedSession,
                onSessionSelected = { viewModel.onSessionSelected(it) },
                selectedDepartment = selectedDepartment,
                onDepartmentSelected = { viewModel.onDepartmentFilterSelected(it) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("main_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = (currentTab == 0),
                    onClick = { viewModel.onTabSelected(0) },
                    icon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Attendance") },
                    label = { Text("ክትትል", fontSize = 10.sp, fontWeight = if (currentTab == 0) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_attendance")
                )

                NavigationBarItem(
                    selected = (currentTab == 1),
                    onClick = { viewModel.onTabSelected(1) },
                    icon = { Icon(imageVector = Icons.AutoMirrored.Filled.MenuBook, contentDescription = "Learning") },
                    label = { Text("ምን ይማራሉ?", fontSize = 10.sp, fontWeight = if (currentTab == 1) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_learning")
                )

                NavigationBarItem(
                    selected = (currentTab == 2),
                    onClick = { viewModel.onTabSelected(2) },
                    icon = { Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Add Student") },
                    label = { Text("ተማሪ መዝግብ", fontSize = 10.sp, fontWeight = if (currentTab == 2) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_add_student")
                )

                NavigationBarItem(
                    selected = (currentTab == 3),
                    onClick = { viewModel.onTabSelected(3) },
                    icon = { Icon(imageVector = Icons.Default.People, contentDescription = "Students") },
                    label = { Text("ተማሪዎች", fontSize = 10.sp, fontWeight = if (currentTab == 3) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_students")
                )

                NavigationBarItem(
                    selected = (currentTab == 4),
                    onClick = { viewModel.onTabSelected(4) },
                    icon = { Icon(imageVector = Icons.Default.Assessment, contentDescription = "Reports") },
                    label = { Text("ሪፖርት", fontSize = 10.sp, fontWeight = if (currentTab == 4) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_reports")
                )

                NavigationBarItem(
                    selected = (currentTab == 5),
                    onClick = { viewModel.onTabSelected(5) },
                    icon = { Icon(imageVector = Icons.Default.Info, contentDescription = "About") },
                    label = { Text("መረጃ", fontSize = 10.sp, fontWeight = if (currentTab == 5) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_tab_info")
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                0 -> TakeAttendanceScreen(
                    students = filteredStudents,
                    attendanceRecords = attendanceRecords,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
                    selectedDepartment = selectedDepartment,
                    onDepartmentSelected = { viewModel.onDepartmentFilterSelected(it) },
                    onMarkAttendance = { id, status, note ->
                        viewModel.markAttendance(id, status, note)
                    },
                    onMarkAllPresent = { viewModel.markAllPresent() }
                )

                1 -> LearningProgressScreen(
                    students = filteredStudents,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
                    selectedDepartment = selectedDepartment,
                    onDepartmentSelected = { viewModel.onDepartmentFilterSelected(it) },
                    onUpdateLesson = { student, lesson, level ->
                        viewModel.updateStudentLesson(student, lesson, level)
                    }
                )

                2 -> AddStudentScreen(
                    onAddStudent = { name, code, dept, phone, lesson, level, guardian, note ->
                        viewModel.addStudent(
                            fullName = name,
                            code = code,
                            department = dept,
                            phone = phone,
                            currentLesson = lesson,
                            lessonLevel = level,
                            guardianName = guardian,
                            note = note
                        )
                        viewModel.onTabSelected(1) // Navigate to "What they are learning" page after adding
                    }
                )

                3 -> StudentListScreen(
                    students = filteredStudents,
                    attendanceRecords = allAttendanceRecords,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
                    onOpenAddStudent = { viewModel.onTabSelected(2) },
                    onUpdateStudent = { viewModel.updateStudent(it) },
                    onDeleteStudent = { viewModel.deleteStudent(it) }
                )

                4 -> ReportsScreen(
                    students = allStudents,
                    allAttendanceRecords = allAttendanceRecords,
                    selectedSession = selectedSession
                )

                5 -> AbinetInfoScreen()
            }
        }
    }

    if (isAddStudentDialogOpen) {
        AddStudentDialog(
            onDismiss = { viewModel.isAddStudentDialogOpen.value = false },
            onConfirm = { name, code, dept, phone, guardian, note ->
                viewModel.addStudent(
                    fullName = name,
                    code = code,
                    department = dept,
                    phone = phone,
                    guardianName = guardian,
                    note = note
                )
            }
        )
    }
}
