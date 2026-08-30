package com.example.phonestore

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

@Composable
fun AdminOrderScreen(
    onLogout: () -> Unit
) {

    val db =
        FirebaseFirestore.getInstance()

    val orders =
        remember {
            mutableStateListOf<Order>()
        }

    // ==========================================
    // ĐỌC ORDERS REALTIME
    // ==========================================

    DisposableEffect(Unit) {

        val listener: ListenerRegistration =
            db.collection("orders")
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        return@addSnapshotListener
                    }

                    orders.clear()

                    snapshot?.documents?.forEach { document ->

                        val order =
                            Order(

                                id =
                                    document.id,

                                userId =
                                    document.getString(
                                        "userId"
                                    ) ?: "",

                                customerName =
                                    document.getString(
                                        "customerName"
                                    ) ?: "",

                                email =
                                    document.getString(
                                        "email"
                                    ) ?: "",

                                phoneNumber =
                                    document.getString(
                                        "phoneNumber"
                                    ) ?: "",

                                address =
                                    document.getString(
                                        "address"
                                    ) ?: "",

                                paymentMethod =
                                    document.getString(
                                        "paymentMethod"
                                    ) ?: "",

                                totalPrice =
                                    document.getLong(
                                        "totalPrice"
                                    ) ?: 0L,

                                status =
                                    document.getString(
                                        "status"
                                    ) ?: "PENDING",

                                createdAt =
                                    document.getLong(
                                        "createdAt"
                                    ) ?: 0L
                            )

                        orders.add(order)
                    }
                }

        onDispose {
            listener.remove()
        }
    }

    // ==========================================
    // GIAO DIỆN
    // ==========================================

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Color(0xFFF5F7FB)
                )
    ) {

        // ==========================================
        // HEADER
        // ==========================================

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF0F172A)
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 10.dp
                    ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        "QUẢN LÝ ĐƠN HÀNG",

                    color =
                        Color.White,

                    fontSize = 20.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    text =
                        "ADMIN • ${orders.size} đơn",

                    color =
                        Color.White.copy(
                            alpha = 0.75f
                        ),

                    fontSize = 11.sp
                )
            }

            IconButton(
                onClick =
                    onLogout
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Logout,

                    contentDescription =
                        "Đăng xuất",

                    tint =
                        Color.White
                )
            }
        }

        // ==========================================
        // KHÔNG CÓ ĐƠN
        // ==========================================

        if (orders.isEmpty()) {

            Column(
                modifier =
                    Modifier.fillMaxSize(),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text =
                        "Chưa có đơn hàng",

                    fontSize = 20.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Text(
                    text =
                        "Đơn khách đặt sẽ xuất hiện tại đây.",

                    color =
                        Color(0xFF64748B),

                    fontSize = 13.sp
                )
            }

        } else {

            LazyColumn(
                modifier =
                    Modifier.fillMaxSize(),

                contentPadding =
                    PaddingValues(14.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = orders,
                    key = {
                        it.id
                    }
                ) { order ->

                    AdminOrderCard(
                        order = order,
                        db = db
                    )
                }
            }
        }
    }
}


// ======================================================
// ORDER CARD
// ======================================================

@Composable
private fun AdminOrderCard(
    order: Order,
    db: FirebaseFirestore
) {

    val statusColor =
        when (order.status) {

            "APPROVED" ->
                Color(0xFF16A34A)

            "CANCELLED" ->
                Color(0xFFDC2626)

            else ->
                Color(0xFFD97706)
        }

    val statusText =
        when (order.status) {

            "APPROVED" ->
                "ĐÃ DUYỆT"

            "CANCELLED" ->
                "ĐÃ HỦY"

            else ->
                "CHỜ DUYỆT"
        }

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    "Đơn #${order.id.take(8)}",

                fontSize = 17.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            AdminInfoRow(
                title = "Khách hàng",
                value = order.customerName
            )

            AdminInfoRow(
                title = "Email",
                value = order.email
            )

            AdminInfoRow(
                title = "Số điện thoại",
                value = order.phoneNumber
            )

            AdminInfoRow(
                title = "Địa chỉ",
                value = order.address
            )

            AdminInfoRow(
                title = "Thanh toán",
                value =
                    if (
                        order.paymentMethod ==
                        "COD"
                    ) {
                        "Thanh toán khi nhận hàng"
                    } else {
                        "Chuyển khoản"
                    }
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text =
                    "Tổng tiền: ${
                        formatAdminPrice(
                            order.totalPrice
                        )
                    }",

                color =
                    Color(0xFFDC2626),

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            Text(
                text =
                    "Trạng thái: $statusText",

                color =
                    statusColor,

                fontWeight =
                    FontWeight.Bold
            )

            // ==========================================
            // BUTTONS
            // ==========================================

            if (
                order.status ==
                "PENDING"
            ) {

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        onClick = {

                            db.collection("orders")
                                .document(order.id)
                                .update(
                                    "status",
                                    "APPROVED"
                                )
                        },

                        modifier =
                            Modifier.weight(1f),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF16A34A)
                            ),

                        shape =
                            RoundedCornerShape(10.dp)
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                null
                        )

                        Text(
                            text = " DUYỆT"
                        )
                    }

                    Button(
                        onClick = {

                            db.collection("orders")
                                .document(order.id)
                                .update(
                                    "status",
                                    "CANCELLED"
                                )
                        },

                        modifier =
                            Modifier.weight(1f),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFFDC2626)
                            ),

                        shape =
                            RoundedCornerShape(10.dp)
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Close,

                            contentDescription =
                                null
                        )

                        Text(
                            text = " HỦY"
                        )
                    }
                }
            }
        }
    }
}


// ======================================================
// INFO ROW
// ======================================================

@Composable
private fun AdminInfoRow(
    title: String,
    value: String
) {

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 3.dp
                )
    ) {

        Text(
            text =
                "$title: ",

            color =
                Color(0xFF64748B),

            fontSize = 13.sp,

            modifier =
                Modifier.weight(0.35f)
        )

        Text(
            text =
                value,

            fontSize = 13.sp,

            fontWeight =
                FontWeight.SemiBold,

            modifier =
                Modifier.weight(0.65f)
        )
    }
}


// ======================================================
// FORMAT
// ======================================================

private fun formatAdminPrice(
    price: Long
): String {

    return String
        .format(
            "%,dđ",
            price
        )
        .replace(",", ".")
}