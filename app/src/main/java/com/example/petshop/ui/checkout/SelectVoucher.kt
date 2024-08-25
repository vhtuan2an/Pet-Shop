package com.example.petshop.ui.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petshop.model.Voucher
import com.example.petshop.ui.theme.PetShopTheme
import com.example.petshop.view_model.OrderViewModel


@Composable
fun SelectVoucher(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    orderViewModel: OrderViewModel,
    onSearchVoucher: (String) -> Unit = {},
) {
    val allVouchers by orderViewModel.allVouchers.collectAsState()
    val order by orderViewModel.currentOrder.collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (order.voucher != null)
                VoucherStatus(
                    onClick = { navController?.popBackStack() },
                    discount = order.discount,
                    numVoucherSelected = 1
                )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            var searchText by remember { mutableStateOf("") }
            SearchBox(
                value = searchText,
                onValueChange = { newText ->
                    searchText = newText
                },
                onDone = onSearchVoucher
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        allVouchers.forEachIndexed { index, voucher ->
                            Voucher(
                                voucher = voucher,
                                isSelected = order.voucher == voucher,
                                isDisable = order.productTotal < voucher.minOrderValue,
                                warning = "Mua thêm ${(voucher.minOrderValue - order.productTotal).toInt()} đ để sử dụng voucher",
                                onClick = {

                                    val discount = orderViewModel.calculateDiscount(order, voucher)
                                    orderViewModel.updateOrder(
                                        order.copy(
                                            voucher = voucher,
                                            discount = discount
                                        )
                                    )
                                }
                            )
                            if (index != allVouchers.lastIndex) {
                                Divider(color = Color(0xFFD9D9D9))
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun Voucher(
    modifier: Modifier = Modifier,
    voucher: Voucher,
    isSelected: Boolean = false,
    isDisable: Boolean = false,
    warning: String = "Không thể sử dụng",
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier =
        if (!isDisable)
            modifier
                .fillMaxWidth()
                .clickable { onClick() }
        else
            modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = voucher.image),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Fit
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = voucher.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = voucher.description,
                style = MaterialTheme.typography.bodyMedium,
            )
            if (isDisable)
                Text(
                    text = warning,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFFE33131)
                    )
                )
        }
        if (!isDisable)
            RadioButton(
                selected = isSelected,
                onClick = onClick,
            )
    }
}

@Composable
fun SearchBox(
    value: String = "",
    onValueChange: (String) -> Unit,
    onDone: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current // để ẩn bàn phím
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(text = "Nhập mã giảm giá ở đây")
        },
        shape = RoundedCornerShape(16.dp),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone(value)
                keyboardController?.hide()
            }
        ),

        )
}

@Composable
fun VoucherStatus(
    modifier: Modifier = Modifier,
    numVoucherSelected: Int = 0,
    discount: Double = 0.0,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Đã chọn $numVoucherSelected voucher",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp
                ),
            )
            if (discount > 0.0)
                Text(
                    text = "Giảm ${discount.toInt()} đ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = Color(0xFF46AE7C)
                    ),
                )
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46AE7C)),
            onClick = onClick
        ) {
            Text(
                text = "Sử dụng",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
fun VoucherStatusPreview() {
    PetShopTheme {
        VoucherStatus(
            numVoucherSelected = 1,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun SelectVoucherPreview() {
    PetShopTheme {
        SelectVoucher(
            orderViewModel = OrderViewModel()
        )
    }
}