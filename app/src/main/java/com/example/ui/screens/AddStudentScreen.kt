package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Department
import com.example.ui.components.SimpleCrossIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(
    onAddStudent: (
        fullName: String,
        code: String,
        department: Department,
        phone: String,
        currentLesson: String,
        lessonLevel: String,
        guardianName: String,
        note: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var selectedDepartment by remember { mutableStateOf(Department.NIBEB_TSELOT) }
    var currentLesson by remember { mutableStateOf("") }
    var lessonLevel by remember { mutableStateOf("መካከለኛ ደረጃ") }
    var phone by remember { mutableStateOf("") }
    var guardianName by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    var isDeptExpanded by remember { mutableStateOf(false) }
    var isSubjectExpanded by remember { mutableStateOf(false) }
    var isLevelExpanded by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    val levelOptions = listOf("መጀመሪያ ደረጃ", "መካከለኛ ደረጃ", "ከፍተኛ ደረጃ", "የአስመሳይ/የመምህርነት ደረጃ")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Page Title Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("add_student_page_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "አዲስ ተማሪና የሚማሩትን መዝግብ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "ተማሪዎችን ከእነ ክፍልና የሚከታተሉት ትምህርት ይመዝግቡ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section 1: Basic Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "1. የተማሪው መሠረታዊ መረጃ",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        if (it.isNotBlank()) isError = false
                    },
                    label = { Text("የተማሪው ሙሉ ስም *") },
                    placeholder = { Text("ምሳሌ፡ ገብረ ማርያም ወልደ ኢየሱስ") },
                    isError = isError,
                    supportingText = if (isError) { { Text("የተማሪ ስም ማስገባት ግዴታ ነው") } } else null,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_full_name")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("ስልክ ቁጥር") },
                        placeholder = { Text("09...") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_student_phone")
                    )

                    OutlinedTextField(
                        value = guardianName,
                        onValueChange = { guardianName = it },
                        label = { Text("የመጠጊያ/የመምህሩ ስም") },
                        placeholder = { Text("ምሳሌ፡ መምህር ወልደ ጊዮርጊስ") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1.2f)
                            .testTag("input_guardian_name")
                    )
                }
            }
        }

        // Section 2: Learning Information
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoStories,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "2. የሚማሩት ክፍልና ትምህርት",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Department Dropdown
                ExposedDropdownMenuBox(
                    expanded = isDeptExpanded,
                    onExpandedChange = { isDeptExpanded = !isDeptExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedDepartment.nameAmharic,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("የትምህርት ክፍል *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDeptExpanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                            .testTag("dropdown_department")
                    )

                    ExposedDropdownMenu(
                        expanded = isDeptExpanded,
                        onDismissRequest = { isDeptExpanded = false }
                    ) {
                        Department.entries.forEach { dept ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(dept.nameAmharic, fontWeight = FontWeight.Bold)
                                        Text(
                                            dept.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                onClick = {
                                    selectedDepartment = dept
                                    currentLesson = dept.subjects.firstOrNull() ?: ""
                                    isDeptExpanded = false
                                }
                            )
                        }
                    }
                }

                // Subject / Book Dropdown Selector under Selected Department
                ExposedDropdownMenuBox(
                    expanded = isSubjectExpanded,
                    onExpandedChange = { isSubjectExpanded = !isSubjectExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = currentLesson,
                        onValueChange = { currentLesson = it },
                        label = { Text("የሚማሩት መጽሐፍ / ትምህርት *") },
                        placeholder = { Text("ከዝርዝር ይምረጡ ወይም ይጻፉ") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSubjectExpanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryEditable)
                            .fillMaxWidth()
                            .testTag("input_current_lesson")
                    )

                    ExposedDropdownMenu(
                        expanded = isSubjectExpanded,
                        onDismissRequest = { isSubjectExpanded = false }
                    ) {
                        selectedDepartment.subjects.forEach { subject ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Bookmark,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(subject)
                                    }
                                },
                                onClick = {
                                    currentLesson = subject
                                    isSubjectExpanded = false
                                }
                            )
                        }
                    }
                }

                // Lesson Level Dropdown
                ExposedDropdownMenuBox(
                    expanded = isLevelExpanded,
                    onExpandedChange = { isLevelExpanded = !isLevelExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = lessonLevel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("የትምህርት ደረጃ") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLevelExpanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                            .testTag("dropdown_lesson_level")
                    )

                    ExposedDropdownMenu(
                        expanded = isLevelExpanded,
                        onDismissRequest = { isLevelExpanded = false }
                    ) {
                        levelOptions.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level) },
                                onClick = {
                                    lessonLevel = level
                                    isLevelExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("ተጨማሪ ማስታወሻ") },
                    placeholder = { Text("ምሳሌ፡ የቤተ ክርስቲያን አገልግሎት ይከታተላል") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Submit Button
        Button(
            onClick = {
                if (fullName.isBlank()) {
                    isError = true
                } else {
                    val finalLesson = currentLesson.ifBlank {
                        selectedDepartment.subjects.firstOrNull() ?: "የ${selectedDepartment.nameAmharic} መሠረታዊ ትምህርት"
                    }
                    val generatedCode = "ABN-" + (1000..9999).random()
                    onAddStudent(
                        fullName,
                        generatedCode,
                        selectedDepartment,
                        phone,
                        finalLesson,
                        lessonLevel,
                        guardianName,
                        note
                    )
                    Toast.makeText(context, "$fullName በጥሩ ሁኔታ ተመዝግቧል!", Toast.LENGTH_SHORT).show()
                    fullName = ""
                    currentLesson = ""
                    phone = ""
                    guardianName = ""
                    note = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_add_student_button")
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ተማሪውን መዝግብ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Footer Card: "የአብነት ተማሪዎች"
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .testTag("footer_add_student_card"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleCrossIcon(size = 22.dp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "የአብነት ተማሪዎች",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "ሰበታ መካነ ሰላም ቅዱስ ገብርኤል አብነት ተማሪዎች",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(70.dp))
    }
}
