package com.example.phonestore

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

// ======================================================
// MODEL - CHỈ DÙNG TRONG FILE NÀY
// ======================================================

private data class ProductAdminItem(
    val id: String = "",
    val name: String = "",
    val price: String = "",
    val oldPrice: String = "",
    val discount: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val storagePath: String = "",
    val description: String = ""
)

// ======================================================
// ADMIN PRODUCT SCREEN
// ======================================================

@Composable
fun AdminProductScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val firestore = remember {
        FirebaseFirestore.getInstance()
    }

    val storage = remember {
        FirebaseStorage.getInstance()
    }

    var products by remember {
        mutableStateOf<List<ProductAdminItem>>(emptyList())
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var refreshing by remember {
        mutableStateOf(false)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var saving by remember {
        mutableStateOf(false)
    }

    var editingProduct by remember {
        mutableStateOf<ProductAdminItem?>(null)
    }

    var deletingProduct by remember {
        mutableStateOf<ProductAdminItem?>(null)
    }

    // ==================================================
    // ĐỌC FIRESTORE
    // ==================================================

    fun readText(
        document: DocumentSnapshot,
        field: String
    ): String {

        return when (val value = document.get(field)) {

            null -> ""

            is String -> value

            is Number -> value.toString()

            is Boolean -> value.toString()

            else -> value.toString()
        }
    }

    // ==================================================
    // LOAD PRODUCTS
    // ==================================================

    fun loadProducts(
        showLoading: Boolean = true
    ) {

        if (showLoading) {
            loading = true
        }

        firestore
            .collection("products")
            .get()
            .addOnSuccessListener { result ->

                products =
                    result.documents.map { document ->

                        ProductAdminItem(

                            id =
                                document.id,

                            name =
                                readText(
                                    document,
                                    "name"
                                ),

                            price =
                                readText(
                                    document,
                                    "price"
                                ),

                            oldPrice =
                                readText(
                                    document,
                                    "oldPrice"
                                ),

                            discount =
                                readText(
                                    document,
                                    "discount"
                                ),

                            category =
                                readText(
                                    document,
                                    "category"
                                ),

                            imageUrl =
                                readText(
                                    document,
                                    "imageUrl"
                                ),

                            storagePath =
                                readText(
                                    document,
                                    "storagePath"
                                ),

                            description =
                                readText(
                                    document,
                                    "description"
                                )
                        )
                    }

                loading = false
                refreshing = false
            }
            .addOnFailureListener { error ->

                loading = false
                refreshing = false

                Toast.makeText(
                    context,
                    "Lỗi tải sản phẩm:\n${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    LaunchedEffect(Unit) {
        loadProducts()
    }

    // ==================================================
    // GIAO DIỆN CHÍNH
    // ==================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF5F7FB)
            )
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
                            Color(0xFF111827),
                            Color(0xFF4F46E5),
                            Color(0xFF7C3AED)
                        )
                    )
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 12.dp
                ),

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

                    tint =
                        Color.White
                )
            }

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        "QUẢN LÝ SẢN PHẨM",

                    color =
                        Color.White,

                    fontSize =
                        19.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    text =
                        "${products.size} sản phẩm",

                    color =
                        Color.White.copy(
                            alpha = 0.8f
                        ),

                    fontSize =
                        11.sp
                )
            }

            IconButton(
                onClick = {

                    refreshing = true

                    loadProducts(
                        showLoading = false
                    )
                }
            ) {

                if (refreshing) {

                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(21.dp),

                        color =
                            Color.White,

                        strokeWidth =
                            2.dp
                    )

                } else {

                    Icon(
                        imageVector =
                            Icons.Default.Refresh,

                        contentDescription =
                            "Tải lại",

                        tint =
                            Color.White
                    )
                }
            }
        }

        // ==================================================
        // NÚT THÊM
        // ==================================================

        Button(
            onClick = {

                editingProduct = null
                showDialog = true
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 14.dp,
                        vertical = 12.dp
                    )
                    .height(52.dp),

            shape =
                RoundedCornerShape(14.dp),

            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFF4F46E5),

                    contentColor =
                        Color.White
                )
        ) {

            Icon(
                imageVector =
                    Icons.Default.Add,

                contentDescription =
                    null
            )

            Spacer(
                modifier =
                    Modifier.width(7.dp)
            )

            Text(
                text =
                    "THÊM SẢN PHẨM",

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        // ==================================================
        // NỘI DUNG
        // ==================================================

        when {

            loading -> {

                Box(
                    modifier =
                        Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color =
                            Color(0xFF4F46E5)
                    )
                }
            }

            products.isEmpty() -> {

                Box(
                    modifier =
                        Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Inventory,

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(
                                    65.dp
                                ),

                            tint =
                                Color(0xFF94A3B8)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    10.dp
                                )
                        )

                        Text(
                            text =
                                "Chưa có sản phẩm",

                            fontSize =
                                18.sp,

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }

            else -> {

                LazyColumn(

                    modifier =
                        Modifier.fillMaxSize(),

                    contentPadding =
                        PaddingValues(
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 25.dp
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            10.dp
                        )
                ) {

                    items(
                        items =
                            products,

                        key = {
                            it.id
                        }
                    ) { product ->

                        ProductAdminCard(

                            product =
                                product,

                            onEdit = {

                                editingProduct =
                                    product

                                showDialog =
                                    true
                            },

                            onDelete = {

                                deletingProduct =
                                    product
                            }
                        )
                    }
                }
            }
        }
    }

    // ==================================================
    // FORM THÊM / SỬA
    // ==================================================

    if (showDialog) {

        ProductEditorDialog(

            product =
                editingProduct,

            saving =
                saving,

            onDismiss = {

                if (!saving) {
                    showDialog = false
                }
            },

            onSave = { product, imageUri ->

                if (product.name.isBlank()) {

                    Toast.makeText(
                        context,
                        "Vui lòng nhập tên sản phẩm.",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@ProductEditorDialog
                }

                if (product.price.isBlank()) {

                    Toast.makeText(
                        context,
                        "Vui lòng nhập giá sản phẩm.",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@ProductEditorDialog
                }

                if (product.category.isBlank()) {

                    Toast.makeText(
                        context,
                        "Vui lòng nhập danh mục.",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@ProductEditorDialog
                }

                // =========================================
                // BẮT ĐẦU LƯU
                // =========================================

                saving = true

                fun saveFirestore(
                    imageUrl: String,
                    storagePath: String
                ) {

                    val data =
                        hashMapOf<String, Any>(

                            "name" to
                                    product.name.trim(),

                            "price" to
                                    product.price.trim(),

                            "oldPrice" to
                                    product.oldPrice.trim(),

                            "discount" to
                                    product.discount.trim(),

                            "category" to
                                    product.category.trim(),

                            "imageUrl" to
                                    imageUrl,

                            "storagePath" to
                                    storagePath,

                            "description" to
                                    product.description.trim()
                        )

                    if (
                        product.id.isBlank()
                    ) {

                        firestore
                            .collection("products")
                            .add(data)
                            .addOnSuccessListener {

                                saving = false
                                showDialog = false

                                Toast.makeText(
                                    context,
                                    "✅ Thêm sản phẩm thành công!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                loadProducts()
                            }
                            .addOnFailureListener { error: Exception ->

                                saving = false

                                Toast.makeText(
                                    context,
                                    "❌ Lưu Firestore thất bại:\n${error.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                    } else {

                        firestore
                            .collection("products")
                            .document(product.id)
                            .set(data)
                            .addOnSuccessListener {

                                saving = false
                                showDialog = false

                                Toast.makeText(
                                    context,
                                    "✅ Cập nhật thành công!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                loadProducts()
                            }
                            .addOnFailureListener { error: Exception ->

                                saving = false

                                Toast.makeText(
                                    context,
                                    "❌ Cập nhật thất bại:\n${error.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }
                }

                // =========================================
                // CÓ ẢNH MỚI
                // =========================================

                if (imageUri != null) {

                    val fileName =
                        "product_${System.currentTimeMillis()}.jpg"

                    val storagePath =
                        "products/$fileName"

                    val imageRef =
                        storage.reference
                            .child(storagePath)

                    imageRef
                        .putFile(imageUri)
                        .addOnSuccessListener {

                            imageRef
                                .downloadUrl
                                .addOnSuccessListener { uri: Uri ->

                                    saveFirestore(
                                        uri.toString(),
                                        storagePath
                                    )
                                }
                                .addOnFailureListener { error: Exception ->

                                    saving = false

                                    Toast.makeText(
                                        context,
                                        "❌ Không lấy được URL ảnh:\n${error.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                        .addOnFailureListener { error: Exception ->

                            saving = false

                            Toast.makeText(
                                context,
                                "❌ Upload ảnh thất bại:\n${error.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                } else {

                    // =========================================
                    // KHÔNG ĐỔI ẢNH KHI SỬA
                    // =========================================

                    saveFirestore(
                        product.imageUrl,
                        product.storagePath
                    )
                }
            }
        )
    }

    // ==================================================
    // XÓA
    // ==================================================

    deletingProduct?.let { product ->

        AlertDialog(

            onDismissRequest = {

                deletingProduct = null
            },

            title = {

                Text(
                    "Xóa sản phẩm?"
                )
            },

            text = {

                Text(
                    "Bạn có chắc muốn xóa:\n\n${product.name}"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        fun deleteFirestore() {

                            firestore
                                .collection(
                                    "products"
                                )
                                .document(
                                    product.id
                                )
                                .delete()
                                .addOnSuccessListener {

                                    deletingProduct =
                                        null

                                    Toast.makeText(
                                        context,
                                        "✅ Đã xóa sản phẩm!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    loadProducts()
                                }
                                .addOnFailureListener { error: Exception ->

                                    Toast.makeText(
                                        context,
                                        "❌ Xóa thất bại:\n${error.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        if (
                            product.storagePath.isNotBlank()
                        ) {

                            storage.reference
                                .child(
                                    product.storagePath
                                )
                                .delete()
                                .addOnCompleteListener {

                                    deleteFirestore()
                                }

                        } else {

                            deleteFirestore()
                        }
                    }
                ) {

                    Text(
                        text =
                            "XÓA",

                        color =
                            Color(0xFFDC2626),

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        deletingProduct = null
                    }
                ) {

                    Text("HỦY")
                }
            }
        )
    }
}


// ======================================================
// CARD
// ======================================================

@Composable
private fun ProductAdminCard(
    product: ProductAdminItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

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
                defaultElevation =
                    3.dp
            )
    ) {

        Row(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            if (
                product.imageUrl.isNotBlank()
            ) {

                AsyncImage(

                    model =
                        product.imageUrl,

                    contentDescription =
                        product.name,

                    modifier =
                        Modifier
                            .size(75.dp)
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                Color(0xFFF8FAFC)
                            ),

                    contentScale =
                        ContentScale.Fit
                )

            } else {

                Box(

                    modifier =
                        Modifier
                            .size(75.dp)
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                Color(0xFFF1F5F9)
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Image,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.width(
                        12.dp
                    )
            )

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        product.name,

                    color =
                        Color(0xFF111827),

                    fontSize =
                        14.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            3.dp
                        )
                )

                Text(
                    text =
                        product.category,

                    color =
                        Color(0xFF4F46E5),

                    fontSize =
                        10.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            3.dp
                        )
                )

                Text(
                    text =
                        product.price,

                    color =
                        Color(0xFFE11D48),

                    fontSize =
                        14.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )
            }

            IconButton(
                onClick =
                    onEdit
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Edit,

                    contentDescription =
                        "Sửa",

                    tint =
                        Color(0xFF4F46E5)
                )
            }

            IconButton(
                onClick =
                    onDelete
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Delete,

                    contentDescription =
                        "Xóa",

                    tint =
                        Color(0xFFDC2626)
                )
            }
        }
    }
}


// ======================================================
// FORM
// ======================================================

@Composable
private fun ProductEditorDialog(
    product: ProductAdminItem?,
    saving: Boolean,
    onDismiss: () -> Unit,
    onSave: (
        ProductAdminItem,
        Uri?
    ) -> Unit
) {

    var name by remember(product) {
        mutableStateOf(
            product?.name ?: ""
        )
    }

    var price by remember(product) {
        mutableStateOf(
            product?.price ?: ""
        )
    }

    var oldPrice by remember(product) {
        mutableStateOf(
            product?.oldPrice ?: ""
        )
    }

    var discount by remember(product) {
        mutableStateOf(
            product?.discount ?: ""
        )
    }

    var category by remember(product) {
        mutableStateOf(
            product?.category ?: ""
        )
    }

    var description by remember(product) {
        mutableStateOf(
            product?.description ?: ""
        )
    }

    var selectedImageUri by remember(product) {
        mutableStateOf<Uri?>(null)
    }

    // ==================================================
    // FILE PICKER
    // ==================================================

    val imagePicker =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            selectedImageUri =
                uri
        }

    AlertDialog(

        onDismissRequest = {

            if (!saving) {
                onDismiss()
            }
        },

        title = {

            Text(
                text =
                    if (product == null)
                        "THÊM SẢN PHẨM"
                    else
                        "SỬA SẢN PHẨM",

                fontWeight =
                    FontWeight.ExtraBold
            )
        },

        text = {

            LazyColumn {

                item {

                    // ==================================================
                    // PREVIEW
                    // ==================================================

                    Box(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(175.dp)
                                .clip(
                                    RoundedCornerShape(
                                        16.dp
                                    )
                                )
                                .background(
                                    Color(0xFFF1F5F9)
                                ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        when {

                            selectedImageUri != null -> {

                                AsyncImage(

                                    model =
                                        selectedImageUri,

                                    contentDescription =
                                        "Ảnh đã chọn",

                                    modifier =
                                        Modifier.fillMaxSize(),

                                    contentScale =
                                        ContentScale.Fit
                                )
                            }

                            !product?.imageUrl.isNullOrBlank() -> {

                                AsyncImage(

                                    model =
                                        product?.imageUrl,

                                    contentDescription =
                                        "Ảnh hiện tại",

                                    modifier =
                                        Modifier.fillMaxSize(),

                                    contentScale =
                                        ContentScale.Fit
                                )
                            }

                            else -> {

                                Column(

                                    horizontalAlignment =
                                        Alignment.CenterHorizontally
                                ) {

                                    Icon(

                                        imageVector =
                                            Icons.Default.Image,

                                        contentDescription =
                                            null,

                                        modifier =
                                            Modifier.size(
                                                50.dp
                                            ),

                                        tint =
                                            Color(0xFF94A3B8)
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(
                                                6.dp
                                            )
                                    )

                                    Text(
                                        text =
                                            "Chưa chọn ảnh",

                                        color =
                                            Color(0xFF64748B),

                                        fontSize =
                                            12.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(
                                10.dp
                            )
                    )

                    // ==================================================
                    // CHỌN ẢNH
                    // ==================================================

                    Button(

                        onClick = {

                            imagePicker.launch(
                                "image/*"
                            )
                        },

                        enabled =
                            !saving,

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF7C3AED),

                                contentColor =
                                    Color.White
                            )
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Image,

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(
                                    18.dp
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.width(
                                    7.dp
                                )
                        )

                        Text(
                            text =
                                if (
                                    selectedImageUri != null
                                ) {
                                    "ĐỔI ẢNH"
                                } else {
                                    "CHỌN ẢNH TỪ MÁY"
                                },

                            fontWeight =
                                FontWeight.Bold
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(
                                10.dp
                            )
                    )

                    // ==================================================
                    // TÊN
                    // ==================================================

                    OutlinedTextField(

                        value =
                            name,

                        onValueChange = {
                            name = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Tên sản phẩm *"
                            )
                        },

                        singleLine =
                            true,

                        enabled =
                            !saving
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                8.dp
                            )
                    )

                    // ==================================================
                    // GIÁ
                    // ==================================================

                    OutlinedTextField(

                        value =
                            price,

                        onValueChange = {
                            price = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Giá bán *"
                            )
                        },

                        singleLine =
                            true,

                        enabled =
                            !saving
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                8.dp
                            )
                    )

                    // ==================================================
                    // GIÁ CŨ
                    // ==================================================

                    OutlinedTextField(

                        value =
                            oldPrice,

                        onValueChange = {
                            oldPrice = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Giá cũ"
                            )
                        },

                        singleLine =
                            true,

                        enabled =
                            !saving
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                8.dp
                            )
                    )

                    // ==================================================
                    // GIẢM GIÁ
                    // ==================================================

                    OutlinedTextField(

                        value =
                            discount,

                        onValueChange = {
                            discount = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Giảm giá"
                            )
                        },

                        placeholder = {
                            Text(
                                "-14%"
                            )
                        },

                        singleLine =
                            true,

                        enabled =
                            !saving
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                8.dp
                            )
                    )

                    // ==================================================
                    // CATEGORY
                    // ==================================================

                    OutlinedTextField(

                        value =
                            category,

                        onValueChange = {
                            category = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Danh mục *"
                            )
                        },

                        placeholder = {
                            Text(
                                "iPhone / Samsung / Xiaomi / OPPO"
                            )
                        },

                        singleLine =
                            true,

                        enabled =
                            !saving
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                8.dp
                            )
                    )

                    // ==================================================
                    // DESCRIPTION
                    // ==================================================

                    OutlinedTextField(

                        value =
                            description,

                        onValueChange = {
                            description = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Mô tả sản phẩm"
                            )
                        },

                        minLines =
                            3,

                        maxLines =
                            5,

                        enabled =
                            !saving
                    )
                }
            }
        },

        confirmButton = {

            Button(

                onClick = {

                    onSave(

                        ProductAdminItem(

                            id =
                                product?.id
                                    ?: "",

                            name =
                                name,

                            price =
                                price,

                            oldPrice =
                                oldPrice,

                            discount =
                                discount,

                            category =
                                category,

                            imageUrl =
                                product?.imageUrl
                                    ?: "",

                            storagePath =
                                product?.storagePath
                                    ?: "",

                            description =
                                description
                        ),

                        selectedImageUri
                    )
                },

                enabled =
                    !saving,

                shape =
                    RoundedCornerShape(
                        10.dp
                    ),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF4F46E5)
                    )
            ) {

                if (saving) {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.size(
                                18.dp
                            ),

                        color =
                            Color.White,

                        strokeWidth =
                            2.dp
                    )

                    Spacer(
                        modifier =
                            Modifier.width(
                                6.dp
                            )
                    )

                    Text(
                        "ĐANG LƯU..."
                    )

                } else {

                    Text(
                        "LƯU SẢN PHẨM",

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        },

        dismissButton = {

            TextButton(

                onClick =
                    onDismiss,

                enabled =
                    !saving
            ) {

                Text("HỦY")
            }
        }
    )
}