package com.bimabaraja.sellora.ui.pesanan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bimabaraja.sellora.ui.components.EmptyState
import com.bimabaraja.sellora.ui.components.SellTopBar

@Composable
fun PesananScreen(
    onNavigateToForm: (Int?, Int?) -> Unit = { _, _ -> },
    onNavigateToDetail: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = { SellTopBar(title = "Pesanan") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            EmptyState(
                title = "Daftar Pesanan",
                description = "Fitur Pesanan akan dihubungkan pada Tahap 6."
            )
        }
    }
}
