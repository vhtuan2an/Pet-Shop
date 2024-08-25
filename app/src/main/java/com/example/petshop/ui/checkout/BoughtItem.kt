package com.example.petshop.ui.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petshop.R
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.Product
import com.example.petshop.ui.theme.PetShopTheme

@Composable
fun BoughtItem(
    modifier: Modifier = Modifier,
    p: Product
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .background(color = Color(0xFFFEFEFE))
            .padding(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.avt),
                contentDescription = "image description",
                contentScale = ContentScale.Fit,
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
                    .width(69.dp)
                    .height(69.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.End,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = p.name,

                        // Body/14/Medium
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "${p.price} đ",

                        // Body/14/Medium
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = p.description,

                        // Body/12/Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            //fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF555555),
                        ),

                        modifier = Modifier.width(150.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "x${p.quantity}",

                        // Body/12/Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            //fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF555555),
                            textAlign = TextAlign.Right,
                        )
                    )
                }
            }
        }

        Row(
            //horizontalArrangement = Arrangement.spacedBy(77.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(modifier = Modifier.width(69.dp))

            IconButton(
                onClick = {},
                modifier = Modifier
                    .width(80.dp)
                    .height(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {


                    Text(
                        text = "Chỉnh sửa",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            //fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF3C3C3C),
                            textAlign = TextAlign.Center,

                            ),
                    )

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,

                            )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(20.dp, 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(1.dp)
                            .size(20.dp)
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(20.dp, 20.dp)
                ) {
                    Image(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        /*modifier = Modifier.size(20.dp)*/
                    )
                }

                Text(
                    text = p.quantity.toString(),
                    modifier = Modifier.size(30.dp, 20.dp),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(20.dp, 20.dp)
                ) {
                    Image(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = null,
                        //modifier = Modifier.size(20.dp)
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BoughtItemPreview() {
    PetShopTheme {
        BoughtItem(
            p = FoodProduct(
                id = "1",
                name = "Đồ ăn cho chó",
                description = "Chó rất thích ăn nó",
                price = 10000.0,
                oldPrice = 15000.0,
                star = 4.0,
                quantity = 1,
                image = R.drawable.avt,
                detailDescription = "Chi tiết sản phẩm"
            )
        )
    }
}