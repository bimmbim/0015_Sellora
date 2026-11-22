package com.bimabaraja.sellora.ui.rekap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bimabaraja.sellora.ui.components.EmptyState
import com.bimabaraja.sellora.ui.components.SellTopBar

@Composable
fun RekapScreen() {
    Scaffold(
        topBar = { SellTopBar(title = "Rekap Omzet") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            EmptyState(
                title = "Dashboard Rekap",
                description = "Fitur Grafik & Rekap akan dihubungkan pada Tahap 8."
            )
        }
    }
}
