package com.example.phonestore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductDetailScreen(
    phone: Phone,
    onBack: () -> Unit,
    onAddToCart: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2563EB))
                .padding(8.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,
                    contentDescription =
                        "Quay lại",
                    tint = Color.White
                )
            }

            Text(
                text = "Chi tiết sản phẩm",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp)
        ) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(22.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    )
            ) {

                Image(
                    painter =
                        painterResource(
                            id = phone.image
                        ),
                    contentDescription =
                        phone.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(18.dp),
                    contentScale =
                        ContentScale.Fit
                )
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text =
                        phone.category.uppercase(),
                    color =
                        Color(0xFF2563EB),
                    fontWeight =
                        FontWeight.Bold
                )

                Text(
                    text = phone.discount,
                    color = Color.White,
                    fontWeight =
                        FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            Color(0xFFEF4444),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(
                            horizontal = 9.dp,
                            vertical = 5.dp
                        )
                )
            }

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            Text(
                text = phone.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827)
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            Text(
                text = phone.price,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFDC2626)
            )

            Text(
                text = phone.oldPrice,
                color = Color(0xFF94A3B8),
                fontSize = 15.sp
            )

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(18.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    Text(
                        text =
                            "Thông tin sản phẩm",
                        fontSize = 19.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    ProductDetailInfoRow(
                        "Thương hiệu",
                        phone.category
                    )

                    ProductDetailInfoRow(
                        "Tình trạng",
                        "Hàng mới chính hãng"
                    )

                    ProductDetailInfoRow(
                        "Bảo hành",
                        "12 tháng"
                    )

                    ProductDetailInfoRow(
                        "Đổi trả",
                        "Trong 30 ngày"
                    )

                    ProductDetailInfoRow(
                        "Giao hàng",
                        "Toàn quốc"
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(18.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Mô tả sản phẩm",
                        fontSize = 19.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    Text(
                        text =
                            "${phone.name} chính hãng với thiết kế " +
                                    "hiện đại, hiệu năng mạnh mẽ và trải nghiệm " +
                                    "ổn định. Sản phẩm phù hợp cho học tập, " +
                                    "làm việc, giải trí và nhu cầu sử dụng " +
                                    "hằng ngày.\n\n" +
                                    "Sản phẩm được kiểm tra trước khi giao đến " +
                                    "khách hàng và đi kèm chính sách bảo hành.",
                        color =
                            Color(0xFF64748B),
                        fontSize = 14.sp,
                        lineHeight = 23.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape =
                    RoundedCornerShape(15.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF2563EB)
                    )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ShoppingCart,
                    contentDescription = null
                )

                Spacer(
                    modifier =
                        Modifier.width(8.dp)
                )

                Text(
                    text =
                        "THÊM VÀO GIỎ HÀNG",
                    fontSize = 16.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )
        }
    }
}

@Composable
private fun ProductDetailInfoRow(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color =
                Color(0xFF64748B),
            fontSize = 14.sp
        )

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight =
                FontWeight.SemiBold
        )
    }
}