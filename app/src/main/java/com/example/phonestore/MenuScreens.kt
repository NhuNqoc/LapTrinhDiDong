package com.example.phonestore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleMenuScreen(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = Color.White
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )

            Spacer(Modifier.padding(4.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "PHONE STORE cung cấp thông tin và dịch vụ liên quan đến điện thoại, công nghệ và hỗ trợ khách hàng.",
                        color = Color(0xFF64748B),
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BuildPcScreen(
    onBack: () -> Unit
) {
    var cpu by remember { mutableStateOf("Core i5") }
    var ram by remember { mutableStateOf("16 GB") }
    var storage by remember { mutableStateOf("512 GB SSD") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
    ) {
        MenuHeader(
            title = "BUILD PC",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            BuildOptionCard(
                title = "CPU",
                value = cpu,
                options = listOf(
                    "Core i3",
                    "Core i5",
                    "Core i7",
                    "Ryzen 5",
                    "Ryzen 7"
                ),
                onChange = { cpu = it }
            )

            Spacer(Modifier.height(14.dp))

            BuildOptionCard(
                title = "RAM",
                value = ram,
                options = listOf(
                    "8 GB",
                    "16 GB",
                    "32 GB",
                    "64 GB"
                ),
                onChange = { ram = it }
            )

            Spacer(Modifier.height(14.dp))

            BuildOptionCard(
                title = "Ổ cứng",
                value = storage,
                options = listOf(
                    "256 GB SSD",
                    "512 GB SSD",
                    "1 TB SSD",
                    "2 TB SSD"
                ),
                onChange = { storage = it }
            )

            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    Modifier.padding(18.dp)
                ) {
                    Text(
                        "Cấu hình đề xuất",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(10.dp))

                    Text("CPU: $cpu")
                    Text("RAM: $ram")
                    Text("Ổ cứng: $storage")

                    Spacer(Modifier.height(15.dp))

                    Button(
                        onClick = {}
                    ) {
                        Text("LƯU CẤU HÌNH")
                    }
                }
            }
        }
    }
}

@Composable
private fun BuildOptionCard(
    title: String,
    value: String,
    options: List<String>,
    onChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = value == option,
                        onClick = {
                            onChange(option)
                        }
                    )
                    Text(option)
                }
            }
        }
    }
}

@Composable
fun TechnicalRequestScreen(
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
    ) {
        MenuHeader(
            title = "YÊU CẦU KỸ THUẬT",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    Modifier.padding(16.dp)
                ) {
                    Text(
                        "Gửi yêu cầu hỗ trợ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Họ và tên") },
                        singleLine = true
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Số điện thoại") },
                        singleLine = true
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nội dung yêu cầu") },
                        minLines = 5
                    )

                    Spacer(Modifier.height(15.dp))

                    Button(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null
                        )

                        Spacer(Modifier.padding(3.dp))

                        Text("GỬI YÊU CẦU")
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuHeader(
    title: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0F172A))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Quay lại",
                tint = Color.White
            )
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}