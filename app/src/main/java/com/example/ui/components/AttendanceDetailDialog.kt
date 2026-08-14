package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AttendanceStatus
import com.example.data.Student

@Composable
fun AttendanceDetailDialog(
    student: Student,
    currentStatus: AttendanceStatus,
    currentNote: String,
    onDismiss: () -> Unit,
    onSave: (status: AttendanceStatus, note: String) -> Unit
) {
    var selectedStatus by remember { mutableStateOf(currentStatus) }
    var note by remember { mutableStateOf(currentNote) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = student.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "መለያ፡ ${student.studentCode}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "የሁኔታ መረጣ፡",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                AttendanceStatus.entries.forEach { status ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedStatus == status),
                            onClick = { selectedStatus = status },
                            modifier = Modifier.testTag("radio_${status.name}")
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = status.nameAmharic,
                            color = status.getColor(),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("ምክንያት / ማስታወሻ") },
                    placeholder = { Text("ምሳሌ፡ ለልመና አልተመለሰም / በሕመም") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("attendance_note_input")
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(selectedStatus, note) },
                modifier = Modifier.testTag("save_attendance_detail_button")
            ) {
                Text("አስቀምጥ", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("ሰርዝ")
            }
        },
        shape = RoundedCornerShape(18.dp)
    )
}
