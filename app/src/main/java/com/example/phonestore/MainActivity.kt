package com.example.phonestore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.phonestore.ui.theme.PhoneStoreTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhoneStoreTheme {
                PhoneStoreApp()
            }
        }
    }
}

@Composable
fun PhoneStoreApp() {

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    // ==================================================
    // TRẠNG THÁI ĐĂNG NHẬP
    // ==================================================

    var isLoggedIn by remember {
        mutableStateOf(auth.currentUser != null)
    }

    // ==================================================
    // ĐĂNG KÝ
    // ==================================================

    var showRegister by remember {
        mutableStateOf(false)
    }

    // ==================================================
    // MÀN HÌNH HIỆN TẠI
    // ==================================================

    var currentScreen by remember {
        mutableStateOf(
            when {
                auth.currentUser == null -> "login"

                auth.currentUser?.email
                    ?.trim()
                    ?.lowercase() == "admin@gmail.com" -> "admin"

                else -> "home"
            }
        )
    }

    // ==================================================
    // SẢN PHẨM ĐANG XEM
    // ==================================================

    var selectedPhone by remember {
        mutableStateOf<Phone?>(null)
    }

    // ==================================================
    // GIỎ HÀNG
    // ==================================================

    val cartItems = remember {
        mutableStateListOf<CartItem>()
    }

    // ==================================================
    // LOGIN / REGISTER
    // ==================================================

    if (!isLoggedIn) {

        if (showRegister) {

            RegisterScreen(
                onRegisterSuccess = {
                    isLoggedIn = true
                    showRegister = false
                    currentScreen = "home"
                },
                onLoginClick = {
                    showRegister = false
                }
            )

        } else {

            LoginScreen(
                onLoginSuccess = {

                    val email = auth.currentUser
                        ?.email
                        ?.trim()
                        ?.lowercase()

                    isLoggedIn = true

                    currentScreen =
                        if (email == "admin@gmail.com") {
                            "admin"
                        } else {
                            "home"
                        }
                },
                onRegisterClick = {
                    showRegister = true
                }
            )
        }

        return
    }

    // ==================================================
    // ADMIN
    // ==================================================

    if (currentScreen == "admin") {

        AdminOrderScreen(
            onLogout = {

                auth.signOut()

                isLoggedIn = false
                showRegister = false
                selectedPhone = null
                currentScreen = "login"

                cartItems.clear()
            }
        )

        return
    }

    // ==================================================
    // MENU: DANH MỤC SẢN PHẨM
    // ==================================================

    if (currentScreen == "category") {

        SimpleMenuScreen(
            title = "Danh mục sản phẩm",
            icon = Icons.Default.ShoppingCart,
            onBack = {
                currentScreen = "home"
            }
        )

        return
    }

    // ==================================================
    // MENU: TIN TỨC
    // ==================================================

    if (currentScreen == "news") {

        SimpleMenuScreen(
            title = "Tin tức",
            icon = Icons.Default.Newspaper,
            onBack = {
                currentScreen = "home"
            }
        )

        return
    }

    // ==================================================
    // MENU: BUILD PC
    // ==================================================

    if (currentScreen == "build_pc") {

        BuildPcScreen(
            onBack = {
                currentScreen = "home"
            }
        )

        return
    }

    // ==================================================
    // MENU: TUYỂN DỤNG
    // ==================================================

    if (currentScreen == "recruitment") {

        SimpleMenuScreen(
            title = "Tuyển dụng",
            icon = Icons.Default.Group,
            onBack = {
                currentScreen = "home"
            }
        )

        return
    }

    // ==================================================
    // MENU: GIỚI THIỆU
    // ==================================================

    if (currentScreen == "about") {

        SimpleMenuScreen(
            title = "Giới thiệu",
            icon = Icons.Default.Info,
            onBack = {
                currentScreen = "home"
            }
        )

        return
    }

    // ==================================================
    // MENU: LIÊN HỆ
    // ==================================================

    if (currentScreen == "contact") {

        SimpleMenuScreen(
            title = "Liên hệ",
            icon = Icons.Default.Call,
            onBack = {
                currentScreen = "home"
            }
        )

        return
    }

    // ==================================================
    // MENU: YÊU CẦU KỸ THUẬT
    // ==================================================

    if (currentScreen == "technical") {

        TechnicalRequestScreen(
            onBack = {
                currentScreen = "home"
            }
        )

        return
    }

    // ==================================================
    // GIỎ HÀNG
    // ==================================================

    if (currentScreen == "cart") {

        CartScreen(
            cartItems = cartItems,

            onBack = {
                currentScreen = "home"
            },

            onCheckout = {

                if (cartItems.isEmpty()) {

                    Toast.makeText(
                        context,
                        "Giỏ hàng đang trống",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    currentScreen = "checkout"
                }
            }
        )

        return
    }

    // ==================================================
    // THANH TOÁN
    // ==================================================

    if (currentScreen == "checkout") {

        CheckoutScreen(
            cartItems = cartItems,

            onBack = {
                currentScreen = "cart"
            },

            onOrderSuccess = {

                cartItems.clear()
                currentScreen = "home"

                Toast.makeText(
                    context,
                    "Đặt hàng thành công!",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        return
    }

    // ==================================================
    // CHI TIẾT SẢN PHẨM
    // ==================================================

    if (
        currentScreen == "detail" &&
        selectedPhone != null
    ) {

        ProductDetailScreen(
            phone = selectedPhone!!,

            onBack = {
                selectedPhone = null
                currentScreen = "home"
            },

            onAddToCart = {

                val phone = selectedPhone!!

                val index = cartItems.indexOfFirst {
                    it.phone.name == phone.name
                }

                if (index >= 0) {

                    cartItems[index] =
                        cartItems[index].copy(
                            quantity =
                                cartItems[index].quantity + 1
                        )

                } else {

                    cartItems.add(
                        CartItem(
                            phone = phone,
                            quantity = 1
                        )
                    )
                }

                Toast.makeText(
                    context,
                    "${phone.name} đã thêm vào giỏ hàng",
                    Toast.LENGTH_SHORT
                ).show()

                selectedPhone = null
                currentScreen = "cart"
            }
        )

        return
    }

    // ==================================================
    // HOME
    // ==================================================

    HomeScreen(
        onLogout = {

            auth.signOut()

            isLoggedIn = false
            showRegister = false
            selectedPhone = null
            currentScreen = "login"

            cartItems.clear()
        },

        onProductClick = { phone ->

            selectedPhone = phone
            currentScreen = "detail"
        },

        onCartClick = {

            currentScreen = "cart"
        },

        onMenuClick = { menu ->

            currentScreen =
                when (menu) {

                    "Danh mục sản phẩm" ->
                        "category"

                    "Tin tức" ->
                        "news"

                    "Build PC" ->
                        "build_pc"

                    "Tuyển dụng" ->
                        "recruitment"

                    "Giới thiệu" ->
                        "about"

                    "Liên hệ" ->
                        "contact"

                    "Yêu cầu kỹ thuật" ->
                        "technical"

                    else ->
                        "home"
                }
        }
    )
}