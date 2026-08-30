package com.example.phonestore

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "PHONE STORE",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Tạo tài khoản mới"
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Mật khẩu")
            },
            visualTransformation =
                PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            label = {
                Text("Nhập lại mật khẩu")
            },
            visualTransformation =
                PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Button(
            onClick = {

                if (email.isBlank()) {

                    Toast.makeText(
                        context,
                        "Vui lòng nhập email",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                if (password.length < 6) {

                    Toast.makeText(
                        context,
                        "Mật khẩu phải có ít nhất 6 ký tự",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                if (password != confirmPassword) {

                    Toast.makeText(
                        context,
                        "Mật khẩu nhập lại không khớp",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                loading = true

                FirebaseAuth
                    .getInstance()
                    .createUserWithEmailAndPassword(
                        email.trim(),
                        password
                    )
                    .addOnCompleteListener { task ->

                        loading = false

                        if (task.isSuccessful) {

                            Toast.makeText(
                                context,
                                "Đăng ký thành công",
                                Toast.LENGTH_SHORT
                            ).show()

                            onRegisterSuccess()

                        } else {

                            Toast.makeText(
                                context,
                                task.exception?.message
                                    ?: "Đăng ký thất bại",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = if (loading)
                    "Đang đăng ký..."
                else
                    "ĐĂNG KÝ"
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        TextButton(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Đã có tài khoản? Đăng nhập"
            )
        }
    }
}