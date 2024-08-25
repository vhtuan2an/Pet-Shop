@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
)

package com.example.petshop.ui.user_informaion

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.petshop.R
import com.example.petshop.view_model.UserViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    userViewModel: UserViewModel,
    onEditProfileClicked: () -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onShippingClicked: () -> Unit = {},
    onShippedClicked: () -> Unit = {},
    onEditAddressClicked: () -> Unit = {},
    onFavoriteProductClicked: () -> Unit = {},
    onLogoutClicked: () -> Unit = {},
) {
    val user by userViewModel.currentUser.collectAsState()
    val intentContext = LocalContext.current

    val sheetState = rememberModalBottomSheetState()
    var isAppInforOpen by rememberSaveable { mutableStateOf(false) }
    var isShopInforOpen by rememberSaveable { mutableStateOf(false) }
    var isAlertOpen by rememberSaveable { mutableStateOf(false) }

    val onDismissRequset = {
        isShopInforOpen = false
        isAppInforOpen = false
    }


    if (isShopInforOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequset,
            sheetState = sheetState,
        ) {
            ShopIntroduction()
        }
    }

    if (isAppInforOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequset,
            sheetState = sheetState,
        ) {
            AppIntroduction()
        }
    }

    if (isAlertOpen) {
        AlertDialog(
            onDismissRequest = { isAlertOpen = false },
            title = {
                Text(
                    text = "Đăng xuất",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Bạn có chắc muốn đăng xuất?",
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isAlertOpen = false
                        onLogoutClicked()
                    }
                ) {
                    Text(
                        "Đăng xuất", style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Red
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isAlertOpen = false }
                ) {
                    Text("Hủy")
                }
            }
        )
    }

    LazyColumn(modifier = modifier) {
        item {
            // Personal information
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = user.avatar),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .width(82.dp)
                            .height(82.dp)
                            .clip(CircleShape)
                    )
                }
                if (user.password != null)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = 30.sp,
                                color = Color(0xFF555555),
                            ),
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                        Text(
                            text = user.role,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF48854B),
                            ),
                        )
                        Row(
                            modifier = Modifier
                                .width(100.dp)
                                .height(25.dp)
                                .clickable { onEditProfileClicked() }
                        ) {
                            Text(
                                text = "Chỉnh sửa",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                            IconButton(
                                onClick = { onEditProfileClicked() },
                                modifier = Modifier.padding(all = 0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier.padding(all = 0.dp)
                                )
                            }
                        }
                    } else
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onLoginClicked() }
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "Đăng nhập",
                            style = TextStyle(
                                fontSize = 25.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF555555),
                            ),
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(all = 0.dp)
                                .width(40.dp)
                                .height(40.dp)
                        )
                    }
            }
        }

        item {
            // Orders section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = "Đơn hàng",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = 28.sp,
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(size = 30.dp)
                            )
                            .clip(RoundedCornerShape(size = 30.dp))
                            .width(160.dp)
                            .height(90.dp)
                            .clickable { onShippingClicked() }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fi_truck),
                            contentDescription = null,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Đang giao",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF222222),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = RoundedCornerShape(size = 30.dp)
                            )
                            .clip(RoundedCornerShape(size = 30.dp))
                            .width(160.dp)
                            .height(90.dp)
                            .clickable { onShippedClicked() }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fi_package),
                            contentDescription = null,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Đã giao",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF222222),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }

        item {
            // Settings section
            Column(modifier = Modifier.padding(horizontal = 0.dp)) {
                SettingComponent(
                    text = "Sửa địa chỉ nhận hàng",
                    painter = painterResource(id = R.drawable.location),
                    onClick = { onEditAddressClicked() }
                )
                if (user.password != null) {
                    SettingComponent(
                        text = "Sản phẩm yêu thích",
                        painter = painterResource(id = R.drawable.fav),
                        onClick = { onFavoriteProductClicked() }
                    )
                }
                SettingComponent(
                    text = "Giới thiệu cho bạn bè",
                    painter = painterResource(id = R.drawable.ic_user),
                    onClick = {
                        val shareContent =
                            "Hãy tải ứng dụng Pet Shop ngay đi. Nó có nhiều thứ mà thú cưng cần lắm á!"
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareContent)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        try {
                            ContextCompat.startActivity(intentContext, shareIntent, null)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                intentContext,
                                "No app found to share",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
                SettingComponent(
                    text = "Thông tin cửa hàng",
                    painter = painterResource(id = R.drawable.shop_infor),
                    onClick = { onDismissRequset(); isShopInforOpen = true }
                )
                SettingComponent(
                    text = "Thông tin ứng dụng",
                    painter = painterResource(id = R.drawable.infor),
                    onClick = { onDismissRequset(); isAppInforOpen = true }
                )
                if (user.password != null) {
                    SettingComponent(
                        text = "Đăng xuất",
                        painter = painterResource(id = R.drawable.log_out),
                        color = Color.Red,
                        onClick = { isAlertOpen = true }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}


@Composable
fun SettingComponent(
    painter: Painter,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = Color.Black
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = color,
                ),
            )
        }
    }
}

@Composable
fun ShopIntroduction(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.logo_tranfer),
            contentDescription = "Hình ảnh cửa hàng",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                //.fillMaxWidth()
                .height(180.dp)
                .width(260.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Chào mừng đến với Pet Shop",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Pet Shop  tôi cung cấp một loạt " +
                    "các sản phẩm để đáp ứng tất cả nhu cầu thú cưng của bạn. " +
                    "Từ các thực phẩm mới nhất đến các nhu yếu phẩm hàng ngày, " +
                    "chúng tôi đều có. Cam kết của chúng tôi là cung cấp sản phẩm chất " +
                    "lượng cao với giá cả phải chăng.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Tại sao nên chọn Pet Shop?",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "1. Sản phẩm chất lượng cao\n2. Giá cả phải chăng\n3. Dịch vụ khách hàng tuyệt vời\n4. Giao hàng nhanh chóng",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))
    }
}

@Composable
fun AppIntroduction(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Pet Shop",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Đây là ứng dụng bán hàng online, là sản phẩm của môn học SE114 - Nhập môn ứng dụng di động do giảng viên Huỳnh Thị Hồ Mộng Trinh hướng dẫn và được phát triển bởi:\n" +
                    "      1. Nguyễn Anh Phi\n" +
                    "      2. Nguyễn Công Tú\n" +
                    "      3. Bế Ích Tuân\n" +
                    "      4. Võ Hoàng Tuấn\n" +
                    "      5. Nguyễn Phú Triệu \n\n" +
                    "Ứng dụng vẫn đang trong quá trình phát triển nên có thể xảy ra lỗi nào không mong muốn. Kính mong cô và các bạn góp ý.\n" +
                    "Nhóm chúng mình xin cảm ơn <3 !",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {

    ProfileScreen(
        userViewModel = UserViewModel(),
    )
}

