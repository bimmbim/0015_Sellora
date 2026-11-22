package com.bimabaraja.sellora.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bimabaraja.sellora.ui.components.FormTextField
import com.bimabaraja.sellora.ui.components.PrimaryButton
import com.bimabaraja.sellora.ui.theme.PrimaryDark
import com.bimabaraja.sellora.ui.theme.PrimaryIndigo
import com.bimabaraja.sellora.ui.theme.PrimaryLight

@Composable
fun RegisterScreen(
    onRegisterClick: (nama: String, email: String, pass: String) -> Unit = { _, _, _ -> },
    onNavigateToLogin: () -> Unit = {},
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var namaError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(PrimaryDark, PrimaryIndigo, PrimaryLight)
                    )
                )
        ) {
            IconButton(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali ke Login",
                    tint = Color.White
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Daftar Akun Owner",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                )
                Text(
                    text = "Kelola bisnis UMKM live streaming Anda",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                if (!errorMessage.isNullOrEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                FormTextField(
                    value = nama,
                    onValueChange = {
                        nama = it
                        namaError = null
                    },
                    label = "Nama Lengkap",
                    placeholder = "Nama pemilik bisnis",
                    errorText = namaError
                )

                Spacer(modifier = Modifier.height(14.dp))

                FormTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    label = "Email",
                    placeholder = "contoh@umkm.com",
                    errorText = emailError
                )

                Spacer(modifier = Modifier.height(14.dp))

                FormTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    label = "Password (min 8 karakter)",
                    placeholder = "Masukkan password",
                    isPassword = true,
                    errorText = passwordError
                )

                Spacer(modifier = Modifier.height(14.dp))

                FormTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = null
                    },
                    label = "Konfirmasi Password",
                    placeholder = "Ulangi password",
                    isPassword = true,
                    errorText = confirmPasswordError
                )

                Spacer(modifier = Modifier.height(28.dp))

                PrimaryButton(
                    text = "Daftar Akun",
                    isLoading = isLoading,
                    onClick = {
                        var hasError = false
                        if (nama.isBlank()) {
                            namaError = "Nama lengkap tidak boleh kosong"
                            hasError = true
                        }
                        if (email.isBlank()) {
                            emailError = "Email tidak boleh kosong"
                            hasError = true
                        }
                        if (password.length < 8) {
                            passwordError = "Password minimal 8 karakter"
                            hasError = true
                        }
                        if (confirmPassword != password) {
                            confirmPasswordError = "Konfirmasi password tidak cocok"
                            hasError = true
                        }

                        if (!hasError) {
                            onRegisterClick(nama, email, password)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sudah punya akun? ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    TextButton(onClick = onNavigateToLogin) {
                        Text(
                            text = "Masuk",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
        }
    }
}
