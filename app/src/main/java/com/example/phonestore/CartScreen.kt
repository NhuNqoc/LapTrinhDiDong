package com.example.phonestore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartScreen(
    cartItems: SnapshotStateList<CartItem>,
    onBack: () -> Unit,
    onCheckout: () -> Unit
) {

    val total = cartItems.sumOf { item ->

        val price = item.phone.price
            .replace(".", "")
            .replace("đ", "")
            .replace(",", "")
            .trim()
            .toLongOrNull() ?: 0L

        price * item.quantity
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
    ) {

        // ==============================
        // HEADER
        // ==============================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2563EB))
                .padding(
                    horizontal = 8.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = Color.White
                )
            }

            Column {

                Text(
                    text = "Giỏ hàng",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${cartItems.size} sản phẩm",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }

        // ==============================
        // GIỎ HÀNG TRỐNG
        // ==============================

        if (cartItems.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(65.dp),
                    tint = Color(0xFF94A3B8)
                )

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                Text(
                    text = "Giỏ hàng đang trống",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Button(
                    onClick = onBack
                ) {

                    Text("TIẾP TỤC MUA SẮM")
                }
            }

        } else {

            // ==============================
            // DANH SÁCH
            // ==============================

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = cartItems,
                    key = { it.phone.name }
                ) { item ->

                    CartProductRow(
                        item = item,

                        onIncrease = {

                            val index =
                                cartItems.indexOfFirst {
                                    it.phone.name == item.phone.name
                                }

                            if (index >= 0) {

                                cartItems[index] =
                                    item.copy(
                                        quantity =
                                            item.quantity + 1
                                    )
                            }
                        },

                        onDecrease = {

                            val index =
                                cartItems.indexOfFirst {
                                    it.phone.name == item.phone.name
                                }

                            if (index >= 0) {

                                if (item.quantity > 1) {

                                    cartItems[index] =
                                        item.copy(
                                            quantity =
                                                item.quantity - 1
                                        )

                                } else {

                                    cartItems.removeAt(index)
                                }
                            }
                        },

                        onDelete = {

                            cartItems.removeAll {
                                it.phone.name == item.phone.name
                            }
                        }
                    )
                }
            }

            // ==============================
            // TỔNG TIỀN
            // ==============================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(17.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Tổng cộng",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = formatCartPrice(total),
                            color = Color(0xFFDC2626),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Button(
                        onClick = onCheckout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Text(
                            text = "TIẾN HÀNH THANH TOÁN",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


// ==========================================
// CART PRODUCT ROW
// ==========================================

@Composable
private fun CartProductRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = item.phone.image
                ),
                contentDescription = item.phone.name,
                modifier = Modifier.size(85.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = item.phone.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = item.phone.price,
                    color = Color(0xFFDC2626),
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier.size(30.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Giảm"
                        )
                    }

                    Text(
                        text = item.quantity.toString(),
                        modifier = Modifier.padding(
                            horizontal = 8.dp
                        ),
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = onIncrease,
                        modifier = Modifier.size(30.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tăng"
                        )
                    }
                }
            }

            IconButton(
                onClick = onDelete
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Xóa",
                    tint = Color(0xFFEF4444)
                )
            }
        }
    }
}


// ==========================================
// FORMAT PRICE
// ==========================================

private fun formatCartPrice(
    price: Long
): String {

    return String
        .format("%,dđ", price)
        .replace(",", ".")
}