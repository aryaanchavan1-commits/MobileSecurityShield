package com.mobile.securityshield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mobile.securityshield.ui.screens.DashboardScreen
import com.mobile.securityshield.ui.screens.LoginScreen
import com.mobile.securityshield.ui.screens.LicensePlanScreen
import com.mobile.securityshield.ui.screens.ScanScreen
import com.mobile.securityshield.ui.screens.SettingsScreen
import com.mobile.securityshield.ui.theme.MobileSecurityShieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {\n        super.onCreate(savedInstanceState)\n        setContent {\n            MobileSecurityShieldTheme {\n                Box(\n                    modifier = Modifier\n                        .fillMaxSize()\n                        .background(MaterialTheme.colorScheme.background)\n                ) {\n                    AppNavigation()\n                }\n            }\n        }\n    }\n}\n\n@Composable\nfun AppNavigation() {\n    var isLoggedIn by remember { mutableStateOf(false) }\n    var currentScreen by remember { mutableStateOf(\"dashboard\") }\n\n    if (!isLoggedIn) {\n        LoginScreen(\n            onLoginSuccess = { isLoggedIn = true }\n        )\n    } else {\n        when (currentScreen) {\n            \"dashboard\" -> DashboardScreen(onNavigate = { currentScreen = it })\n            \"scan\" -> ScanScreen(onNavigate = { currentScreen = it })\n            \"licenses\" -> LicensePlanScreen(onNavigate = { currentScreen = it })\n            \"settings\" -> SettingsScreen(onNavigate = { currentScreen = it })\n        }\n    }\n}\n"