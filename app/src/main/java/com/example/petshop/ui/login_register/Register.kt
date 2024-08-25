package com.example.petshop.ui.login_register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petshop.R
import com.example.petshop.database.controller.AccountController
import com.example.petshop.model.Screen
import com.example.petshop.ui.theme.PetShopTheme

@Composable
fun Register(
    modifier: Modifier = Modifier,
    navController: NavController,
    onRegisterClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
                .weight(1f)

        ) {
            var phoneText by remember { mutableStateOf("") }
            var passwordText by remember { mutableStateOf("") }

            Logo()
            Spacer(modifier = Modifier.height(8.dp))
            TypingSdtField(
                title = "Số điện thoại",
                placeholder = "Nhập số điện thoại của bạn",
                value = phoneText,
                keyboardType = KeyboardType.Phone,
                onPhoneChange = { newText -> phoneText = newText }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TypingSdtField(
                title = "Mật khẩu",
                placeholder = "Mật khẩu",
                value = passwordText,
                keyboardType = KeyboardType.Password,
                onPhoneChange = { newText -> passwordText = newText }
            )
            Spacer(modifier = Modifier.height(28.dp))
            Button(
                title = "Đăng ký",
                isDisable = if (phoneText == "" || passwordText == "") true else false,
                onClick = {
                    val pass = passwordText.toString()
                    val sdt = phoneText.toString()

                    AccountController.registerAccount(sdt, pass) { success ->
                        if (success) {
                            // Hiện toast thông báo đã thêm vào giỏ hàng
                            Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.LoginScreen.route)
                        } else {
                            Toast.makeText(
                                context,
                                "Số điện thoại đã được sử dụng",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        Row {
            Text(
                text = "Đã có tài khoản?",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = " Đăng nhập",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                ),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.RegisterScreen.route) { inclusive = true }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TypingNameField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Tên",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(horizontal = 20.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        val keyboardController = LocalSoftwareKeyboardController.current // để ẩn bàn phím
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = "Nhập tên của bạn")
            },
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
        )
    }
}

@Preview
@Composable
fun RegisterPreview() {
    PetShopTheme {
        Register(
            navController = rememberNavController()
        )
    }
}