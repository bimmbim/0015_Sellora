package com.bimabaraja.sellora.ui.sesilive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bimabaraja.sellora.ui.components.EmptyState
import com.bimabaraja.sellora.ui.components.SellTopBar

@Composable
fun SesiLiveScreen(
    onNavigateToForm: (Int?) -> Unit = {},
    onNavigateToDetail: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = { SellTopBar(title = "Sesi Live") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            EmptyState(
                title = "Sesi Live Streaming",
                description = "Fitur Sesi Live akan dihubungkan pada Tahap 5."
            )
        }
    }
}
