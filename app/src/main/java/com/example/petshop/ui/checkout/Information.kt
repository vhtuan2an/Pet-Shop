package com.example.petshop.ui.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petshop.model.DeliveryMethod
import com.example.petshop.model.PaymentMethod
import com.example.petshop.view_model.OrderViewModel
import com.example.petshop.view_model.UserViewModel

@Composable
fun Information(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    onEditAddressClick: () -> Unit = { /*TODO*/ },
) {
    val user by userViewModel.currentUser.collectAsState()

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = "Thông tin người nhận",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight(300),
                color = Color(0xFF3C3C3C),
            ),
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = user.phone,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight(300),
                color = Color(0xFF3C3C3C),
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Địa chỉ",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.address,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight(300),
                color = Color(0xFF3C3C3C),
            ),
        )

        Row(
            modifier = Modifier
                .width(100.dp)
                .height(25.dp)
                .clickable { onEditAddressClick() }
                .align(Alignment.End)
        ) {
            Text(
                text = "Chỉnh sửa",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            IconButton(
                onClick = onEditAddressClick,
                modifier = Modifier.padding(all = 0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.padding(all = 0.dp)
                )
            }
        }
    }
}

@Composable
fun Delivery(
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel,
    onPaymentClick: () -> Unit = {},
    onVoucherClick: () -> Unit = {}
) {
    val order by orderViewModel.currentOrder.collectAsState()

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Phương thức giao hàng",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "*Chúng tôi mở cửa từ 8:00 - 18:00",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight(300),
                color = Color(0xFF555555),
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        DeliveryOption(
            deliveryMethod = DeliveryMethod.NORMAL,
            selected = order.deliveryMethod == DeliveryMethod.NORMAL,
            onClick = {
                //orderViewModel.updateDeliveryMethod(DeliveryMethod.NORMAL)
                orderViewModel.updateOrder(
                    order.copy(
                        deliveryMethod = DeliveryMethod.NORMAL
                    )
                )
            }
        )
        Divider()
        DeliveryOption(
            deliveryMethod = DeliveryMethod.FAST,
            selected = order.deliveryMethod == DeliveryMethod.FAST,
            onClick = {
                //orderViewModel.updateDeliveryMethod(DeliveryMethod.FAST)
                orderViewModel.updateOrder(
                    order.copy(
                        deliveryMethod = DeliveryMethod.FAST
                    )
                )
            }
        )
        Divider()
        Spacer(modifier = Modifier.height(4.dp))
        PaymentOption(
            title = "Phương thức thanh toán",
            description = when (order.paymentMethod) {
                PaymentMethod.CASH -> "Thanh toán khi nhận hàng"
                PaymentMethod.BANK -> "Chuyển khoản ngân hàng"
            },
            onClick = onPaymentClick,
        )
        Divider()
        PaymentOption(
            title = "Voucher",
            description = when (order.voucher) {
                null -> "Không có voucher nào được chọn"
                else -> order.voucher!!.title
            },
            onClick = onVoucherClick
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun DeliveryOption(
    deliveryMethod: DeliveryMethod,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Column {
            Text(
                text = when (deliveryMethod) {
                    DeliveryMethod.FAST -> "Hỏa tốc"
                    DeliveryMethod.NORMAL -> "Thường"
                },
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight(500),
                    color = Color(0xFF3C3C3C),
                ),
            )
            Text(
                text = when (deliveryMethod) {
                    DeliveryMethod.FAST -> "Khoảng 30 phút"
                    DeliveryMethod.NORMAL -> "Khoảng 1 đến 2 ngày"
                },
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight(300),
                    color = Color(0xFF555555),
                ),
            )
        }
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}

@Composable
fun PaymentOption(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight(300),
                        color = Color(0xFF555555),
                    ),
                )
            }
        }
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(all = 0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(all = 0.dp)

            )
        }
    }
}

@Composable
fun PaymentDetail(
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel,
) {
    val order by orderViewModel.currentOrder.collectAsState()

    //val currentDiscount by orderViewModel.currentDiscount.collectAsState()
    //println("Voucher in PaymentDetail: ${order.voucher?.title}")

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = "Chi tiết thanh toán",
            style = MaterialTheme.typography.titleMedium,
        )

        PaymentDetailRow(
            label = "Tổng tiền hàng",
            value = "${order.productTotal.toInt()} đ",
            description = "(${order.products.size} sản phẩm)",
            isBold = true,
        )

        PaymentDetailRow(
            label = "Voucher",
            value = "-${order.discount.toInt()} đ",
            isBold = true,
        )

        PaymentDetailRow(
            label = "Phí vận chuyển",
            value = "${order.shippingFee.toInt()} đ",
            isBold = true,
        )

        PaymentDetailRow(
            label = "Tổng cộng",
            value = "${(order.total).toInt()} đ",
            isBold = true,
        )
    }
}


@Composable
fun PaymentDetailRow(
    label: String,
    value: String,
    description: String = "",
    isBold: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = if (isBold) FontWeight(500) else FontWeight(400),
                    color = Color(0xFF3C3C3C),
                ),
            )
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight(300),
                        color = Color(0xFF555555),
                    ),
                )
            }
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isBold) FontWeight(500) else FontWeight(400),
                color = Color(0xFF3C3C3C),
                textAlign = TextAlign.Right,
            ),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun InformationPreview() {
    Information(
        userViewModel = UserViewModel()
    )
}

@Preview(showBackground = true)
@Composable
fun DeliveryPreview() {
    Delivery(
        orderViewModel = OrderViewModel()
    )
}

@Preview(showBackground = true)
@Composable
fun PaymentDetailPreview() {
    PaymentDetail(
        orderViewModel = OrderViewModel()
    )
}