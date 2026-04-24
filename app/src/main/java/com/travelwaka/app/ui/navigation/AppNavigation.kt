package com.travelwaka.app.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.travelwaka.app.datastore.TokenDataStore
import com.travelwaka.app.ui.screens.onboarding.OnboardingScreen
import com.travelwaka.app.ui.screens.auth.LoginScreen
import com.travelwaka.app.ui.screens.auth.RegisterScreen
import com.travelwaka.app.ui.screens.home.HomeScreen
import com.travelwaka.app.ui.screens.explore.ExploreScreen
import com.travelwaka.app.ui.screens.detail.DetailWisataScreen
import com.travelwaka.app.ui.screens.detail.ReviewScreen
import com.travelwaka.app.ui.screens.bookmark.BookmarkScreen
import com.travelwaka.app.ui.screens.profile.ProfileScreen
import com.travelwaka.app.ui.screens.profile.FormPengajuanScreen
import com.travelwaka.app.ui.screens.profile.NotifikasiScreen
import com.travelwaka.app.ui.screens.pengelola.DaftarWisataSayaScreen
import com.travelwaka.app.ui.screens.pengelola.FormWisataScreen
import com.travelwaka.app.ui.screens.superadmin.DashboardApprovalScreen
import com.travelwaka.app.ui.theme.Primary

object Routes {
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val EXPLORE = "explore"
    const val DETAIL_WISATA = "detail_wisata/{wisataId}"
    const val REVIEW = "review/{wisataId}"
    const val BOOKMARK = "bookmark"
    const val PROFILE = "profile"
    const val FORM_PENGAJUAN = "form_pengajuan"
    const val NOTIFIKASI = "notifikasi"
    const val DAFTAR_WISATA_SAYA = "daftar_wisata_saya"
    const val FORM_WISATA = "form_wisata?wisataId={wisataId}"
    const val DASHBOARD_APPROVAL = "dashboard_approval"
    const val SPLASH = "splash"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val tokenDataStore = remember { TokenDataStore.getInstance(context) }

    val hasSeenOnboarding by tokenDataStore.hasSeenOnboarding.collectAsState(initial = false)
    val token by tokenDataStore.token.collectAsState(initial = null)
    val userRole by tokenDataStore.userRole.collectAsState(initial = "")

    android.util.Log.d("AppNav", "token: $token, role: $userRole, onboarding: $hasSeenOnboarding")

    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        tokenDataStore.token.collect { t ->
            if (startDestination == null) {
                val onboarding = hasSeenOnboarding
                val role = userRole
                startDestination = when {
                    onboarding != true -> Routes.ONBOARDING
                    !t.isNullOrEmpty() -> when (role) {
                        "super_admin" -> Routes.DASHBOARD_APPROVAL
                        else -> Routes.HOME
                    }
                    else -> Routes.LOGIN
                }
            }
            return@collect
        }
    }

    if (startDestination == null) return

    NavHost(
        navController = navController,
        startDestination = startDestination!!
    ) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { role ->
                    when (role) {
                        "super_admin" -> navController.navigate(Routes.DASHBOARD_APPROVAL) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                        else -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onSkip = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                onWisataClick = { id -> navController.navigate("detail_wisata/$id") },
                onSearchClick = { navController.navigate(Routes.EXPLORE) }
            )
        }
        composable(Routes.EXPLORE) {
            ExploreScreen(
                navController = navController,
                onWisataClick = { id -> navController.navigate("detail_wisata/$id") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("detail_wisata/{wisataId}") { backStackEntry ->
            val wisataId = backStackEntry.arguments?.getString("wisataId") ?: ""
            DetailWisataScreen(
                wisataId = wisataId,
                navController = navController,
                onBack = { navController.popBackStack() },
                onWriteReview = { navController.navigate("review/$wisataId") },
                token = token?.ifEmpty { null }
            )
        }
        composable("review/{wisataId}") { backStackEntry ->
            val wisataId = backStackEntry.arguments?.getString("wisataId") ?: ""
            ReviewScreen(
                wisataId = wisataId,
                onBack = { navController.popBackStack() },
                onSubmit = { navController.popBackStack() }
            )
        }
        composable(Routes.BOOKMARK) {
            BookmarkScreen(
                navController = navController,
                onWisataClick = { id -> navController.navigate("detail_wisata/$id") },
                token = token?.ifEmpty { null }
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                navController = navController,
                onAjukanPengelola = { navController.navigate(Routes.FORM_PENGAJUAN) },
                onKelolWisata = { navController.navigate(Routes.DAFTAR_WISATA_SAYA) },
                onNotifikasi = { navController.navigate(Routes.NOTIFIKASI) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.FORM_PENGAJUAN) {
            FormPengajuanScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { navController.popBackStack() }
            )
        }
        composable(Routes.NOTIFIKASI) {
            NotifikasiScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.DAFTAR_WISATA_SAYA) {
            DaftarWisataSayaScreen(
                navController = navController,
                onBack = { navController.popBackStack() },
                onTambahWisata = { navController.navigate("form_wisata?wisataId=") },
                onEditWisata = { id -> navController.navigate("form_wisata?wisataId=$id") }
            )
        }
        composable("form_wisata?wisataId={wisataId}") { backStackEntry ->
            val wisataId = backStackEntry.arguments?.getString("wisataId") ?: ""
            FormWisataScreen(
                wisataId = wisataId,
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Routes.DASHBOARD_APPROVAL) {
            DashboardApprovalScreen(
                navController = navController
            )
        }
    }
}