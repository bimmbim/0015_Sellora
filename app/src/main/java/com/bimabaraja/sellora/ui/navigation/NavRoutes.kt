package com.bimabaraja.sellora.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

enum class UserRole(val value: String) {
    OWNER("owner"),
    STAF("staf");

    companion object {
        fun fromString(role: String?): UserRole {
            return if (role.equals("staf", ignoreCase = true)) STAF else OWNER
        }
    }
}

sealed class Screen(val route: String, val title: String = "", val icon: ImageVector? = null) {
    // Auth
    data object Login : Screen("login")
    data object Register : Screen("register")

    // Main Bottom Nav Tabs
    data object Produk : Screen("produk", "Produk", Icons.Default.ShoppingBag)
    data object SesiLive : Screen("sesi_live", "Live", Icons.Default.LiveTv)
    data object Pesanan : Screen("pesanan", "Pesanan", Icons.Default.ReceiptLong)
    data object Rekap : Screen("rekap", "Rekap", Icons.Default.Analytics)
    data object Profil : Screen("profil", "Profil", Icons.Default.Person)

    // Sub-screens (Kelola)
    data object Staf : Screen("staf")
    data object FileRekap : Screen("file_rekap")
    data object FileRekapDetail : Screen("file_rekap_detail/{fileName}") {
        fun createRoute(fileName: String) = "file_rekap_detail/$fileName"
    }

    // Forms & Details
    data object ProdukForm : Screen("produk_form?id={id}") {
        fun createRoute(id: Int? = null) = if (id != null) "produk_form?id=$id" else "produk_form"
    }
    data object ProdukDetail : Screen("produk_detail/{id}") {
        fun createRoute(id: Int) = "produk_detail/$id"
    }

    data object SesiLiveForm : Screen("sesi_live_form?id={id}") {
        fun createRoute(id: Int? = null) = if (id != null) "sesi_live_form?id=$id" else "sesi_live_form"
    }
    data object SesiLiveDetail : Screen("sesi_live_detail/{id}") {
        fun createRoute(id: Int) = "sesi_live_detail/$id"
    }

    data object PesananForm : Screen("pesanan_form?id={id}&sesiId={sesiId}") {
        fun createRoute(id: Int? = null, sesiId: Int? = null): String {
            val queryParams = mutableListOf<String>()
            if (id != null) queryParams.add("id=$id")
            if (sesiId != null) queryParams.add("sesiId=$sesiId")
            return if (queryParams.isNotEmpty()) "pesanan_form?${queryParams.joinToString("&")}" else "pesanan_form"
        }
    }
    data object PesananDetail : Screen("pesanan_detail/{id}") {
        fun createRoute(id: Int) = "pesanan_detail/$id"
    }
}
