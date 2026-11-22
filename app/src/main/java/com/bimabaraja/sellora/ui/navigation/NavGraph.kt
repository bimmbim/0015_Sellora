package com.bimabaraja.sellora.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bimabaraja.sellora.ui.auth.AuthViewModel
import com.bimabaraja.sellora.ui.auth.LoginScreen
import com.bimabaraja.sellora.ui.auth.RegisterScreen
import com.bimabaraja.sellora.ui.components.LoadingState
import com.bimabaraja.sellora.ui.pesanan.PesananScreen
import com.bimabaraja.sellora.ui.produk.ProdukScreen
import com.bimabaraja.sellora.ui.profil.ProfilScreen
import com.bimabaraja.sellora.ui.rekap.RekapScreen
import com.bimabaraja.sellora.ui.sesilive.SesiLiveScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
) {
    val authUiState by authViewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }

    val userRole = UserRole.fromString(authUiState.currentUser?.role)

    LaunchedEffect(authUiState.isLoggedIn, authUiState.isInitializing) {
        if (!authUiState.isInitializing) {
            if (authUiState.isLoggedIn) {
                if (currentRoute == Screen.Login.route || currentRoute == Screen.Register.route || currentRoute == null) {
                    navController.navigate(Screen.Produk.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            } else {
                if (currentRoute != Screen.Login.route && currentRoute != Screen.Register.route) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    if (authUiState.isInitializing) {
        LoadingState()
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                SelloraBottomBar(
                    currentRoute = currentRoute,
                    userRole = userRole,
                    onNavigateToRoute = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Produk.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = if (authUiState.isLoggedIn) Screen.Produk.route else Screen.Login.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(
                        isLoading = authUiState.isLoading,
                        errorMessage = authUiState.errorMessage,
                        onLoginClick = { email, pass ->
                            authViewModel.login(email, pass)
                        },
                        onNavigateToRegister = {
                            authViewModel.clearError()
                            navController.navigate(Screen.Register.route)
                        }
                    )
                }

                composable(Screen.Register.route) {
                    LaunchedEffect(authUiState.isSuccess) {
                        if (authUiState.isSuccess && !authUiState.isLoggedIn) {
                            snackbarHostState.showSnackbar("Pendaftaran berhasil! Silakan masuk.")
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Register.route) { inclusive = true }
                            }
                        }
                    }

                    RegisterScreen(
                        isLoading = authUiState.isLoading,
                        errorMessage = authUiState.errorMessage,
                        onRegisterClick = { nama, email, pass ->
                            authViewModel.register(nama, email, pass)
                        },
                        onNavigateToLogin = {
                            authViewModel.clearError()
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.Produk.route) {
                    ProdukScreen()
                }

                composable(Screen.SesiLive.route) {
                    SesiLiveScreen()
                }

                composable(Screen.Pesanan.route) {
                    PesananScreen()
                }

                composable(Screen.Rekap.route) {
                    RekapScreen()
                }

                composable(Screen.Profil.route) {
                    ProfilScreen(
                        currentUser = authUiState.currentUser,
                        userRole = userRole,
                        onLogoutClick = {
                            authViewModel.logout()
                        }
                    )
                }
            }
        }
    }
}
