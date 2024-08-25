package com.example.petshop.ui.login_register


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petshop.R
import com.example.petshop.database.controller.AccountController
import com.example.petshop.model.Screen
import com.example.petshop.model.User
import com.example.petshop.ui.theme.PetShopTheme
import com.example.petshop.view_model.CartViewModel
import com.example.petshop.view_model.ChatViewModel
import com.example.petshop.view_model.ProductViewModel
import com.example.petshop.view_model.UserViewModel

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel,
    cartViewModel: CartViewModel,
    chatViewModel: ChatViewModel,
    onLoginClick: () -> Unit = {}
) {
    val currentUser by userViewModel.currentUser.collectAsState()
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(it),
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
                    title = "Đăng nhập",
                    isDisable = phoneText == "" || passwordText == "",
                    onClick = {
                        val pass = passwordText
                        val sdt = phoneText

                        AccountController.Login(sdt, pass) { success ->
                            if (success) {
                                AccountController.getAccountByNumberphone(sdt) { account ->
                                    if (account != null) {
                                        userViewModel.updateUser(
                                            User(
                                                user_id = account.user_id.toString(),
                                                name = account.name.toString(),
                                                role = "Khách hàng thân thiết",
                                                favoriteProducts = listOf(),
                                                sex = account.sex.toString(),
                                                email = account.email.toString(),
                                                phone = account.numberphone.toString(),
                                                avatar = R.drawable.avatar,
                                                birthday = account.birthDay.toString(),
                                                password = account.password,
                                                address = account.address.toString(),
                                            )
                                        )
                                        chatViewModel.up_set_UserId(account.user_id.toString())
                                        chatViewModel.fetchMessagesFromFirebase()

                                        cartViewModel.up_set_UserId(account.user_id.toString())
                                        cartViewModel.getProductsByUserId()

                                        if (currentUser.name == "Chưa đặt") {
                                            navController.navigate(Screen.HomeScreen.route) {
                                                popUpTo(Screen.LoginScreen.route) {
                                                    inclusive = true
                                                }
                                            }
                                            navController.navigate(Screen.EditProfileScreen.route)
                                        } else {
                                            navController.navigate(Screen.HomeScreen.route) {
                                                popUpTo(Screen.LoginScreen.route) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    } else {
                                        println("Không tìm thấy tài khoản")
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Sai thông tin đăng nhập",
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
                    text = "Bạn chưa có tài khoản?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = " Đăng ký",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.RegisterScreen.route) {
                            popUpTo(Screen.LoginScreen.route) { inclusive = true }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    isDisable: Boolean = true,
    onClick: () -> Unit,
    title: String,
    color: Color = Color(0xFF5D4037)
) {
    Button(
        enabled = !isDisable,
        modifier = modifier
            .sizeIn(minHeight = 48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = Color(0xFFCACACA)
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun TypingSdtField(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    value: String,
    onPhoneChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current // để ẩn bàn phím
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(horizontal = 20.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = value,
            onValueChange = onPhoneChange,
            placeholder = {
                Text(text = placeholder)
            },
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
        )
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo_tranfer),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .width(308.dp)
            .height(190.dp)
    )
}

@Preview
@Composable
fun LoginPreview() {
    PetShopTheme {
        Login(
            navController = rememberNavController(),
            userViewModel = UserViewModel(),
            cartViewModel = CartViewModel(productViewModel = ProductViewModel()),
            chatViewModel = ChatViewModel()
        )
    }
}