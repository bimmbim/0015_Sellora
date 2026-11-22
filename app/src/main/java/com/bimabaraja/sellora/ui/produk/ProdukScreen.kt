package com.bimabaraja.sellora.ui.produk

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bimabaraja.sellora.ui.components.EmptyState
import com.bimabaraja.sellora.ui.components.SellTopBar

@Composable
fun ProdukScreen(
    onNavigateToForm: (Int?) -> Unit = {},
    onNavigateToDetail: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = { SellTopBar(title = "Katalog Produk") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            EmptyState(
                title = "Daftar Produk",
                description = "Fitur produk akan dihubungkan pada Tahap 4."
            )
        }
    }
}
