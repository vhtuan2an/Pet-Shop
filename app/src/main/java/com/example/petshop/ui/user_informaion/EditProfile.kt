package com.example.petshop.ui.user_informaion

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petshop.database.controller.AccountController
import com.example.petshop.database.model.Account
import com.example.petshop.view_model.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    userViewModel: UserViewModel,
) {
    val user by userViewModel.currentUser.collectAsState()
    val context = LocalContext.current
    var showEditDialog by remember { mutableStateOf(false) }
    var editField by remember { mutableStateOf("") }
    var editValue by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(user.sex) }
    var showDatePicker by remember { mutableStateOf(false) }

    val openEditDialog: (String, String) -> Unit = { field, value ->
        editField = field
        editValue = value
        when (field) {
            "Giới tính" -> selectedGender = value
            "Ngày sinh" -> showDatePicker = true
        }
        if (field != "Ngày sinh") {
            showEditDialog = true
        }
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text(text = "Chỉnh sửa $editField") },
            text = {
                when (editField) {
                    "Giới tính" -> GenderPicker(selectedGender) { selectedGender = it }
                    "Ngày sinh" -> {
                        showDatePicker = true
                        Text(text = editValue)
                    }

                    else -> TextField(
                        value = editValue,
                        onValueChange = { editValue = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showEditDialog = false
                        userViewModel.updateUser(
                            user.copy(
                                name = if (editField == "Tên") editValue else user.name,
                                sex = if (editField == "Giới tính") selectedGender else user.sex,
                                birthday = if (editField == "Ngày sinh") editValue else user.birthday,
                                phone = if (editField == "Số điện thoại") editValue else user.phone,
                                email = if (editField == "Email") editValue else user.email,
                                address = if (editField == "Địa chỉ") editValue else user.address
                            )
                        )
                    }
                ) {
                    Text("Lưu")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled =
            remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        editValue = datePickerState.selectedDateMillis?.let { millis ->
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))
                        } ?: editValue
                        showEditDialog = false
                        // Update user birthday
                        userViewModel.updateUser(
                            user.copy(birthday = editValue)
                        )
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    showEditDialog = false
                }) {
                    Text("Hủy")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = user.avatar),
                            contentDescription = "User Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    TextButton(
                        onClick = {
                            Toast.makeText(context, "Chức năng đang phát triển", Toast.LENGTH_SHORT)
                                .show()
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            "Sửa",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }

            items(
                listOf(
                    "Tên" to user.name,
                    "Giới tính" to user.sex,
                    "Ngày sinh" to user.birthday,
                    "Số điện thoại" to user.phone,
                    "Email" to user.email,
                    "Địa chỉ" to user.address
                )
            ) { (label, value) ->
                Box(
                    modifier = Modifier.clickable { openEditDialog(label, value) }
                ) { ProfileItem(label, value) }
            }
        }

        com.example.petshop.ui.login_register.Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                val account = Account(
                    user_id = user.user_id,
                    numberphone = user.phone,
                    password = user.password,
                    name = user.name,
                    email = user.email,
                    sex = user.sex,
                    address = user.address,
                    birthDay = user.birthday
                )
                AccountController.editProfile(account) { success ->
                    if (success) {
                        Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Thay đổi thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
                navController?.popBackStack()
            },
            title = "Xong",
            isDisable = false,
            color = Color(0xFF4CAF50)
        )
    }
}

@Composable
fun ProfileItem(
    label: String,
    value: String,
) {
    val iconSize = 24.dp

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun GenderPicker(selectedGender: String, onGenderSelected: (String) -> Unit) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onGenderSelected("Nam") }
        ) {
            RadioButton(
                selected = selectedGender == "Nam",
                onClick = { onGenderSelected("Nam") }
            )
            Text(text = "Nam")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onGenderSelected("Nữ") }
        ) {
            RadioButton(
                selected = selectedGender == "Nữ",
                onClick = { onGenderSelected("Nữ") }
            )
            Text(text = "Nữ")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onGenderSelected("Khác") }
        ) {
            RadioButton(
                selected = selectedGender == "Khác",
                onClick = { onGenderSelected("Khác") }
            )
            Text(text = "Khác")
        }
    }
}

@Preview
@Composable
fun EditProfilePreview() {
    EditProfile(userViewModel = UserViewModel())
}
