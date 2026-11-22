package com.bimabaraja.sellora.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SelloraBottomBar(
    currentRoute: String?,
    userRole: UserRole,
    onNavigateToRoute: (String) -> Unit
) {
    val items = when (userRole) {
        UserRole.OWNER -> listOf(
            Screen.Produk,
            Screen.SesiLive,
            Screen.Pesanan,
            Screen.Rekap,
            Screen.Profil
        )
        UserRole.STAF -> listOf(
            Screen.Produk,
            Screen.SesiLive,
            Screen.Pesanan,
            Screen.Profil
        )
    }

    val hideBottomBarRoutes = setOf(
        Screen.Login.route,
        Screen.Register.route,
        Screen.Staf.route,
        Screen.FileRekap.route
    )

    val shouldHide = currentRoute in hideBottomBarRoutes ||
            currentRoute?.startsWith("produk_") == true ||
            currentRoute?.startsWith("sesi_live_") == true ||
            currentRoute?.startsWith("pesanan_") == true ||
            currentRoute?.startsWith("file_rekap_") == true

    if (!shouldHide && currentRoute != null) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            items.forEach { screen ->
                val selected = currentRoute == screen.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            onNavigateToRoute(screen.route)
                        }
                    },
                    icon = {
                        screen.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = screen.title
                            )
                        }
                    },
                    label = {
                        Text(
                            text = screen.title,
                            fontSize = 11.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
