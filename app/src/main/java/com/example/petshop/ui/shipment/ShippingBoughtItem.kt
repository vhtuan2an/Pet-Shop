package com.example.petshop.ui.shipment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petshop.R
import com.example.petshop.ui.theme.PetShopTheme


@Composable
fun BoughtItemShipping() {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .height(150.dp)
            .background(color = Color(0xFFFFFFFF))
            .padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(79.dp)
                .background(color = Color(0xFFFFFFFF))
                .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 13.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.width(58.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.product_image),
                    contentDescription = null,
                    modifier = Modifier
                        .shadow(
                            elevation = 2.2138051986694336.dp,
                            spotColor = Color(0x05000000),
                            ambientColor = Color(0x05000000)
                        )
                        .shadow(
                            elevation = 5.32008171081543.dp,
                            spotColor = Color(0x07000000),
                            ambientColor = Color(0x07000000)
                        )
                        .shadow(
                            elevation = 10.017241477966309.dp,
                            spotColor = Color(0x09000000),
                            ambientColor = Color(0x09000000)
                        )
                        .shadow(
                            elevation = 17.869047164916992.dp,
                            spotColor = Color(0x0B000000),
                            ambientColor = Color(0x0B000000)
                        )
                        .shadow(
                            elevation = 33.422088623046875.dp,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color(0x0D000000)
                        )
                        .width(58.dp)
                        .height(69.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "Thức ăn hạt mềm Zenith",
                    // Body/14/Medium
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF555555),
                    ),
                    modifier = Modifier
                        .height(20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nổi tiếng với đồ ăn cho chó con được yêu thích",
                    // Body/12/Regular
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF555555),
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))
            }
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(22.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(29.dp)
                .background(color = Color(0xFFFFFFFF))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start),
                verticalAlignment = Alignment.Top
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier
                        .height(23.dp)
                        .background(
                            color = Color(0xFF5D4037),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = "0.5kg",
                        style = TextStyle(
                            fontSize = 10.sp,
                            //fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFEFEFE),
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .width(53.dp)
                        .height(23.dp)
                        .background(
                            color = Color(0xFF5D4037),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
                )
                {
                    Text(
                        text = "Cá hồi",
                        style = TextStyle(
                            fontSize = 10.sp,
                            //fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFEFEFE),
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "45.000 đ",

                        // Body/14/Medium
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            //fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF3C3C3C),
                            textAlign = TextAlign.Center
                        ),

                        modifier = Modifier
                            .width(75.dp)
                            .height(29.dp)
                    )

                    Text(
                        text = "x2",

                        // Body/14/Medium
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            //fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(350),
                            color = Color(0xFF3C3C3C),
                            textAlign = TextAlign.Center
                        ),

                        modifier = Modifier
                            .height(29.dp)
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Thành tiền: 90.000 đ",

                // Body/14/Medium
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    //fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFEE2828),
                    textAlign = TextAlign.Center
                ),

                modifier = Modifier
                    .padding(top = 3.dp)
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(28.dp)
                    .height(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFFEE2828)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoughtItemShippingPreview() {
    PetShopTheme {
        BoughtItemShipping()
    }
}