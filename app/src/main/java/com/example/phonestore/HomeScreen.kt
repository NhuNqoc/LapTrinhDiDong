package com.example.phonestore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onProductClick: (Phone) -> Unit,
    onCartClick: () -> Unit,
    onMenuClick: (String) -> Unit
) {

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("Tất cả")
    }

    // ==================================================
    // DANH SÁCH 20 SẢN PHẨM
    // ==================================================

    val phones = remember {

        listOf(

            // ================= IPHONE =================

            Phone(
                name = "iPhone 15 Pro Max",
                price = "29.990.000đ",
                oldPrice = "34.990.000đ",
                discount = "-14%",
                category = "iPhone",
                image = R.drawable.iphone15promax
            ),

            Phone(
                name = "iPhone 15 Pro",
                price = "25.990.000đ",
                oldPrice = "29.990.000đ",
                discount = "-10%",
                category = "iPhone",
                image = R.drawable.iphone15promax
            ),

            Phone(
                name = "iPhone 15 Plus",
                price = "21.990.000đ",
                oldPrice = "24.990.000đ",
                discount = "-12%",
                category = "iPhone",
                image = R.drawable.iphone15promax
            ),

            Phone(
                name = "iPhone 15",
                price = "18.990.000đ",
                oldPrice = "21.990.000đ",
                discount = "-14%",
                category = "iPhone",
                image = R.drawable.iphone15promax
            ),

            Phone(
                name = "iPhone 14 Pro Max",
                price = "23.990.000đ",
                oldPrice = "27.990.000đ",
                discount = "-14%",
                category = "iPhone",
                image = R.drawable.iphone15promax
            ),

            // ================= SAMSUNG =================

            Phone(
                name = "Samsung Galaxy S24 Ultra",
                price = "25.990.000đ",
                oldPrice = "30.990.000đ",
                discount = "-16%",
                category = "Samsung",
                image = R.drawable.samsung_s24_ultra
            ),

            Phone(
                name = "Samsung Galaxy S24+",
                price = "21.990.000đ",
                oldPrice = "25.990.000đ",
                discount = "-15%",
                category = "Samsung",
                image = R.drawable.samsung_s24_ultra
            ),

            Phone(
                name = "Samsung Galaxy S24",
                price = "18.490.000đ",
                oldPrice = "21.990.000đ",
                discount = "-16%",
                category = "Samsung",
                image = R.drawable.samsung_s24_ultra
            ),

            Phone(
                name = "Samsung Galaxy S23 Ultra",
                price = "20.990.000đ",
                oldPrice = "25.990.000đ",
                discount = "-19%",
                category = "Samsung",
                image = R.drawable.samsung_s24_ultra
            ),

            Phone(
                name = "Samsung Galaxy A55 5G",
                price = "9.990.000đ",
                oldPrice = "11.490.000đ",
                discount = "-13%",
                category = "Samsung",
                image = R.drawable.samsung_s24_ultra
            ),

            // ================= XIAOMI =================

            Phone(
                name = "Xiaomi 14",
                price = "16.990.000đ",
                oldPrice = "19.990.000đ",
                discount = "-15%",
                category = "Xiaomi",
                image = R.drawable.xiaomi14
            ),

            Phone(
                name = "Xiaomi 14 Ultra",
                price = "24.990.000đ",
                oldPrice = "29.990.000đ",
                discount = "-17%",
                category = "Xiaomi",
                image = R.drawable.xiaomi14
            ),

            Phone(
                name = "Xiaomi 13T Pro",
                price = "12.990.000đ",
                oldPrice = "15.990.000đ",
                discount = "-19%",
                category = "Xiaomi",
                image = R.drawable.xiaomi14
            ),

            Phone(
                name = "Xiaomi Redmi Note 13 Pro",
                price = "7.490.000đ",
                oldPrice = "8.990.000đ",
                discount = "-17%",
                category = "Xiaomi",
                image = R.drawable.xiaomi14
            ),

            Phone(
                name = "Xiaomi Redmi Note 13",
                price = "5.490.000đ",
                oldPrice = "6.490.000đ",
                discount = "-15%",
                category = "Xiaomi",
                image = R.drawable.xiaomi14
            ),

            // ================= OPPO =================

            Phone(
                name = "OPPO Reno 12",
                price = "10.990.000đ",
                oldPrice = "12.990.000đ",
                discount = "-15%",
                category = "OPPO",
                image = R.drawable.oppo_reno12
            ),

            Phone(
                name = "OPPO Reno 12 Pro",
                price = "13.990.000đ",
                oldPrice = "16.490.000đ",
                discount = "-15%",
                category = "OPPO",
                image = R.drawable.oppo_reno12
            ),

            Phone(
                name = "OPPO Reno 11",
                price = "9.490.000đ",
                oldPrice = "11.490.000đ",
                discount = "-17%",
                category = "OPPO",
                image = R.drawable.oppo_reno12
            ),

            Phone(
                name = "OPPO A79 5G",
                price = "6.490.000đ",
                oldPrice = "7.490.000đ",
                discount = "-13%",
                category = "OPPO",
                image = R.drawable.oppo_reno12
            ),

            Phone(
                name = "OPPO A58",
                price = "4.990.000đ",
                oldPrice = "5.990.000đ",
                discount = "-17%",
                category = "OPPO",
                image = R.drawable.oppo_reno12
            )
        )
    }

    // ==================================================
    // LỌC
    // ==================================================

    val filteredPhones = phones.filter { phone ->

        val matchSearch =
            searchText.isBlank() ||
                    phone.name.contains(
                        searchText,
                        ignoreCase = true
                    )

        val matchCategory =
            selectedCategory == "Tất cả" ||
                    phone.category == selectedCategory

        matchSearch && matchCategory
    }

    // ==================================================
    // GIAO DIỆN
    // ==================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
    ) {

        // ==================================================
        // HEADER
        // ==================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF0F172A),
                            Color(0xFF2563EB)
                        )
                    )
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "PHONE STORE",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Công nghệ trong tầm tay",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 11.sp
                )
            }

            IconButton(
                onClick = onCartClick
            ) {

                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Giỏ hàng",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = onLogout
            ) {

                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Đăng xuất",
                    tint = Color.White
                )
            }
        }

        // ==================================================
        // TÌM KIẾM
        // ==================================================

        OutlinedTextField(
            value = searchText,

            onValueChange = {
                searchText = it
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                ),

            singleLine = true,

            leadingIcon = {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Tìm kiếm"
                )
            },

            placeholder = {

                Text(
                    text = "Tìm kiếm điện thoại..."
                )
            },

            shape = RoundedCornerShape(14.dp)
        )

        // ==================================================
        // MENU NGANG
        // ==================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 12.dp
                ),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            val menuItems = listOf(
                "Danh mục sản phẩm",
                "Tin tức",
                "Build PC",
                "Tuyển dụng",
                "Giới thiệu",
                "Liên hệ",
                "Yêu cầu kỹ thuật"
            )

            menuItems.forEach { menu ->

                Surface(
                    modifier = Modifier.clickable {
                        onMenuClick(menu)
                    },

                    shape =
                        RoundedCornerShape(12.dp),

                    color =
                        if (
                            menu ==
                            "Danh mục sản phẩm"
                        ) {
                            Color(0xFF2563EB)
                        } else {
                            Color.White
                        },

                    shadowElevation = 2.dp
                ) {

                    Text(
                        text = menu,

                        modifier =
                            Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 9.dp
                            ),

                        color =
                            if (
                                menu ==
                                "Danh mục sản phẩm"
                            ) {
                                Color.White
                            } else {
                                Color(0xFF334155)
                            },

                        fontSize = 11.sp,

                        fontWeight =
                            FontWeight.SemiBold,

                        maxLines = 1
                    )
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        // ==================================================
        // BANNER
        // ==================================================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp
                )
                .height(160.dp),

            shape =
                RoundedCornerShape(20.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Color(0xFF2563EB)
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(18.dp)
            ) {

                Text(
                    text = "SALE CÔNG NGHỆ",
                    color =
                        Color(0xFFBFDBFE),
                    fontSize = 12.sp,
                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )

                Text(
                    text = "GIẢM ĐẾN 30%",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    text = "Smartphone chính hãng",
                    color =
                        Color.White.copy(
                            alpha = 0.9f
                        ),
                    fontSize = 12.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Button(
                    onClick = {
                        selectedCategory = "Tất cả"
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color.White
                        )
                ) {

                    Text(
                        text = "Xem sản phẩm",
                        color =
                            Color(0xFF2563EB),
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }

        // ==================================================
        // DANH MỤC
        // ==================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
                .padding(12.dp),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            listOf(
                "Tất cả",
                "iPhone",
                "Samsung",
                "Xiaomi",
                "OPPO"
            ).forEach { category ->

                Surface(
                    modifier =
                        Modifier.clickable {

                            selectedCategory =
                                category
                        },

                    shape =
                        RoundedCornerShape(12.dp),

                    color =
                        if (
                            selectedCategory ==
                            category
                        ) {
                            Color(0xFF2563EB)
                        } else {
                            Color.White
                        },

                    shadowElevation = 2.dp
                ) {

                    Text(
                        text = category,

                        modifier =
                            Modifier.padding(
                                horizontal = 13.dp,
                                vertical = 9.dp
                            ),

                        color =
                            if (
                                selectedCategory ==
                                category
                            ) {
                                Color.White
                            } else {
                                Color(0xFF334155)
                            },

                        fontSize = 11.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }
        }

        // ==================================================
        // TIÊU ĐỀ
        // ==================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp
                ),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = "Sản phẩm nổi bật",
                fontSize = 18.sp,
                fontWeight =
                    FontWeight.Bold
            )

            Text(
                text =
                    "${filteredPhones.size} sản phẩm",
                color =
                    Color(0xFF2563EB),
                fontSize = 11.sp
            )
        }

        Spacer(
            modifier =
                Modifier.height(5.dp)
        )

        // ==================================================
        // SẢN PHẨM
        // ==================================================

        if (filteredPhones.isEmpty()) {

            Column(
                modifier =
                    Modifier.fillMaxSize(),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Search,

                    contentDescription = null,

                    modifier =
                        Modifier.size(50.dp),

                    tint =
                        Color(0xFF94A3B8)
                )

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Text(
                    text =
                        "Không tìm thấy sản phẩm",

                    fontWeight =
                        FontWeight.Bold
                )
            }

        } else {

            LazyVerticalGrid(
                columns =
                    GridCells.Fixed(2),

                modifier =
                    Modifier.fillMaxSize(),

                contentPadding =
                    PaddingValues(10.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(9.dp),

                verticalArrangement =
                    Arrangement.spacedBy(9.dp)
            ) {

                items(
                    items = filteredPhones,
                    key = {
                        it.name
                    }
                ) { phone ->

                    PhoneCard(
                        phone = phone,
                        onClick = {
                            onProductClick(phone)
                        }
                    )
                }
            }
        }
    }
}


// ======================================================
// PHONE CARD
// ======================================================

@Composable
private fun PhoneCard(
    phone: Phone,
    onClick: () -> Unit
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },

        shape =
            RoundedCornerShape(16.dp),

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

        Column {

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp)
            ) {

                Image(
                    painter =
                        painterResource(
                            id = phone.image
                        ),

                    contentDescription =
                        phone.name,

                    modifier =
                        Modifier.fillMaxSize(),

                    contentScale =
                        ContentScale.Fit
                )

                Text(
                    text =
                        phone.discount,

                    color = Color.White,

                    fontSize = 10.sp,

                    fontWeight =
                        FontWeight.Bold,

                    modifier =
                        Modifier
                            .padding(7.dp)
                            .background(
                                Color(0xFFEF4444),
                                RoundedCornerShape(6.dp)
                            )
                            .padding(
                                horizontal = 6.dp,
                                vertical = 3.dp
                            )
                )
            }

            Column(
                modifier =
                    Modifier.padding(10.dp)
            ) {

                Text(
                    text =
                        phone.category.uppercase(),

                    color =
                        Color(0xFF2563EB),

                    fontSize = 9.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )

                Text(
                    text =
                        phone.name,

                    fontSize = 13.sp,

                    fontWeight =
                        FontWeight.Bold,

                    maxLines = 2,

                    minLines = 2
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text =
                        phone.price,

                    color =
                        Color(0xFFDC2626),

                    fontSize = 15.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    text =
                        phone.oldPrice,

                    color =
                        Color(0xFF94A3B8),

                    fontSize = 10.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(7.dp)
                )

                Button(
                    onClick = onClick,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(38.dp),

                    contentPadding =
                        PaddingValues(0.dp),

                    shape =
                        RoundedCornerShape(9.dp)
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.ShoppingCart,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(15.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(4.dp)
                    )

                    Text(
                        text =
                            "Xem chi tiết",

                        fontSize = 11.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}